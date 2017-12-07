package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

public class TccVipAndVmGroup implements Serializable {

	private static final long serialVersionUID = 197755027867546495L;

	private Long id;

	private String ipconfigInfo;

	private Long resourceId;

	private Long groupId;

	private int enableFlg;// 1表示有效，0表示无效

	private Date crtDttm;

	private Long srtId;

	private String cloudplatformName; // 资源池

	private String networkName; // 网络

	private String ipAddress; // IP地址

	public TccVipAndVmGroup() {
	}

	public TccVipAndVmGroup(String ipconfigInfo, Long resourceId, Long groupId, Long srtId) {
		super();
		this.ipconfigInfo = ipconfigInfo;
		this.resourceId = resourceId;
		this.groupId = groupId;
		this.enableFlg = 1;
		this.crtDttm = new Date();
		this.srtId = srtId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getIpconfigInfo() {
		return ipconfigInfo;
	}

	public void setIpconfigInfo(String ipconfigInfo) {
		this.ipconfigInfo = ipconfigInfo;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public int getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(int enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Date getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Long getSrtId() {
		return srtId;
	}

	public void setSrtId(Long srtId) {
		this.srtId = srtId;
	}

	public String getCloudplatformName() {
		return cloudplatformName;
	}

	public void setCloudplatformName(String cloudplatformName) {
		this.cloudplatformName = cloudplatformName;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
