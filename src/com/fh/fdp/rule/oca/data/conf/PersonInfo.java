package com.fh.fdp.rule.oca.data.conf;

import com.google.gson.annotations.SerializedName;

/**
 * 个人信息
 * @author chgu
 *
 */
public class PersonInfo {


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
	@SerializedName("HomePage")
	private String homePage;
	@SerializedName("RealName")
	private String realName;
	@SerializedName("Introduction")
	private String introduction;
	@SerializedName("Signature")
	private String signature;
	@SerializedName("Remark")
	private String remark;
	@SerializedName("MobilePhone")
	private String mobilePhone;
	@SerializedName("Email")
	private String email;
	@SerializedName("RegisterTime")
	private long registerTime;
	@SerializedName("LastactiveTime")
	private long lastactiveTime;
	@SerializedName("Skills")
	private String skills;
	@SerializedName("Residence")
	private String residence;
	@SerializedName("HomeTown")
	private String homeTown;
	@SerializedName("Gender")
	private int gender;
	@SerializedName("SexOrient")
	private String sexOrient;
	@SerializedName("Language")
	private String language;
	@SerializedName("Marriage")
	private String marriage = "-1";
	@SerializedName("FavouriteQuotes")
	private String favouriteQuotes;
	@SerializedName("InterestList")
	private String interestList;
	@SerializedName("Website")
	private String website;
	@SerializedName("Career")
	private String career;
	@SerializedName("Post")
	private String post;
	@SerializedName("Nationality")
	private String nationality;
	@SerializedName("CompanyName")
	private String companyName;
	@SerializedName("CompanyAddr")
	private String companyAddr;
	@SerializedName("CertificateType")
	private String certificateType;
	@SerializedName("CertificateCode")
	private String certificateCode;
	@SerializedName("BandingQQ")
	private String bandingQQ;
	@SerializedName("BandingWx")
	private String bandingWx;
	@SerializedName("BandingMsn")
	private String bandingMsn;
	@SerializedName("Balance")
	private String balance;
	@SerializedName("Amount")
	private String amount;
	@SerializedName("AccumulatedIncome")
	private String accumulatedIncome;
	@SerializedName("Birthday")
	private String birthday;
	@SerializedName("Age")
	private int age;
	@SerializedName("Constellation")
	private String constellation;
	@SerializedName("FollowerNum")
	private int followerNum;
	@SerializedName("FollowingNum")
	private int followingNum;
	@SerializedName("PostNum")
	private int postNum;
	@SerializedName("UserlikeNum")
	private int userlikeNum;
	@SerializedName("MediaNum")
	private int mediaNum;
	@SerializedName("IsPrivate")
	private int isPrivate = 9;
	@SerializedName("IsVerified")
	private int isVerified = 9;
	@SerializedName("SchoolName")
	private String schoolName;
	@SerializedName("Degree")
	private String degree;
	@SerializedName("EducationDesc")
	private String educationDesc;
	@SerializedName("WorkExperience")
	private String workExperience;
	@SerializedName("Status")
	private String status;
	@SerializedName("CollectTime")
	private long collectTime;


	/**
	 * 境内 Person new add
	 * DomainType 用来区分境内境外
	 * 2023 0713
	 */

	@SerializedName("DomainType")
	private String domainType;

	@SerializedName("Category")
	private String category ="";

	@SerializedName("AuthInfo")
	private String authInfo = "";

	@SerializedName("VerifiedType")
	private int verifiedType = 0;

	@SerializedName("TokenTouTiao")
	private String tokenTouTiao ="";

	@SerializedName("Tags")
	private String tags = "";

	@SerializedName("WebSite")
	private String webSite ="";

	@SerializedName("Industry")
	private String industry ="";


	/**
	 * 这个Score
	 * 只入境内的的库
	 * adm_base_person_domestic_personinfo
	 */
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



	private long updateTime;

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
	private String LinkAddressCode="";
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(long registerTime) {
		this.registerTime = registerTime;
	}

	public long getLastactiveTime() {
		return lastactiveTime;
	}

