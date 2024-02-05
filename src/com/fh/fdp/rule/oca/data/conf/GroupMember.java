package com.fh.fdp.rule.oca.data.conf;

import com.google.gson.annotations.SerializedName;

/**
 * 群成员信息
 * 
 * @author chgu
 *
 */
public class GroupMember {

	@SerializedName("GroupType")
	private String groupType;
	@SerializedName("GroupID")
	private String groupID;
	@SerializedName("GroupName")
	private String groupName;
	@SerializedName("GroupUserName")
	private String groupUserName;
	@SerializedName("Url")
	private String url;
	@SerializedName("UserSign")
	private String userSign;
	@SerializedName("UserId")
	private String userId;
	@SerializedName("UserName")
	private String userName;
	@SerializedName("NickName")
	private String nickName;
	@SerializedName("PortraitImage")
	private String portraitImage;
	@SerializedName("MobilePhone")
	private String mobilePhone;
	@SerializedName("Introduction")
	private String introduction;
	@SerializedName("Email")
	private String email;
	@SerializedName("ZombieId")
	private String zombieId;
	@SerializedName("Role")
	private String role = "03";
	@SerializedName("Status")
	private String status;
	@SerializedName("Remark")
	private String remark;
	@SerializedName("JoinTime")
	private long joinTime;
	@SerializedName("CollectTime")
	private long collectTime;
	@SerializedName("AppType")
	private String appType;
	@SerializedName("CLUE_ID")
	private String clueId;
	@SerializedName("BUSINESS_TAG")
	private String businessTag;
	@SerializedName("HasAccess")
	private int hasAccess;
	@SerializedName("IsBlocked")
	private int isBlocked;
	@SerializedName("IsFake")
	private int isFake;
	@SerializedName("IsScam")
	private int isScam;
	@SerializedName("IsSupport")
	private int isSupport;
	@SerializedName("IsVerified")
	private int isVerified;
	@SerializedName("LanguageCode")
	private String languageCode;
	@SerializedName("LastOnLineTime")
	private long lastOnLineTime;
	@SerializedName("NeedPhoneNumberPrivacyException")
	private int needPhoneNumberPrivacyException;
	@SerializedName("RestrictReason")
	private String restrictReason;
	@SerializedName("UserType")
	private int userType;
	@SerializedName("Commands")
	private String commands;
	@SerializedName("ShareText")
	private String shareText;
	@SerializedName("LinkAddressCode")
	private String linkAddressCode;
	@SerializedName("DataSource")
	private String dataSource;
	/**
	 * 短链 -》 长链
	 */
	private String linkMapping;

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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPortraitImage() {
		return portraitImage;
	}

	public void setPortraitImage(String portraitImage) {
		this.portraitImage = portraitImage;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZombieId() {
		return zombieId;
	}

	public void setZombieId(String zombieId) {
		this.zombieId = zombieId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	public long getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(long collectTime) {
		this.collectTime = collectTime;
	}

	public String getGroupUserName() {
		return groupUserName;
	}

	public void setGroupUserName(String groupUserName) {
		this.groupUserName = groupUserName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

		GroupMember other = (GroupMember) obj;
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
			if (other.userSign != null){
				return false;
			}

		} else if (!userSign.equals(other.userSign)){
			return false;
		}
		return true;
	}

	public String getLinkMapping() {
		return linkMapping;
	}

	public void setLinkMapping(String linkMapping) {
		this.linkMapping = linkMapping;
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

	@Override
	public String toString() {
		return "GroupMember [groupType=" + groupType + ", groupID=" + groupID + ", groupName=" + groupName
				+ ", groupUserName=" + groupUserName + ", url=" + url + ", userSign=" + userSign + ", userId=" + userId
				+ ", userName=" + userName + ", nickName=" + nickName + ", portraitImage=" + portraitImage
				+ ", mobilePhone=" + mobilePhone + ", introduction=" + introduction + ", email=" + email + ", zombieId="
				+ zombieId + ", role=" + role + ", status=" + status + ", remark=" + remark + ", joinTime=" + joinTime
				+ ", collectTime=" + collectTime + ", HasAccess=" + hasAccess + ", IsBlocked=" + isBlocked + ", IsFake="
				+ isFake + ", IsScam=" + isScam + ", IsSupport=" + isSupport + ", IsVerified=" + isVerified
				+ ", LanguageCode=" + languageCode + ", LastOnLineTime=" + lastOnLineTime
				+ ", NeedPhoneNumberPrivacyException=" + needPhoneNumberPrivacyException + ", RestrictReason="
				+ restrictReason + ", UserType=" + userType + ", Commands=" + commands + ", ShareText=" + shareText
				+ ", LinkMapping=" + linkMapping + ", appType=" + appType + ", clueId=" + clueId+ ", businessTag=" + businessTag + "]";
	}

	public int getHasAccess() {
		return hasAccess;
	}

	public void setHasAccess(int hasAccess) {
		this.hasAccess = hasAccess;
	}

	public int getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(int isBlocked) {
		this.isBlocked = isBlocked;
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

	public int getIsSupport() {
		return isSupport;
	}

	public void setIsSupport(int isSupport) {
		this.isSupport = isSupport;
	}

	public int getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(int isVerified) {
		this.isVerified = isVerified;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public long getLastOnLineTime() {
		return lastOnLineTime;
	}

	public void setLastOnLineTime(long lastOnLineTime) {
		this.lastOnLineTime = lastOnLineTime;
	}

	public int getNeedPhoneNumberPrivacyException() {
		return needPhoneNumberPrivacyException;
	}

	public void setNeedPhoneNumberPrivacyException(int needPhoneNumberPrivacyException) {
		this.needPhoneNumberPrivacyException = needPhoneNumberPrivacyException;
	}

	public String getRestrictReason() {
		return restrictReason;
	}

	public void setRestrictReason(String restrictReason) {
		this.restrictReason = restrictReason;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getCommands() {
		return commands;
	}

	public void setCommands(String commands) {
		this.commands = commands;
	}

	public String getShareText() {
		return shareText;
	}

	public void setShareText(String shareText) {
		this.shareText = shareText;
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
}
