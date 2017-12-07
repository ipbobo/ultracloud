package com.cmp.entity.tcc;

// Generated 2013-2-6 14:54:20 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TccIpConfigInfo implements java.io.Serializable {

	private static final long serialVersionUID = 5506707305886414278L;

	private Long ipConfigId;

	private String ipType;

	private String usedFlag;// 取值见Ip类中的四个常量：0（未使用）、10（使用中）、20已禁用(获取IP for 小机)

	private String ipAddress;

	private String remark;// 分配说明

	private String memo;// 备注

	private Long crtUserId;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String enableFlg;

	private String name;

	private Set<TccPhysiscResourceInfo> tccPhysiscResourceInfos = new HashSet<TccPhysiscResourceInfo>(
			0);

	private Set<TccApplyedHostinfo> tccApplyedHostinfos = new HashSet<TccApplyedHostinfo>(
			0);

	/**
	 * 分配时间
	 */

	private Date	distributeTime;
	/**
	 * vlan关联
	 */

	private Long	networkId;

	public Long getIpConfigId() {
		return this.ipConfigId;
	}

	public void setIpConfigId(Long ipConfigId) {
		this.ipConfigId = ipConfigId;
	}

	public String getIpType() {
		return this.ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

	public String getUsedFlag() {
		return this.usedFlag;
	}

	public void setUsedFlag(String usedFlag) {
		this.usedFlag = usedFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

	public Set<TccPhysiscResourceInfo> getTccPhysiscResourceInfos() {
		return this.tccPhysiscResourceInfos;
	}

	public void setTccPhysiscResourceInfos(
			Set<TccPhysiscResourceInfo> tccPhysiscResourceInfos) {
		this.tccPhysiscResourceInfos = tccPhysiscResourceInfos;
	}

	public Set<TccApplyedHostinfo> getTccApplyedHostinfos() {
		return this.tccApplyedHostinfos;
	}

	public void setTccApplyedHostinfos(
			Set<TccApplyedHostinfo> tccApplyedHostinfos) {
		this.tccApplyedHostinfos = tccApplyedHostinfos;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getDistributeTime() {
		return distributeTime;
	}

	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}

	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}

}
