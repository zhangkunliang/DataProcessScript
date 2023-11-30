package com.fh.fdp.rule.oca.data.conf;

import com.google.gson.annotations.SerializedName;


/**
 * redis 缓存 UserId  Ad 信息
 * @author dhl
 */
public class UserIdAdCache {


	/**
	 * UserID
	 */
	@SerializedName("userid")
	private String userId;
	/**
	 * authType
	 */
	@SerializedName("auth_type")
	private String authType;

	/**
	 * authAccount
	 */
	@SerializedName("auth_account")
	private String authAccount;

	@SerializedName("app_type")
	private String appType;

	@SerializedName("last_time")
	private String lastTime;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getAuthAccount() {
		return authAccount;
	}

	public void setAuthAccount(String authAccount) {
		this.authAccount = authAccount;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	@Override
	public String toString() {
		return "UserIdAdCache{" +
				"userId='" + userId + '\'' +
				", authType='" + authType + '\'' +
				", authAccount='" + authAccount + '\'' +
				", appType='" + appType + '\'' +
				", lastTime='" + lastTime + '\'' +
				'}';
	}
}
