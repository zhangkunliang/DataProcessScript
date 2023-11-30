package com.fh.fdp.rule.oca.data.deal.exfield;

import java.util.Arrays;
import java.util.List;

import com.fh.fitdataprep.biga.command.rule.ExternalFieldCommand;

/**
 * 采集任务相关需要新增的字段
 */
public class ExternalField extends ExternalFieldCommand {

	/**
	 * @return 新产生的字段名
	 */
	@Override
	public List<String> externalFields() {
		return Arrays.asList("apptype", "rule_type", "rule_value", "collect_status", "collect_count", "updatetime",
				"createtime", "source");
	}

}
