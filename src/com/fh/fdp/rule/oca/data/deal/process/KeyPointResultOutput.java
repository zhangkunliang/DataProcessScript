package com.fh.fdp.rule.oca.data.deal.process;

import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import com.google.gson.JsonArray;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class KeyPointResultOutput extends RuleBaseCommand {

	@Override
	public void execute(Message msg) throws Exception {
		// 空包处理
		if (msg.getData() == null) {
			getExecutor().sendToNext(this, msg);
			return;
		}
		// 获取输入数据
		List<List<DataField>> inputRows = msg.getData();

		JsonArray arr = change2Json(inputRows);

		// 构造一条文件对象返回数据
		String data = GsonUtil.toStr(arr);
		if (StringUtils.isBlank(data)) {
			logger.error("pares data null..");
			return;
		}
		String base64Value = Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
		DataField fileContent = new DataField(FILE_CONTENT, base64Value);
		DataField rowsNumber = new DataField(ROWS_NUMBER, String.valueOf(inputRows.size())); // 统一转成String类型
		List<DataField> retRow = new ArrayList<>();
		retRow.add(fileContent);
		retRow.add(rowsNumber);

		List<List<DataField>> outputRows = new ArrayList<>();
		outputRows.add(retRow);
		msg.setData(outputRows); // 设置新的返回数据
		getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节
	}

	private JsonArray change2Json(List<List<DataField>> inputRows) {
		return ResultOutput.getJsonElements(inputRows);
	}

	@Override
	public void onInit() {
		// 初始化
	}

	@Override
	public void onStop() {
		// 停止
	}

}
