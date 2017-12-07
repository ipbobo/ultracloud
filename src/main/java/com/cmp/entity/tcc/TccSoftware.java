package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

/**
 * 软件信息
 */
public class TccSoftware implements Serializable {

	private static final long serialVersionUID = -3628150162957568009L;

	/**
	 * 主键
	 */
	private Long	id;
	/**
	 * 软件名
	 */

	private String	name;
	/**
	 * 软件版本
	 */

	private String	version;
	/**
	 * 安装路径
	 */

	private String	path;
	/**
	 * 是否在运行中
	 */

	private int		isRun;
	/**
	 * 软件来源
	 */

	private String	source;
	/**
	 * 安装此软件的用户
	 */

	private Long	installUser;
	/**
	 * 安装在什么操作系统上面
	 * 0：linux
	 * 1：windows
	 * 2：aix
	 */

	private int		runningOS;
	/**
	 * 软件所在机器ID
	 */

	private Long	vmID;

	/**
	 * 可用标识
	 */

	private int enableFlg;

	/**
	 * 安装日期
	 */

	private Date installTime;

	/**
	 * 更新日期
	 */

	private Date	updateTime;
	/**
	 * 安装此软件的用户
	 */

	private Long	updateUser;

	/** 产品需求4.0.1 [补充安装软件] start */
	/**
	 * 备注
	 */

	private String remark;

	/** 产品需求4.0.1 [补充安装软件] end */

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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getIsRun() {
		return isRun;
	}

	public void setIsRun(int isRun) {
		this.isRun = isRun;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getInstallUser() {
		return installUser;
	}

	public void setInstallUser(Long installUser) {
		this.installUser = installUser;
	}

	public int getRunningOS() {
		return runningOS;
	}

	public void setRunningOS(int runningOS) {
		this.runningOS = runningOS;
	}

	public int getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(int enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Long getVmID() {
		return vmID;
	}

	public void setVmID(Long vmID) {
		this.vmID = vmID;
	}

	public Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

}
