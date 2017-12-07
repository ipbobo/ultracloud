package com.cmp.entity.tcc;

import java.util.Date;

public class TccActRuleConfig implements java.io.Serializable {

	private static final long serialVersionUID = -197755027867434495L;

	private Long ruleConfigId;

	private String srtTypeCd;

	private String roleTypeCd;

	private String isForce;

	private String memo;

	private Long crtUserId;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String enableFlg;

	private String envType;

	private String lastuptUserName;

	public Long getRuleConfigId() {
		return this.ruleConfigId;
	}

	public void setRuleConfigId(Long ruleConfigId) {
		this.ruleConfigId = ruleConfigId;
	}

	public String getSrtTypeCd() {
		return this.srtTypeCd;
	}

	public void setSrtTypeCd(String srtTypeCd) {
		this.srtTypeCd = srtTypeCd;
	}

	public String getRoleTypeCd() {
		return this.roleTypeCd;
	}

	public void setRoleTypeCd(String roleTypeCd) {
		this.roleTypeCd = roleTypeCd;
	}

	public String getIsForce() {
		return this.isForce;
	}

	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getCrtUserId() {
		return this.crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtDttm() {
		return this.crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Date getLastuptDttm() {
		return this.lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public Long getLastuptUserId() {
		return this.lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public String getEnableFlg() {
		return this.enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public String getLastuptUserName() {
		return lastuptUserName;
	}

	public void setLastuptUserName(String lastuptUserName) {
		this.lastuptUserName = lastuptUserName;
	}

	public String getEnvType() {
		return envType;
	}

	public void setEnvType(String envType) {
		this.envType = envType;
	}

}
