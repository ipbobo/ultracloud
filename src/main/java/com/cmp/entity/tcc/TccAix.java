package com.cmp.entity.tcc;

import java.io.Serializable;

public class TccAix implements Serializable {

	private static final long serialVersionUID = -2472024542102976125L;

	/** 主键 */

	private Long	id;
	/** 展示名 */

	private String	displayName;
	/** 操作系统登录名 */

	private String	loginName;
	/** 操作系统登录密码 */

	private String	loginPassword;
	/** 操作系统IP地址 */

	private String	ipAddress;
	/** 所属逻辑分区（HMC中） */

	private String	lparID;

	private String	enableFlag;
	/**
	 * 所属逻辑分区（数据库中对应表）
	 */

	private Long	ledId;

	private String physicName;

	private String applyedResourceID;// 关联虚拟机表

	public TccAix(Long id, String displayName, String ipAddress, String lparName) {
		super();
		this.id = id;
		this.displayName = displayName;
		this.ipAddress = ipAddress;
		this.lparName = lparName;
	}

	public String getApplyedResourceID() {
		return applyedResourceID;
	}

	public void setApplyedResourceID(String applyedResourceID) {
		this.applyedResourceID = applyedResourceID;
	}

	public TccAix() {
	}

	public TccAix(String lparId, String lparName, String ipAddress, String osVersion) {
		super();
		this.lparID = lparId;
		this.lparName = lparName;
		this.ipAddress = ipAddress;
	}

	/**
	 * 逻辑分区名
	 */

	private String lparName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLparName() {
		return lparName;
	}

	public void setLparName(String lparName) {
		this.lparName = lparName;
	}

	public String getLparID() {
		return lparID;
	}

	public void setLparID(String lparID) {
		this.lparID = lparID;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getPhysicName() {
		return physicName;
	}

	public void setPhysicName(String physicName) {
		this.physicName = physicName;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLedId() {
		return ledId;
	}

	public void setLedId(Long ledId) {
		this.ledId = ledId;
	}

	public Long getId() {
		return id;
	}
}
