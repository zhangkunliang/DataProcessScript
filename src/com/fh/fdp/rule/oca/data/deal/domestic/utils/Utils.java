package com.fh.fdp.rule.oca.data.deal.domestic.utils;

import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.fh.fitdataprep.biga.bean.DataField;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
public class Utils {
    private Utils(){}
    /**
     * 是否email
     *
     */
    public static boolean isEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



    public static void splicingKey(Config config, long expandTime, Map<String, String> cache, List<List<DataField>> outputRowsDistinct, List<DataField> row, String key, JedisUtil jedis) {
        cache.computeIfAbsent(key, k -> String.valueOf(System.currentTimeMillis() / 1000));
        boolean isExitId = jedis.exist(key, config.getAtvtCache());
        if (isExitId) {
            return;
        }
        jedis.add(key,
                String.valueOf(System.currentTimeMillis() / 1000), expandTime,
                config.getAtvtCache());
        outputRowsDistinct.add(row);
    }

}
