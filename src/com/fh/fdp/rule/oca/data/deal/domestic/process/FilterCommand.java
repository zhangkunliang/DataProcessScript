package com.fh.fdp.rule.oca.data.deal.domestic.process;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;

/**
 * 境内过滤
 */
public class FilterCommand extends RuleBaseCommand {

	@Override
	public void execute(Message msg) throws Exception {

		// 空包处理
		if (msg.getData() == null) {
			getExecutor().sendToNext(this, msg);
			return;
		}
		// 获取多行数据，一行行处理
		List<List<DataField>> inputRows = msg.getData();
		// 示例3: 过滤数据, 根据业务条件进行判断
		List<List<DataField>> outputRowsFilter = new ArrayList<>(); // 定义新的返回数据
		for (List<DataField> row : inputRows) {
			for (DataField field : row) {
				if (Constant.isAppType(field.getName())) {
					// "100000006".equals(field.getValue())
					if (Constant.filterAppType(field.getValue())) {  //过滤AppType
						outputRowsFilter.add(row);
					}

				}
			}
		}

		List<List<DataField>> outputRowsFilter2 = new ArrayList<>(); // 定义新的返回数据
		for (List<DataField> row : outputRowsFilter) {
			for (DataField field : row) {
				if (Constant.isUserId(field.getName())) {
					if (StringUtils.isNotBlank(field.getValue())) { //将UserId不为空的过滤
						outputRowsFilter2.add(row);
					}

				}
			}
		}
		msg.setData(outputRowsFilter2); // 设置新的返回数据
		getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节

	}

	@Override
	public void onInit() {
	}

	@Override
	public void onStop() {
	}

}
