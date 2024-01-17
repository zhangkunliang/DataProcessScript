package com.fh.fdp.rule.oca.data.deal.domestic.process;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * redis 保存AuthType 和 AuthAccount
 * @author zkl
 */
public class RedisAdCommandForEmail extends RuleBaseCommand {


    private JedisUtil jedis = null;

    @Override
    public void execute(Message msg) throws Exception {
        long startTime = System.currentTimeMillis();
        // 空包处理
        if (msg.getData() == null) {
            getExecutor().sendToNext(this, msg);
            return;
        }
        Config config = (Config) getExecutionContext().get("config");

        long expandTime = 30 * Constant.ONE_DAYS_TIME;

        // 获取多行数据，一行行处理
        List<List<DataField>> inputRows = msg.getData();


        inputRows.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> {
                if (d.getName().equalsIgnoreCase(Constant.USER_NAME)) {
                    cache.put(d.getName().toLowerCase(Locale.ROOT), d.getValue());
                }
                if (d.getName().equalsIgnoreCase(Constant.AUTH_TYPE)) {
                    cache.put(Constant.AUTH_TYPE, d.getValue());
                }
                if (d.getName().equalsIgnoreCase(Constant.AUTH_ACCOUNT)) {
                    cache.put(Constant.AUTH_ACCOUNT, d.getValue());
                }
            });
            logger.debug("key-->{}",cache.get(Constant.USER_NAME));
            logger.debug(GsonUtil.toStr(cache));
            jedis.add(cache.get(Constant.USER_NAME), GsonUtil.toStr(cache), expandTime, config.getAtvtCache());
        });
        msg.setData(inputRows); // 设置新的返回数据
        getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节
        logger.debug("cost time:{}",(System.currentTimeMillis() - startTime));
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