package com.fh.fdp.rule.oca.data.conf;

public class CrawlerStatus {

	private String mdid;
	private String apptype;
	private String source;
	private Long updatetime;
	private int rule_type;
	private String rule_value;
	private int collect_status;
	private int collect_count;
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

	public int getRule_type() {
		return rule_type;
	}

	public void setRule_type(int rule_type) {
		this.rule_type = rule_type;
	}

	public String getRule_value() {
		return rule_value;
	}

	public void setRule_value(String rule_value) {
		this.rule_value = rule_value;
	}

	public int getCollect_status() {
		return collect_status;
	}

	public void setCollect_status(int collect_status) {
		this.collect_status = collect_status;
	}

	public int getCollect_count() {
		return collect_count;
	}

	public void setCollect_count(int collect_count) {
		this.collect_count = collect_count;
	}

	public Long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}

}
