package com.fh.fdp.rule.oca.data.deal.domestic.utils;

/**
 * 一些变量 用来判断 isUserId isAppType
 *
 * @author dhl
 */
public class Constant {
    /**
     * douyin apptyoe
     */
    public static final String DOUYIN_TYPE = "100000010";   // 抖音

    /**
     * tuite apptype
     */
    public static final String TUITE_TYPE = "100004275";   // 推特
    /**
     * zhihu appytpe
     */
    public static final String ZHIHU_TYPE = "100000074";  // 知乎 新海量

    /**
     * toutiao appytpe
     */
    public static final String TOUTIAO_TYPE = "100000007";  //今日头条 新海量

    /**
     * Bzhan appytpe
     */
    public static final String BZHAN_TYPE = "100000268";  //B站 新海量

    /**
     * weibo apptype
     */
    public static final String WEIBO_TYPE = "1330001";  //老海量 1330001

    /**
     * new weibo apptype
     */
    public static final String NEW_WEIBO_TYPE = "100000006";  //新海量微博

    /**
     * RED_apptype
     */
    public static final String RED_TYPE = "100000152";

    /**
     * 快手
     */
    public static final String KWAI_TYPE = "100000021";


    public static final String KWAI_TYPE2 = "123456487";
    public static final String KWAI_TYPE3 = "10001021";
    public static final String KWAI_TYPE4 = "100020021";
    public static final String KWAI_TYPE5 = "103000021";
    /**
     * UserId
     */
    public static final String APP_ID = "userid";// APPID userid


    /**
     * UserId
     */
    public static final String APP_ID2 = "appid";// APPID userid


    /**
     * username
     */
    public static final String USER_NAME = "username";// APPID userid

    /**
     * app_type
     */
    public static final String APP_TYPE = "app_type";

    /**
     * apptype
     */
    public static final String APP_TYPE_2 = "apptype";

    /**
     * ADM_AT_VT 和 ADM_TERMINAL_VT
     */
    public static final String LAST_TIME = "last_time";

    /**
     * DWD_PER_PER_LOG_TR_VT_RT 表的lastTime
     */
    public static final String DISC_TIME = "DISC_TIME";
    /**
     * NB_MASS_RESOURCE_HTTP COOKIE
     */
    public static final String COOKIE = "cookie";

    /**
     * NB_MASS_RESOURCE_TERMINAL
     */
    public static final String APP_HARDWARE_FEATURE = "app_hardware_feature";
    /**
     * NB_MASS_RESOURCE_HTTP auth_account
     */
    public static final String AUTH_ACCOUNT = "auth_account";
    /**
     * 1天 单位 s
     */
    public static final long ONE_DAYS_TIME = 24 * 60 * 60 * 1;

    /**
     * 账号表 任务表 key
     */
    public static final String TASK_KEY = "task";

    /**
     * 账号表 关联表Key
     */
    public static final String TASK_RELATION_KEY = "task_relation";

    /**
     * 设备表 key
     */
    public static final String MAC_TASK_KEY = "mac_task";

    /**
     * 设备表 关联表Key
     */
    public static final String MAC_TASK_RELATION_KEY = "mac_task_relation";

    /**
     * http资源表 cookie
     */
    public static final String COOKIE_KEY = "cookie";

    public static final String APP_HARDWARE_FEATURE_KEY = "app_hardware_feature";
    /**
     * 是否LastTime
     *
     * @return
     */
    public static boolean isLastTime(String fieldName) {
        return Constant.LAST_TIME.equalsIgnoreCase(fieldName) || Constant.DISC_TIME.equalsIgnoreCase(fieldName);
    }

    /**
     * 是不是UserId
     * 新增字段appid
     *
     * @return
     */
    public static boolean isUserId(String fieldName) {
        return Constant.APP_ID.equalsIgnoreCase(fieldName) || Constant.APP_ID2.equalsIgnoreCase(fieldName);
    }

    /**
     * 是不是APPID
     *
     * @param fieldName
     * @return
     */

    public static boolean isUserName(String fieldName) {
        return Constant.USER_NAME.equalsIgnoreCase(fieldName);
    }

    /**
     * 是不是AppType
     */
    public static boolean isAppType(String fieldName) {
        return Constant.APP_TYPE.equalsIgnoreCase(fieldName) || Constant.APP_TYPE_2.equalsIgnoreCase(fieldName);
    }

    /**
     * 保留三种数据类型
     *
     * @param fieldName
     * @return
     */
    public static boolean filterAppType(String fieldName) {
        return Constant.WEIBO_TYPE.equals(fieldName) || Constant.RED_TYPE.equals(fieldName) || Constant.KWAI_TYPE.equals(fieldName) || Constant.NEW_WEIBO_TYPE.equalsIgnoreCase(fieldName)
                || Constant.ZHIHU_TYPE.equals(fieldName) || Constant.TOUTIAO_TYPE.equals(fieldName)  || Constant.BZHAN_TYPE.equals(fieldName)|| Constant.TUITE_TYPE.equals(fieldName)
                || Constant.DOUYIN_TYPE.equals(fieldName)||Constant.KWAI_TYPE2.equals(fieldName)||Constant.KWAI_TYPE3.equals(fieldName)||Constant.KWAI_TYPE4.equals(fieldName);
    }

}
