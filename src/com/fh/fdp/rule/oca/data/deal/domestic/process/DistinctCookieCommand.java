package com.fh.fdp.rule.oca.data.deal.domestic.process;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fdp.rule.oca.data.tools.Encode;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 快手 采集资源去重
 * cookie
 * @author zkl
 */

public class DistinctCookieCommand extends RuleBaseCommand {
	
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
		Encode encoder = new Encode(config.getPasskey(),logger);
		// 获取多行数据，一行行处理
		List<List<DataField>> inputRows = msg.getData();
		Set<String> cache = new HashSet<>();

		List<List<DataField>> outputRowsDistinct = new ArrayList<>(); // 定义新的返回数据
		for (List<DataField> row : inputRows) {
			for (DataField field : row) {
				if (Constant.COOKIE.equalsIgnoreCase(field.getName())) {
					field.setValue(encoder.encode(field.getValue()));
					List<DataField> data = new ArrayList<>(new Gson().fromJson(new Gson().toJson(row), new TypeToken<List<DataField>>() {
					}.getType()));
					for(DataField d : data){
						if (Constant.isAppType(d.getName())) {
							String key = d.getValue() + Constant.COOKIE_KEY + field.getValue();
							if (cache.contains(key)) {
								break;
							}
							cache.add(key);
							boolean isExitId = jedis.exist(key, config.getAtvtCache());
							if (isExitId) {
								break;
							}
							jedis.add(key,
									String.valueOf(System.currentTimeMillis() / 1000), expandTime,
									config.getAtvtCache());
							outputRowsDistinct.add(row);
							break;
						}
					}
					break;
				}
			}
		}
		msg.setData(outputRowsDistinct); // 设置新的返回数据
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