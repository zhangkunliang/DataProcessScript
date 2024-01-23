package com.fh.fdp.rule.oca.data.deal.process;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.conf.CrawlerStatus;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.CluesTask;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


public class RespProcess extends RuleBaseCommand {

    private JedisUtil jedis = null;

    @Override
    public void execute(Message msg) throws Exception {

        // 空包处理
        if (msg.getData() == null) {
            getExecutor().sendToNext(this, msg);
            return;
        }
        Config config = (Config) getExecutionContext().get("config");

        // 获取多行数据，一行行处理
        List<List<DataField>> inputRows = msg.getData();
        List<Map<String, String>> fieldCacheMap = new ArrayList<>();
        // list-list 转list-map
        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> cache.put(d.getName(), d.getValue()));
            fieldCacheMap.add(cache);
        });
        List<List<DataField>> output = new ArrayList<>();
        Set<String> idCache = new HashSet<>();
        fieldCacheMap.forEach(data -> {
            if (idCache.contains(data.get(CluesTask.MDID))) {
                return;
            }
            idCache.add(data.get(CluesTask.MDID));
            if ("0".equals(data.get(CluesTask.MDID)) || "1".equals(data.get(CluesTask.MDID))) {
                CrawlerStatus status = new CrawlerStatus();
                status.setMdid(data.get(CluesTask.MDID));
                status.setApptype("0");
                status.setSource("0");
                status.setRuleType(0);
                status.setRuleValue("0");
                status.setCreatetime(0L);
                setCollectStatus(output, data, status);
            } else {
                String res = jedis.get(data.get(CluesTask.MDID), config.getTaskCache());
                if (StringUtils.isBlank(res)) {
                    logger.error(" mdid cache not found.ignore... {}", data.get(CluesTask.MDID));
                    return;
                }
                CrawlerStatus status = GsonUtil.fromJson(res, CrawlerStatus.class);

                status.setMdid(data.get(CluesTask.MDID));
                setCollectStatus(output, data, status);
            }
        });
        msg.setData(output); // 设置新的返回数据
        getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节
    }

    private void setCollectStatus(List<List<DataField>> output, Map<String, String> data, CrawlerStatus status) {
        status.setCollectStatus(Integer.parseInt(data.get(CluesTask.COLLECT_STATUS)));
        status.setUpdatetime(Long.parseLong(data.get(CluesTask.UPDATE_TIME)));
        status.setCollectCount(0);
        List<DataField> out = new ArrayList<>();
        new Gson().toJsonTree(status).getAsJsonObject().entrySet()
                .forEach(e -> out.add(new DataField(e.getKey(), e.getValue().getAsString())));
        output.add(out);
    }

    @Override
    public void onInit() {
        Config conf = (Config) getExecutionContext().get("config");
        jedis = new JedisUtil(conf.getRedisIp(), conf.getRedisPort(), conf.getRedisPass());
    }

    @Override
    public void onStop() {
        if (jedis != null) {
            jedis.close();
        }
    }

}
