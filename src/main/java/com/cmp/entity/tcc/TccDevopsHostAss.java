package com.cmp.entity.tcc;

import java.util.Date;

/**
 * 运维服务工单与虚拟机的关系类
 * 
 * @author HCJ
 */

public class TccDevopsHostAss implements java.io.Serializable {

	private static final long serialVersionUID = 6056614527837380731L;

	// 关系主键

	private Long assId;

	// 云主机ID

	private Long resourceId;

	// 镜像工单ID

	private Long srtId;

	private String enableFlg;

	// 操作状态(0操作,1正,2成功,3失败)

	private String operateFlg;

	// 操作者

	private Long operateUser;

	// 操作日期

	private String operateDate;

	/************************************************* ROOT权限申请 ******************************************/
	/**
	 * root密码
	 */

	private String	password;
	/**
	 * 使用期限
	 */

	private Date	duration;

	private String hostName;

	private String ipAddress;

	/** 产品需求4.0.1 [补充安装软件] start */

	private String softNameAndVersionStr;

	public String getSoftNameAndVersionStr() {
		return softNameAndVersionStr;
	}

	public void setSoftNameAndVersionStr(String softNameAndVersionStr) {
		this.softNameAndVersionStr = softNameAndVersionStr;
	}

	/** 产品需求4.0.1 [补充安装软件] end */

	public Long getAssId() {
		return assId;
	}

	public void setAssId(Long assId) {
		this.assId = assId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getSrtId() {
		return srtId;
	}

	public void setSrtId(Long srtId) {
		this.srtId = srtId;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public String getOperateFlg() {
		return operateFlg;
	}

	public void setOperateFlg(String operateFlg) {
		this.operateFlg = operateFlg;
	}

	public Long getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(Long operateUser) {
		this.operateUser = operateUser;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDuration() {
		return duration;
	}

	public void setDuration(Date duration) {
		this.duration = duration;
	}

}
