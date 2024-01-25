package com.fh.fdp.rule.oca.data.deal.domestic.utils;

/**
 * 一些变量 用来判断 isUserId isAppType
 *
 * @author dhl
 */
public class Constant {
    Constant() {
    }

    public static final String BZHAN_TYPE = "100000268";  //B站新海量
    public static final String OLD_BZHAN_TYPE1 = "1409950";  //B站老海量
    public static final String DOUYIN_TYPE = "100000010";  // 抖音
    public static final String DOUYIN_TYPE2 = "999999999"; // 抖音
    public static final String TUITE_TYPE = "00009315";  // 推特
    public static final String FACEBOOK_TYPE = "00010401"; // 脸书
    public static final String TWITTER_TYPE = "100000595"; // Twiiter类型
    public static final String INS_TYPE = "100000399";  // insgram类型

    public static final String APP_ID = "userid";// APPID userid
    public static final String MD_ID = "md_id";
    public static final String APP_ID2 = "appid";// APPID userid

    public static final String USER_NAME = "username";

    public static final String APP_TYPE = "app_type";

    public static final String APP_TYPE_2 = "apptype";

    public static final String ACCOUNT_TYPE = "account_type";
    public static final String ACCOUNT = "account";
    /**
     * NB_MASS_RESOURCE_HTTP COOKIE
     */
    public static final String COOKIE = "cookie";
    /**
     * NB_MASS_RESOURCE_ACTION
     */
    public static final String TOOL_NAME = "tool_name";
    /**
     * NB_MASS_RESOURCE_ACTION
     */
    public static final String MIX_RELATE_TOKEN = "mix_relate_token";
    /**
     * NB_MASS_RESOURCE_HTTP auth_account
     */
    public static final String AUTH_ACCOUNT = "auth_account";
    public static final String AUTH_TYPE = "auth_type";
    public static final String AUTH_TYPE2 = "authtype";
    public static final String AUTH_ID = "authid";
    public static final String USER_TOKEN = "user_token";
    /**
     * 1天 单位 s
     */
    public static final long ONE_DAYS_TIME = 24 * 60 * 60L;
    /**
     * 账号表 任务表 key
     */
    public static final String TASK_KEY = "task";
    /**
     * 账号表 关联表Key
     */
    public static final String TASK_RELATION_KEY = "task_relation";
    /**
     * http资源表 cookie
     */
    public static final String COOKIE_KEY = "cookie";
    /**
     * 邮箱类型 nb_mass_resource_email: MAIL_FROM    MAIL_TO
     * ADM_OVERSEAS_VT_CLUES: OVERSEAS_USER_AC
     */
    public static final String AUTH_ACCOUNT2 = "INTE_VERACC";
    public static final String MAIL_FROM = "mail_from";
    public static final String OVERSEAS_USER_ACC = "OVERSEAS_USER_ACC";
    public static final String INTE_AUTH_TYPE = "INTE_AUTH_TYPE";
    public static final String INTE_VERACC = "INTE_VERACC";

    /**
     * 是不是UserId
     * 新增字段appid
     */
    public static boolean isUserId(String fieldName) {
        return Constant.APP_ID.equalsIgnoreCase(fieldName) || Constant.APP_ID2.equalsIgnoreCase(fieldName);
    }

    /**
     * 是不是APPID
     */
    public static boolean isUserName(String fieldName) {
        return Constant.USER_NAME.equalsIgnoreCase(fieldName);
    }

    /**
     * 是不是邮箱
     */
    public static boolean isEmail(String fieldName) {
        return Constant.OVERSEAS_USER_ACC.equalsIgnoreCase(fieldName);
    }

    /**
     * 是不是AppType
     */
    public static boolean isAppType(String fieldName) {
        return Constant.APP_TYPE.equalsIgnoreCase(fieldName) || Constant.APP_TYPE_2.equalsIgnoreCase(fieldName) || Constant.ACCOUNT_TYPE.equalsIgnoreCase(fieldName);
    }

    /**
     * atvt应用
     */
    public static boolean filterAppType(String fieldName) {
        return AppType.filterAppType(fieldName);
    }
}
