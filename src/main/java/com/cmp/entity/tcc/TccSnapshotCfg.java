package com.cmp.entity.tcc;

import java.util.Date;

public class TccSnapshotCfg implements java.io.Serializable {

	private static final long serialVersionUID = 1644743494270283372L;

	private Long cfgId;

	private Long hostId;

	private Long shotMaxNum;

	private String vmName;

	private String intervalDef;

	private String crtUserId;

	private Date crtDttm;

	private Long lastuptUserId;

	private Date lastuptDttm;

	private String enableFlg;

	// 默认设置7次定时快照

	// private Long auto_shotMaxNum = BusinessEnvironment.auto_shotMaxNum;

	// 快照策略文字提示信息

	private String tip;

	// Constructors
	/** default constructor */
	public TccSnapshotCfg() {
	}

	/** full constructor */
	public TccSnapshotCfg(Long hostId, Long shotMaxNum, String vmName,
			String intervalDef, String crtUserId, Date crtDttm,
			Long lastuptUserId, Date lastuptDttm, String enableFlg) {
		this.hostId = hostId;
		this.shotMaxNum = shotMaxNum;
		this.vmName = vmName;
		this.intervalDef = intervalDef;
		this.crtUserId = crtUserId;
		this.crtDttm = crtDttm;
		this.lastuptUserId = lastuptUserId;
		this.lastuptDttm = lastuptDttm;
		this.enableFlg = enableFlg;
	}

	public Long getCfgId() {
		return this.cfgId;
	}

	public void setCfgId(Long cfgId) {
		this.cfgId = cfgId;
	}

	public Long getShotMaxNum() {
		return this.shotMaxNum;
	}

	public void setShotMaxNum(Long shotMaxNum) {
		this.shotMaxNum = shotMaxNum;
	}

	public String getIntervalDef() {
		return this.intervalDef;
	}

	public void setIntervalDef(String intervalDef) {
		this.intervalDef = intervalDef;
	}

	public Date getCrtDttm() {
		return this.crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Long getLastuptUserId() {
		return this.lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public Date getLastuptDttm() {
		return this.lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public String getEnableFlg() {
		return this.enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Long getHostId() {
		return hostId;
	}

	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}

	/**
	 * @return the auto_shotMaxNum
	 */
	// public Long getAuto_shotMaxNum() {
	// return auto_shotMaxNum;
	// }

	/**
	 * @param auto_shotMaxNum the auto_shotMaxNum to set
	 */
	// public void setAuto_shotMaxNum(Long auto_shotMaxNum) {
	// this.auto_shotMaxNum = auto_shotMaxNum;
	// }

	/**
	 * @return the tip
	 */
	public String getTip() {
		return tip;
	}

	/**
	 * @param tip the tip to set
	 */
	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
}
