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
 * 邮箱账号非空、符合邮箱格式
 * 2023年11月10日14:46:22
 *
 * @author zkl
 */
public class FilterVtEmailCommand extends RuleBaseCommand {

    @Override
    public void execute(Message msg) throws Exception {
        // 空包处理
        if (msg.getData() == null) {
            getExecutor().sendToNext(this, msg);
            return;
        }
        // 获取多行数据，一行行处理
        // 认证账号非空判断
        List<List<DataField>> inputRows = msg.getData();
        List<List<DataField>> outputRowsFilter = new ArrayList<>();
        for (List<DataField> row : inputRows) {
            //过滤auth_account不为空数据
            for (DataField field : row) {
                if (Constant.AUTH_ACCOUNT2.equalsIgnoreCase(field.getName())
                        && StringUtils.isNotBlank(field.getValue())) {
                    outputRowsFilter.add(row);
                }
            }
        }
        List<List<DataField>> outputRowsFilter2 = new ArrayList<>(); // 定义新的返回数据
        for (List<DataField> row : outputRowsFilter) {
            for (DataField field : row) {
                // adm_overseas_vt_clues表的邮箱
                if (Constant.isEmail(field.getName()) && StringUtils.isNotBlank(field.getValue())
                        && Utils.isEmail(field.getValue())) {
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
