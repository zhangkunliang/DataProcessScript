package com.fh.fdp.rule.oca.data.deal.domestic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
public class Utils {

    /**
     * 是否email
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher =  pattern.matcher(email);
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(isEmail("ns@aaasg.dk"));
    }

}
