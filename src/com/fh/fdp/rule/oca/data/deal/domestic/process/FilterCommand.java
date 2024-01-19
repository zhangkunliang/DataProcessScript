package com.fh.fdp.rule.oca.data.deal.domestic.process;

import com.fh.fdp.rule.oca.data.deal.domestic.utils.AppType;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.Utils;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.spi.Message;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
                if (Constant.isAppType(field.getName()) && Constant.filterAppType(field.getValue())) {
                    outputRowsFilter.add(row);
                }
            }
        }

        List<List<DataField>> outputRowsFilter2 = new ArrayList<>(); // 定义新的返回数据
        for (List<DataField> row : outputRowsFilter) {
            for (DataField field : row) {
                if (Constant.isUserId(field.getName()) && StringUtils.isNotBlank(field.getValue())
                        && !Utils.isEmail(field.getValue())
                        && field.getValue().startsWith(",")) {
                    //  增加userID前缀带有逗号的数据处理逻辑，并且过滤掉userId为邮箱的情况  2023-11-1 10:36:11
                    //  将UserId不为空且值为邮箱的过滤
                    field.setValue(field.getValue().substring(1));
                    outputRowsFilter2.add(row);
                }else if (Constant.isUserId(field.getName()) && StringUtils.isNotBlank(field.getValue())
                        && !Utils.isEmail(field.getValue())){
                    outputRowsFilter2.add(row);
                }

            }
        }
        List<List<DataField>> outputRowsFilter3 = new ArrayList<>(); // 定义新的返回数据
        // 小红书的过滤条件
        for (List<DataField> row : outputRowsFilter2) {
            for (DataField field : row) {
                // 微博B站快手的过滤条件,过滤掉非纯数字的
                if (Constant.isAppType(field.getName())&& AppType.filterWbBzKsAppType(field.getValue())){
                    for (DataField field1:row){
                        if (Constant.isUserId(field1.getName())&& Pattern.matches("\\d+",field1.getValue())){
                            outputRowsFilter3.add(row);
                        }
                    }
                }else if (Constant.isAppType(field.getName())&& !AppType.filterWbBzKsAppType(field.getValue())){
                    // 非微博B站快手的过滤条件
                    outputRowsFilter3.add(row);
                }
            }

        }

        // 除小红书外的过滤条件

        msg.setData(outputRowsFilter3); // 设置新的返回数据
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
