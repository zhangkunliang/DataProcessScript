package com.fh.fdp.rule.oca.data.deal.process;

import cn.hutool.crypto.digest.MD5;
import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.conf.GeneralData;
import com.fh.fdp.rule.oca.data.conf.UserIdAdCache;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.CluesTask;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.CollectField;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.*;

public class StoreDataProcess extends RuleBaseCommand {
    private Config config = null;

    private JedisUtil jedis = null;

    @Override
    public void execute(Message msg) throws Exception {
        // 空包处理
        if (!(msg.getData() instanceof List)) {
            getExecutor().sendToNext(this, msg);
            return;
        }
        logger.info("tableName -->{}", msg.getProperty("tableName"));
        List<List<DataField>> outs = checkData((String) msg.getProperty("tableName"), msg.getData());
        long oriCount = ((List<?>) msg.getData()).size();
        if (outs.isEmpty()) {
            msg.setData(null); // 设置新的返回数据
            logger.warn("clean data over,and get empty result.");
        } else {
            msg.setData(outs);
            logger.info("clean data over,and get {} result.ori data size:{}", outs.size(), oriCount);
        }
        getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节
    }

    private List<List<DataField>> checkData(String table, List<List<DataField>> inputRows) {
        if (table.equals(GeneralData.ADM_BASE_PERSON_OVERSEAS_PERSONINFO)) {
            return personInfo(inputRows);
        }
        if (table.equals(GeneralData.OSS_IM_GROUPINFO_OVERSEAS) || table.equals(GeneralData.OSS_COMMON_PERSONINFO_OVERSEAS)) {
            return addLocalCode(inputRows);
        }

        if (table.equals(GeneralData.OSS_IM_GROUPMEMBER_OVERSEAS)) {
            return ossGM(inputRows);
        }
        if (table.equals(GeneralData.ADM_RELE_PERSON_PERSON_OVERSEAS_RELATION_DETAIL)) {
            return personRelationDetail(inputRows);
        }
        if (table.equals(GeneralData.ADM_RELE_PERSON_PERSON_OVERSEAS_RELATION_TOTAL)) {
            return personRelationTotal(inputRows);
        }
        if (table.equals(GeneralData.ADM_RELE_PERSON_PLACE_OVERSEAS_ADDRESS_DETAIL)) {
            return personPlaceDetail(inputRows);
        }
        if (table.equals(GeneralData.ADM_RELE_PERSON_PLACE_OVERSEAS_ADDRESS_TOTAL)) {
            return personPlaceTotal(inputRows);
        }
        if (table.equals(GeneralData.ADM_BASE_ORG_OVERSEAS_GPMEMBERS)) {
            return groupMembers(inputRows);
        }
        if (table.equals(GeneralData.ADM_RELE_GROUP_PLACE_OVERSEAS_ADDRESS_TOTAL)) {
            return groupPlaceTotal(inputRows);
        }
        if (table.equals(GeneralData.ADM_BASE_ORG_OVERSEAS_GROUPINFO)) {
            return groupInfo(inputRows);
        }
        if (table.equals(GeneralData.ADM_RELE_GROUP_PLACE_OVERSEAS_ADDRESS_DETAIL)) {
            return groupPlaceDetail(inputRows);
        }
        logger.error("no identify table name ,please check.");
        return new ArrayList<>();
    }

