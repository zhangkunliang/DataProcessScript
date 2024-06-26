package com.fh.fdp.rule.oca.data.deal.process;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.crypto.digest.MD5;
import com.fh.fdp.rule.oca.data.conf.*;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.CluesTask;
import com.fh.fdp.rule.oca.data.deal.domestic.utils.CollectField;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.Map.Entry;

public class ResultZipReader extends RuleBaseCommand {
    private Config config = null;
    private StorageClient fsClient = null;

    @Override
    public void execute(Message msg) throws Exception {
        long now = Instant.now().getEpochSecond();
        logger.info("start zip reader . now:{} ", now);
        // 空包处理
        if (msg.getData() == null) {
            getExecutor().sendToNext(this, msg);
            return;
        }
        String key = config.getPasskey();
        //
        Encode encoder = new Encode(key,logger);

        BigaFile zipFile = msg.getData();
        int totalcount = 0;
        Long totalFile = msg.getProperty(BigaConst.MSG_TOTAL_FILE, Long.class);
        String tmppath = config.getTmpPath() + File.separator + "OCA_ZIP_" + UUID.randomUUID().toString();
        new File(tmppath).mkdir();
        logger.info("init zip reader over.time{}", (Instant.now().getEpochSecond() - now));
        try {
            zipUnzip(zipFile, tmppath);
            logger.info("unzip file over.time:{} and file size:{}", (Instant.now().getEpochSecond() - now), zipFile.length());
            List<GroupInfo> gis = new ArrayList<>();
            List<GroupMember> gms = new ArrayList<>();
            List<PersonInfo> pis = new ArrayList<>();
            boolean hasDomainTypeSina = false;
            Map<String, String> fileCache = new HashMap<>();
            CrawlerStatus status = new CrawlerStatus();
            String apptype = "";
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
                    if (obj.has(CollectField.DOMAIN_ID) && !obj.get(CollectField.DOMAIN_ID).isJsonNull()) {
                        apptype = obj.get(CollectField.DOMAIN_ID).getAsString();
                    } else {
                        logger.error("Result file do not contains domainid, no apptype,ignore and del file..");
                        break;
                    }
                    // 是否有微博类型
                    hasDomainTypeSina = obj.has(CollectField.DOMAIN_TYPE) && !obj.get(CollectField.DOMAIN_TYPE).isJsonNull() && obj.get(CollectField.DOMAIN_TYPE).getAsInt() == 0;
                    if (obj.has(CollectField.DATA) && obj.get(CollectField.DATA).isJsonArray()) {
                        obj.get(CollectField.DATA).getAsJsonArray().forEach(ele -> {
                            String type = ele.getAsJsonObject().get(CollectField.RESOURCE_TYPE).getAsString();
                            switch (type) {
                                case "OSS_IM_GROUPINFO":
                                    ele.getAsJsonObject().get(CollectField.RESOURCE_CONTENT).getAsJsonArray()
                                            .forEach(e -> gis.add(GsonUtil.fromJson(e, GroupInfo.class)));
                                    break;
                                case "OSS_COMMON_PERSONINFO":
                                    ele.getAsJsonObject().get(CollectField.RESOURCE_CONTENT).getAsJsonArray()
                                            .forEach(e -> pis.add(GsonUtil.fromJson(e, PersonInfo.class)));
                                    break;
                                case "OSS_IM_GROUPMEMBER":
                                    ele.getAsJsonObject().get(CollectField.RESOURCE_CONTENT).getAsJsonArray()
                                            .forEach(e -> gms.add(GsonUtil.fromJson(e, GroupMember.class)));
                                    break;
                                default:
                                    logger.error("无法识别的数据类型,跳过{}", type);
                                    break;
                            }
                        });
                    }
                    logger.info("deal result.json over,cost{}", (Instant.now().getEpochSecond() - time));
                } else if (f.getName().endsWith(".json")) {
                    long time = Instant.now().getEpochSecond();
                    JsonReader jr = new JsonReader(new FileReader(f, StandardCharsets.UTF_8).getReader());
                    jr.setLenient(true);
                    JsonObject obj = JsonParser.parseReader(jr).getAsJsonObject();
                    if (obj.has(CollectField.DOMAIN) && !obj.get(CollectField.DOMAIN).isJsonNull()) {
                        apptype = obj.get(CollectField.DOMAIN).getAsString();
                    } else {
                        status = null;
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
            // 分别发送到对应的11+1张表
            final String apt = apptype;
            if (!gis.isEmpty()) {
                List<List<DataField>> rows = new ArrayList<>();
                gis.forEach(gi -> {
                    List<DataField> row = new ArrayList<>();
                    new Gson().toJsonTree(gi).getAsJsonObject().entrySet().forEach(e -> {
                        if (e.getKey().equals(CollectField.PORTRAIT_IMAGE) && StringUtils.isNotBlank(e.getValue().getAsString())) {
                            String fsDir = fileCache.get(StringUtils.substringAfter(e.getValue().getAsString(), "/"));
                            row.add(new DataField(e.getKey(), fsDir));
                            logger.info("transPic: {} : {} ", e.getKey(), fsDir);
                        } else {
                            row.add(new DataField(e.getKey(), e.getValue().getAsString()));
                        }
                    });
                    row.add(new DataField(CluesTask.APPTYPE,
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
                        if (e.getKey().equals(CollectField.PORTRAIT_IMAGE)) {
                            String fsDir = fileCache.get(StringUtils.substringAfter(e.getValue().getAsString(), "/"));
                            row.add(new DataField(e.getKey(), fsDir));
                            logger.info("transPic:{}:{}", e.getKey(), fsDir);
                        } else {
                            row.add(new DataField(e.getKey(), e.getValue().getAsString()));
                        }
                    });
                    row.add(new DataField(CluesTask.APPTYPE,
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
                        if (e.getKey().equals(CollectField.PORTRAIT_IMAGE)) {
                            String fsDir = "";
                            if (finalHasDomainTypeSina) { // 境内没有 “/"
                                fsDir = fileCache.get(e.getValue().getAsString());
                            } else {
                                fsDir = fileCache.get(StringUtils.substringAfter(e.getValue().getAsString(), "/"));
                            }
                            row.add(new DataField(e.getKey(), fsDir));
                            logger.info("transPic:{}:{}", e.getKey(), fsDir);
                        } else {
                            row.add(new DataField(e.getKey(), e.getValue().getAsString()));
                        }
                    });


                    if (finalHasDomainTypeSina) {
                        row.add(new DataField(CluesTask.APPTYPE, apt));
                    } else {
                        row.add(new DataField(CluesTask.APPTYPE,
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
                    sendMessage(msg, "oss_common_personinfo_overseas", rows, 0);
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
            logger.info("send Message over.time{}", (Instant.now().getEpochSecond() - now));
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

    static void zipUnzip(BigaFile zipFile, String tmppath) throws IOException {
        if (zipFile instanceof DiskFile) {
            ZipUtil.unzip(((DiskFile) zipFile).getFile(), new File(tmppath));
        } else {
            // 流文件 先落盘
            File diskFile = new File(tmppath + File.separator + "tmp.zip");
            FileUtils.writeByteArrayToFile(diskFile, IOUtils.toByteArray(zipFile.getInputStream()));
            ZipUtil.unzip(diskFile, new File(tmppath));
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
