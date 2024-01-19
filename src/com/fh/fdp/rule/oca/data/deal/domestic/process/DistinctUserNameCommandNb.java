package com.fh.fdp.rule.oca.data.deal.domestic.process;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Utils;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TW username 去重
 * eamil
 */
public class DistinctUserNameCommandNb extends RuleBaseCommand {


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

        long expandTime = config.getExpandDomestic() * Constant.ONE_DAYS_TIME;

        // 获取多行数据，一行行处理
        List<List<DataField>> inputRows = msg.getData();
        Map<String, String> cache = new HashMap<>();

        List<List<DataField>> outputRowsDistinct = new ArrayList<>(); // 定义新的返回数据
        for (List<DataField> row : inputRows) {
            for (DataField field : row) {
                if (Constant.isUserId(field.getName())) {
                    List<DataField> data = new ArrayList<>(new Gson().fromJson(new Gson().toJson(row), new TypeToken<List<DataField>>() {
                    }.getType()));
                    for (DataField d : data) {
                        if (Constant.isAppType(d.getName())) {
                            String key = d.getValue() + Constant.TASK_KEY + field.getValue();
                            Utils.splicingKey(config, expandTime, cache, outputRowsDistinct, row, key, jedis);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        msg.setData(outputRowsDistinct); // 设置新的返回数据
        getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节
        logger.debug("cost time:{}", (System.currentTimeMillis() - startTime));
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