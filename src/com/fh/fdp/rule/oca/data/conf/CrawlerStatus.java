package com.fh.fdp.rule.oca.data.conf;

import com.google.gson.annotations.SerializedName;

public class CrawlerStatus {
	@SerializedName("mdid")
	private String mdid;
	@SerializedName("apptype")
	private String apptype;
	@SerializedName("source")
	private String source;
	@SerializedName("updatetime")
	private Long updatetime;
	@SerializedName("rule_type")
	private int ruleType;
	@SerializedName("rule_value")
	private String ruleValue;
	@SerializedName("collect_status")
	private int collectStatus;
	@SerializedName("collect_count")
	private int collectCount;
	@SerializedName("createtime")
	private Long createtime;

	public String getMdid() {
		return mdid;
	}

	public void setMdid(String mdid) {
		this.mdid = mdid;
	}

	public Long getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Long updatetime) {
		this.updatetime = updatetime;
	}

	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getRuleType() {
		return ruleType;
	}

	public void setRuleType(int ruleType) {
		this.ruleType = ruleType;
	}

	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public int getCollectStatus() {
		return collectStatus;
	}

	public void setCollectStatus(int collectStatus) {
		this.collectStatus = collectStatus;
	}

	public int getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(int collectCount) {
		this.collectCount = collectCount;
	}

	public Long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}

}
