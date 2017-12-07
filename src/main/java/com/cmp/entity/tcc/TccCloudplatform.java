package com.cmp.entity.tcc;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TccCloudplatform implements java.io.Serializable {

	private static final long serialVersionUID = 382566707496973339L;

	private Long cloudplatformId; // 平台ID

	private String cloudplatformName;// 平台名称

	private String cloudplatformIp; // 平台IP

	private String cloudplatformUser;// 平台用户

	private String cloudplatformPassword;// 平台密码

	private String cloudplatformPort;// 平台端口

	private String cloudplatformDesc;// 平台描述

	private Date synchronizationTime;// 平台同步时间

	private String cloudplatformType;// 平台名称(0表示：cloudstack 1:表示vcenter
										// 2:表示xenserver 3表示：openstack)

	private String cloudplatformKey;// 平台，如果是venter则为vcenter的指纹信息

	private String enableFlg;

	private String adminurl;

	private String resourceurl;

	/** 状态，不需要在数据库中展示该字段 */

	private String	state;
	/** 创建人id */

	private Long	crtUserId;
	/** 创建时间 */

	private Date	crtDttm;
	/** 最后修改人ID */

	private Long	lastuptUserId;
	/** 最后修改时间 */

	private Date	lastuptDttm;

	/** 创建人，不在数据库中展示该字段 */

	private String	crtUser;
	/** 最后修改人，不在数据库中展示该字段 */

	private String	lastuptUser;

	/** 租户名，openstack需要使用该字段值 */

	private String tenantName;

	private String accessKeyId;

	private String secretAccessKey;

	private Set<TccCloudDatacenter> tccCloudDatacenters = new HashSet<TccCloudDatacenter>(
			0);

	// Constructors

	/** default constructor */
	public TccCloudplatform() {
	}

	// Property accessors

	public Long getCloudplatformId() {
		return this.cloudplatformId;
	}

	public void setCloudplatformId(Long cloudplatformId) {
		this.cloudplatformId = cloudplatformId;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getSecretAccessKey() {
		return secretAccessKey;
	}

	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}

	public String getCloudplatformName() {
		return this.cloudplatformName;
	}

	public void setCloudplatformName(String cloudplatformName) {
		this.cloudplatformName = cloudplatformName;
	}

	public String getCloudplatformIp() {
		return this.cloudplatformIp;
	}

	public void setCloudplatformIp(String cloudplatformIp) {
		this.cloudplatformIp = cloudplatformIp;
	}

	public String getCloudplatformUser() {
		return this.cloudplatformUser;
	}

	public void setCloudplatformUser(String cloudplatformUser) {
		this.cloudplatformUser = cloudplatformUser;
	}

	public String getCloudplatformPassword() {
		return this.cloudplatformPassword;
	}

	public void setCloudplatformPassword(String cloudplatformPassword) {
		this.cloudplatformPassword = cloudplatformPassword;
	}

	public String getCloudplatformPort() {
		return this.cloudplatformPort;
	}

	public void setCloudplatformPort(String cloudplatformPort) {
		this.cloudplatformPort = cloudplatformPort;
	}

	public String getCloudplatformDesc() {
		return this.cloudplatformDesc;
	}

	public void setCloudplatformDesc(String cloudplatformDesc) {
		this.cloudplatformDesc = cloudplatformDesc;
	}

	public Date getSynchronizationTime() {
		return synchronizationTime;
	}

	public void setSynchronizationTime(Date synchronizationTime) {
		this.synchronizationTime = synchronizationTime;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Set<TccCloudDatacenter> getTccCloudDatacenters() {
		return tccCloudDatacenters;
	}

	public void setTccCloudDatacenters(
			Set<TccCloudDatacenter> tccCloudDatacenters) {
		this.tccCloudDatacenters = tccCloudDatacenters;
	}

	public String getCloudplatformType() {
		return cloudplatformType;
	}

	public void setCloudplatformType(String cloudplatformType) {
		this.cloudplatformType = cloudplatformType;
	}

	public String getCloudplatformKey() {
		return cloudplatformKey;
	}

	public void setCloudplatformKey(String cloudplatformKey) {
		this.cloudplatformKey = cloudplatformKey;
	}

	public String getAdminurl() {
		return adminurl;
	}

	public void setAdminurl(String adminurl) {
		this.adminurl = adminurl;
	}

	public String getResourceurl() {
		return resourceurl;
	}

	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public Long getLastuptUserId() {
		return lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public Date getLastuptDttm() {
		return lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public String getCrtUser() {
		return crtUser;
	}

	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	public String getLastuptUser() {
		return lastuptUser;
	}

	public void setLastuptUser(String lastuptUser) {
		this.lastuptUser = lastuptUser;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

}
