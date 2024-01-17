package com.fh.fdp.rule.oca.data.deal.domestic.process;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Utils;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 境内资产去重
 *
 * @author zkl 任务表
 */
public class DistinctCommandVtEmail extends RuleBaseCommand {


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
                // 判断是否是两张邮箱表中的其中一张
                if (Constant.OVERSEAS_USER_ACC.equalsIgnoreCase(field.getName())) {
                    if (!jedis.exist(field.getValue(), config.getMobileCacheDB())) {
                        break;
                    }
                    // 拼接字符串作为唯一标识
                    String key = Constant.TUITE_TYPE + field.getValue();
                    Utils.splicingKey(config, expandTime, cache, outputRowsDistinct, row, key, jedis);
                    break;
                }
            }
        }
        msg.setData(outputRowsDistinct); // 设置新的返回数据
        getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节
        logger.info("cost time:{}", (System.currentTimeMillis() - startTime));
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