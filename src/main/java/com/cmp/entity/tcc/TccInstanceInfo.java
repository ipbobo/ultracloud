package com.cmp.entity.tcc;

import java.util.Date;

public class TccInstanceInfo implements java.io.Serializable {

	private static final long serialVersionUID = 8500727157032094097L;

	private Long instanceId;

	private String appType;

	private String accountInstaceName;

	private String version;

	private String memo;

	private Long crtUserId;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String enableFlg;

	private String instaceStatus;

	private Long instPort;

	private String operateStatus;

	private Long orginInstanceId;

	/**
	 * 实例信息ID
	 * 
	 * @return
	 */
	public Long getInstanceId() {
		return this.instanceId;
	}

	/**
	 * 实例信息ID
	 * 
	 * @return
	 */
	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * 应用类型(01:应用服务器;02:数据库服务器)
	 * 
	 * @return
	 */
	public String getAppType() {
		return this.appType;
	}

	/**
	 * 应用类型(01:应用服务器;02:数据库服务器)
	 * 
	 * @return
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * 应用账户/实例名称
	 * 
	 * @return
	 */
	public String getAccountInstaceName() {
		return this.accountInstaceName;
	}

	/**
	 * 应用账户/实例名称
	 * 
	 * @return
	 */
	public void setAccountInstaceName(String accountInstaceName) {
		this.accountInstaceName = accountInstaceName;
	}

	/**
	 * 版本
	 * 
	 * @return
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * 版本
	 * 
	 * @return
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 备注
	 * 
	 * @return
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 创建人
	 * 
	 * @return
	 */
	public Long getCrtUserId() {
		return this.crtUserId;
	}

	/**
	 * 创建人
	 * 
	 * @return
	 */
	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	/**
	 * 创建日期时间
	 * 
	 * @return
	 */
	public Date getCrtDttm() {
		return this.crtDttm;
	}

	/**
	 * 创建日期时间
	 * 
	 * @return
	 */
	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	/**
	 * 最后更新时间
	 * 
	 * @return
	 */
	public Date getLastuptDttm() {
		return this.lastuptDttm;
	}

	/**
	 * 最后更新时间
	 * 
	 * @return
	 */
	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	/**
	 * 最后修改人
	 * 
	 * @return
	 */
	public Long getLastuptUserId() {
		return this.lastuptUserId;
	}

	/**
	 * 最后修改人
	 * 
	 * @return
	 */
	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	/**
	 * 数据是否有效(0:无效;1:有效)
	 * 
	 * @return
	 */
	public String getEnableFlg() {
		return this.enableFlg;
	}

	/**
	 * 数据是否有效(0:无效;1:有效)
	 */
	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public String getInstaceStatus() {
		return instaceStatus;
	}

	public void setInstaceStatus(String instaceStatus) {
		this.instaceStatus = instaceStatus;
	}

	public Long getInstPort() {
		return instPort;
	}

	public void setInstPort(Long instPort) {
		this.instPort = instPort;
	}

	public Long getOrginInstanceId() {
		return orginInstanceId;
	}

	public void setOrginInstanceId(Long orginInstanceId) {
		this.orginInstanceId = orginInstanceId;
	}

	public String getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}

}
