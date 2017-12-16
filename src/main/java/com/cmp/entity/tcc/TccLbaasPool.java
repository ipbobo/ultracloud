package com.cmp.entity.tcc;

import java.util.List;

public class TccLbaasPool implements java.io.Serializable {

	private static final long serialVersionUID = 284226726378578182L;

	private Long id;

	/** Pool ID */

	private String poolId;

	/** 租户id */

	private String tenantId;

	/** Vip ID */

	private String vipId;

	/** pool名称 */

	private String name;

	// 是否有效

	private String enableFlg;

	/** pool描述信息 */

	private String description;

	/** 后端member的所属子网，当前无意义 */

	private String subnetId;

	/** 服务提供者.,公有云场景必须指定,值为haproxy_int */

	private String provider;

	/** 后端协议'TCP', 'HTTP', 'HTTPS' */

	private String protocol;

	/**
	 * 负载均衡算法
	 * “ROUND_ROBIN”
	 * “LEAST_CONNECTIONS”
	 */

	private String lbMethod;

	// 负载均衡池下的member数量

	private String memberCount;

	/** 健康检查uuid列表 */

	private List<String> healthMonitors;

	/** 健康状态 “INACTIVE”, ”ACTIVE”、“PENDING_CREATE”,”ERROR” */

	private String healthMonitorsStatus;

	/** 管理状态, true/false */

	private boolean adminStateUp;

	/**
	 * 状态 “INACTIVE”, ”ACTIVE”、“PENDING_CREATE”,”ERROR”
	 */

	private String status;

	/** 状态描述 */

	private String statusDescription;

	private TccCloudPlatform	cloudplatform;
	/**
	 * 子网名称
	 */

	private String				subnetName;
	/**
	 * 子网cidr
	 */

	private String				cidr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPoolId() {
		return poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getLbMethod() {
		return lbMethod;
	}

	public void setLbMethod(String lbMethod) {
		this.lbMethod = lbMethod;
	}

	public List<String> getHealthMonitors() {
		return healthMonitors;
	}

	public void setHealthMonitors(List<String> healthMonitors) {
		this.healthMonitors = healthMonitors;
	}

	public String getHealthMonitorsStatus() {
		return healthMonitorsStatus;
	}

	public void setHealthMonitorsStatus(String healthMonitorsStatus) {
		this.healthMonitorsStatus = healthMonitorsStatus;
	}

	public boolean isAdminStateUp() {
		return adminStateUp;
	}

	public void setAdminStateUp(boolean adminStateUp) {
		this.adminStateUp = adminStateUp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public TccCloudPlatform getCloudplatform() {
		return cloudplatform;
	}

	public void setCloudplatform(TccCloudPlatform cloudplatform) {
		this.cloudplatform = cloudplatform;
	}

	public String getSubnetName() {
		return subnetName;
	}

	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}

	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public String getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(String memberCount) {
		this.memberCount = memberCount;
	}

}
