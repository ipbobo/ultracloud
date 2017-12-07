package com.cmp.entity.tcc;

import java.io.Serializable;

public class TccLbaasVip implements Serializable {

	private static final long serialVersionUID = 2949214070085416969L;

	// Fields
	private Long id;

	/** VIP ID,openstack中的ID UUID */

	private String vipId;

	/** 租户id */

	private String tenantId;

	/** VIP名称 */

	private String name;

	/** VIP描述信息 */

	private String description;

	/** VIP在此Subnet分配 */

	private String subnetId;

	/** VIP对应的IP地址，公有云场景必须指定 */

	private String address;

	/** 端口id, */

	private String portId;

	/** 协议号 */

	private String protocolPort;

	/** 协议'TCP', 'HTTP', 'HTTPS' */

	private String protocol;

	/**
	 * 会话保持类型
	 * {
	 * ‘type’: APP_COOKIE\HTTP_COOKIE\SOURCE_IP,
	 * ‘cookie_name’:””（可选仅在type为APP_COOKIE时有意义）
	 * }
	 */

	private String sessionPersistence;

	/** 连接数 */

	private String connectionLimit;

	/** 管理状态, true/false */

	private Boolean adminStateUp;

	/**
	 * cookieName
	 */

	private String cookieName;

	/**
	 * 状态：
	 * INACTIVE
	 * ACTIVE
	 * PENDING_CREATE
	 * ERROR
	 */

	private String status;

	/** 状态描述 */

	private String statusDescri;

	private TccLbaasPool pool;

	// 是否有效

	private String enableFlg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPortId() {
		return portId;
	}

	public void setPortId(String portId) {
		this.portId = portId;
	}

	public String getProtocolPort() {
		return protocolPort;
	}

	public void setProtocolPort(String protocolPort) {
		this.protocolPort = protocolPort;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getSessionPersistence() {
		return sessionPersistence;
	}

	public void setSessionPersistence(String sessionPersistence) {
		this.sessionPersistence = sessionPersistence;
	}

	public String getConnectionLimit() {
		return connectionLimit;
	}

	public void setConnectionLimit(String connectionLimit) {
		this.connectionLimit = connectionLimit;
	}

	public Boolean getAdminStateUp() {
		return adminStateUp;
	}

	public void setAdminStateUp(Boolean adminStateUp) {
		this.adminStateUp = adminStateUp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescri() {
		return statusDescri;
	}

	public void setStatusDescri(String statusDescri) {
		this.statusDescri = statusDescri;
	}

	public TccLbaasPool getPool() {
		return pool;
	}

	public void setPool(TccLbaasPool pool) {
		this.pool = pool;
	}

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}
}
