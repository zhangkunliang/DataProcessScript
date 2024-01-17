package com.fh.fdp.rule.oca.data.deal.domestic.process;

import cn.hutool.crypto.digest.MD5;
import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.CluesTask;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fdp.rule.oca.data.deal.process.PhoneProcess;
import com.fh.fdp.rule.oca.data.tools.Encode;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
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
 * 邮箱境内资产处理
 *
 * @author zkl
 */
public class ProcessCommandTaskVtEmail extends RuleBaseCommand {

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
        Encode encoder = new Encode(key,logger);
        List<List<DataField>> inputRows = msg.getData();
        List<List<DataField>> output = new ArrayList<>();
        // 一个list是一条记录
        for (List<DataField> row : inputRows) {
            try {
                for (DataField field : row) {
                    if (Constant.OVERSEAS_USER_ACC.equalsIgnoreCase(field.getName())) {
                        field.setValue(encoder.encode(field.getValue()));
                        List<DataField> data = new ArrayList<>(new Gson().fromJson(new Gson().toJson(row), new TypeToken<List<DataField>>() {
                        }.getType()));
                        data.forEach(apptype -> {
                            if (Constant.isAppType(apptype.getName())) {
                                apptype.setValue(Constant.TUITE_TYPE);
                                taskSetValue(field, data, apptype);
                            }
                        });
                        output.add(data);
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        msg.setData(output); // 设置新的返回数据
        output.forEach(in -> {
            Map<String, String> cacheVt = new HashMap<>();
            in.forEach(d -> {
                logger.debug(GsonUtil.toStr(d));
                if (d.getName().equalsIgnoreCase(CluesTask.MD_ID)) {
                    cacheVt.put(CluesTask.MDID, d.getValue());
                } else if (d.getName().equalsIgnoreCase(Constant.APP_TYPE)) {
                    cacheVt.put(CluesTask.APPTYPE, d.getValue());
                } else if (d.getName().equalsIgnoreCase(Constant.OVERSEAS_USER_ACC)) {
                    cacheVt.put(CluesTask.RULE_VALUE, d.getValue());
                } else {
                    cacheVt.put(d.getName(), d.getValue());
                }
            });
            logger.debug("md_id-->{}", cacheVt.get(CluesTask.MDID));
            jedis.add(cacheVt.get(CluesTask.MDID), GsonUtil.toStr(cacheVt), config.getTaskCache());
        });
        getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节

    }

    private void taskSetValue(DataField field, List<DataField> data, DataField apptype) {
        data.forEach(d -> {
            // 0 未下发采集 1 已下发采集 2 已确认下发采集 3，采集成功 4，采集失败
            PhoneProcess.dataCollectSet3(d);
            if (CluesTask.SOURCE.equalsIgnoreCase(d.getName())) {
                d.setValue("");
            }
            if (CluesTask.RULE_TYPE.equalsIgnoreCase(d.getName())) {
                d.setValue("5");
            }
            if (CluesTask.MD_ID.equalsIgnoreCase(d.getName())) {
                d.setValue(MD5.create().digestHex(apptype.getValue() + "\t" + "5" + "\t" + field.getValue()));
            }
        });
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
