package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class TccTenantQuotaApply extends TccSrt implements Serializable {

	private static final long	serialVersionUID	= -197755027867434495L;
	/**
	 * CPU配额
	 */

	private int					cpuQuota;
	/**
	 * 内存配额
	 */

	private int					ramQuota;
	/**
	 * 存储配额
	 */

	private Long				storageQuota;
	/**
	 * 申请人
	 */

	private Long				applyUserId;

	/**
	 * 已分配的资源
	 */

	public Set<TccDistributedResource> distributedResources;

	/**
	 * 是否生效，是否已经划分资源
	 * 0：否
	 * 1：是
	 */

	private String	isActive;
	/**
	 * 有效标识
	 * 0：无效
	 * 1：有效
	 */

	private String	enableFlag;

	/**
	 * 使用期限
	 */

	private Date closeDate;

	/**
	 * ip数量
	 */

	private int ips;

	/**
	 * 老配额工单ID
	 */

	private Long oldSrtId;

	/**
	 * 是否已分配资源
	 */

	private boolean isDistributed;

	public int getCpuQuota() {
		return cpuQuota;
	}

	public void setCpuQuota(int cpuQuota) {
		this.cpuQuota = cpuQuota;
	}

	public int getRamQuota() {
		return ramQuota;
	}

	public void setRamQuota(int ramQuota) {
		this.ramQuota = ramQuota;
	}

	public Long getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Set<TccDistributedResource> getDistributedResources() {
		return distributedResources;
	}

	public void setDistributedResources(
			Set<TccDistributedResource> distributedResources) {
		this.distributedResources = distributedResources;
	}

	public Long getStorageQuota() {
		return storageQuota;
	}

	public void setStorageQuota(Long storageQuota) {
		this.storageQuota = storageQuota;
	}

	public int getIps() {
		return ips;
	}

	public void setIps(int ips) {
		this.ips = ips;
	}

	public Long getOldSrtId() {
		return oldSrtId;
	}

	public void setOldSrtId(Long oldSrtId) {
		this.oldSrtId = oldSrtId;
	}

	public boolean isDistributed() {
		return isDistributed;
	}

	public void setDistributed(boolean isDistributed) {
		this.isDistributed = isDistributed;
	}

}
