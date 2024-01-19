package com.fh.fdp.rule.oca.data.deal.domestic.process;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.conf.UserIdAdCache;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
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
import java.util.List;

/**
 * 预处理
 *
 * @author zkl
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
        List<List<DataField>> inputRows = msg.getData();
        List<List<DataField>> output = new ArrayList<>();
        // 一个list是一条记录
        for (List<DataField> row : inputRows) {
            try {
                String cache = null;
                String userId = null;
                for (DataField field : row) {
                    if (Constant.APP_ID.equalsIgnoreCase(field.getName())) {
                        cache = jedis.get(field.getValue(), config.getAtvtCache());
                        userId = field.getValue();
                    }
                    if (cache == null && Constant.USER_TOKEN.equalsIgnoreCase(field.getName())) {
                        cache = jedis.get(field.getValue(), config.getAtvtCache());
                    }
                }
                List<DataField> data = new ArrayList<>(new Gson().fromJson(new Gson().toJson(row), new TypeToken<List<DataField>>() {
                }.getType()));
                UserIdAdCache userIdAdCache = GsonUtil.fromJson(cache, UserIdAdCache.class);
                for (DataField field : data) {
                    if (Constant.USER_NAME.equalsIgnoreCase(field.getName()) && StringUtils.isEmpty(field.getValue())) {
                        data.forEach(d -> {
                            if ("nickname".equalsIgnoreCase(d.getName())) {
                                field.setValue(d.getValue());
                            }
                        });
                    }
                    if ("NETACCOUNT_TYPE".equalsIgnoreCase(field.getName()) && userIdAdCache != null) {
                        field.setValue(userIdAdCache.getAuthType());
                    }
                    if ("NETACCOUNT".equalsIgnoreCase(field.getName()) && userIdAdCache != null) {
                        field.setValue(userIdAdCache.getAuthAccount());
                    }
                    if ("usersign".equalsIgnoreCase(field.getName())) {
                        field.setValue(userId);
                    }

                    if ("ACCESS_TIME".equalsIgnoreCase(field.getName())) {
                        field.setValue(Instant.now().getEpochSecond() + "");
                    }
                    if ("COLL_TIME2".equalsIgnoreCase(field.getName())) {
                        data.forEach(d -> {
                            if ("coll_time".equalsIgnoreCase(d.getName())) {
                                field.setValue(d.getValue());
                            }
                        });
                    }
                }
                output.add(data);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        msg.setData(output); // 设置新的返回数据
        getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节

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
