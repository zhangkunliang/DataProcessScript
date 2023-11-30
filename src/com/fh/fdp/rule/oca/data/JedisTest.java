package com.fh.fdp.rule.oca.data;

import cn.hutool.core.io.IoUtil;

import cn.hutool.crypto.digest.MD5;
import com.fh.fdp.rule.oca.data.conf.Config;
import com.fh.fdp.rule.oca.data.conf.GroupInfo;
import com.fh.fdp.rule.oca.data.conf.PersonInfo;
import com.fh.fdp.rule.oca.data.conf.UserIdAdCache;
import com.fh.fdp.rule.oca.data.deal.init.Init;
import com.fh.fdp.rule.oca.data.tools.Encode;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import com.fh.fdp.rule.oca.data.tools.JedisUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JedisTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisTest.class);
    public static void main(String[] args) {

        JedisUtil jedisUtil = null;

        System.out.println(System.currentTimeMillis()/1000);
        System.out.println((System.currentTimeMillis() / 1000 - 1686650043));
        long sevenDaysTime = 7 * 24 * 60 * 60 ;
        //1687343535
        //1687082043
        System.out.println((System.currentTimeMillis() / 1000 - 1687082043 > sevenDaysTime));
        Config conf = new Config(Init.class.getClassLoader().getResourceAsStream("data.properties"), LOGGER);
        System.out.println(conf.getExpandDomestic());
        jedisUtil = new JedisUtil(conf.getRedisIp(), conf.getRedisPort(), conf.getRedisPass(),LOGGER);

        Map<String, String> cache = new HashMap<>();
        cache.put("userid", "2a39d6239ce0c46bd8d7485ed66ef041");
//        cache.put("cookie","JSESSIONID = A98ED4F92616D3A");
        cache.put("auth_type", "1000");
        cache.put("auth_account", "48996789");
        System.out.println(GsonUtil.toStr(cache));
//        jedisUtil.get("sssss",14);
        jedisUtil.add(cache.get("userid"), GsonUtil.toStr(cache), 36000, 14);
//        jedisUtil.add(cache.get("userid"), GsonUtil.toStr(cache), 36000, 14);
//        String res = JedisUtil.getInstance().get("38044b650861b18128056731c0ca40cf", conf.getAtvtCache());
//        System.out.println("res"+res);
//        jedisUtil.flashDb(conf.getAtvtCache());
//        jedisUtil.flashDb(conf.getAtvtCacheRelation());

       // jedisTestUserID(jedisUtil,"1779389333",conf);
       // gsonTest();
//        jedisTestUserID(jedisUtil,"7818907874",conf);
      //  System.out.println(JedisUtil.getInstance().get("123456", conf.getAtvtCache()));
//        String res = JedisUtil.getInstance().get("960a47c37c824832f8ca73ff768a04d1", conf.getAtvtCache());
//        System.out.println(res);
//        CrawlerStatus status = GsonUtil.fromJson(res, CrawlerStatus.class);
//        System.out.println(GsonUtil.toStr(status));

        //jedisTestTime("charlie",conf);


    }

    public static void main111(String[] args) {
        Map<String, String> cache = new HashMap<>();
        cache.put("userid", "3945476488167695");
        cache.put("auth_type", "1007");
        cache.put("auth_account", "456789451245");
        System.out.println(GsonUtil.toStr(cache));
    }




    public static void jedisTestUserID(JedisUtil jedisUtil,String key ,Config config){
        boolean isExitId = jedisUtil.exist(key, config.getAtvtCache());
        if (!isExitId) {
            System.out.println("么有key");
          //  JedisUtil.getInstance().add(key, String.valueOf(System.currentTimeMillis() / 1000), 60, config.getAtvtCacheRelation());

        }else {
            String res = jedisUtil.get(key,config.getAtvtCache());
            System.out.println("有key"+res);
            UserIdAdCache userIdAdCache = GsonUtil.fromJson(res,UserIdAdCache.class);
            System.out.println(userIdAdCache.toString());

        }
    }

    public static void gsonTest(){

        System.out.println(GsonUtil.toStr(new PersonInfo()));

         Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
        System.out.println( gson.toJson((new PersonInfo())));


    }

    public static void testMdid(){

        Config conf = new Config(Init.class.getClassLoader().getResourceAsStream("data.properties"), LOGGER);
        Encode encoder = new Encode(conf.getPasskey(), LOGGER);
       // System.out.println(MD5.create().digestHex("100000006" + "\t" + "2" + "\t" + encoder.encode("3245164370")));
    }
}
