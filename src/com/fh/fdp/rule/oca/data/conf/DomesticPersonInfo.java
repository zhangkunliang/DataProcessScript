package com.fh.fdp.rule.oca.data.conf;

import com.google.gson.annotations.SerializedName;

/**
 * 境内个人信息
 *
 * @author 董恒龙 境内 PersonInfo 和 PersonInfo 分开
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
    private double scoreTourism;

    @SerializedName("ScoreSports")
    private double scoreSports;

    @SerializedName("ScoreDelicacy")
    private double scoreDelicacy;

    @SerializedName("ScoreHealth")
    private double scoreHealth;

    @SerializedName("ScoreEconomics")
    private double sScoreEconomics;

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

    @SerializedName("CollectTime")
    private long collectTime;


    private long viewCount;
    private String bandingGoogle;
    private int hasAccess;
    private int isBlocked;
    private int isFake;
    private int ssScam;
    private int isSupport;
    private String languageCode;
    private long lastOnLineTime;
    private int needPhoneNumberPrivacyException;
    private String restrictReason;
    private int userType;
    private String commands;
    private String shareText;
    private String linkAddressCode;
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

    public double getsScoreEconomics() {
        return sScoreEconomics;
    }

    public void setsScoreEconomics(double sScoreEconomics) {
        this.sScoreEconomics = sScoreEconomics;
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

    public int getSsScam() {
        return ssScam;
    }

    public void setSsScam(int ssScam) {
        this.ssScam = ssScam;
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
}
