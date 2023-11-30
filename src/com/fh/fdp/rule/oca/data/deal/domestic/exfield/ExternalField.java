package com.fh.fdp.rule.oca.data.deal.domestic.exfield;

import com.fh.fitdataprep.biga.command.rule.ExternalFieldCommand;

import java.util.Arrays;
import java.util.List;

/**
 * ExternalFieldCommandDemo 规则开发样例 </br>
 * 继承ExternalFieldCommand类,在externalFields方法中实现新增的字段
 * 
 * @version 1.0
 * @date 2022/5/11
 * @since 1.8
 */
public class ExternalField extends ExternalFieldCommand {

	/**
	 * 用于声明规则新产生的字段 , 可添加多个字段
	 * 
	 * @return 新产生的字段名
	 */
	@Override
	public List<String> externalFields() {
		return Arrays.asList("apptype", "rule_type", "rule_value", "collect_status", "collect_count", "updatetime",
				"createtime", "source");
	}

}
