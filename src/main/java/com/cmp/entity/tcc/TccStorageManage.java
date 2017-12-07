package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方虚拟化存储管理
 */
public class TccStorageManage implements Serializable {

	private static final long serialVersionUID = -6564562076577183453L;

	/**
	 * 主键Id
	 */

	private Long id;

	/**
	 * 别名
	 */

	private String alias;

	/**
	 * 存储系统IP地址
	 */

	private String host;

	/**
	 * 服务器登录名
	 */

	private String loginName;

	/**
	 * 服务器登录密码
	 */

	private String loginPwd;

	/**
	 * 服务器端口
	 */

	private String port;

	/**
	 * 备注
	 */

	private String remark;

	/**
	 * 存储服务器连接状态 代之，能否ping通 1能ping通；0不能
	 */

	private String isConnected;

	/**
	 * 存储服务存活状态 判断依据：是否可以sdk获取版本信息 1存活；0未知
	 */

	private String state;

	/**
	 * 是否是集群模式
	 */

	private String isClustered;

	/**
	 * 版本信息
	 */

	private String version;

	/**
	 * 存储厂商类型
	 */

	private String vendorType;

	/**
	 * 删除标识 0删除 1可用
	 */

	private String enableFlg;

	/**
	 * 创建人
	 */

	private Long crtUserId;

	private String crtName;

	/**
	 * 创建时间
	 */

	private Date crtDttm;

	/**
	 * 修改人
	 */

	private Long lastuptUserId;

	/**
	 * 修改时间
	 */

	private Date lastuptDttm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(String isConnected) {
		this.isConnected = isConnected;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIsClustered() {
		return isClustered;
	}

	public void setIsClustered(String isClustered) {
		this.isClustered = isClustered;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVendorType() {
		return vendorType;
	}

	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Long getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public String getCrtName() {
		return crtName;
	}

	public void setCrtName(String crtName) {
		this.crtName = crtName;
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

}
