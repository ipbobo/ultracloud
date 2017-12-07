package com.cmp.entity.tcc;

import java.util.Date;

public class TccOs implements java.io.Serializable {

	private static final long serialVersionUID = 1323L;

	private Long osId;

	private String osVersionCd;

	private String osName;

	private String oneLevelOs;

	private String memo;

	private Long osTypeId;

	private Long sortNum = 0L;

	private Long crtUserId;

	private String crtUserName;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String lastuptUserName;

	private String enableFlg;

	private TccOsType osType;

	public Long getOsId() {
		return osId;
	}

	public void setOsId(Long osId) {
		this.osId = osId;
	}

	public String getOneLevelOs() {
		return oneLevelOs;
	}

	public void setOneLevelOs(String oneLevelOs) {
		this.oneLevelOs = oneLevelOs;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOsVersionCd() {
		return osVersionCd;
	}

	public void setOsVersionCd(String osVersionCd) {
		this.osVersionCd = osVersionCd;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
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

	public Date getLastuptDttm() {
		return lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public Long getLastuptUserId() {
		return lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Long getOsTypeId() {
		return osTypeId;
	}

	public void setOsTypeId(Long osTypeId) {
		this.osTypeId = osTypeId;
		if (getOsType() != null) {
			getOsType().setOsTypeId(osTypeId);
		}
	}

	public TccOsType getOsType() {
		return osType;
	}

	public void setOsType(TccOsType osType) {
		this.osType = osType;
	}

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public String getLastuptUserName() {
		return lastuptUserName;
	}

	public void setLastuptUserName(String lastuptUserName) {
		this.lastuptUserName = lastuptUserName;
	}

}
