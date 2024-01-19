package com.fh.fdp.rule.oca.data.deal.domestic.process;

import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Utils;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤appid 为空，且appid 是 email
 *
 * @author zkl
 */
public class FilterTwCommandNb extends RuleBaseCommand {

    @Override
    public void execute(Message msg) throws Exception {

        // 空包处理
        if (msg.getData() == null) {
            getExecutor().sendToNext(this, msg);
            return;
        }
        // 获取多行数据，一行行处理
        List<List<DataField>> inputRows = msg.getData();

        List<List<DataField>> outputRowsFilter = new ArrayList<>(); // 定义新的返回数据
        for (List<DataField> row : inputRows) {
            for (DataField field : row) {
                if (Constant.isUserId(field.getName()) && StringUtils.isNotBlank(field.getValue()) && Utils.isEmail(field.getValue())) {
                    outputRowsFilter.add(row);
                }
            }
        }
        List<List<DataField>> outputRowsFilter2 = new ArrayList<>(); // 定义新的返回数据
        for (List<DataField> row : outputRowsFilter) {
            for (DataField field : row) {
                if (Constant.isAppType(field.getName()) && Constant.TUITE_TYPE.equals(field.getValue())) {  // 过滤推特类型数据
                    outputRowsFilter2.add(row);
                }
            }
        }

        msg.setData(outputRowsFilter2); // 设置新的返回数据
        getExecutor().sendToNext(this, msg); // 最后把数据发送到下一环节

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
