package com.cmp.entity.tcc;

import java.util.Date;

public class TtccBillDay implements java.io.Serializable {

	private static final long serialVersionUID = 2927400781263153516L;

	private Long id;

	private Date rptDate;

	private Long hostCount;

	private Double amout;

	private Long crtUserId;

	private Date crtDttm;

	private Long lastuptUserId;

	private Date lastuptDttm;

	private String enableFlg;

	private String appType;

	private String appGrade;

	private String envcode;

	private String Remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRptDate() {
		return rptDate;
	}

	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
	}

	public Long getHostCount() {
		return hostCount;
	}

	public void setHostCount(Long hostCount) {
		this.hostCount = hostCount;
	}

	public Double getAmout() {
		return amout;
	}

	public void setAmout(Double amout) {
		this.amout = amout;
	}

	public Long getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Long getLastuptUserId() {
		return lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public Date getLastuptDttm() {
		return lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppGrade() {
		return appGrade;
	}

	public void setAppGrade(String appGrade) {
		this.appGrade = appGrade;
	}

	public String getEnvcode() {
		return envcode;
	}

	public void setEnvcode(String envcode) {
		this.envcode = envcode;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

}