	public void setLastactiveTime(long lastactiveTime) {
		this.lastactiveTime = lastactiveTime;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
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

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getSexOrient() {
		return sexOrient;
	}

	public void setSexOrient(String sexOrient) {
		this.sexOrient = sexOrient;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getFavouriteQuotes() {
		return favouriteQuotes;
	}

	public void setFavouriteQuotes(String favouriteQuotes) {
		this.favouriteQuotes = favouriteQuotes;
	}

	public String getInterestList() {
		return interestList;
	}

	public void setInterestList(String interestList) {
		this.interestList = interestList;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateCode() {
		return certificateCode;
	}

	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	public String getBandingQQ() {
		return bandingQQ;
	}

	public void setBandingQQ(String bandingQQ) {
		this.bandingQQ = bandingQQ;
	}

	public String getBandingWx() {
		return bandingWx;
	}

	public void setBandingWx(String bandingWx) {
		this.bandingWx = bandingWx;
	}

	public String getBandingMsn() {
		return bandingMsn;
	}

	public void setBandingMsn(String bandingMsn) {
		this.bandingMsn = bandingMsn;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAccumulatedIncome() {
		return accumulatedIncome;
	}

	public void setAccumulatedIncome(String accumulatedIncome) {
		this.accumulatedIncome = accumulatedIncome;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
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

	public int getPostNum() {
		return postNum;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}

	public int getUserlikeNum() {
		return userlikeNum;
	}

	public void setUserlikeNum(int userlikeNum) {
		this.userlikeNum = userlikeNum;
	}

	public int getMediaNum() {
		return mediaNum;
	}

	public void setMediaNum(int mediaNum) {
		this.mediaNum = mediaNum;
	}

	public int getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(int isPrivate) {
		this.isPrivate = isPrivate;
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

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getEducationDesc() {
		return educationDesc;
	}

	public void setEducationDesc(String educationDesc) {
		this.educationDesc = educationDesc;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(long collectTime) {
		this.collectTime = collectTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
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

	public String getDataSource() {
		return DataSource;
	}

	public void setDataSource(String dataSource) {
		DataSource = dataSource;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	public String getDomainType() {
		return domainType;
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

	public double getScoreTourism() {
		return ScoreTourism;
	}

	public void setScoreTourism(double scoreTourism) {
		ScoreTourism = scoreTourism;
	}

	public double getScoreSports() {
		return ScoreSports;
	}

	public void setScoreSports(double scoreSports) {
		ScoreSports = scoreSports;
	}

	public double getScoreDelicacy() {
		return ScoreDelicacy;
	}

	public void setScoreDelicacy(double scoreDelicacy) {
		ScoreDelicacy = scoreDelicacy;
	}

	public double getScoreHealth() {
		return ScoreHealth;
	}

	public void setScoreHealth(double scoreHealth) {
		ScoreHealth = scoreHealth;
	}

	public double getScoreEconomics() {
		return ScoreEconomics;
	}

	public void setScoreEconomics(double scoreEconomics) {
		ScoreEconomics = scoreEconomics;
	}

	public double getScoreCar() {
		return ScoreCar;
	}

	public void setScoreCar(double scoreCar) {
		ScoreCar = scoreCar;
	}

	public double getScoreEducation() {
		return ScoreEducation;
	}

	public void setScoreEducation(double scoreEducation) {
		ScoreEducation = scoreEducation;
	}

	public double getScoreScience() {
		return ScoreScience;
	}

	public void setScoreScience(double scoreScience) {
		ScoreScience = scoreScience;
	}

	public double getScoreEntertainment() {
		return ScoreEntertainment;
	}

	public void setScoreEntertainment(double scoreEntertainment) {
		ScoreEntertainment = scoreEntertainment;
	}

	public double getScoreCulture() {
		return ScoreCulture;
	}

	public void setScoreCulture(double scoreCulture) {
		ScoreCulture = scoreCulture;
	}

	public double getScoreMilitary() {
		return ScoreMilitary;
	}

	public void setScoreMilitary(double scoreMilitary) {
		ScoreMilitary = scoreMilitary;
	}

	public double getScoreDisaster() {
		return ScoreDisaster;
	}

	public void setScoreDisaster(double scoreDisaster) {
		ScoreDisaster = scoreDisaster;
	}

	public double getScoreAccident() {
		return ScoreAccident;
	}

	public void setScoreAccident(double scoreAccident) {
		ScoreAccident = scoreAccident;
	}

	public double getScoreOffense() {
		return ScoreOffense;
	}

	public void setScoreOffense(double scoreOffense) {
		ScoreOffense = scoreOffense;
	}

	public double getScoreEstate() {
		return ScoreEstate;
	}

	public void setScoreEstate(double scoreEstate) {
		ScoreEstate = scoreEstate;
	}

	public double getScoreCorrupted() {
		return ScoreCorrupted;
	}

	public void setScoreCorrupted(double scoreCorrupted) {
		ScoreCorrupted = scoreCorrupted;
	}

	public double getScoreStability() {
		return ScoreStability;
	}

	public void setScoreStability(double scoreStability) {
		ScoreStability = scoreStability;
	}
}
