package com.fh.fdp.rule.oca.data.deal.process;

import java.io.ObjectInputFilter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fh.fdp.rule.oca.data.conf.UserIdAdCache;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import org.apache.commons.lang3.StringUtils;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.conf.GeneralData;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;

import cn.hutool.crypto.digest.MD5;

public class StoreDataProcess extends RuleBaseCommand {
    private ObjectInputFilter.Config config = null;

    private JedisUtil jedis = null;

    @Override
    public void execute(Message msg) throws Exception {
        // 空包处理
        if (!(msg.getData() instanceof List)) {
            getExecutor().sendToNext(this, msg);
            return;
        }
        logger.info("tableName -->" + msg.getProperty("tableName"));
        List<List<DataField>> outs = checkData((String) msg.getProperty("tableName"), msg.getData());
        long oriCount = ((List<?>) msg.getData()).size();
        if (outs == null || outs.isEmpty()) {
            msg.setData(null); // 设置新的返回数据
            logger.warn("clean data over,and get empty result.");
        } else {
            msg.setData(outs);
            logger.info("clean data over,and get " + outs.size() + " result.ori data size:" + oriCount);
        }
        getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节
    }

    private List<List<DataField>> checkData(String table, List<List<DataField>> inputRows) {
        switch (table) {
            case GeneralData.ADM_BASE_PERSON_OVERSEAS_PERSONINFO: {
                return persionInfo(inputRows);
            }
            case GeneralData.OSS_IM_GROUPINFO_OVERSEAS: {
                return addLocalCode(inputRows);
            }
            case GeneralData.OSS_IM_GROUPMEMBER_OVERSEAS: {
                return ossGM(inputRows);
            }
            case GeneralData.OSS_COMMON_PERSONINFO_OVERSEAS: {
                return addLocalCode(inputRows);
            }
            case GeneralData.ADM_RELE_PERSON_PERSON_OVERSEAS_RELATION_DETAIL: {
                return persionRelationDetail(inputRows);
            }
            case GeneralData.ADM_RELE_PERSON_PERSON_OVERSEAS_RELATION_TOTAL: {
                return persionRelationTotal(inputRows);
            }
            case GeneralData.ADM_RELE_PERSON_PLACE_OVERSEAS_ADDRESS_DETAIL: {
                return persionPlaceDetail(inputRows);
            }
            case GeneralData.ADM_RELE_PERSON_PLACE_OVERSEAS_ADDRESS_TOTAL: {
                return persionPlaceTotal(inputRows);
            }
            case GeneralData.ADM_BASE_ORG_OVERSEAS_GPMEMBERS: {
                return groupMembers(inputRows);
            }
            case GeneralData.ADM_RELE_GROUP_PLACE_OVERSEAS_ADDRESS_TOTAL: {
                return groupPlaceTotal(inputRows);
            }
            case GeneralData.ADM_BASE_ORG_OVERSEAS_GROUPINFO: {
                return groupInfo(inputRows);
            }
            case GeneralData.ADM_RELE_GROUP_PLACE_OVERSEAS_ADDRESS_DETAIL: {
                return groupPlaceDetail(inputRows);
            }
            default: {
                logger.error("no identify table name ,please check.");
            }
        }
        return null;
    }

