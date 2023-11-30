package com.fh.fdp.rule.oca.data.deal.domestic.process;

import cn.hutool.crypto.digest.MD5;
import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.conf.UserIdAdCache;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fdp.rule.oca.data.tools.Encode;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预处理
 *
 * @author dhl
 */
public class ProcessPretreatCommandTask extends RuleBaseCommand {

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
                    if ("userid".equalsIgnoreCase(field.getName())) {
                        List<DataField> data = new ArrayList<>();
                        data.addAll(new Gson().fromJson(new Gson().toJson(row), new TypeToken<List<DataField>>() {
                        }.getType()));
                        String cache = jedis.get(field.getValue(), config.getAtvtCache());
                        logger.info("UserId cache" + cache);
                        UserIdAdCache userIdAdCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
                        data.forEach(apptype -> {

                            if ("username".equalsIgnoreCase(apptype.getName())) {
                                if(StringUtils.isEmpty(apptype.getValue())){
                                    data.forEach(d -> {
                                        if ("nickname".equalsIgnoreCase(d.getName())) {
                                            apptype.setValue(d.getValue());
                                        }
                                    });
                                }

                            }

                            if ("NETACCOUNT_TYPE".equalsIgnoreCase(apptype.getName())) {
                                if (userIdAdCache != null) {
                                    apptype.setValue(userIdAdCache.getAuthType());
                                }

                            }

                            if ("NETACCOUNT".equalsIgnoreCase(apptype.getName())) {
                                if (userIdAdCache != null) {
                                    apptype.setValue(userIdAdCache.getAuthAccount());
                                }
                            }

                            if ("usersign".equalsIgnoreCase(apptype.getName())) {
                                apptype.setValue(field.getValue());
                            }

                            if ("ACCESS_TIME".equalsIgnoreCase(apptype.getName())) {
                                apptype.setValue(Instant.now().getEpochSecond() + "");
                            }

                            //COLL_TIME
                            if ("COLL_TIME2".equalsIgnoreCase(apptype.getName())) {
                                data.forEach(d -> {
                                    if ("coll_time".equalsIgnoreCase(d.getName())) {
                                        apptype.setValue(d.getValue());
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
        logger.info("outputData =" + output.toString());
        msg.setData(output); // 设置新的返回数据
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
