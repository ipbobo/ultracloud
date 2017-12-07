package com.cmp.entity.tcc;

/**
 * Description:已分配应用主机资源信息引用T_CC_APPLYED_HOSTINFO表主键 TccApplyedHostResource.java
 * Create on Feb 27, 2013 9:47:29 AM
 * 
 * @author bin.liu
 * @version 1.0
 */

public class TccApplyedHostResource extends TccApplyedHostinfo implements
		java.io.Serializable {

	private static final long serialVersionUID = 8802730675351267475L;

	private Double cpuCoreCount;

	private Double ramSize;

	private Double nasStorageSize;

	private Double storageSize;

	private String cpuUsage;

	private TccPhysiscResourceInfo tccPhysiscResourceInfo;

	// public Long getApplyResourceId() {
	// return this.applyResourceId;
	// }

	// public void setApplyResourceId(Long applyResourceId) {
	// this.applyResourceId = applyResourceId;
	// }

	public Double getCpuCoreCount() {
		return this.cpuCoreCount;
	}

	public void setCpuCoreCount(Double cpuCoreCount) {
		this.cpuCoreCount = cpuCoreCount;
	}

	public Double getRamSize() {
		return this.ramSize;
	}

	public void setRamSize(Double ramSize) {
		this.ramSize = ramSize;
	}

	public Double getNasStorageSize() {
		return this.nasStorageSize;
	}

	public void setNasStorageSize(Double nasStorageSize) {
		this.nasStorageSize = nasStorageSize;
	}

	public Double getStorageSize() {
		return this.storageSize;
	}

	public void setStorageSize(Double storageSize) {
		this.storageSize = storageSize;
	}

	public TccPhysiscResourceInfo getTccPhysiscResourceInfo() {
		return this.tccPhysiscResourceInfo;
	}

	public void setTccPhysiscResourceInfo(
			TccPhysiscResourceInfo tccPhysiscResourceInfo) {
		this.tccPhysiscResourceInfo = tccPhysiscResourceInfo;
	}

	public String getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(String cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

}
