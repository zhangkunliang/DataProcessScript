package com.fh.fdp.rule.oca.data.conf;

import com.google.gson.annotations.SerializedName;

/**
 * 个人信息
 *
 * @author chgu
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
    private String websiteUrl;
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
    private String category = "";

    @SerializedName("AuthInfo")
    private String authInfo = "";

    @SerializedName("VerifiedType")
    private int verifiedType = 0;

    @SerializedName("UserToken")
    private String userToken = "";

    @SerializedName("Tags")
    private String tags = "";

    @SerializedName("WebSite")
    private String websitetUrrl = "";

    @SerializedName("Industry")
    private String industry = "";


    /**
     * 这个Score
     * 只入境内的的库
     * adm_base_person_domestic_personinfo
     */
    @SerializedName("ScoreTourism")
    private double scoreTourism;

    @SerializedName("ScoreSports")
    private double scoreSports;

    @SerializedName("ScoreDelicacy")
    private double scoreDelicacy;

    @SerializedName("ScoreHealth")
    private double scoreHealth;

    @SerializedName("ScoreEconomics")
    private double scoreEconomics;

    @SerializedName("ScoreCar")
    private double scoreCar;

    @SerializedName("ScoreEducation")
    private double scoreEducation;

    @SerializedName("ScoreScience")
    private double scoreScience;

    @SerializedName("ScoreEntertainment")
    private double scoreEntertainment;

    @SerializedName("ScoreCulture")
    private double scoreCulture;

    @SerializedName("ScoreMilitary")
    private double scoreMilitary;

    @SerializedName("ScoreDisaster")
    private double scoreDisaster;

    @SerializedName("ScoreAccident")
    private double scoreAccident;

    @SerializedName("ScoreOffense")
    private double scoreOffense;

    @SerializedName("ScoreEstate")
    private double scoreEstate;

    @SerializedName("ScoreCorrupted")
    private double scoreCorrupted;

    @SerializedName("ScoreStability")
    private double scoreStability;
    /**
     * 抖音任务新增的个人信息字段
     * 2023年10月31日17:07:44 zkl
     */

    @SerializedName("LikeCount")
    private long likeCount;
    @SerializedName("SecUid")
    private String secUid;
    @SerializedName("PrivateUserId")
    private String privateUserId;
    @SerializedName("FriendNum")
    private long friendNum;
    @SerializedName("ReleOtherAppS")
    private String releOtherAppS;
    @SerializedName("DiscMcode")
    private String discMcode;
    @SerializedName("SourceValue")
    private String sourceValue;
    @SerializedName("updateTime")
    private long updateTime;
    @SerializedName("ViewCount")
    private long viewCount;
    @SerializedName("BandingGoogle")
    private String bandingGoogle;
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
    private String linkAddressCode = "";
    @SerializedName("DataSource")
    private String dataSource;
    /**
     * 短链 -》 长链
     */
    @SerializedName("LinkMapping")
    private String linkMapping;
    /**
     * 经纬度信息
     */
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("distince")
    private double distince;

    public String getLinkMapping() {
        return linkMapping;
    }

    public void setLinkMapping(String linkMapping) {
        this.linkMapping = linkMapping;
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

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWebsitetUrrl() {
        return websitetUrrl;
    }

    public void setWebsitetUrrl(String websitetUrrl) {
        this.websitetUrrl = websitetUrrl;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public String getSecUid() {
        return secUid;
    }

    public void setSecUid(String secUid) {
        this.secUid = secUid;
    }

    public String getPrivateUserId() {
        return privateUserId;
    }

    public void setPrivateUserId(String privateUserId) {
        this.privateUserId = privateUserId;
    }

    public long getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(long friendNum) {
        this.friendNum = friendNum;
    }

    public String getReleOtherAppS() {
        return releOtherAppS;
    }

    public void setReleOtherAppS(String releOtherAppS) {
        this.releOtherAppS = releOtherAppS;
    }

    public String getDiscMcode() {
        return discMcode;
    }

    public void setDiscMcode(String discMcode) {
        this.discMcode = discMcode;
    }

    public String getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(String sourceValue) {
        this.sourceValue = sourceValue;
    }

    public double getScoreTourism() {
        return scoreTourism;
    }

    public void setScoreTourism(double scoreTourism) {
        this.scoreTourism = scoreTourism;
    }

    public double getScoreSports() {
        return scoreSports;
    }

    public void setScoreSports(double scoreSports) {
        this.scoreSports = scoreSports;
    }

    public double getScoreDelicacy() {
        return scoreDelicacy;
    }

    public void setScoreDelicacy(double scoreDelicacy) {
        this.scoreDelicacy = scoreDelicacy;
    }

    public double getScoreHealth() {
        return scoreHealth;
    }

    public void setScoreHealth(double scoreHealth) {
        this.scoreHealth = scoreHealth;
    }

    public double getScoreEconomics() {
        return scoreEconomics;
    }

    public void setScoreEconomics(double scoreEconomics) {
        this.scoreEconomics = scoreEconomics;
    }

    public double getScoreCar() {
        return scoreCar;
    }

    public void setScoreCar(double scoreCar) {
        this.scoreCar = scoreCar;
    }

    public double getScoreEducation() {
        return scoreEducation;
    }

    public void setScoreEducation(double scoreEducation) {
        this.scoreEducation = scoreEducation;
    }

    public double getScoreScience() {
        return scoreScience;
    }

    public void setScoreScience(double scoreScience) {
        this.scoreScience = scoreScience;
    }

    public double getScoreEntertainment() {
        return scoreEntertainment;
    }

    public void setScoreEntertainment(double scoreEntertainment) {
        this.scoreEntertainment = scoreEntertainment;
    }

    public double getScoreCulture() {
        return scoreCulture;
    }

    public void setScoreCulture(double scoreCulture) {
        this.scoreCulture = scoreCulture;
    }

    public double getScoreMilitary() {
        return scoreMilitary;
    }

    public void setScoreMilitary(double scoreMilitary) {
        this.scoreMilitary = scoreMilitary;
    }

    public double getScoreDisaster() {
        return scoreDisaster;
    }

    public void setScoreDisaster(double scoreDisaster) {
        this.scoreDisaster = scoreDisaster;
    }

    public double getScoreAccident() {
        return scoreAccident;
    }

    public void setScoreAccident(double scoreAccident) {
        this.scoreAccident = scoreAccident;
    }

    public double getScoreOffense() {
        return scoreOffense;
    }

    public void setScoreOffense(double scoreOffense) {
        this.scoreOffense = scoreOffense;
    }

    public double getScoreEstate() {
        return scoreEstate;
    }

    public void setScoreEstate(double scoreEstate) {
        this.scoreEstate = scoreEstate;
    }

    public double getScoreCorrupted() {
        return scoreCorrupted;
    }

    public void setScoreCorrupted(double scoreCorrupted) {
        this.scoreCorrupted = scoreCorrupted;
    }

    public double getScoreStability() {
        return scoreStability;
    }

    public void setScoreStability(double scoreStability) {
        this.scoreStability = scoreStability;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public String getBandingGoogle() {
        return bandingGoogle;
    }

    public void setBandingGoogle(String bandingGoogle) {
        this.bandingGoogle = bandingGoogle;
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
