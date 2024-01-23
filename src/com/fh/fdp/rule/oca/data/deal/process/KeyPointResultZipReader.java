package com.fh.fdp.rule.oca.data.deal.process;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.crypto.digest.MD5;
import com.fh.fdp.rule.oca.data.conf.*;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.CollectField;
import com.fh.fdp.rule.oca.data.tools.Encode;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import com.fh.fdp.rule.oca.data.tools.StorageClient;
import com.fh.fitdataprep.biga.bean.BigaFile;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.core.BigaConst;
import com.fh.fitdataprep.biga.core.stats.StatsReporter;
import com.fh.fitdataprep.biga.spi.Message;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

public class KeyPointResultZipReader extends RuleBaseCommand {
    private Config config = null;
    private StorageClient fsClient = null;

    @Override
    public void execute(Message msg) throws Exception {
        long now = Instant.now().getEpochSecond();
        logger.info("start zip reader . now: {}", now);
        // 空包处理
        if (msg.getData() == null) {
            getExecutor().sendToNext(this, msg);
            return;
        }
        String key = config.getPasskey();
        Encode encoder = new Encode(key,logger);
        BigaFile zipFile = msg.getData();
        int totalcount = 0;
        Long totalFile = msg.getProperty(BigaConst.MSG_TOTAL_FILE, Long.class);
        String tmppath = config.getTmpPath() + File.separator + "OTHER_ZIP_" + UUID.randomUUID().toString();
        new File(tmppath).mkdir();
        logger.info("init zip reader over.time:{}", (Instant.now().getEpochSecond() - now));
        try {
            ResultZipReader.zipUnzip(zipFile, tmppath);
            logger.info("unzip file over.time:{}and file size:{}", (Instant.now().getEpochSecond() - now),
                    zipFile.length());
            // 重点目标文件协议
            List<WebPost> wbo = new ArrayList<>();
            List<GroupChat> cis = new ArrayList<>();
            List<GroupInfo> gis = new ArrayList<>();
            List<GroupMember> gms = new ArrayList<>();
            Map<String, String> fileCache = new HashMap<>();
            CrawlerStatus status = new CrawlerStatus();
            File[] fileList = new File(tmppath).listFiles();
            assert fileList != null;
            for (File f : fileList) {
                if (f.getName().endsWith(".zip")) {
                    // 落盘zip忽略
                    continue;
                }
                if (f.getName().equals("RESULT.json")) {
                    long time = Instant.now().getEpochSecond();
                    JsonReader jr = new JsonReader(new FileReader(f, StandardCharsets.UTF_8).getReader());
                    jr.setLenient(true);
                    JsonObject obj = JsonParser.parseReader(jr).getAsJsonObject();
                    if (!obj.has(CollectField.DOMAIN_ID) || obj.get(CollectField.DOMAIN_ID).isJsonNull()) {  // 获取appType
                        logger.error("Result file donot contains domainid, no apptype,ignore and del file..");
                        break;
                    }
                    String appType = obj.has(CollectField.DOMAIN_ID) ? obj.get(CollectField.DOMAIN_ID).getAsString() : "";
                    String businessTag = obj.has(CollectField.BUSINESS_TAG) ? obj.get(CollectField.BUSINESS_TAG).getAsString() : "";
                    String clueId = obj.has(CollectField.CLUE_ID) ? obj.get(CollectField.CLUE_ID).getAsString() : "";

                    if (obj.has(CollectField.DATA) && obj.get(CollectField.DATA).isJsonArray()) {
                        obj.get(CollectField.DATA).getAsJsonArray().forEach(ele -> {
                            String type = ele.getAsJsonObject().get(CollectField.RESOURCE_TYPE).getAsString();
                            switch (type) {
                                case "OSS_BBS_WEBPOST":
                                    ele.getAsJsonObject().get(CollectField.RESOURCE_CONTENT).getAsJsonArray()
                                            .forEach(e -> {
                                                WebPost webPost = GsonUtil.fromJson(e, WebPost.class);
                                                webPost.setBusinessTag(businessTag);
                                                webPost.setClueId(clueId);
                                                webPost.setAppType(appType);
                                                wbo.add(webPost);
                                            });
                                    break;
                                case "OSS_IM_GROUPINFO":
                                    ele.getAsJsonObject().get(CollectField.RESOURCE_CONTENT).getAsJsonArray()
                                            .forEach(e -> {
                                                GroupInfo groupInfo = GsonUtil.fromJson(e, GroupInfo.class);
                                                groupInfo.setBusinessTag(businessTag);
                                                groupInfo.setClueId(clueId);
                                                groupInfo.setAppType(appType);
                                                gis.add(groupInfo);
                                            });
                                    break;
                                case "OSS_IM_GROUPMEMBER":
                                    ele.getAsJsonObject().get(CollectField.RESOURCE_CONTENT).getAsJsonArray()
                                            .forEach(e -> {
                                                GroupMember groupMember = GsonUtil.fromJson(e, GroupMember.class);
                                                groupMember.setBusinessTag(businessTag);
                                                groupMember.setClueId(clueId);
                                                groupMember.setAppType(appType);
                                                gms.add(groupMember);
                                            });
                                    break;
                                case "OSS_IM_GROUPCHAT":
                                    ele.getAsJsonObject().get(CollectField.RESOURCE_CONTENT).getAsJsonArray()
                                            .forEach(e -> {
                                                GroupChat groupChat = GsonUtil.fromJson(e, GroupChat.class);
                                                groupChat.setBusinessTag(businessTag);
                                                groupChat.setClueId(clueId);
                                                groupChat.setAppType(appType);
                                                cis.add(groupChat);
                                            });
                                    break;
                                default:
                                    logger.error("无法识别的数据类型,跳过{}", type);
                            }
                        });
                    }
                    logger.info("deal result.json over,cost:{}", (Instant.now().getEpochSecond() - time));
                } else if (f.getName().endsWith(".json")) {
                    long time = Instant.now().getEpochSecond();
                    JsonReader jr = new JsonReader(new FileReader(f, StandardCharsets.UTF_8).getReader());
                    jr.setLenient(true);
                    JsonObject obj = JsonParser.parseReader(jr).getAsJsonObject();
                    String apptype = "";
                    if (obj.has(CollectField.DOMAIN) && !obj.get(CollectField.DOMAIN).isJsonNull()) {
                        apptype = obj.get(CollectField.DOMAIN).getAsString();
                    } else {
                        break;
                    }
                    status.setCollectStatus(obj.get(CollectField.RESULT_CODE).getAsInt());
                    status.setUpdatetime(Instant.now().getEpochSecond());

                    String ruletype = obj.get(CollectField.TASK_TYPE).getAsString();
                    String rulevalue = obj.get(CollectField.TASK_VALUE).getAsString();
                    if ("10".equals(ruletype)) {
                        JsonObject valueObj = JsonParser.parseString(rulevalue).getAsJsonObject();
                        String lon = valueObj.get(CollectField.LONGITUDE).getAsString();
                        String lat = valueObj.get(CollectField.LATITUDE).getAsString();
                        rulevalue = lon + "," + lat;
                    }
                    status.setApptype(apptype);
                    status.setMdid(
                            MD5.create().digestHex(apptype + "\t" + ruletype + "\t" + encoder.encode(rulevalue)));
                    logger.info("deal rule json over,cost{}", (Instant.now().getEpochSecond() - time));
                } else {
                    fileCache.put(f.getName(),
                            fsClient.sendFile(FileUtils.readFileToByteArray(f), f.getName(), "case"));
                    logger.info("upload file{}", f.getName());
                }
            }
            logger.info("upload fs file over.time{}", (Instant.now().getEpochSecond() - now));
            if (!wbo.isEmpty()) {
                List<List<DataField>> rows = new ArrayList<>();
                wbo.forEach(wb -> {
                    List<DataField> row = new ArrayList<>();
                    new Gson().toJsonTree(wb).getAsJsonObject().entrySet().forEach(e -> {
                        if (e.getKey().equals(CollectField.PORTRAIT_IMAGE) && StringUtils.isNotBlank(e.getValue().getAsString())) {
                            String fsDir = fileCache.get(StringUtils.substringAfter(e.getValue().getAsString(), "/"));
                            row.add(new DataField(e.getKey(), fsDir));
                            logger.debug("transPic :{}:{}", e.getKey(), fsDir);
                        } else {
                            row.add(new DataField(e.getKey(), e.getValue().getAsString()));
                        }
                    });

                    rows.add(row);
                });
                totalcount += rows.size();
                sendMessage(msg, "oss_bbs_webpost", rows, 0);
            }
            if (!gis.isEmpty()) {
                List<List<DataField>> rows = new ArrayList<>();
                gis.forEach(data -> {
                    List<DataField> row = new ArrayList<>();
                    new Gson().toJsonTree(data).getAsJsonObject().entrySet().forEach(e -> {
                        if (e.getKey().equals(CollectField.PORTRAIT_IMAGE)) {
                            String fsDir = fileCache.get(StringUtils.substringAfter(e.getValue().getAsString(), "/"));
                            row.add(new DataField(e.getKey(), fsDir));
                            logger.debug("transPic:{}: {}", e.getKey(), fsDir);
                        } else {
                            row.add(new DataField(e.getKey(), e.getValue().getAsString()));
                        }
                    });

                    rows.add(row);
                });
                totalcount += rows.size();
                sendMessage(msg, "oss_im_groupinfo", rows, 0);
            }
            if (!gms.isEmpty()) {
                List<List<DataField>> rows = new ArrayList<>();
                gms.forEach(data -> {
                    List<DataField> row = new ArrayList<>();
                    new Gson().toJsonTree(data).getAsJsonObject().entrySet().forEach(e -> {
                        if (e.getKey().equals(CollectField.PORTRAIT_IMAGE)) {
                            String fsDir = fileCache.get(StringUtils.substringAfter(e.getValue().getAsString(), "/"));
                            row.add(new DataField(e.getKey(), fsDir));
                            logger.debug("transPic:{}:{}", e.getKey(), fsDir);
                        } else {
                            row.add(new DataField(e.getKey(), e.getValue().getAsString()));
                        }
                    });
                    rows.add(row);
                });
                totalcount += rows.size();
                sendMessage(msg, "oss_im_groupmember", rows, 0);
            }
            if (!cis.isEmpty()) {
                List<List<DataField>> rows = new ArrayList<>();
                cis.forEach(data -> {
                    List<DataField> row = new ArrayList<>();
                    new Gson().toJsonTree(data).getAsJsonObject().entrySet().forEach(e -> {
                        if (e.getKey().equals(CollectField.MAIN_FILE)) {
                            String fsDir = fileCache.get(StringUtils.substringAfterLast(e.getValue().getAsString(), "/"));
                            row.add(new DataField(e.getKey(), fsDir));
                            logger.debug("transPic:{}:{}", e.getKey(), fsDir);
                        } else {
                            row.add(new DataField(e.getKey(), e.getValue().getAsString()));
                        }
                    });

                    rows.add(row);
                });
                totalcount += rows.size();
                sendMessage(msg, "oss_im_groupchat", rows, 0);
            }
            logger.info("send Message over.time {}", (Instant.now().getEpochSecond() - now));
            // 上报处理文件详情
            StatsReporter.reportInputFileCount(this, null, 1L, null, totalFile);
            StatsReporter.reportInputFileInfo(this, null, getTaskConfig().getSourceDefinition().getTableName(),
                    zipFile.getPath(), (long) totalcount, null, zipFile.length(), null, null);
            // 消费成功后的确认
            zipFile.onProcessSuccess();
            logger.info("exec zip reader over.time{}", (Instant.now().getEpochSecond() - now));
        } catch (Exception e) {
            logger.error("派大星解析文件内容异常,{}", e.getMessage(), e);
            StatsReporter.reportInputFileCount(this, null, null, 1L, totalFile);
        }
        FileUtils.deleteDirectory(new File(tmppath));
    }

    private void sendMessage(Message msg, String tablename, List<List<DataField>> rows, int failCount) {
        // 构造消息
        Message message = msg.copyWithoutData();
        // 设置表名
        message.setProperty("tableName", tablename);
        // 放置数据
        message.setData(rows);
        // 记台账
        StatsReporter.reportInputRowCount(this, null, (long) rows.size(), (long) failCount, null);
        // 发给后续节点
        try {
            getExecutor().sendToNext(this, message);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public void onInit() {
        long now = Instant.now().getEpochSecond();
        config = (Config) getExecutionContext().get("config");
        fsClient = new StorageClient(config.getFhfs(), logger);
        logger.info("init fsclient over.time{}", (Instant.now().getEpochSecond() - now));
    }

    @Override
    public void onStop() {
        try {
            if (fsClient != null) {
                fsClient.destroy();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

}
