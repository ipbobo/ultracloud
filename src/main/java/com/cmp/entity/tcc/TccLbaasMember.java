package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

public class TccLbaasMember implements Serializable {

	private static final long serialVersionUID = 61503121156394412L;

	private Long			id;
	/**
	 * name
	 */

	private String			name;
	/**
	 * uuid
	 */

	private String			uuid;
	/**
	 * 状态
	 */

	private String			status;
	/**
	 * 所属资源池
	 */

	private TccLbaasPool	pool;
	/**
	 * ip
	 */

	private String			ipAddress;
	/**
	 * 状态描述
	 */

	private String			statusDescription;
	/**
	 * 管理状态 true/false
	 */

	private boolean			adminStateUp;
	/**
	 * 权重
	 */

	private int				weight;
	/**
	 * 租户ID
	 */

	private String			tenantId;
	/**
	 * 注册进F5的设备在私有云中的ID
	 */

	private String			vmId;
	/**
	 * 创建时间
	 */

	private Date			createDate;

	// 端口

	private int protocolPort;

	// 是否有效

	private String enableFlg;

	// 变更时间

	private Date updateDate;

	private String projectName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TccLbaasPool getPool() {
		return pool;
	}

	public void setPool(TccLbaasPool pool) {
		this.pool = pool;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public boolean isAdminStateUp() {
		return adminStateUp;
	}

	public void setAdminStateUp(boolean adminStateUp) {
		this.adminStateUp = adminStateUp;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getProtocolPort() {
		return protocolPort;
	}

	public void setProtocolPort(int protocolPort) {
		this.protocolPort = protocolPort;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
