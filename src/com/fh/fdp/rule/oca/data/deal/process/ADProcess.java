package com.fh.fdp.rule.oca.data.deal.process;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.tools.Encode;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.hutool.crypto.digest.MD5;

/**
 */
public class ADProcess extends RuleBaseCommand {

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
		long expand = config.getExpandTime();
		Encode encoder = new Encode(key, logger);

		// 获取多行数据，一行行处理
		List<List<DataField>> inputRows = msg.getData();

		List<List<DataField>> output = new ArrayList<>();
		Set<String> contextCache = new HashSet<>();
		int nrCount = 0;
		int noAdress = 0;
		int repeat = 0;
		// 一个list是一条记录
		for (List<DataField> row : inputRows) {
			try {
				for (DataField field : row) {
					if ("auth_account".equalsIgnoreCase(field.getName())) {
						String oldValue = field.getValue(); // 获取字段的值
						if (StringUtils.isBlank(oldValue)) {
							logger.warn("auth_account empty.ignore...");
							nrCount++;
							continue;
						}
						if (!jedis.exist(oldValue, config.getAdAddressCacheDB())) {
							logger.warn("no longitude&latitude message find in cache...pleasecheck.." + oldValue);
							noAdress++;
							continue;
						}
						String address = jedis.get(oldValue, config.getAdAddressCacheDB());
						if (StringUtils.isBlank(address) || contextCache.contains(address)) {
							logger.debug("Address null or repeat.ignore..." + address);
							nrCount++;
							continue;
						}
						contextCache.add(address);
						if (jedis.exist(address, config.getAdCacheDB())) {
							logger.debug("exist!!" + address);
							repeat++;
						} else {
							logger.info("get new address:" + address);
							jedis.add(address, "", expand, config.getAdCacheDB());
							String coderes = encoder.encode(address);
							if (coderes == null) {
								continue;
							}
							field.setValue(coderes); // 设置新的值
							config.getApptypes().forEach((k, v) -> {
								if (!config.getEnableAppType().contains(k)) {
									return;
								}
								List<DataField> data = new ArrayList<>();
								data.addAll(
										new Gson().fromJson(new Gson().toJson(row), new TypeToken<List<DataField>>() {
										}.getType()));
								data.forEach(d -> {
									if ("first_time".equalsIgnoreCase(d.getName())) {
										d.setValue("0");
									}
									if ("last_time".equalsIgnoreCase(d.getName())) {
										d.setValue("0");
									}
									if ("ccount".equalsIgnoreCase(d.getName())) {
										d.setValue("0");
									}
									if ("dcount".equalsIgnoreCase(d.getName())) {
										d.setValue("0");
									}

									if ("apptype".equalsIgnoreCase(d.getName())) {
										d.setValue(v);
									}
									if ("rule_type".equalsIgnoreCase(d.getName())) {
										d.setValue("10");
									}
									if ("rule_value".equalsIgnoreCase(d.getName())) {
										d.setValue(field.getValue());
									}
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
										d.setValue("vpn_ad");
									}
									if ("md_id".equalsIgnoreCase(d.getName())) {
										d.setValue(MD5.create().digestHex(v + "\t10\t" + field.getValue()));
									}
								});
								output.add(data);
							});
						}
					}
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		logger.info("exec over.get task/total/nrcount/noAd/repeat=" + output.size() + "/" + inputRows.size() + "/"
				+ nrCount + "/" + noAdress + "/" + repeat);
		msg.setData(output); // 设置新的返回数据
		output.forEach(in -> {
			Map<String, String> cache = new HashMap<>();
			in.forEach(d -> {
				cache.put(d.getName(), d.getValue());
			});
			logger.debug(GsonUtil.toStr(cache));
			jedis.add(cache.get("md_id"), GsonUtil.toStr(cache), config.getTaskCache());
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
