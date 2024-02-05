package com.fh.fdp.rule.oca.data.conf;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.fh.fdp.rule.oca.data.deal.domestic.utils.Constant;
import org.slf4j.Logger;

import com.google.gson.JsonParser;

public class Config {

    private String apolloHost;

    private String tmpPath;
    private Set<String> enableAppType = new HashSet<>();


    private String localCode;

    private Map<String, String> apptypes = new HashMap<>();
    private Map<String, String> waapptypes = new HashMap<>();
    private String passkey;

    private String fhfs;

    private String redisIp;
    private int redisPort;
    private String redisPass;
    private int expandTime;
    private int adCacheDB;
    private int mobileCacheDB;
    private int adAddressCacheDB;
    private int countCache;
    private int taskCache;

    /**
     * 境内配置
     */
    /**
     * 境内过期时间
     */
    private int expandDomestic;

    /**
     * 主表
     */
    private int atvtCache;

    /**
     * 关联表
     */
    private int atvtCacheRelation;

    public int getAdAddressCacheDB() {
        return adAddressCacheDB;
    }

    public void setAdAddressCacheDB(int adAddressCacheDB) {
        this.adAddressCacheDB = adAddressCacheDB;
    }

    public int getAdCacheDB() {
        return adCacheDB;
    }

    public void setAdCacheDB(int adCacheDB) {
        this.adCacheDB = adCacheDB;
    }

    public int getMobileCacheDB() {
        return mobileCacheDB;
    }

    public void setMobileCacheDB(int mobileCacheDB) {
        this.mobileCacheDB = mobileCacheDB;
    }

    public Map<String, String> getApptypes() {
        return apptypes;
    }

    public void setApptypes(Map<String, String> apptypes) {
        this.apptypes = apptypes;
    }

    public String getPasskey() {
        return passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }

    public String getRedisIp() {
        return redisIp;
    }

    public void setRedisIp(String redisIp) {
        this.redisIp = redisIp;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisPass() {
        return redisPass;
    }

    public void setRedisPass(String redisPass) {
        this.redisPass = redisPass;
    }

    public int getExpandTime() {
        return expandTime * (int) Constant.ONE_DAYS_TIME;
    }

    public void setExpandTime(int expandTime) {
        this.expandTime = expandTime;
    }

    public String getFhfs() {
        return fhfs;
    }

    public void setFhfs(String fhfs) {
        this.fhfs = fhfs;
    }

    public Map<String, String> getWaapptypes() {
        return waapptypes;
    }

    public void setWaapptypes(Map<String, String> waapptypes) {
        this.waapptypes = waapptypes;
    }

    public int getCountCache() {
        return countCache;
    }

    public void setCountCache(int countCache) {
        this.countCache = countCache;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public int getTaskCache() {
        return taskCache;
    }

    public void setTaskCache(int taskCache) {
        this.taskCache = taskCache;
    }

    public String getTmpPath() {
        return tmpPath;
    }

    public void setTmpPath(String tmpPath) {
        this.tmpPath = tmpPath;
    }

    public Set<String> getEnableAppType() {
        return enableAppType;
    }

    public void setEnableAppType(Set<String> enableAppType) {
        this.enableAppType = enableAppType;
    }

    public int getExpandDomestic() {
        return expandDomestic;
    }

    public void setExpandDomestic(int expandDomestic) {
        this.expandDomestic = expandDomestic;
    }

    public int getAtvtCache() {
        return atvtCache;
    }

    public void setAtvtCache(int atvtCache) {
        this.atvtCache = atvtCache;
    }

    public int getAtvtCacheRelation() {
        return atvtCacheRelation;
    }

    public void setAtvtCacheRelation(int atvtCacheRelation) {
        this.atvtCacheRelation = atvtCacheRelation;
    }

    public String getApolloHost() {
        return apolloHost;
    }

    public void setApolloHost(String apolloHost) {
        this.apolloHost = apolloHost;
    }

    public Config(InputStream io, Logger logger) {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(io, StandardCharsets.UTF_8));

            String apolloHostStr = prop.getProperty("APOLLO_HOST", "");
            String codeStr = prop.getProperty("LOCAL_AREACODE", "");
            String yqAppStr = prop.getProperty("YQ_APPTYPE", "");
            String waAPPStr = prop.getProperty("WA_APPTYPE", "");
            String enableAppStr = prop.getProperty("ENABLE_APPTYPE", "");
            String passStr = prop.getProperty("AES_PASSKEY", "");
            String pathStr = prop.getProperty("TMP_PATH", "");
            String fhfsStr = prop.getProperty("FHFS_HOST", "");
            String redisIPStr = prop.getProperty("REDIS_HOST", "");
            String redisPortStr = prop.getProperty("REDIS_PORT", "");
            String redisPassStr = prop.getProperty("REDIS_PASSWD", "");
            String redisExpStr = prop.getProperty("REDIS_EXPIRE_DAY", "");
            String redisAdAddStr = prop.getProperty("REDIS_AD_ADDRESS_DB", "");
            String redisAdTaskStr = prop.getProperty("REDIS_AD_TASK_DB", "");
            String redisPhoneTaskStr = prop.getProperty("REDIS_PHONE_TASK_DB", "");
            String redisPointCountStr = prop.getProperty("REDIS_POINT_COUNT_DB", "");
            String redisTaskCacheStr = prop.getProperty("REDIS_TASK_CACHE_DB", "");

            String redisExpandDomesticStr = prop.getProperty("REDIS_EXPAND_DOMESTIC", "");
            String redisAtvtDbStr = prop.getProperty("REDIS_ATVT_DB", "");
            String redisAtvtRelationStr = prop.getProperty("REDIS_ATVT_RELATION", "");

            this.apolloHost = apolloHostStr;
            this.tmpPath = pathStr;
            this.localCode = codeStr;
            this.passkey = passStr;
            this.fhfs = fhfsStr;
            this.redisIp = redisIPStr;
            this.redisPass = redisPassStr;
            this.redisPort = Integer.parseInt(redisPortStr);
            this.expandTime = Integer.parseInt(redisExpStr);
            this.adCacheDB = Integer.parseInt(redisAdTaskStr);
            this.adAddressCacheDB = Integer.parseInt(redisAdAddStr);
            this.mobileCacheDB = Integer.parseInt(redisPhoneTaskStr);
            this.countCache = Integer.parseInt(redisPointCountStr);
            this.taskCache = Integer.parseInt(redisTaskCacheStr);
            // 境内塞值
            this.expandDomestic = Integer.parseInt(redisExpandDomesticStr);
            this.atvtCache = Integer.parseInt(redisAtvtDbStr);
            this.atvtCacheRelation = Integer.parseInt(redisAtvtRelationStr);

            JsonParser.parseString(enableAppStr).getAsJsonArray().forEach(ele -> enableAppType.add(ele.getAsString()));
            JsonParser.parseString(yqAppStr).getAsJsonArray().forEach(ele -> ele.getAsJsonObject().keySet()
                    .forEach(k -> apptypes.put(k, ele.getAsJsonObject().get(k).getAsString())));
            JsonParser.parseString(waAPPStr).getAsJsonArray().forEach(ele -> ele.getAsJsonObject().keySet()
                    .forEach(k -> waapptypes.put(k, ele.getAsJsonObject().get(k).getAsString())));

        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
