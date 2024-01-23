package com.fh.fdp.rule.oca.data.deal.process;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 2023年11月22日15:35:31
 * 抽取抖音任务到文件目录中
 *
 * @author zkl
 */
public class DouyinClueOutAddLocalcodeProcess extends RuleBaseCommand {

    @Override
    public void execute(Message msg) throws Exception {
        // 空包处理
        if (msg.getData() == null) {
            getExecutor().sendToNext(this, msg);
            return;
        }
        Config config = (Config) getExecutionContext().get("config");
        // 获取多行数据，一行行处理
        List<List<DataField>> inputRows = msg.getData();
        List<List<DataField>> outputRowsFilter = new ArrayList<>();
        for (List<DataField> row : inputRows) {
            for (DataField field : row) {
                if (Constant.isAppType(field.getName()) && Constant.DOUYIN_TYPE.equals(field.getValue())) {  // 过滤抖音类型数据
                    outputRowsFilter.add(row);
                }
            }
        }
        msg.setData(outputRowsFilter);   //设置新的返回数据
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
