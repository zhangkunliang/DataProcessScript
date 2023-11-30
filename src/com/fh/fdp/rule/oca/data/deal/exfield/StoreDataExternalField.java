package com.fh.fdp.rule.oca.data.deal.exfield;

import java.util.Arrays;
import java.util.List;

import com.fh.fitdataprep.biga.command.rule.ExternalFieldCommand;

/**
 * 采集结果入库需要新增的字段
 * 
 */
public class StoreDataExternalField extends ExternalFieldCommand {
	@Override
	public List<String> externalFields() {
		return Arrays.asList("CollectPlace", "DataSource", "DiscMcode", "PostCode", "SourceType", "SourceValue",
				"is_delete", "is_robot", "first_time", "count", "RelationType", "RelationAccount", "Score");
	}
}
