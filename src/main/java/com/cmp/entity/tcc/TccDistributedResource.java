package com.cmp.entity.tcc;

/**
 * 已分配给租户管理员的资源
 * 
 * @author ZL
 * @date 2016年10月9日
 */

public class TccDistributedResource {

	private Long	id;
	/**
	 * 分配到的网络
	 */

	private Long	vlanId;
	/**
	 * 分配给到的集群
	 */

	private Long	clusterId;
	/**
	 * 分配到的网络区域
	 */

	private Long	vlanAreaId;
	/**
	 * 分配到资源池类型
	 */

	private Long	cloudplatformTypeId;
	/**
	 * 分配到的资源池
	 */

	private Long	cloudplatformId;
	/**
	 * 分配到的Zone
	 */

	private Long	zoneId;

	/**
	 * 租户配额申请工单
	 */

	public TccTenantQuotaApply quotaApply;

	public Long getVlanId() {
		return vlanId;
	}

	public void setVlanId(Long vlanId) {
		this.vlanId = vlanId;
	}

	public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}

	public Long getVlanAreaId() {
		return vlanAreaId;
	}

	public void setVlanAreaId(Long vlanAreaId) {
		this.vlanAreaId = vlanAreaId;
	}

	public Long getCloudplatformTypeId() {
		return cloudplatformTypeId;
	}

	public void setCloudplatformTypeId(Long cloudplatformTypeId) {
		this.cloudplatformTypeId = cloudplatformTypeId;
	}

	public Long getCloudplatformId() {
		return cloudplatformId;
	}

	public void setCloudplatformId(Long cloudplatformId) {
		this.cloudplatformId = cloudplatformId;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public TccTenantQuotaApply getQuotaApply() {
		return quotaApply;
	}

	public void setQuotaApply(TccTenantQuotaApply quotaApply) {
		this.quotaApply = quotaApply;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
