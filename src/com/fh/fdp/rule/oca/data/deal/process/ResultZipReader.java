package com.fh.fdp.rule.oca.data.deal.process;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import com.fh.fdp.rule.oca.data.conf.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import com.fh.fdp.rule.oca.data.tools.Encode;
import com.fh.fdp.rule.oca.data.tools.GsonUtil;
import com.fh.fdp.rule.oca.data.tools.StorageClient;
import com.fh.fitdataprep.biga.bean.BigaFile;
import com.fh.fitdataprep.biga.bean.DataField;
import com.fh.fitdataprep.biga.bean.DiskFile;
import com.fh.fitdataprep.biga.command.rule.RuleBaseCommand;
import com.fh.fitdataprep.biga.core.BigaConst;
import com.fh.fitdataprep.biga.core.stats.StatsReporter;
import com.fh.fitdataprep.biga.spi.Message;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.crypto.digest.MD5;

public class ResultZipReader extends RuleBaseCommand {
    private Config config = null;
    private StorageClient fsClient = null;

    @Override
    public void execute(Message msg) throws Exception {
        long now = Instant.now().getEpochSecond();
        logger.info("start zip reader . now: " + now);
        // 空包处理
        if (msg.getData() == null) {
            getExecutor().sendToNext(this, msg);
            return;
        }
        String key = config.getPasskey();
        Encode encoder = new Encode(key, logger);

        BigaFile zipFile = msg.getData();
        int totalcount = 0;
        Long totalFile = msg.getProperty(BigaConst.MSG_TOTAL_FILE, Long.class);
        String tmppath = config.getTmpPath() + File.separator + "OCA_ZIP_" + UUID.randomUUID().toString();
        new File(tmppath).mkdir();
        logger.info("init zip reader over.time:" + (Instant.now().getEpochSecond() - now));
        try {
            if (zipFile instanceof DiskFile) {
                ZipUtil.unzip(((DiskFile) zipFile).getFile(), new File(tmppath));
            } else {
                // 流文件 先落盘
                File diskFile = new File(tmppath + File.separator + "tmp.zip");
                FileUtils.writeByteArrayToFile(diskFile, IOUtils.toByteArray(zipFile.getInputStream()));
                ZipUtil.unzip(diskFile, new File(tmppath));
            }
            File[] filelist = new File(tmppath).listFiles();
            logger.info("unzip file over.time:" + (Instant.now().getEpochSecond() - now) + " and file size:"
                    + zipFile.length());
            List<GroupInfo> gis = new ArrayList<>();
            List<GroupMember> gms = new ArrayList<>();
            List<PersonInfo> pis = new ArrayList<>();

            boolean hasDomainTypeSina = false;
            Map<String, String> fileCache = new HashMap<>();
            CrawlerStatus status = new CrawlerStatus();
            String apptype = "";
            for (File f : filelist) {
                if (f.getName().endsWith(".zip")) {
                    // 落盘zip忽略
                    continue;
                }
                if (f.getName().equals("RESULT.json" +
                        "" +
                        "")) {
                    long time = Instant.now().getEpochSecond();
                    JsonReader jr = new JsonReader(new FileReader(f, StandardCharsets.UTF_8).getReader());
                    jr.setLenient(true);
                    JsonObject obj = JsonParser.parseReader(jr).getAsJsonObject();
                    if (obj.has("DomainId") && !obj.get("DomainId").isJsonNull()) {
                        apptype = obj.get("DomainId").getAsString();
                    } else {
                        logger.error("Result file donot contains domainid, no apptype,ignore and del file..");
                        break;
                    }
                    // 是否有微博类型
                    hasDomainTypeSina = obj.has("DomainType") && obj.get("DomainType").getAsInt() == 0;
                    if (obj.has("Data") && obj.get("Data").isJsonArray()) {
                        obj.get("Data").getAsJsonArray().forEach(ele -> {
                            String type = ele.getAsJsonObject().get("ResourceType").getAsString();
                            switch (type) {
                                case "OSS_IM_GROUPINFO": {
                                    ele.getAsJsonObject().get("ResourceContent").getAsJsonArray()
                                            .forEach(e -> gis.add(GsonUtil.fromJson(e, GroupInfo.class)));
                                    break;
                                }
                                case "OSS_COMMON_PERSONINFO": {
                                    ele.getAsJsonObject().get("ResourceContent").getAsJsonArray()
                                            .forEach(e -> pis.add(GsonUtil.fromJson(e, PersonInfo.class)));
                                    break;
                                }
                                case "OSS_IM_GROUPMEMBER": {
                                    ele.getAsJsonObject().get("ResourceContent").getAsJsonArray()
                                            .forEach(e -> gms.add(GsonUtil.fromJson(e, GroupMember.class)));
                                    break;
                                }
                                default: {
                                    logger.error(type + "  无法识别的数据类型,跳过");
                                    return;
                                }
                            }
                        });
                    }
                    logger.info("deal result.json over,cost:" + (Instant.now().getEpochSecond() - time));
                } else if (f.getName().endsWith(".json")) {
                    long time = Instant.now().getEpochSecond();
                    JsonReader jr = new JsonReader(new FileReader(f, StandardCharsets.UTF_8).getReader());
                    jr.setLenient(true);
                    JsonObject obj = JsonParser.parseReader(jr).getAsJsonObject();
                    if (obj.has("domain") && !obj.get("domain").isJsonNull()) {
                        apptype = obj.get("domain").getAsString();
                    } else {
                        status = null;
                        break;
                    }
                    status.setCollect_status(obj.get("result_code").getAsInt());
                    status.setUpdatetime(Instant.now().getEpochSecond());

                    String ruletype = obj.get("task_type").getAsString();
                    String rulevalue = obj.get("task_value").getAsString();
                    if ("10".equals(ruletype)) {
                        JsonObject valueObj = JsonParser.parseString(rulevalue).getAsJsonObject();
                        String lon = valueObj.get("longitude").getAsString();
                        String lat = valueObj.get("latitude").getAsString();
                        rulevalue = lon + "," + lat;
                    }
                    status.setApptype(apptype);
                    status.setMdid(
                            MD5.create().digestHex(apptype + "\t" + ruletype + "\t" + encoder.encode(rulevalue)));
                    logger.info("deal rule json over,cost:" + (Instant.now().getEpochSecond() - time));
                } else {
                    fileCache.put(f.getName(),
                            fsClient.sendFile(FileUtils.readFileToByteArray(f), f.getName(), "case"));
                    logger.debug("upload file:" + f.getName());
                }
            }
            logger.info("upload fs file over.time:" + (Instant.now().getEpochSecond() - now));
            // 分别发送到对应的11+1张表
            final String apt = apptype;
            if (!gis.isEmpty()) {
                List<List<DataField>> rows = new ArrayList<>();
                gis.forEach(gi -> {
                    List<DataField> row = new ArrayList<>();
                    new Gson().toJsonTree(gi).getAsJsonObject().entrySet().forEach(e -> {
                        if (e.getKey().equals("PortraitImage") && StringUtils.isNotBlank(e.getValue().getAsString())) {
                            String fsDir = fileCache.get(StringUtils.substringAfter(e.getValue().getAsString(), "/"));
                            row.add(new DataField(e.getKey(), fsDir));
                            logger.debug("transPic:" + e.getKey() + ":" + fsDir);
                        } else {
                            row.add(new DataField(e.getKey(), e.getValue().getAsString()));
                        }
                    });
                    row.add(new DataField("apptype",
                            config.getWaapptypes().get(getMapKeyByValue(config.getApptypes(), apt))));
                    rows.add(row);
                });
                totalcount += rows.size();
                sendMessage(msg, "adm_base_org_overseas_groupinfo", rows, 0);
                sendMessage(msg, "adm_rele_group_place_overseas_address_total", rows, 0);
                sendMessage(msg, "adm_rele_group_place_overseas_address_detail", rows, 0);
                sendMessage(msg, "oss_im_groupinfo_overseas", rows, 0);
            }
            if (!gms.isEmpty()) {
                List<List<DataField>> rows = new ArrayList<>();
                gms.forEach(data -> {
                    List<DataField> row = new ArrayList<>();
                    new Gson().toJsonTree(data).getAsJsonObject().entrySet().forEach(e -> {
                        if (e.getKey().equals("PortraitImage")) {
                            String fsDir = fileCache.get(StringUtils.substringAfter(e.getValue().getAsString(), "/"));
                            row.add(new DataField(e.getKey(), fsDir));
                            logger.debug("transPic:" + e.getKey() + ":" + fsDir);
                        } else {
                            row.add(new DataField(e.getKey(), e.getValue().getAsString()));
                        }
                    });
                    row.add(new DataField("apptype",
                            config.getWaapptypes().get(getMapKeyByValue(config.getApptypes(), apt))));
                    rows.add(row);
                });
                totalcount += rows.size();
                sendMessage(msg, "adm_base_org_overseas_gpmembers", rows, 0);
                sendMessage(msg, "oss_im_groupmember_overseas", rows, 0);
                sendMessage(msg, "adm_base_person_overseas_personinfo", rows, 0);
                sendMessage(msg, "oss_common_personinfo_overseas", rows, 0);
            }
            if (!pis.isEmpty()) {
                List<List<DataField>> rows = new ArrayList<>();
                boolean finalHasDomainTypeSina = hasDomainTypeSina;
                pis.forEach(data -> {
                    List<DataField> row = new ArrayList<>();
                    new Gson().toJsonTree(data).getAsJsonObject().entrySet().forEach(e -> {
                        if (e.getKey().equals("PortraitImage")) {
                            String fsDir = "";
                            if (finalHasDomainTypeSina) { // 境内没有 “/"
                                fsDir = fileCache.get(e.getValue().getAsString());
                            } else {
                                fsDir = fileCache.get(StringUtils.substringAfter(e.getValue().getAsString(), "/"));
                            }
                            row.add(new DataField(e.getKey(), fsDir));
                            logger.debug("transPic:" + e.getKey() + ":" + fsDir);
                        } else {
                            row.add(new DataField(e.getKey(), e.getValue().getAsString()));
                        }
                    });


                    if (finalHasDomainTypeSina) {
                        row.add(new DataField("apptype", apt));
                    } else {
                        row.add(new DataField("apptype",
                                config.getWaapptypes().get(getMapKeyByValue(config.getApptypes(), apt))));
                    }

                    rows.add(row);
                });
                totalcount += rows.size();

                // 境内输出4张表
                if (hasDomainTypeSina) {
                    sendMessage(msg, "adm_base_person_overseas_personinfo", rows, 0);
                    sendMessage(msg, "adm_base_person_domestic_personinfo", rows, 0);
                    sendMessage(msg, "adm_rele_person_person_overseas_relation_detail", rows, 0);
                    sendMessage(msg, "adm_rele_person_person_overseas_relation_total", rows, 0);
                } else {
                    sendMessage(msg, "adm_base_person_overseas_personinfo", rows, 0);
                    sendMessage(msg, "oss_common_personinfo_overseas", rows, 0);
                    sendMessage(msg, "adm_rele_person_person_overseas_relation_detail", rows, 0);
                    sendMessage(msg, "adm_rele_person_person_overseas_relation_total", rows, 0);
                    sendMessage(msg, "adm_rele_person_place_overseas_address_detail", rows, 0);
                    sendMessage(msg, "adm_rele_person_place_overseas_address_total", rows, 0);
                }
            }
            if (status != null && status.getMdid() != null) {
                List<List<DataField>> rows = new ArrayList<>();
                List<DataField> row = new ArrayList<>();
                new Gson().toJsonTree(status).getAsJsonObject().entrySet()
                        .forEach(e -> row.add(new DataField(e.getKey(), e.getValue().getAsString())));
                rows.add(row);
                totalcount += rows.size();
                sendMessage(msg, "RESP", rows, 0);
            }
            logger.info("send Message over.time:" + (Instant.now().getEpochSecond() - now));
            // 上报处理文件详情
            StatsReporter.reportInputFileCount(this, null, 1L, null, totalFile);
            StatsReporter.reportInputFileInfo(this, null, getTaskConfig().getSourceDefinition().getTableName(),
                    zipFile.getPath(), (long) totalcount, null, zipFile.length(), null, null);
            // 消费成功后的确认
            zipFile.onProcessSuccess();
            logger.info("exec zip reader over.time:" + (Instant.now().getEpochSecond() - now));
        } catch (Exception e) {
            logger.error("派大星解析文件内容异常,{}", e.getMessage(), e);
            StatsReporter.reportInputFileCount(this, null, null, 1L, totalFile);
        } finally {
            FileUtils.deleteDirectory(new File(tmppath));
        }
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

    private String getMapKeyByValue(Map<String, String> ori, String value) {
        Optional<Entry<String, String>> ew;
        if (value != null) {
            ew = ori.entrySet().stream().filter(e -> value.equals(e.getValue())).findFirst();
            if (ew.isPresent()) {
                return ew.get().getKey();
            }
        }
        return null;
    }

    @Override
    public void onInit() {
        long now = Instant.now().getEpochSecond();
        config = (Config) getExecutionContext().get("config");
        fsClient = new StorageClient(config.getFhfs(), logger);
        logger.info("init fsclient over.time:" + (Instant.now().getEpochSecond() - now));
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
