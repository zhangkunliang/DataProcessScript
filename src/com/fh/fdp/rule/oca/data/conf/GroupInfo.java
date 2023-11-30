package com.fh.fdp.rule.oca.data.conf;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.annotations.SerializedName;

/**
 * 群组信息
 * @author chgu
 *
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

	private int AdministratorCount;
	private int AllHistoryAvailable;
	private int BannedCount;
	private int RestrictedCount;
	private String BotCommands;
	private int CanGetMember;
	private int CanGetStatistics;
	private String InviteLink;
	private int IsChannel;
	private int IsFake;
	private int IsScam;
	private int IsVerified;
	private String LinkedChatId;
	private String PortraitImage;
	private String RestrictReason;
	private String UpgradedFromBasicGroupId;
	private String UpgradedFromMaxMessageId;
	private String Location;

	private String LinkAddressCode;
	private String DataSource;

	/**
	 * 短链 -》 长链
	 */
	private String LinkMapping;

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

	public int getAdministratorCount() {
		return AdministratorCount;
	}

	public void setAdministratorCount(int administratorCount) {
		AdministratorCount = administratorCount;
	}

	public int getAllHistoryAvailable() {
		return AllHistoryAvailable;
	}

	public void setAllHistoryAvailable(int allHistoryAvailable) {
		AllHistoryAvailable = allHistoryAvailable;
	}

	public int getBannedCount() {
		return BannedCount;
	}

	public void setBannedCount(int bannedCount) {
		BannedCount = bannedCount;
	}

	public int getRestrictedCount() {
		return RestrictedCount;
	}

	public void setRestrictedCount(int restrictedCount) {
		RestrictedCount = restrictedCount;
	}

	public String getBotCommands() {
		return BotCommands;
	}

	public void setBotCommands(String botCommands) {
		BotCommands = botCommands;
	}

	public int getCanGetMember() {
		return CanGetMember;
	}

	public void setCanGetMember(int canGetMember) {
		CanGetMember = canGetMember;
	}

	public int getCanGetStatistics() {
		return CanGetStatistics;
	}

	public void setCanGetStatistics(int canGetStatistics) {
		CanGetStatistics = canGetStatistics;
	}

	public String getInviteLink() {
		return InviteLink;
	}

	public void setInviteLink(String inviteLink) {
		InviteLink = inviteLink;
	}

	public int getIsChannel() {
		return IsChannel;
	}

	public void setIsChannel(int isChannel) {
		IsChannel = isChannel;
	}

	public int getIsFake() {
		return IsFake;
	}

	public void setIsFake(int isFake) {
		IsFake = isFake;
	}

	public int getIsScam() {
		return IsScam;
	}

	public void setIsScam(int isScam) {
		IsScam = isScam;
	}

	public int getIsVerified() {
		return IsVerified;
	}

	public void setIsVerified(int isVerified) {
		IsVerified = isVerified;
	}

	public String getLinkedChatId() {
		return LinkedChatId;
	}

	public void setLinkedChatId(String linkedChatId) {
		LinkedChatId = linkedChatId;
	}

	public String getPortraitImage() {
		return PortraitImage;
	}

	public void setPortraitImage(String portraitImage) {
		PortraitImage = portraitImage;
	}

	public String getRestrictReason() {
		return RestrictReason;
	}

	public void setRestrictReason(String restrictReason) {
		RestrictReason = restrictReason;
	}

	public String getUpgradedFromBasicGroupId() {
		return UpgradedFromBasicGroupId;
	}

	public void setUpgradedFromBasicGroupId(String upgradedFromBasicGroupId) {
		UpgradedFromBasicGroupId = upgradedFromBasicGroupId;
	}

	public String getUpgradedFromMaxMessageId() {
		return UpgradedFromMaxMessageId;
	}

	public void setUpgradedFromMaxMessageId(String upgradedFromMaxMessageId) {
		UpgradedFromMaxMessageId = upgradedFromMaxMessageId;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupInfo other = (GroupInfo) obj;
		if (groupID == null) {
			if (other.groupID != null)
				return false;
		} else if (!groupID.equals(other.groupID))
			return false;
		if (groupType == null) {
			if (other.groupType != null)
				return false;
		} else if (!groupType.equals(other.groupType))
			return false;
		if (userSign == null) {
			if (other.userSign != null)
				return false;
		} else if (!userSign.equals(other.userSign))
			return false;
		return true;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getLinkMapping() {
		return LinkMapping;
	}

	public void setLinkMapping(String linkMapping) {
		LinkMapping = linkMapping;
	}

	public String getLinkAddressCode() {
		return LinkAddressCode;
	}

	public void setLinkAddressCode(String linkAddressCode) {
		LinkAddressCode = linkAddressCode;
	}

	public String getDataSource() {
		return DataSource;
	}

	public void setDataSource(String dataSource) {
		DataSource = dataSource;
	}

}
