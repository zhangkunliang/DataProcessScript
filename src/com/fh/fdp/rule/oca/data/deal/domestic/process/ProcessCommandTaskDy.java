package com.fh.fdp.rule.oca.data.deal.domestic.process;

import cn.hutool.crypto.digest.MD5;
import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fdp.rule.oca.data.tools.Encode;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 境内资产处理的地方
 *
 * @author dhl
 */
public class ProcessCommandTaskDy extends RuleBaseCommand {

    private JedisUtil jedis = null;

    @Override
    public void execute(Message msg) throws Exception {
        // 空包处理
        if (msg.getData() == null) {
            getExecutor().sendToNext(this, msg);
            return;
        }
        Config config = (Config) getExecutionContext().get("config");
        String key = config.getPasskey();
        Encode encoder = new Encode(key, logger);
        List<List<DataField>> inputRows = msg.getData();

        List<List<DataField>> output = new ArrayList<>();
        // 一个list是一条记录
        for (List<DataField> row : inputRows) {
            try {
                for (DataField field : row) {
                    if ("cookie".equalsIgnoreCase(field.getName())) {
                        field.setValue(encoder.encode(field.getValue()));
                        List<DataField> data = new ArrayList<>();
                        data.addAll(new Gson().fromJson(new Gson().toJson(row), new TypeToken<List<DataField>>() {
                        }.getType()));

                        data.forEach(apptype -> {
                            if (Constant.isAppType(apptype.getName())) {
                                data.forEach(d -> {
                                    /**
                                     * 0 未下发采集 1 已下发采集 2 已确认下发采集 3，采集成功 4，采集失败
                                     */
                                    if ("collect_status".equalsIgnoreCase(d.getName())) {
                                        d.setValue("0");
                                    }
                                    if ("collect_count".equalsIgnoreCase(d.getName())) {
                                        d.setValue("0");
                                    }
                                    if ("updatetime".equalsIgnoreCase(d.getName())) {
                                        d.setValue(Instant.now().getEpochSecond() + "");
                                    }
                                    if ("createtime".equalsIgnoreCase(d.getName())) {
                                        d.setValue(Instant.now().getEpochSecond() + "");
                                    }
                                    if ("source".equalsIgnoreCase(d.getName())) {
                                        d.setValue("");
                                    }

                                    if ("rule_type".equalsIgnoreCase(d.getName())) {
                                        d.setValue("11");
                                    }
                                    if ("id".equalsIgnoreCase(d.getName()) || "ruleid".equalsIgnoreCase(d.getName())) {
                                        d.setValue(MD5.create().digestHex(apptype.getValue() + "\t" + "11" + "\t" + field.getValue()));
                                    }
                                });
                            }
                        });
                        output.add(data);
                    } else {
                        logger.error("no longitude&latitude message find in cache...pleasecheck..");
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        msg.setData(output); // 设置新的返回数据
        output.forEach(in -> {
            Map<String, String> cache = new HashMap<>();
            in.forEach(d -> {
                logger.info(GsonUtil.toStr(d));
                if (d.getName().equalsIgnoreCase("id")) {
                    cache.put("mdid", d.getValue());
                } else if (d.getName().equalsIgnoreCase("app_type")) {
                    cache.put("apptype", d.getValue());
                } else if (d.getName().equalsIgnoreCase("app_hardware_feature")) {
                    cache.put("rule_value", d.getValue());
                } else {
                    cache.put(d.getName(), d.getValue());
                }
            });
            logger.info("md_id-->" + cache.get("mdid"));
            jedis.add(cache.get("mdid"), GsonUtil.toStr(cache), config.getTaskCache());
        });
        getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节

    }

    @Override
    public void onInit() {
        Config conf = (Config) getExecutionContext().get("config");
        jedis = new JedisUtil(conf.getRedisIp(), conf.getRedisPort(), conf.getRedisPass(), logger);
    }

    @Override
    public void onStop() {
        if (jedis != null) {
            jedis.close();
        }
    }

}
