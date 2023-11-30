package com.fh.fdp.rule.oca.data.deal.init;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fitdataprep.biga.command.rule.InitCommand;

/**
 * 初始化必要参数
 */
public class Init extends InitCommand {

	@Override
	public void doInit() {
		getExecutionContext().put("config",
				new Config(Init.class.getClassLoader().getResourceAsStream("data.properties"), logger));

	}
}
