package com.fh.fdp.rule.oca.data.deal.process;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;

public class ClueOutAddLocalcodeProcess extends RuleBaseCommand {

	@Override
	public void execute(Message msg) throws Exception {
		Config config = (Config) getExecutionContext().get("config");
		msg.setProperty("tableName", config.getLocalCode());
		msg.setProperty("schema", "OCA");
		getExecutor().sendToNext(this, msg);
	}

	@Override
	public void onInit() {
	}

	@Override
	public void onStop() {
	}

}
