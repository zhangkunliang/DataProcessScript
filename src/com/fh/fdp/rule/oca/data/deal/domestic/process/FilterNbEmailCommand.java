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
public class FilterNbEmailCommand extends RuleBaseCommand {

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
                if (Constant.AUTH_ACCOUNT.equalsIgnoreCase(field.getName()) && StringUtils.isNotBlank(field.getValue())) {
                    outputRowsFilter.add(row);
                }
            }
        }
        List<List<DataField>> outputRowsFilter2 = new ArrayList<>(); // 定义新的返回数据
        for (List<DataField> row : outputRowsFilter) {
            List<String> emails = new ArrayList<>();
            for (DataField field : row) {
                //email_from->email_to 一对多的关系，输出多条记录
                //nb_mass_resource_mail表的邮箱
                if (field.getName().equalsIgnoreCase("mail_from")
                        && StringUtils.isNotBlank(field.getValue()) && Utils.isEmail(field.getValue())) {
                    emails.add(field.getValue());
                } else if (field.getName().equalsIgnoreCase("mail_to")
                        && StringUtils.isNotBlank(field.getValue())) {
                    String[] values = field.getValue().split("\\s+");
                    for (String v : values) {
                        if (Utils.isEmail(v)) {
                            emails.add(v);
                        }
                    }
                }
            }
            // 从列表中挨个取值放置mail_from中
            for (DataField field2 : row) {
                for (String email : emails) {
                    if (field2.getName().equalsIgnoreCase("mail_from")) {
                        field2.setValue(email);
                        List<DataField> dataFields = new ArrayList<>();
                        row.forEach(f -> dataFields.add(f.copy()));
                        outputRowsFilter2.add(dataFields);
                    }
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
