package com.fh.fdp.rule.oca.data.conf;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.annotations.SerializedName;

/**
 * 群组信息
 *
 * @author chgu
 */
public class GroupInfo {

    @SerializedName("GroupType")
    private String groupType;
    @SerializedName("GroupID")
    private String groupID;
    @SerializedName("GroupUserName")
    private String groupUserName;
    @SerializedName("GroupPortraitImage")
    private String groupPortraitImage;
    @SerializedName("GroupName")
    private String groupName;
    @SerializedName("Url")
    private String url;
    @SerializedName("UserSign")
    private String userSign;
    @SerializedName("UserId")
    private String userId;
    @SerializedName("UserName")
    private String userName;
    @SerializedName("CreateTime")
    private long createTime;
    @SerializedName("Count")
    private int count;
    @SerializedName("LinitNum")
    private int linitNum;
    @SerializedName("Classify")
    private String classify;
    @SerializedName("Board")
    private String board;
    @SerializedName("Introduction")
    private String introduction;
    @SerializedName("Action")
    private String action = "9";
    @SerializedName("CollectTime")
    private long collectTime;
    @SerializedName("AppType")
    private String appType;
    @SerializedName("CLUE_ID")
    private String clueId;
    @SerializedName("BUSINESS_TAG")
    private String businessTag;
    @SerializedName("AdministratorCount")
    private int administratorCount;
    @SerializedName("AllHistoryAvailable")
    private int allHistoryAvailable;
    @SerializedName("BannedCount")
    private int bannedCount;
    @SerializedName("RestrictedCount")
    private int restrictedCount;
    @SerializedName("BotCommands")
    private String botCommands;
    @SerializedName("CanGetMember")
    private int canGetMember;
    @SerializedName("CanGetStatistics")
    private int canGetStatistics;
    @SerializedName("InviteLink")
    private String inviteLink;
    @SerializedName("IsChannel")
    private int isChannel;
    @SerializedName("IsFake")
    private int isFake;
    @SerializedName("IsScam")
    private int isScam;
    @SerializedName("IsVerified")
    private int isVerified;
    @SerializedName("LinkedChatId")
    private String linkedChatId;
    @SerializedName("PortraitImage")
    private String portraitImage;
    @SerializedName("RestrictReason")
    private String restrictReason;
    @SerializedName("UpgradedFromBasicGroupId")
    private String upgradedFromBasicGroupId;
    @SerializedName("UpgradedFromMaxMessageId")
    private String upgradedFromMaxMessageId;
    @SerializedName("Location")
    private String location;
    @SerializedName("LinkAddressCode")
    private String linkAddressCode;
    @SerializedName("DataSource")
    private String dataSource;

    /**
     * 短链 -》 长链
     */
    private String linkMapping;

    /**
     * 经纬度信息
     */
    private double longitude;
    private double latitude;
    private double distince;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDistince() {
        return distince;
    }

    public void setDistince(double distince) {
        this.distince = distince;
    }



    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupUserName() {
        return groupUserName;
    }

    public void setGroupUserName(String groupUserName) {
        this.groupUserName = groupUserName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLinitNum() {
        return linitNum;
    }

    public void setLinitNum(int linitNum) {
        this.linitNum = linitNum;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAction() {
        if (StringUtils.isEmpty(action)) {
            return "9";
        }
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(long collectTime) {
        this.collectTime = collectTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((groupID == null) ? 0 : groupID.hashCode());
        result = prime * result + ((groupType == null) ? 0 : groupType.hashCode());
        result = prime * result + ((userSign == null) ? 0 : userSign.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null){
            return false;
        }

        if (getClass() != obj.getClass()){
            return false;
        }

        GroupInfo other = (GroupInfo) obj;
        if (groupID == null) {
            if (other.groupID != null){
                return false;
            }

        } else if (!groupID.equals(other.groupID)){
            return false;
        }

        if (groupType == null) {
            if (other.groupType != null){
                return false;
            }

        } else if (!groupType.equals(other.groupType)){
            return false;
        }
        if (userSign == null) {
            return other.userSign == null;

        } else {
            return userSign.equals(other.userSign);
        }
    }

    public String getGroupPortraitImage() {
        return groupPortraitImage;
    }

    public void setGroupPortraitImage(String groupPortraitImage) {
        this.groupPortraitImage = groupPortraitImage;
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

    public int getAdministratorCount() {
        return administratorCount;
    }

    public void setAdministratorCount(int administratorCount) {
        this.administratorCount = administratorCount;
    }

    public int getAllHistoryAvailable() {
        return allHistoryAvailable;
    }

    public void setAllHistoryAvailable(int allHistoryAvailable) {
        this.allHistoryAvailable = allHistoryAvailable;
    }

    public int getBannedCount() {
        return bannedCount;
    }

    public void setBannedCount(int bannedCount) {
        this.bannedCount = bannedCount;
    }

    public int getRestrictedCount() {
        return restrictedCount;
    }

    public void setRestrictedCount(int restrictedCount) {
        this.restrictedCount = restrictedCount;
    }

    public String getBotCommands() {
        return botCommands;
    }

    public void setBotCommands(String botCommands) {
        this.botCommands = botCommands;
    }

    public int getCanGetMember() {
        return canGetMember;
    }

    public void setCanGetMember(int canGetMember) {
        this.canGetMember = canGetMember;
    }

    public int getCanGetStatistics() {
        return canGetStatistics;
    }

    public void setCanGetStatistics(int canGetStatistics) {
        this.canGetStatistics = canGetStatistics;
    }

    public String getInviteLink() {
        return inviteLink;
    }

    public void setInviteLink(String inviteLink) {
        this.inviteLink = inviteLink;
    }

    public int getIsChannel() {
        return isChannel;
    }

    public void setIsChannel(int isChannel) {
        this.isChannel = isChannel;
    }

    public int getIsFake() {
        return isFake;
    }

    public void setIsFake(int isFake) {
        this.isFake = isFake;
    }

    public int getIsScam() {
        return isScam;
    }

    public void setIsScam(int isScam) {
        this.isScam = isScam;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public String getLinkedChatId() {
        return linkedChatId;
    }

    public void setLinkedChatId(String linkedChatId) {
        this.linkedChatId = linkedChatId;
    }

    public String getPortraitImage() {
        return portraitImage;
    }

    public void setPortraitImage(String portraitImage) {
        this.portraitImage = portraitImage;
    }

    public String getRestrictReason() {
        return restrictReason;
    }

    public void setRestrictReason(String restrictReason) {
        this.restrictReason = restrictReason;
    }

    public String getUpgradedFromBasicGroupId() {
        return upgradedFromBasicGroupId;
    }

    public void setUpgradedFromBasicGroupId(String upgradedFromBasicGroupId) {
        this.upgradedFromBasicGroupId = upgradedFromBasicGroupId;
    }

    public String getUpgradedFromMaxMessageId() {
        return upgradedFromMaxMessageId;
    }

    public void setUpgradedFromMaxMessageId(String upgradedFromMaxMessageId) {
        this.upgradedFromMaxMessageId = upgradedFromMaxMessageId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLinkAddressCode() {
        return linkAddressCode;
    }

    public void setLinkAddressCode(String linkAddressCode) {
        this.linkAddressCode = linkAddressCode;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getLinkMapping() {
        return linkMapping;
    }

    public void setLinkMapping(String linkMapping) {
        this.linkMapping = linkMapping;
    }
}
