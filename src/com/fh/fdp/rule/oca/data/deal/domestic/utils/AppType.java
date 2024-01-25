package com.fh.fdp.rule.oca.data.deal.domestic.utils;

/**
 * 应用类型字段
 * atvt线索抽取
 * @author zkl
 */
public enum AppType {
    // 用于atvt线索抽取
    ZHIHU_TYPE("100000074"),  // 知乎新海量
    OLD_ZHIHU_TYPE1("1079899"),  // 知乎老海量
    OLD_ZHIHU_TYPE2("1271081"),  // 知乎老海量
    OLD_ZHIHU_TYPE3("9949346"),  // 知乎老海量
    OLD_ZHIHU_TYPE4("1429758"),  // 知乎老海量
    TOUTIAO_TYPE("100000007"),  //今日头条新海量
    OLD_TOUTIAO_TYPE1("1072099"),  //今日头条老海量
    OLD_TOUTIAO_TYPE2("1389981"),  //今日头条老海量
    WEIBO_TYPE("1330001"),  //老海量微博
    WEIBO_TYPE1("1424084"),  //老海量微博
    WEIBO_TYPE2("9940084"),  //老海量微博
    WEIBO_TYPE3("1030203"),  //老海量微博
    NEW_WEIBO_TYPE("100000006"),  //新海量微博
    RED_TYPE("100000152"),  //新海量小红书
    OLD_RED_TYPE1("1037825"),  //老海量小红书
    OLD_RED_TYPE2("119ARPJ"),  //老海量小红书
    KWAI_TYPE("100000021"),// 新海量快手
    KWAI_TYPE1("1199905"),//老海量快手
    KWAI_TYPE2("9941069"),//老海量快手
    KWAI_TYPE3("1425381"),//老海量快手
    KWAI_TYPE4("1409959");//老海量快手

    private final String typeCode;

    AppType(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getAppType() {
        return typeCode;
    }

    public static boolean filterAppType(String fieldName) {
        for (AppType appType : values()) {
            if (appType.getAppType().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
    public static boolean filterWbBzKsAppType(String fieldName){
        return WEIBO_TYPE.getAppType().equals(fieldName) || WEIBO_TYPE1.getAppType().equals(fieldName) || WEIBO_TYPE2.getAppType().equals(fieldName)
                || WEIBO_TYPE3.getAppType().equals(fieldName)|| NEW_WEIBO_TYPE.getAppType().equals(fieldName)
                ||Constant.BZHAN_TYPE.equals(fieldName)||Constant.OLD_BZHAN_TYPE1.equals(fieldName)
                || KWAI_TYPE.getAppType().equals(fieldName)|| KWAI_TYPE1.getAppType().equals(fieldName)|| KWAI_TYPE2.getAppType().equals(fieldName)
                || KWAI_TYPE3.getAppType().equals(fieldName)|| KWAI_TYPE4.getAppType().equals(fieldName);
    }
}
