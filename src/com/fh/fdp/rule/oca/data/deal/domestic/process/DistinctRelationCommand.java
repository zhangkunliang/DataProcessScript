package com.fh.fdp.rule.oca.data.deal.domestic.process;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
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
 * 境内资产去重
 * 
 * @author dhl 关联表
 */
public class DistinctRelationCommand extends RuleBaseCommand {

	private JedisUtil jedis = null;

	private String userIdKey = Constant.TASK_RELATION_KEY;

	@Override
	public void execute(Message msg) throws Exception {

		// 空包处理
		if (msg.getData() == null) {
			getExecutor().sendToNext(this, msg);
			return;
		}
		Config config = (Config) getExecutionContext().get("config");
		long expandTime = config.getExpandDomestic();

		// 获取多行数据，一行行处理
		List<List<DataField>> inputRows = msg.getData();

		Map<String, String> cache = new HashMap<>();
		List<List<DataField>> outputRowsDistinct = new ArrayList<>(); // 定义新的返回数据
		for (List<DataField> row : inputRows) {
			for (DataField field : row) {

				if (Constant.isUserId(field.getName())) {
					List<DataField> data = new ArrayList<>();
					data.addAll(new Gson().fromJson(new Gson().toJson(row), new TypeToken<List<DataField>>() {
					}.getType()));
					data.forEach(d -> {
						if (Constant.isAppType(d.getName())) {
							synchronized (JedisUtil.class) {
								boolean isExitId = jedis.exist(d.getValue() + userIdKey + field.getValue(),
										config.getAtvtCacheRelation());
								if (!isExitId) {
									jedis.add(d.getValue() + userIdKey + field.getValue(),
											String.valueOf(System.currentTimeMillis() / 1000), expandTime,
											config.getAtvtCacheRelation());
									if (!cache.containsKey(d.getValue() + userIdKey + field.getValue())) {
										outputRowsDistinct.add(row);
									}
									cache.put(d.getValue() + userIdKey + field.getValue(),
											String.valueOf(System.currentTimeMillis() / 1000));
								}
							}
						}
					});

				}

			}
		}
		msg.setData(outputRowsDistinct); // 设置新的返回数据
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
