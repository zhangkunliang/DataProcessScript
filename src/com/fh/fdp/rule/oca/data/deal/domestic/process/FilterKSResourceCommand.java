package com.fh.fdp.rule.oca.data.deal.domestic.process;

import com.fh.fdp.rule.oca.data.deal.domestic.utils.AppType;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤快手采集资源cookie 为空，且cookie包含mod\did\did_gt信息
 */
public class FilterKSResourceCommand extends RuleBaseCommand {

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
                if (Constant.isAppType(field.getName()) && AppType.KWAI_TYPE.getAppType().equals(field.getValue())) {
                    outputRowsFilter.add(row);
                }
            }
        }
        List<List<DataField>> outputRowsFilter2 = new ArrayList<>(); // 定义新的返回数据
        for (List<DataField> row : outputRowsFilter) {
            for (DataField field : row) {
                if (Constant.COOKIE.equalsIgnoreCase(field.getName())
                        && StringUtils.isNotBlank(field.getValue())
                        && field.getValue().contains("mod")
                        && field.getValue().contains("did")
                        && field.getValue().contains("did_gt")) {
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
