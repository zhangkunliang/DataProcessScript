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

	private int HasAccess;
	private int IsBlocked;
	private int IsFake;
	private int IsScam;
	private int IsSupport;
	private int IsVerified;
	private String LanguageCode;
	private long LastOnLineTime;
	private int NeedPhoneNumberPrivacyException;
	private String RestrictReason;
	private int UserType;
	private String Commands;
	private String ShareText;
	private String LinkAddressCode;
	private String DataSource;
	/**
	 * 短链 -》 长链
	 */
	private String LinkMapping;

	public String getCommands() {
		return Commands;
	}

	public void setCommands(String commands) {
		Commands = commands;
	}

	public String getShareText() {
		return ShareText;
	}

	public void setShareText(String shareText) {
		ShareText = shareText;
	}

	public int getHasAccess() {
		return HasAccess;
	}

	public void setHasAccess(int hasAccess) {
		HasAccess = hasAccess;
	}

	public int getIsBlocked() {
		return IsBlocked;
	}

	public void setIsBlocked(int isBlocked) {
		IsBlocked = isBlocked;
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

	public int getIsSupport() {
		return IsSupport;
	}

	public void setIsSupport(int isSupport) {
		IsSupport = isSupport;
	}

	public int getIsVerified() {
		return IsVerified;
	}

	public void setIsVerified(int isVerified) {
		IsVerified = isVerified;
	}

	public String getLanguageCode() {
		return LanguageCode;
	}

	public void setLanguageCode(String languageCode) {
		LanguageCode = languageCode;
	}

	public long getLastOnLineTime() {
		return LastOnLineTime;
	}

	public void setLastOnLineTime(long lastOnLineTime) {
		LastOnLineTime = lastOnLineTime;
	}

	public int getNeedPhoneNumberPrivacyException() {
		return NeedPhoneNumberPrivacyException;
	}

	public void setNeedPhoneNumberPrivacyException(int needPhoneNumberPrivacyException) {
		NeedPhoneNumberPrivacyException = needPhoneNumberPrivacyException;
	}

	public String getRestrictReason() {
		return RestrictReason;
	}

	public void setRestrictReason(String restrictReason) {
		RestrictReason = restrictReason;
	}

	public int getUserType() {
		return UserType;
	}

	public void setUserType(int userType) {
		UserType = userType;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupMember other = (GroupMember) obj;
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

	public String getLinkMapping() {
		return LinkMapping;
	}

	public void setLinkMapping(String linkMapping) {
		LinkMapping = linkMapping;
	}

	@Override
	public String toString() {
		return "GroupMember [groupType=" + groupType + ", groupID=" + groupID + ", groupName=" + groupName
				+ ", groupUserName=" + groupUserName + ", url=" + url + ", userSign=" + userSign + ", userId=" + userId
				+ ", userName=" + userName + ", nickName=" + nickName + ", portraitImage=" + portraitImage
				+ ", mobilePhone=" + mobilePhone + ", introduction=" + introduction + ", email=" + email + ", zombieId="
				+ zombieId + ", role=" + role + ", status=" + status + ", remark=" + remark + ", joinTime=" + joinTime
				+ ", collectTime=" + collectTime + ", HasAccess=" + HasAccess + ", IsBlocked=" + IsBlocked + ", IsFake="
				+ IsFake + ", IsScam=" + IsScam + ", IsSupport=" + IsSupport + ", IsVerified=" + IsVerified
				+ ", LanguageCode=" + LanguageCode + ", LastOnLineTime=" + LastOnLineTime
				+ ", NeedPhoneNumberPrivacyException=" + NeedPhoneNumberPrivacyException + ", RestrictReason="
				+ RestrictReason + ", UserType=" + UserType + ", Commands=" + Commands + ", ShareText=" + ShareText
				+ ", LinkMapping=" + LinkMapping + "]";
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
