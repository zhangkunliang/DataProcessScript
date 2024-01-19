package com.fh.fdp.rule.oca.data.deal.domestic.process;

import cn.hutool.crypto.digest.MD5;
import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.CluesTask;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CommandDemo 规则开发样例 继承RuleBaseCommand类,在execute方法中实现数据处理逻辑
 *
 * @author dhl
 *
 */
public class ProcessPersonCommandTask extends RuleBaseCommand {

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
					if (Constant.APP_ID.equalsIgnoreCase(field.getName()) && StringUtils.isNotBlank(field.getValue())) {
						List<DataField> data = new ArrayList<>(new Gson().fromJson(new Gson().toJson(row), new TypeToken<List<DataField>>() {
						}.getType()));
						data.forEach(apptype -> {
							if (Constant.isAppType(apptype.getName())) {
								data.forEach(d -> {
									if ("rule_id".equalsIgnoreCase(d.getName())) {
										d.setValue(MD5.create().digestHex(
												apptype + "\t" + "2" + "\t" + encoder.encode(field.getValue())));
									}
								});
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
			Map<String, String> cache = new HashMap<>();
			in.forEach(d -> {
				logger.debug(GsonUtil.toStr(d));
				if (d.getName().equals(Constant.MD_ID)) {
					cache.put(CluesTask.MDID, d.getValue());
				} else if (d.getName().equals(Constant.APP_ID)) {
					cache.put(CluesTask.RULE_VALUE, d.getValue());
				} else {
					cache.put(d.getName(), d.getValue());
				}
			});
			jedis.add(cache.get(CluesTask.MDID), GsonUtil.toStr(cache), config.getTaskCache());
		});
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
