package com.fh.fdp.rule.oca.data.conf;

import com.google.gson.annotations.SerializedName;

/**
 * 境内个人信息
 * 
 * @author 董恒龙 境内 PersonInfo 和 PersonInfo 分开
 *
 */
public class DomesticPersonInfo {

	@SerializedName("UserSign")
	private String userSign;

	@SerializedName("UserId")
	private String userId;

	@SerializedName("UserName")
	private String userName;

	@SerializedName("PortraitImage")
	private String portraitImage;

	@SerializedName("HomePage")
	private String homePage;

	@SerializedName("Introduction")
	private String introduction;

	@SerializedName("RegisterTime")
	private long registerTime;

	@SerializedName("NickName")
	private String nickName;

	@SerializedName("Residence")
	private String residence;

	@SerializedName("HomeTown")
	private String homeTown;

	@SerializedName("SchoolName")
	private String schoolName;

	@SerializedName("Gender")
	private String gender;

	@SerializedName("Birthday")
	private String birthday;

	@SerializedName("FollowerNum")
	private int followerNum;

	@SerializedName("FollowingNum")
	private int followingNum;

	@SerializedName("PostNum")
	private int postNum;

	@SerializedName("IsVerified")
	private int isVerified;

	@SerializedName("Category")
	private String category;

	@SerializedName("AuthInfo")
	private String authInfo;

	@SerializedName("VerifiedType")
	private int verifiedType;

	@SerializedName("TokenTouTiao")
	private String tokenTouTiao;

	@SerializedName("Tags")
	private String tags;

	@SerializedName("WebSite")
	private String webSite;

	@SerializedName("Industry")
	private String industry;

	@SerializedName("ScoreTourism")
	private double ScoreTourism;

	@SerializedName("ScoreSports")
	private double ScoreSports;

	@SerializedName("ScoreDelicacy")
	private double ScoreDelicacy;

	@SerializedName("ScoreHealth")
	private double ScoreHealth;

	@SerializedName("ScoreEconomics")
	private double ScoreEconomics;

	@SerializedName("ScoreCar")
	private double ScoreCar;

	@SerializedName("ScoreEducation")
	private double ScoreEducation;

	@SerializedName("ScoreScience")
	private double ScoreScience;

	@SerializedName("ScoreEntertainment")
	private double ScoreEntertainment;

	@SerializedName("ScoreCulture")
	private double ScoreCulture;

	@SerializedName("ScoreMilitary")
	private double ScoreMilitary;

	@SerializedName("ScoreDisaster")
	private double ScoreDisaster;

	@SerializedName("ScoreAccident")
	private double ScoreAccident;

	@SerializedName("ScoreOffense")
	private double ScoreOffense;

	@SerializedName("ScoreEstate")
	private double ScoreEstate;

	@SerializedName("ScoreCorrupted")
	private double ScoreCorrupted;

	@SerializedName("ScoreStability")
	private double ScoreStability;

	@SerializedName("CollectTime")
	private long collectTime;

	private long ViewCount;
	private String BandingGoogle;

	private int HasAccess;
	private int IsBlocked;
	private int IsFake;
	private int IsScam;
	private int IsSupport;
	private String LanguageCode;
	private long LastOnLineTime;
	private int NeedPhoneNumberPrivacyException;
	private String RestrictReason;
	private int UserType;
	private String Commands;
	private String ShareText;
	private String LinkAddressCode;
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

	public String getLinkMapping() {
		return LinkMapping;
	}

	public void setLinkMapping(String linkMapping) {
		LinkMapping = linkMapping;
	}

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

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(long registerTime) {
		this.registerTime = registerTime;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	public long getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(long collectTime) {
		this.collectTime = collectTime;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getFollowerNum() {
		return followerNum;
	}

	public void setFollowerNum(int followerNum) {
		this.followerNum = followerNum;
	}

	public int getFollowingNum() {
		return followingNum;
	}

	public void setFollowingNum(int followingNum) {
		this.followingNum = followingNum;
	}

	public long getPostNum() {
		return postNum;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}

	public int getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(int isVerified) {
		this.isVerified = isVerified;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getVerifiedType() {
		return verifiedType;
	}

	public void setVerifiedType(int verifiedType) {
		this.verifiedType = verifiedType;
	}

	public String getTokenTouTiao() {
		return tokenTouTiao;
	}

	public void setTokenTouTiao(String tokenTouTiao) {
		this.tokenTouTiao = tokenTouTiao;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(String authInfo) {
		this.authInfo = authInfo;
	}

	public long getViewCount() {
		return ViewCount;
	}

	public void setViewCount(long viewCount) {
		ViewCount = viewCount;
	}

	public String getBandingGoogle() {
		return BandingGoogle;
	}

	public void setBandingGoogle(String bandingGoogle) {
		BandingGoogle = bandingGoogle;
	}

	public String getLinkAddressCode() {
		return LinkAddressCode;
	}

	public void setLinkAddressCode(String linkAddressCode) {
		LinkAddressCode = linkAddressCode;
	}

}
