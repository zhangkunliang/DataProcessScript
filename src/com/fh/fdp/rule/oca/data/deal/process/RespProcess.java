package com.fh.fdp.rule.oca.data.deal.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.conf.CrawlerStatus;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import com.google.gson.Gson;

/**
 */
public class RespProcess extends RuleBaseCommand {

	private JedisUtil jedis = null;

	@Override
	public void execute(Message msg) throws Exception {

		// 空包处理
		if (msg.getData() == null) {
			getExecutor().sendToNext(this, msg);
			return;
		}
		Config config = (Config) getExecutionContext().get("config");

		Set<String> idCache = new HashSet<>();
		// 获取多行数据，一行行处理
		List<List<DataField>> inputRows = msg.getData();
		List<Map<String, String>> fieldCacheMap = new ArrayList<>();

		// list-list 转list-map
		inputRows.forEach(in -> {
			Map<String, String> cache = new HashMap<>();
			in.forEach(d -> cache.put(d.getName(), d.getValue()));
			fieldCacheMap.add(cache);
		});

		List<List<DataField>> output = new ArrayList<>();

		fieldCacheMap.forEach(data -> {
			if (idCache.contains(data.get("mdid"))) {
				return;
			}
			idCache.add(data.get("mdid"));
			if ("0".equals(data.get("mdid")) || "1".equals(data.get("mdid"))) {
				CrawlerStatus status = new CrawlerStatus();
				status.setMdid(data.get("mdid"));
				status.setApptype("0");
				status.setSource("0");
				status.setRule_type(0);
				status.setRule_value("0");
				status.setCreatetime(0L);
				status.setCollect_status(Integer.parseInt(data.get("collect_status")));
				status.setUpdatetime(Long.parseLong(data.get("updatetime")));
				status.setCollect_count(0);
				logger.info(GsonUtil.toStr(status));
				List<DataField> out = new ArrayList<>();
				new Gson().toJsonTree(status).getAsJsonObject().entrySet()
						.forEach(e -> out.add(new DataField(e.getKey(), e.getValue().getAsString())));
				output.add(out);
			} else {
				String res = jedis.get(data.get("mdid"), config.getTaskCache());
				if (StringUtils.isBlank(res)) {
					logger.error(" mdid cache not found.ignore... " + data.get("mdid"));
					return;
				}
				CrawlerStatus status = GsonUtil.fromJson(res, CrawlerStatus.class);
				status.setMdid(data.get("mdid"));
				status.setCollect_status(Integer.parseInt(data.get("collect_status")));
				status.setUpdatetime(Long.parseLong(data.get("updatetime")));
				status.setCollect_count(0);
				logger.info(GsonUtil.toStr(status));
				List<DataField> out = new ArrayList<>();
				new Gson().toJsonTree(status).getAsJsonObject().entrySet()
						.forEach(e -> out.add(new DataField(e.getKey(), e.getValue().getAsString())));
				output.add(out);
			}
		});
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
