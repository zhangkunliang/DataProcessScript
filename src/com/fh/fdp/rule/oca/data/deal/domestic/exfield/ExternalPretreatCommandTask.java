package com.fh.fdp.rule.oca.data.deal.domestic.exfield;

import com.fh.fitdataprep.biga.command.rule.ExternalFieldCommand;

import java.util.Arrays;
import java.util.List;

/**
 * 主表需要添加字段
 * 
 * @author dhl
 */
public class ExternalPretreatCommandTask extends ExternalFieldCommand {

	/**
	 * 用于声明规则新产生的字段 , 可添加多个字段
	 * 
	 * @return 新产生的字段名
	 */
	@Override
	public List<String> externalFields() {
		return Arrays.asList("usersign","ACCESS_TIME","NETACCOUNT_TYPE", "NETACCOUNT","COLL_TIME2");// create_time
	}

}