    private List<List<DataField>> persionInfo(List<List<DataField>> inputRows) {
        Set<String> idCache = new HashSet<>();
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();
        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });
        fieldCacheMap.forEach(d -> {
            if (StringUtils.isNotBlank(d.get("UserSign"))) {
                String sign = d.get("UserSign");
                if (idCache.contains(sign)) {
                    logger.debug("repeat user.ignore:" + sign);
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
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();

        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });

        fieldCacheMap.forEach(d -> {
            d.put("CollectPlace", config.getLocalCode());
            List<DataField> out = new ArrayList<>();
            d.forEach((k, v) -> out.add(new DataField(k, v)));
            outs.add(out);
        });
        return outs;
    }

    private List<List<DataField>> ossGM(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();

        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });

        fieldCacheMap.forEach(d -> {
            d.put("CollectPlace", config.getLocalCode());
            if (StringUtils.isNotBlank(d.get("UserType"))) {
                d.put("is_delete", "" + ("3".equals(d.get("UserType")) ? 1 : 0));
                d.put("is_robot", "" + ("2".equals(d.get("UserType")) ? 1 : 0));
            }
            List<DataField> out = new ArrayList<>();
            d.forEach((k, v) -> out.add(new DataField(k, v)));
            outs.add(out);
        });
        return outs;
    }

    private List<List<DataField>> persionRelationDetail(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();

        Set<String> idCache = new HashSet<>();
        List<Integer> noInfoCount = new ArrayList<>();
        noInfoCount.add(0);
        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });
        fieldCacheMap.forEach(d -> {
            /**
             * 境内
             */
            if ("0".equals(d.get("DomainType"))) {
                if (StringUtils.isBlank(d.get("DataSource"))) {
                    d.put("DataSource", "6");
                }
                d.put("DomainType", "0");
                String cache = "";
                if (StringUtils.isNotBlank(d.get("UserId"))) {
                    cache = jedis.get(d.get("UserId"), config.getAtvtCache());
                }
                if (StringUtils.isBlank(cache) && StringUtils.isNotBlank(d.get("UserSign"))) {
                    cache = jedis.get(d.get("UserSign"), config.getAtvtCache());
                }
                if (StringUtils.isNotBlank(cache)) {
                    UserIdAdCache userIdAdCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
                    if (userIdAdCache != null) {
//                        d.put("RelationType", userIdAdCache.getAuthType());  // TODO
                        d.put("RelationType", "D201009");
                        d.put("RelationAccount", userIdAdCache.getAuthAccount());
                        d.put("UserSign",
                                MD5.create()
                                        .digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t" + d.get("UserId")
                                                + "\t" + d.get("RelationType") + "\t" + d.get("RelationAccount") + "\t"
                                                + d.get("CollectTime") + "\t" + d.get("LinkAddressCode") + "\t"
                                                + d.get("DataSource")));
                        if (!idCache.contains(d.get("UserSign"))) {
                            idCache.add(d.get("UserSign"));
                            List<DataField> out = new ArrayList<>();
                            d.forEach((k, v) -> out.add(new DataField(k, v)));
                            outs.add(out);
                        }
                    }
                }


            } else {
                if (StringUtils.isBlank(d.get("MobilePhone")) && StringUtils.isBlank(d.get("Email"))) {
                    noInfoCount.set(0, noInfoCount.get(0) + 1);
                    return;
                }
                if (StringUtils.isBlank(d.get("DataSource"))) {
                    d.put("DataSource", "6");
                }

                if (StringUtils.isBlank(d.get("SourceType"))) {
                    if (StringUtils.isNotBlank(d.get("MobilePhone"))) {
                        d.put("RelationType", "D201005");
                        d.put("RelationAccount", d.get("MobilePhone"));
                        d.put("UserSign",
                                MD5.create()
                                        .digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t" + d.get("UserId")
                                                + "\t" + d.get("RelationType") + "\t" + d.get("RelationAccount") + "\t"
                                                + d.get("CollectTime") + "\t" + d.get("LinkAddressCode") + "\t"
                                                + d.get("DataSource")));
                        if (!idCache.contains(d.get("UserSign"))) {
                            idCache.add(d.get("UserSign"));
                            List<DataField> out = new ArrayList<>();
                            d.forEach((k, v) -> out.add(new DataField(k, v)));
                            outs.add(out);
                        }
                    }
                    if (StringUtils.isNotBlank(d.get("Email"))) {
                        d.put("RelationType", "1011000");
                        d.put("RelationAccount", d.get("Email"));
                        d.put("UserSign",
                                MD5.create()
                                        .digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t" + d.get("UserId")
                                                + "\t" + d.get("RelationType") + "\t" + d.get("RelationAccount") + "\t"
                                                + d.get("CollectTime") + "\t" + d.get("LinkAddressCode") + "\t"
                                                + d.get("DataSource")));
                        if (!idCache.contains(d.get("UserSign"))) {
                            idCache.add(d.get("UserSign"));
                            List<DataField> out = new ArrayList<>();
                            d.forEach((k, v) -> out.add(new DataField(k, v)));
                            outs.add(out);
                        }
                    }
                    if (StringUtils.isNotBlank(d.get("Email"))) {
                        String cache = jedis.get(d.get("Email"), config.getAtvtCache());
                        UserIdAdCache emailCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
                        if (emailCache != null) {
                            d.put("RelationType", "D201009");
                            d.put("RelationAccount", emailCache.getAuthAccount());
                            d.put("UserSign",
                                    MD5.create()
                                            .digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t" + d.get("UserId")
                                                    + "\t" + d.get("RelationType") + "\t" + d.get("RelationAccount") + "\t"
                                                    + d.get("CollectTime") + "\t" + d.get("LinkAddressCode") + "\t"
                                                    + d.get("DataSource")));
                            if (!idCache.contains(d.get("UserSign"))) {
                                idCache.add(d.get("UserSign"));
                                List<DataField> out = new ArrayList<>();
                                d.forEach((k, v) -> out.add(new DataField(k, v)));
                                outs.add(out);
                            }
                        }

                    }
                } else {
                    d.put("UserSign", MD5.create().digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t"
                            + d.get("UserId") + "\t" + d.get("RelationType") + "\t" + d.get("RelationAccount") + "\t"
                            + d.get("CollectTime") + "\t" + d.get("LinkAddressCode") + "\t" + d.get("DataSource")));
                    if (!idCache.contains(d.get("UserSign"))) {
                        idCache.add(d.get("UserSign"));
                        List<DataField> out = new ArrayList<>();
                        d.forEach((k, v) -> out.add(new DataField(k, v)));
                        outs.add(out);
                    }
                }
            }
        });

        logger.info("exec over ,get data/repeat/noInfoCount:" + outs.size() + "/" + idCache.size() + "/"
                + noInfoCount.get(0));
        return outs;
    }

    private List<List<DataField>> persionRelationTotal(List<List<DataField>> inputRows) {

        logger.info("persionRelationTotal--->");
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();
        Set<String> idCache = new HashSet<>();
        List<Integer> noInfoCount = new ArrayList<>();
        noInfoCount.add(0);
        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });
        fieldCacheMap.forEach(d -> {
            /**
             * 境内
             */
            if ("0".equals(d.get("DomainType"))) {
                if (StringUtils.isBlank(d.get("DataSource"))) {
                    d.put("DataSource", "6");
                }
                d.put("DomainType", "0");
                String cache = "";
                if (StringUtils.isNotBlank(d.get("UserId"))) {
                    cache = jedis.get(d.get("UserId"), config.getAtvtCache());
                }
                if (StringUtils.isBlank(cache) && StringUtils.isNotBlank(d.get("UserSign"))) {
                    cache = jedis.get(d.get("UserSign"), config.getAtvtCache());
                }
                if (StringUtils.isNotBlank(cache)) {
                    UserIdAdCache userIdAdCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
                    if (userIdAdCache != null) {
//                        d.put("RelationType", userIdAdCache.getAuthType()); // TODO
                        d.put("RelationType", "D201009");
                        d.put("RelationAccount", userIdAdCache.getAuthAccount());
                        d.put("UserSign",
                                MD5.create()
                                        .digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t" + d.get("UserId")
                                                + "\t" + d.get("RelationType") + "\t" + d.get("RelationAccount") + "\t"
                                                + d.get("CollectTime") + "\t" + d.get("LinkAddressCode") + "\t"
                                                + d.get("DataSource")));
                        if (!idCache.contains(d.get("UserSign"))) {
                            idCache.add(d.get("UserSign"));
                            // 缓存中查创建时间和命中次数
                            String data = jedis.get("uu" + d.get("UserSign"), config.getCountCache());
                            if (StringUtils.isNotBlank(data) && data.split("\t", -1).length == 2) {
                                String time = data.split("\t")[0];
                                String count = data.split("\t")[1];
                                d.put("first_time", time);
                                d.put("count", (Long.parseLong(count) + 1) + "");
                            } else {
                                d.put("first_time", "" + Instant.now().getEpochSecond());
                                d.put("count", "" + 1);
                            }
                            // 回填count
                            jedis.add("uu" + d.get("UserSign"), d.get("first_time") + "\t" + d.get("count"),
                                    config.getCountCache());
                            // 生成最终入库数据
                            List<DataField> out = new ArrayList<>();
                            d.forEach((k, v) -> out.add(new DataField(k, v)));
                            outs.add(out);
                        }
                    }
                }


            } else {
                if (StringUtils.isBlank(d.get("MobilePhone")) && StringUtils.isBlank(d.get("Email"))) {
                    noInfoCount.set(0, noInfoCount.get(0) + 1);
                    return;
                }
                if (StringUtils.isBlank(d.get("DataSource"))) {
                    d.put("DataSource", "6");
                }
                if (StringUtils.isBlank(d.get("SourceType"))) {
                    if (StringUtils.isNotBlank(d.get("MobilePhone"))) {
                        d.put("RelationType", "D201005");
                        d.put("RelationAccount", d.get("MobilePhone"));
                        d.put("UserSign", MD5.create().digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t"
                                + d.get("UserId") + "\t" + d.get("RelationType") + "\t" + d.get("RelationAccount")));
                        if (!idCache.contains(d.get("UserSign"))) {
                            idCache.add(d.get("UserSign"));
                            // 缓存中查创建时间和命中次数
                            String data = jedis.get("uu" + d.get("UserSign"), config.getCountCache());
                            if (StringUtils.isNotBlank(data) && data.split("\t", -1).length == 2) {
                                String time = data.split("\t")[0];
                                String count = data.split("\t")[1];
                                d.put("first_time", time);
                                d.put("count", (Long.parseLong(count) + 1) + "");
                            } else {
                                d.put("first_time", "" + Instant.now().getEpochSecond());
                                d.put("count", "" + 1);
                            }
                            // 回填count
                            jedis.add("uu" + d.get("UserSign"), d.get("first_time") + "\t" + d.get("count"),
                                    config.getCountCache());

                            // 生成最终入库数据
                            List<DataField> out = new ArrayList<>();
                            d.forEach((k, v) -> out.add(new DataField(k, v)));
                            outs.add(out);
                        }
                    }
                    if (StringUtils.isNotBlank(d.get("Email"))) {
                        d.put("RelationType", "1011000");
                        d.put("RelationAccount", d.get("Email"));
                        d.put("UserSign", MD5.create().digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t"
                                + d.get("UserId") + "\t" + d.get("RelationType") + "\t" + d.get("RelationAccount")));
                        if (!idCache.contains(d.get("UserSign"))) {
                            idCache.add(d.get("UserSign"));
                            // 缓存中查创建时间和命中次数
                            String data = jedis.get("uu" + d.get("UserSign"), config.getCountCache());
                            if (StringUtils.isNotBlank(data) && data.split("\t", -1).length == 2) {
                                String time = data.split("\t")[0];
                                String count = data.split("\t")[1];
                                d.put("first_time", time);
                                d.put("count", (Long.parseLong(count) + 1) + "");
                            } else {
                                d.put("first_time", "" + Instant.now().getEpochSecond());
                                d.put("count", "" + 1);
                            }
                            // 回填count
                            jedis.add("uu" + d.get(" "), d.get("first_time") + "\t" + d.get("count"),
                                    config.getCountCache());

                            // 生成最终入库数据
                            List<DataField> out = new ArrayList<>();
                            d.forEach((k, v) -> out.add(new DataField(k, v)));
                            outs.add(out);
                        }
                    }

                    if (StringUtils.isNotBlank(d.get("Email"))) {
                        String cache = jedis.get(d.get("Email"), config.getAtvtCache());
                        UserIdAdCache emailCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
                        if (emailCache != null) {
                            d.put("RelationType", "D201009");
                            d.put("RelationAccount", emailCache.getAuthAccount());
                            d.put("UserSign", MD5.create().digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t"
                                    + d.get("UserId") + "\t" + d.get("RelationType") + "\t" + d.get("RelationAccount")));
                            if (!idCache.contains(d.get("UserSign"))) {
                                idCache.add(d.get("UserSign"));
                                // 缓存中查创建时间和命中次数
                                String data = jedis.get("uu" + d.get("UserSign"), config.getCountCache());
                                if (StringUtils.isNotBlank(data) && data.split("\t", -1).length == 2) {
                                    String time = data.split("\t")[0];
                                    String count = data.split("\t")[1];
                                    d.put("first_time", time);
                                    d.put("count", (Long.parseLong(count) + 1) + "");
                                } else {
                                    d.put("first_time", "" + Instant.now().getEpochSecond());
                                    d.put("count", "" + 1);
                                }
                                // 回填count
                                jedis.add("uu" + d.get(" "), d.get("first_time") + "\t" + d.get("count"),
                                        config.getCountCache());

                                // 生成最终入库数据
                                List<DataField> out = new ArrayList<>();
                                d.forEach((k, v) -> out.add(new DataField(k, v)));
                                outs.add(out);

                            }
                        }

                    }
                } else {
                    d.put("UserSign", MD5.create().digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t"
                            + d.get("UserId") + "\t" + d.get("RelationType") + "\t" + d.get("RelationAccount")));
                    if (!idCache.contains(d.get("UserSign"))) {
                        idCache.add(d.get("UserSign"));
                        // 缓存中查创建时间和命中次数
                        String data = jedis.get("uu" + d.get("UserSign"), config.getCountCache());
                        if (StringUtils.isNotBlank(data) && data.split("\t", -1).length == 2) {
                            String time = data.split("\t")[0];
                            String count = data.split("\t")[1];
                            d.put("first_time", time);
                            d.put("count", (Long.parseLong(count) + 1) + "");
                        } else {
                            d.put("first_time", "" + Instant.now().getEpochSecond());
                            d.put("count", "" + 1);
                        }
                        // 回填count
                        jedis.add("uu" + d.get("UserSign"), d.get("first_time") + "\t" + d.get("count"),
                                config.getCountCache());
                        // 生成最终入库数据
                        List<DataField> out = new ArrayList<>();
                        d.forEach((k, v) -> out.add(new DataField(k, v)));
                        outs.add(out);
                    }
                }
            }

        });
        logger.info("exec over ,get data/repeat/noInfoCount:" + outs.size() + "/" + idCache.size() + "/"
                + noInfoCount.get(0));
        return outs;
    }

    private List<List<DataField>> persionPlaceDetail(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();

        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });
        Set<String> idCache = new HashSet<>();
        List<Integer> noInfoCount = new ArrayList<>();
        noInfoCount.add(0);
        fieldCacheMap.forEach(d -> {
            if (StringUtils.isBlank(d.get("LinkAddressCode"))) {
                noInfoCount.set(0, noInfoCount.get(0) + 1);
                return;
            }
            if (StringUtils.isBlank(d.get("DataSource"))) {
                d.put("DataSource", "6");
            }
            String cache = jedis.get(d.get("Email"), config.getAtvtCache());
            UserIdAdCache emailCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
            if (emailCache != null) {
                //d.put("RelationType", );
                d.put("SOURCE_VALUE_S", emailCache.getAuthAccount());
                d.put("DISC_MCODE_S", "3");
//                if ("D201009".equals(emailCache.getAuthType())) {
//                    d.put("DISC_MCODE_S", "3");
//                } else if ("D201005".equals(emailCache.getAuthType())) {
//                    d.put("DISC_MCODE_S", "4");
//                }

            }
            d.put("CollectPlace", config.getLocalCode());
            d.put("UserSign",
                    MD5.create()
                            .digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t" + d.get("UserId") + "\t"
                                    + d.get("LinkAddressCode") + "\t" + d.get("CollectTime") + "\t"
                                    + d.get("CollectPlace") + "\t" + d.get("DataSource")));
            if (!idCache.contains(d.get("UserSign"))) {
                idCache.add(d.get("UserSign"));
                List<DataField> out = new ArrayList<>();
                d.forEach((k, v) -> out.add(new DataField(k, v)));
                outs.add(out);
            }


        });
        logger.info("exec over ,get data/repeat/noInfoCount:" + outs.size() + "/" + idCache.size() + "/"
                + noInfoCount.get(0));
        return outs;
    }

    private List<List<DataField>> persionPlaceTotal(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();
        Set<String> idCache = new HashSet<>();
        List<Integer> noInfoCount = new ArrayList<>();
        noInfoCount.add(0);
        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });

        fieldCacheMap.forEach(d -> {
            if (StringUtils.isBlank(d.get("LinkAddressCode"))) {
                noInfoCount.set(0, noInfoCount.get(0) + 1);
                return;
            }
            if (StringUtils.isBlank(d.get("DataSource"))) {
                d.put("DataSource", "6");
            }

            String cache = jedis.get(d.get("Email"), config.getAtvtCache());
            UserIdAdCache emailCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
            if (emailCache != null) {
                //d.put("RelationType", );
                d.put("SOURCE_VALUE_S", emailCache.getAuthAccount());
                d.put("DISC_MCODE_S", "3");
//                if ("D201009".equals(emailCache.getAuthType())) {
//                    d.put("DISC_MCODE_S", "3");
//                } else if ("D201005".equals(emailCache.getAuthType())) {
//                    d.put("DISC_MCODE_S", "4");
//                }

            }
            d.put("CollectPlace", config.getLocalCode());
            d.put("UserSign", MD5.create().digestHex(d.get("apptype") + "\t" + d.get("UserName") + "\t"
                    + d.get("UserId") + "\t" + d.get("LinkAddressCode")));
            if (!idCache.contains(d.get("UserSign"))) {
                idCache.add(d.get("UserSign"));
                // 缓存中查创建时间和命中次数
                String data = jedis.get("pu" + d.get("UserSign"), config.getCountCache());
                if (StringUtils.isNotBlank(data) && data.split("\t", -1).length == 2) {
                    String time = data.split("\t")[0];
                    String count = data.split("\t")[1];
                    d.put("first_time", time);
                    d.put("count", (Long.parseLong(count) + 1) + "");
                } else {
                    d.put("first_time", "" + Instant.now().getEpochSecond());
                    d.put("count", "" + 1);
                }
                // 回填count
                jedis.add("pu" + d.get("UserSign"), d.get("first_time") + "\t" + d.get("count"),
                        config.getCountCache());

                List<DataField> out = new ArrayList<>();
                d.forEach((k, v) -> out.add(new DataField(k, v)));
                outs.add(out);
            }
        });
        logger.info("exec over ,get data/repeat/noInfoCount:" + outs.size() + "/" + idCache.size() + "/"
                + noInfoCount.get(0));
        return outs;
    }

    private List<List<DataField>> groupMembers(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();

        Set<String> idCache = new HashSet<>();
        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });

        fieldCacheMap.forEach(d -> {
            if (StringUtils.isNotBlank(d.get("GroupID")) && StringUtils.isNotBlank(d.get("UserId"))) {
                String ukey = d.get("GroupID") + "\t" + d.get("UserId");
                if (idCache.contains(ukey)) {
                    logger.warn("repeat groupmembers.ignore");
                    return;
                }
                idCache.add(ukey);
                if (StringUtils.isNotBlank(d.get("UserType"))) {
                    d.put("is_delete", "" + ("3".equals(d.get("UserType")) ? 1 : 0));
                    d.put("is_robot", "" + ("2".equals(d.get("UserType")) ? 1 : 0));
                }
                List<DataField> out = new ArrayList<>();
                d.forEach((k, v) -> out.add(new DataField(k, v)));
                outs.add(out);
            }
        });
        logger.info("exec over ,get data/repeat:" + outs.size() + "/" + idCache.size());
        return outs;
    }

    private List<List<DataField>> groupInfo(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();

        Set<String> idCache = new HashSet<>();
        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });

        fieldCacheMap.forEach(d -> {
            if (StringUtils.isNotBlank(d.get("GroupID"))) {
                if (idCache.contains(d.get("GroupID"))) {
                    logger.warn("repeat GroupID.ignore");
                    return;
                }
                idCache.add(d.get("GroupID"));
                List<DataField> out = new ArrayList<>();
                d.forEach((k, v) -> out.add(new DataField(k, v)));
                outs.add(out);
            }
        });
        logger.info("exec over ,get data/repeat:" + outs.size() + "/" + idCache.size());
        return outs;
    }

    private List<List<DataField>> groupPlaceTotal(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();

        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });
        Set<String> idCache = new HashSet<>();
        fieldCacheMap.forEach(d -> {
            // 缓存中查创建时间和命中次数
            d.put("UserSign", MD5.create().digestHex(d.get("apptype") + "\t" + d.get("GroupName") + "\t"
                    + d.get("GroupID") + "\t" + d.get("LinkAddressCode")));

            String data = jedis.get("pg" + d.get("UserSign"), config.getCountCache());
            if (StringUtils.isNotBlank(data) && data.split("\t", -1).length == 2) {
                String time = data.split("\t")[0];
                String count = data.split("\t")[1];
                d.put("first_time", time);
                d.put("count", (Long.parseLong(count) + 1) + "");
            } else {
                d.put("first_time", "" + Instant.now().getEpochSecond());
                d.put("count", "" + 1);
            }
            // 回填count
            jedis.add("pg" + d.get("UserSign"), d.get("first_time") + "\t" + d.get("count"), config.getCountCache());
            if (!idCache.contains(d.get("UserSign"))) {
                idCache.add(d.get("UserSign"));
                List<DataField> out = new ArrayList<>();
                d.forEach((k, v) -> out.add(new DataField(k, v)));
                outs.add(out);
            }
        });
        logger.info("exec over ,get data/repeat:" + outs.size() + "/" + idCache.size());
        return outs;
    }

    private List<List<DataField>> groupPlaceDetail(List<List<DataField>> inputRows) {
        List<List<DataField>> outs = new ArrayList<>();
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();

        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });
        Set<String> idCache = new HashSet<>();
        fieldCacheMap.forEach(d -> {
            d.put("CollectPlace", config.getLocalCode());
            if (StringUtils.isBlank(d.get("DataSource"))) {
                d.put("DataSource", "6");
            }
            d.put("UserSign",
                    MD5.create()
                            .digestHex(d.get("apptype") + "\t" + d.get("GroupName") + "\t" + d.get("GroupID") + "\t"
                                    + d.get("LinkAddressCode") + "\t" + d.get("CollectTime") + "\t"
                                    + d.get("CollectPlace") + "\t" + d.get("DataSource")));
            // 回填count
            if (!idCache.contains(d.get("UserSign"))) {
                idCache.add(d.get("UserSign"));
                List<DataField> out = new ArrayList<>();
                d.forEach((k, v) -> out.add(new DataField(k, v)));
                outs.add(out);
            }
        });
        logger.info("exec over ,get data/repeat:" + outs.size() + "/" + idCache.size());
        return outs;
    }

    @Override
    public void onInit() {
        config = (Config) getExecutionContext().get("config");
        jedis = new JedisUtil(config.getRedisIp(), config.getRedisPort(), config.getRedisPass(), logger);
    }

    @Override
    public void onStop() {
        if (jedis != null) {
            jedis.close();
        }
    }

}
