package com.fh.fdp.rule.oca.data.deal.domestic.exfield;

import com.fh.fitdataprep.biga.command.rule.ExternalFieldCommand;

import java.util.Arrays;
import java.util.List;

/**
 * ExternalFieldCommandDemo 规则开发样例 </br>
 * 继承ExternalFieldCommand类,在externalFields方法中实现新增的字段 主表需要添加字段 预处理要添加 last_time
 * 字段
 */
public class ExternalFieldCommandForPretreatmentTask extends ExternalFieldCommand {

	/**
	 * 用于声明规则新产生的字段 , 可添加多个字段
	 * 
	 * @return 新产生的字段名
	 */
	@Override
	public List<String> externalFields() {
		return Arrays.asList("md_id", "createtime", "updatetime", "collect_status", "collect_count", "source",
				"rule_type", "last_time");// create_time
	}

}