    public List<Map<String, String>> transferListToMap(List<List<DataField>> inputRows) {
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();
        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });
        return fieldCacheMap;
    }

    private List<List<DataField>> personInfo(List<List<DataField>> inputRows) {
        Set<String> idCache = new HashSet<>();
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = transferListToMap(inputRows);
        fieldCacheMap.forEach(d -> {
            if (StringUtils.isNotBlank(d.get(CollectField.USER_SIGN))) {
                String sign = d.get(CollectField.USER_SIGN);
                if (idCache.contains(sign)) {
                    logger.debug("repeat user.ignore{}", sign);
                } else {
                    idCache.add(sign);
                    List<DataField> out = new ArrayList<>();
                    d.forEach((k, v) -> out.add(new DataField(k, v)));
                    outs.add(out);
                }
            }
        });
        return outs;
    }

    private List<List<DataField>> addLocalCode(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = transferListToMap(inputRows);
        fieldCacheMap.forEach(d -> {
            d.put(CollectField.COLLECT_PLACE, config.getLocalCode());
            List<DataField> out = new ArrayList<>();
            d.forEach((k, v) -> out.add(new DataField(k, v)));
            outs.add(out);
        });
        return outs;
    }

    private List<List<DataField>> ossGM(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = transferListToMap(inputRows);
        fieldCacheMap.forEach(d -> {
            d.put(CollectField.COLLECT_PLACE, config.getLocalCode());
            addUserType(outs, d);
        });
        return outs;
    }

    private List<List<DataField>> personRelationDetail(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = transferListToMap(inputRows);
        Set<String> idCache = new HashSet<>();
        List<Integer> noInfoCount = new ArrayList<>();
        noInfoCount.add(0);
        fieldCacheMap.forEach(d -> {
            // 境内
            if ("0".equals(d.get(CollectField.DOMAIN_TYPE))) {
                String cache = "";
                if (StringUtils.isBlank(d.get(CollectField.DATA_SOURCE))) {
                    d.put(CollectField.DATA_SOURCE, "6");
                }
                d.put(CollectField.DOMAIN_TYPE, "0");
                if (StringUtils.isNotBlank(getCache(d, cache))) {
                    userIdCacheAndAdd(outs, idCache, d, cache);
                }


            } else {
                if (setMobileAndEmail(noInfoCount, d)) return;

                if (StringUtils.isBlank(d.get(CollectField.SOURCE_TYPE))) {
                    if (StringUtils.isNotBlank(d.get(CollectField.MOBILE_PHONE))) {
                        d.put(CollectField.RELATION_TYPE, "D201005");
                        d.put(CollectField.RELATION_ACCOUNT, d.get(CollectField.MOBILE_PHONE));
                        fieldPutUserSignTypeName(outs, idCache, d);
                    }
                    if (StringUtils.isNotBlank(d.get(CollectField.EMAIL))) {
                        d.put(CollectField.RELATION_TYPE, "1011000");
                        d.put(CollectField.RELATION_ACCOUNT, d.get(CollectField.EMAIL));
                        fieldPutUserSignTypeName(outs, idCache, d);
                    }
                    if (StringUtils.isNotBlank(d.get(CollectField.EMAIL))) {
                        String cache = jedis.get(d.get(CollectField.EMAIL), config.getAtvtCache());
                        userIdCacheAndAdd(outs, idCache, d, cache);

                    }
                } else {
                    fieldPutUserSignTypeName(outs, idCache, d);
                }
            }
        });

        logger.info("exec over ,get data:{}/repeat:{}/noInfoCount{}:", outs.size(), idCache.size(), noInfoCount.get(0));

        return outs;
    }

    private void fieldPutUserSignTypeName(List<List<DataField>> outs, Set<String> idCache, Map<String, String> d) {
        fieldPutUserSignAppUserName(d);
        addUserSign(outs, idCache, d);
    }

    private void fieldPutUserSignAppUserName(Map<String, String> d) {
        d.put(CollectField.USER_SIGN, MD5.create().digestHex(d.get(CluesTask.APPTYPE) + "\t" + d.get(CollectField.USER_NAME) + "\t"
                + d.get(CollectField.USER_ID) + "\t" + d.get(CollectField.RELATION_TYPE) + "\t" + d.get(CollectField.RELATION_ACCOUNT) + "\t"
                + d.get(CollectField.COLLECT_TIME) + "\t" + d.get(CollectField.LINK_ADDRESS_CODE) + "\t" + d.get(CollectField.DATA_SOURCE)));
    }

    private void userIdCacheAndAdd(List<List<DataField>> outs, Set<String> idCache, Map<String, String> d, String cache) {
        UserIdAdCache userIdAdCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
        if (userIdAdCache != null) {
            dataFieldPutRelation(d, userIdAdCache);
            addUserSign(outs, idCache, d);
        }
    }

    private String getCache(Map<String, String> d, String cache) {
        if (StringUtils.isNotBlank(d.get(CollectField.USER_ID))) {
            cache = jedis.get(d.get(CollectField.USER_ID), config.getAtvtCache());
        }
        if (StringUtils.isBlank(cache) && StringUtils.isNotBlank(d.get(CollectField.USER_SIGN))) {
            cache = jedis.get(d.get(CollectField.USER_SIGN), config.getAtvtCache());
        }
        if (StringUtils.isBlank(cache) && StringUtils.isNotBlank(d.get(CollectField.USER_TOKEN))) {
            cache = jedis.get(d.get(CollectField.USER_TOKEN), config.getAtvtCache());
        }
        return cache;
    }

    private List<List<DataField>> personRelationTotal(List<List<DataField>> inputRows) {

        logger.debug("persionRelationTotal--->");
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = transferListToMap(inputRows);
        Set<String> idCache = new HashSet<>();
        List<Integer> noInfoCount = new ArrayList<>();
        noInfoCount.add(0);
        fieldCacheMap.forEach(d -> {
            // 境内
            if ("0".equals(d.get(CollectField.DOMAIN_TYPE))) {
                if (StringUtils.isBlank(d.get(CollectField.DATA_SOURCE))) {
                    d.put(CollectField.DATA_SOURCE, "6");
                }
                d.put(CollectField.DOMAIN_TYPE, "0");
                String cache = "";
                cache = getCache(d, cache);
                if (StringUtils.isNotBlank(cache)) {
                    UserIdAdCache userIdAdCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
                    if (userIdAdCache != null) {
                        dataFieldPutRelation(d, userIdAdCache);
                        idCacheAddUserSign(outs, idCache, d);
                    }
                }

            } else {
                if (setMobileAndEmail(noInfoCount, d)) return;
                if (StringUtils.isBlank(d.get(CollectField.SOURCE_TYPE))) {
                    if (StringUtils.isNotBlank(d.get(CollectField.MOBILE_PHONE))) {
                        d.put(CollectField.RELATION_TYPE, "D201005");
                        d.put(CollectField.RELATION_ACCOUNT, d.get(CollectField.MOBILE_PHONE));
                        addCollectFieldAndCache(outs, idCache, d);
                    }
                    if (StringUtils.isNotBlank(d.get(CollectField.EMAIL))) {
                        d.put(CollectField.RELATION_TYPE, "1011000");
                        d.put(CollectField.RELATION_ACCOUNT, d.get(CollectField.EMAIL));
                        addCollectFieldUserSignJedisAdd(outs, idCache, d);
                    }

                    if (StringUtils.isNotBlank(d.get(CollectField.EMAIL))) {
                        String cache = jedis.get(d.get(CollectField.EMAIL), config.getAtvtCache());
                        UserIdAdCache emailCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
                        if (emailCache != null) {
                            d.put(CollectField.RELATION_TYPE, CollectField.RELATION_CODE);
                            d.put(CollectField.RELATION_ACCOUNT, emailCache.getAuthAccount());
                            addCollectFieldUserSignJedisAdd(outs, idCache, d);
                        }

                    }
                } else {
                    addCollectFieldAndCache(outs, idCache, d);
                }
            }

        });
        logger.info("exec over ,get data:{}/repeat:{}/ noInfoCount :{}", outs.size(), idCache.size(), noInfoCount.get(0));

        return outs;
    }

    private void dataFieldPutRelation(Map<String, String> d, UserIdAdCache userIdAdCache) {
        d.put(CollectField.RELATION_TYPE, CollectField.RELATION_CODE);
        d.put(CollectField.RELATION_ACCOUNT, userIdAdCache.getAuthAccount());
        fieldPutUserSignAppUserName(d);
    }

    private void idCacheAddUserSign(List<List<DataField>> outs, Set<String> idCache, Map<String, String> d) {
        if (!idCache.contains(d.get(CollectField.USER_SIGN))) {
            idCache.add(d.get(CollectField.USER_SIGN));
            // 缓存中查创建时间和命中次数
            String data = jedis.get("uu" + d.get(CollectField.USER_SIGN), config.getCountCache());
            timeCountSplit(d, data);
            // 回填count
            jedis.add("uu" + d.get(CollectField.USER_SIGN), d.get(CollectField.FIRST_TIME) + "\t" + d.get(CollectField.COUNT),
                    config.getCountCache());
            // 生成最终入库数据
            List<DataField> out = new ArrayList<>();

            d.forEach((k, v) -> out.add(new DataField(k, v)));
            outs.add(out);

        }
    }

    private boolean setMobileAndEmail(List<Integer> noInfoCount, Map<String, String> d) {
        if (StringUtils.isBlank(d.get(CollectField.MOBILE_PHONE)) && StringUtils.isBlank(d.get(CollectField.EMAIL))) {
            noInfoCount.set(0, noInfoCount.get(0) + 1);
            return true;
        }
        if (StringUtils.isBlank(d.get(CollectField.DATA_SOURCE))) {
            d.put(CollectField.DATA_SOURCE, "6");
        }
        return false;
    }

    private void addCollectFieldUserSignJedisAdd(List<List<DataField>> outs, Set<String> idCache, Map<String, String> d) {
        d.put(CollectField.USER_SIGN, MD5.create().digestHex(d.get(CluesTask.APPTYPE) + "\t" + d.get(CollectField.USER_NAME) + "\t"
                + d.get(CollectField.USER_ID) + "\t" + d.get(CollectField.RELATION_TYPE) + "\t" + d.get(CollectField.RELATION_ACCOUNT)));
        if (!idCache.contains(d.get(CollectField.USER_SIGN))) {
            idCache.add(d.get(CollectField.USER_SIGN));
            // 缓存中查创建时间和命中次数
            String data = jedis.get("uu" + d.get(CollectField.USER_SIGN), config.getCountCache());
            timeCountSplit(d, data);
            // 回填count
            jedis.add("uu" + d.get(" "), d.get(CollectField.FIRST_TIME) + "\t" + d.get(CollectField.COUNT),
                    config.getCountCache());

            // 生成最终入库数据
            List<DataField> out = new ArrayList<>();
            d.forEach((k, v) -> out.add(new DataField(k, v)));
            outs.add(out);
        }
    }

    private void addCollectFieldAndCache(List<List<DataField>> outs, Set<String> idCache, Map<String, String> d) {
        d.put(CollectField.USER_SIGN, MD5.create().digestHex(d.get(CluesTask.APPTYPE) + "\t" + d.get(CollectField.USER_NAME) + "\t"
                + d.get(CollectField.USER_ID) + "\t" + d.get(CollectField.RELATION_TYPE) + "\t" + d.get(CollectField.RELATION_ACCOUNT)));
        idCacheAddUserSign(outs, idCache, d);
    }

    private List<List<DataField>> personPlaceDetail(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = transferListToMap(inputRows);
        Set<String> idCache = new HashSet<>();
        List<Integer> noInfoCount = new ArrayList<>();
        noInfoCount.add(0);
        fieldCacheMap.forEach(d -> {
            if (noInfoCountAndPlaceCache(noInfoCount, d)) return;
            d.put(CollectField.USER_SIGN,
                    MD5.create()
                            .digestHex(d.get(CluesTask.APPTYPE) + "\t" + d.get(CollectField.USER_NAME) + "\t" + d.get(CollectField.USER_ID) + "\t"
                                    + d.get(CollectField.LINK_ADDRESS_CODE) + "\t" + d.get(CollectField.COLLECT_TIME) + "\t"
                                    + d.get(CollectField.COLLECT_PLACE) + "\t" + d.get(CollectField.DATA_SOURCE)));
            addUserSign(outs, idCache, d);


        });
        logger.info("exec over ,get data:{}/repeat:{}/noInfoCount:{}", outs.size(), idCache.size(), noInfoCount.get(0));

        return outs;
    }

    private List<List<DataField>> personPlaceTotal(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = transferListToMap(inputRows);
        Set<String> idCache = new HashSet<>();
        List<Integer> noInfoCount = new ArrayList<>();
        noInfoCount.add(0);

        fieldCacheMap.forEach(d -> {
            if (noInfoCountAndPlaceCache(noInfoCount, d)) return;
            d.put(CollectField.USER_SIGN, MD5.create().digestHex(d.get(CluesTask.APPTYPE) + "\t" + d.get(CollectField.USER_NAME) + "\t"
                    + d.get(CollectField.USER_ID) + "\t" + d.get(CollectField.LINK_ADDRESS_CODE)));
            if (!idCache.contains(d.get(CollectField.USER_SIGN))) {
                idCache.add(d.get(CollectField.USER_SIGN));
                // 缓存中查创建时间和命中次数
                String data = jedis.get("pu" + d.get(CollectField.USER_SIGN), config.getCountCache());
                timeCountSplit(d, data);
                // 回填count
                jedis.add("pu" + d.get(CollectField.USER_SIGN), d.get(CollectField.FIRST_TIME) + "\t" + d.get(CollectField.COUNT),
                        config.getCountCache());

                List<DataField> out = new ArrayList<>();
                d.forEach((k, v) -> out.add(new DataField(k, v)));
                outs.add(out);
            }
        });
        logger.info("exec over ,get data:{}/repeat:{}/noInfoCount:{}", outs.size(), idCache.size(), noInfoCount.get(0));
        return outs;
    }

    private boolean noInfoCountAndPlaceCache(List<Integer> noInfoCount, Map<String, String> d) {
        if (StringUtils.isBlank(d.get(CollectField.LINK_ADDRESS_CODE))) {
            noInfoCount.set(0, noInfoCount.get(0) + 1);
            return true;
        }
        if (StringUtils.isBlank(d.get(CollectField.DATA_SOURCE))) {
            d.put(CollectField.DATA_SOURCE, "6");
        }

        String cache = jedis.get(d.get(CollectField.EMAIL), config.getAtvtCache());
        UserIdAdCache emailCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
        if (emailCache != null) {
            d.put("SOURCE_VALUE_S", emailCache.getAuthAccount());
            d.put("DISC_MCODE_S", "3");
        }
        d.put(CollectField.COLLECT_PLACE, config.getLocalCode());
        return false;
    }

    private List<List<DataField>> groupMembers(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = transferListToMap(inputRows);
        Set<String> idCache = new HashSet<>();
        fieldCacheMap.forEach(d -> {
            if (StringUtils.isNotBlank(d.get(CollectField.GROUP_ID)) && StringUtils.isNotBlank(d.get(CollectField.USER_ID))) {
                String ukey = d.get(CollectField.GROUP_ID) + "\t" + d.get(CollectField.USER_ID);
                if (idCache.contains(ukey)) {
                    logger.warn("repeat group_members.ignore");
                    return;
                }
                idCache.add(ukey);
                addUserType(outs, d);
            }
        });
        logger.info("exec over,get data/repeat:{}/{}", outs.size(), idCache.size());
        return outs;
    }

    private void addUserType(List<List<DataField>> outs, Map<String, String> d) {
        if (StringUtils.isNotBlank(d.get(CollectField.USER_TYPE))) {
            d.put("is_delete", "" + ("3".equals(d.get(CollectField.USER_TYPE)) ? 1 : 0));
            d.put("is_robot", "" + ("2".equals(d.get(CollectField.USER_TYPE)) ? 1 : 0));
        }
        List<DataField> out = new ArrayList<>();
        d.forEach((k, v) -> out.add(new DataField(k, v)));
        outs.add(out);
    }

    private List<List<DataField>> groupInfo(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = transferListToMap(inputRows);
        Set<String> idCache = new HashSet<>();
        fieldCacheMap.forEach(d -> {
            if (StringUtils.isNotBlank(d.get(CollectField.GROUP_ID))) {
                if (idCache.contains(d.get(CollectField.GROUP_ID))) {
                    logger.warn("repeat GroupID.ignore");
                    return;
                }
                idCache.add(d.get(CollectField.GROUP_ID));
                List<DataField> out = new ArrayList<>();
                d.forEach((k, v) -> out.add(new DataField(k, v)));
                outs.add(out);
            }
        });
        logger.info("exec over,get data/repeat:{}/{}", outs.size(), idCache.size());
        return outs;
    }

    private List<List<DataField>> groupPlaceTotal(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = transferListToMap(inputRows);
        Set<String> idCache = new HashSet<>();
        fieldCacheMap.forEach(d -> {
            // 缓存中查创建时间和命中次数
            d.put(CollectField.USER_SIGN, MD5.create().digestHex(d.get(CluesTask.APPTYPE) + "\t" + d.get(CollectField.GROUP_NAME) + "\t"
                    + d.get(CollectField.GROUP_ID) + "\t" + d.get(CollectField.LINK_ADDRESS_CODE)));

            String data = jedis.get("pg" + d.get(CollectField.USER_SIGN), config.getCountCache());
            timeCountSplit(d, data);
            // 回填count
            jedis.add("pg" + d.get(CollectField.USER_SIGN), d.get(CollectField.FIRST_TIME) + "\t" + d.get(CollectField.COUNT), config.getCountCache());
            addUserSign(outs, idCache, d);
        });
        logger.info("exec over ,get data/repeat:{}/{}", outs.size(), idCache.size());
        return outs;
    }

    private void timeCountSplit(Map<String, String> d, String data) {
        if (StringUtils.isNotBlank(data) && data.split("\t", -1).length == 2) {
            String time = data.split("\t")[0];
            String count = data.split("\t")[1];
            d.put(CollectField.FIRST_TIME, time);
            d.put(CollectField.COUNT, (Long.parseLong(count) + 1) + "");
        } else {
            d.put(CollectField.FIRST_TIME, "" + Instant.now().getEpochSecond());
            d.put(CollectField.COUNT, "" + 1);
        }
    }

    private List<List<DataField>> groupPlaceDetail(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = transferListToMap(inputRows);
        Set<String> idCache = new HashSet<>();
        fieldCacheMap.forEach(d -> {
            d.put(CollectField.COLLECT_PLACE, config.getLocalCode());
            if (StringUtils.isBlank(d.get(CollectField.DATA_SOURCE))) {
                d.put(CollectField.DATA_SOURCE, "6");
            }
            d.put(CollectField.USER_SIGN,
                    MD5.create()
                            .digestHex(d.get(CluesTask.APPTYPE) + "\t" + d.get(CollectField.GROUP_NAME) + "\t" + d.get(CollectField.GROUP_ID) + "\t"
                                    + d.get(CollectField.LINK_ADDRESS_CODE) + "\t" + d.get(CollectField.COLLECT_TIME) + "\t"
                                    + d.get(CollectField.COLLECT_PLACE) + "\t" + d.get(CollectField.DATA_SOURCE)));
            // 回填count
            addUserSign(outs, idCache, d);
        });
        logger.info("exec over ,get data/repeat:{}/{}", outs.size(), idCache.size());
        return outs;
    }

    private void addUserSign(List<List<DataField>> outs, Set<String> idCache, Map<String, String> d) {
        if (!idCache.contains(d.get(CollectField.USER_SIGN))) {
            idCache.add(d.get(CollectField.USER_SIGN));
            List<DataField> out = new ArrayList<>();
            d.forEach((k, v) -> out.add(new DataField(k, v)));
            outs.add(out);
        }
    }

    @Override
    public void onInit() {
        config = (Config) getExecutionContext().get("config");
        jedis = new JedisUtil(config.getRedisIp(), config.getRedisPort(), config.getRedisPass());
    }

    @Override
    public void onStop() {
        if (jedis != null) {
            jedis.close();
        }
    }

}
