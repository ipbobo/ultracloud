package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 小机硬件管理平台信息
 */
public class TccHmc implements Serializable {

	private static final long serialVersionUID = 562835544893046552L;

	/**
	 * 主键Id
	 */

	private Long id;

	/**
	 * 别名
	 */

	private String alias;

	/**
	 * HMC服务IP地址
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
	 * HMC服务器连接状态
	 * 代之，能否ping通
	 * 1能ping通；0不能
	 */

	private String isConnected;

	/**
	 * HMC存活状态
	 * 代之能否通过lshmc -V命令获取HMC版本信息
	 * 1存活；0未知
	 */

	private String state;

	/**
	 * 删除标识
	 * 0删除
	 * 1可用
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

	/**
	 * 被HMC管理的服务器
	 */

	private List<TccHmcManagedSystem> hmcManagedSystems;

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

	public List<TccHmcManagedSystem> getHmcManagedSystems() {
		return hmcManagedSystems;
	}

	public void setHmcManagedSystems(List<TccHmcManagedSystem> hmcManagedSystems) {
		this.hmcManagedSystems = hmcManagedSystems;
	}

}
