package com.fh.fdp.rule.oca.data.conf;

import com.google.gson.annotations.SerializedName;

/**
 * 群聊消息
 */
public class GroupChat {
    @SerializedName("GroupID")
    private String groupID;
    @SerializedName("GroupName")
    private String groupName;
    @SerializedName("GroupType")
    private String groupType;
    @SerializedName("MsgID")
    private String msgID;
    @SerializedName("SendUserID")
    private String sendUserID;
    @SerializedName("SendUserSign")
    private String sendUserSign;
    @SerializedName("SendUserName")
    private String sendUserName;
    @SerializedName("SendUserNickName")
    private String sendUserNickName;
    @SerializedName("PostTime")
    private long postTime;
    @SerializedName("Msgtype")
    private String msgtype;
    @SerializedName("Content")
    private String content;
    @SerializedName("FileSize")
    private String fileSize;
    @SerializedName("FileMd5")
    private String fileMd5;
    @SerializedName("MainFile")
    private String mainFile;
    @SerializedName("VoiceLength")
    private String voiceLength;
    @SerializedName("ActionType")
    private String actionType;
    @SerializedName("ReplyToMsgId")
    private String replyToMsgId;
    @SerializedName("GroupedId")
    private String groupedId;
    @SerializedName("CollectTime")
    private long collectTime;
    @SerializedName("AppType")
    private String appType;
    @SerializedName("CLUE_ID")
    private String clueId;
    @SerializedName("BUSINESS_TAG")
    private String businessTag;
    @SerializedName("LanguageType")
    private String languageType;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getSendUserID() {
        return sendUserID;
    }

    public void setSendUserID(String sendUserID) {
        this.sendUserID = sendUserID;
    }

    public String getSendUserSign() {
        return sendUserSign;
    }

    public void setSendUserSign(String sendUserSign) {
        this.sendUserSign = sendUserSign;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getSendUserNickName() {
        return sendUserNickName;
    }

    public void setSendUserNickName(String sendUserNickName) {
        this.sendUserNickName = sendUserNickName;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getMainFile() {
        return mainFile;
    }

    public void setMainFile(String mainFile) {
        this.mainFile = mainFile;
    }

    public String getVoiceLength() {
        return voiceLength;
    }

    public void setVoiceLength(String voiceLength) {
        this.voiceLength = voiceLength;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getReplyToMsgId() {
        return replyToMsgId;
    }

    public void setReplyToMsgId(String replyToMsgId) {
        this.replyToMsgId = replyToMsgId;
    }

    public String getGroupedId() {
        return groupedId;
    }

    public void setGroupedId(String groupedId) {
        this.groupedId = groupedId;
    }

    public long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(long collectTime) {
        this.collectTime = collectTime;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getClueId() {
        return clueId;
    }

    public void setClueId(String clueId) {
        this.clueId = clueId;
    }

    public String getBusinessTag() {
        return businessTag;
    }

    public void setBusinessTag(String businessTag) {
        this.businessTag = businessTag;
    }

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }
}
