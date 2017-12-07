package com.cmp.entity.tcc;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TccApplyedHostinfo implements java.io.Serializable {

	private static final long serialVersionUID = 3468971254789409414L;

	private Long applyResourceId;

	private String hostNane;

	private String ipAddress;

	private String hardwareTypeCd;

	private String osCd;

	private String osVersionCd;

	private String runStatusCd;

	private String useageFlage;

	private String virtualType;

	private String shareFlg;

	private String uuid;

	private String hostLoginUsername;

	private String hostLoginPassword;

	private String memo;

	private Long crtUserId;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String enableFlg;

	private String processStatus;

	private Long orginResourceID;

	private String operateStatus;

	private Long vlanId;

	private String vrSubType;

	private Date closeDttm;

	private Long isSetup;// 是否是安装产生1,安装结果；2，同步结果

	private String autoResizeFlag; // 自动扩容的标识1:自动扩容

	private String resourceType; // 资源类型0:物理机 1:虚拟机

	/* 服务器对应的快照策略 */

	private String exe_time_cycle;

	/* 是否启用动态任务执行定时快照策略 */

	// private Integer quart_enable = BusinessEnvironment.QUAR_ENABLE_FALSE;

	private String quartTip;
	// 云主机监控状态: 0未注册，1监控中，2监控异常

	private String monitorFlg;

	/** 卷轴UUID */

	private String volumeUuid;

	/** 网络uuid */

	private String networkUuid;

	/**
	 * 模板的uuid
	 */

	private String	templateId;
	/**
	 * 集群的Id
	 */

	private String	clusterId;

	/**
	 * 集群的Id
	 */

	private String zoneId;

	private TccIpConfigInfo tccIpConfigInfo;

	private Set<TccSnapshotCfg>	tccSnapshotCfgs	= new HashSet<TccSnapshotCfg>(0);
	/** 硬件类型名称 */

	private String				hardwareTypeCdName;
	/** 操作名称 */

	private String				osVersionCdName;

	/** 操作系统类型 */

	private String osTypeId;

	private String	isoId;
	/** 应用帐号名称 */

	private String	hostSuperUsername;
	/** 应用帐号密码 */

	private String	hostSuperPassword;
	/** NAS存储地址 */

	private String	nasStorageAddreess;
	/*** 模板大小 ***/

	private String	templateSize;

	/*
	 * 流程编号 关联t_cc_srt_procdef.proc_id使用
	 */

	private String produceId;

	/**
	 * 原始的虚拟机的root密码
	 */

	private String originalRootPassword;

	/** VIP占用需求添加start */

	private Set<TccVipSrt> tccVipSrties = new HashSet<TccVipSrt>(0);

	/** 最后登录时间 */

	private Date lastLoginDate;
	/** VIP占用需求添加end */

	/** 是否线下安装 */

	private boolean	offlineinstall;
	/** 序列号 */

	private String	serialNo;
	/** 虚拟机备注 */
	private String	remark;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public boolean isOfflineinstall() {
		return offlineinstall;
	}

	public void setOfflineinstall(boolean offlineinstall) {
		this.offlineinstall = offlineinstall;
	}

	public String getProduceId() {
		return produceId;
	}

	public void setProduceId(String produceId) {
		this.produceId = produceId;
	}

	public String getOsTypeId() {
		return osTypeId;
	}

	public void setOsTypeId(String osTypeId) {
		this.osTypeId = osTypeId;
	}

	public String getIsoId() {
		return isoId;
	}

	public void setIsoId(String isoId) {
		this.isoId = isoId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getAutoResizeFlag() {
		return autoResizeFlag;
	}

	public void setAutoResizeFlag(String autoResizeFlag) {
		this.autoResizeFlag = autoResizeFlag;
	}

	public String getMonitorFlg() {
		return monitorFlg;
	}

	public void setMonitorFlg(String monitorFlg) {
		this.monitorFlg = monitorFlg;
	}

	public Long getApplyResourceId() {
		return this.applyResourceId;
	}

	public void setApplyResourceId(Long applyResourceId) {
		this.applyResourceId = applyResourceId;
	}

	public Long getIsSetup() {
		return isSetup;
	}

	public void setIsSetup(Long isSetup) {
		this.isSetup = isSetup;
	}

	public String getVrSubType() {
		return vrSubType;
	}

	public void setVrSubType(String vrSubType) {
		this.vrSubType = vrSubType;
	}

	public Long getVlanId() {
		return vlanId;
	}

	public void setVlanId(Long vlanId) {
		this.vlanId = vlanId;
	}

	public String getHostNane() {
		return this.hostNane;
	}

	public void setHostNane(String hostNane) {
		this.hostNane = hostNane;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getHardwareTypeCd() {
		return this.hardwareTypeCd;
	}

	public void setHardwareTypeCd(String hardwareTypeCd) {
		this.hardwareTypeCd = hardwareTypeCd;
	}

	public String getOsCd() {
		return this.osCd;
	}

	public void setOsCd(String osCd) {
		this.osCd = osCd;
	}

	public String getOsVersionCd() {
		return this.osVersionCd;
	}

	public void setOsVersionCd(String osVersionCd) {
		this.osVersionCd = osVersionCd;
	}

	public String getRunStatusCd() {
		return this.runStatusCd;
	}

	public void setRunStatusCd(String runStatusCd) {
		this.runStatusCd = runStatusCd;
	}

	public String getUseageFlage() {
		return this.useageFlage;
	}

	public void setUseageFlage(String useageFlage) {
		this.useageFlage = useageFlage;
	}

	public String getVirtualType() {
		return this.virtualType;
	}

	public void setVirtualType(String virtualType) {
		this.virtualType = virtualType;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getCrtUserId() {
		return this.crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtDttm() {
		return this.crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Date getLastuptDttm() {
		return this.lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public Long getLastuptUserId() {
		return this.lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public String getEnableFlg() {
		return this.enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public TccIpConfigInfo getTccIpConfigInfo() {
		return this.tccIpConfigInfo;
	}

	public void setTccIpConfigInfo(TccIpConfigInfo tccIpConfigInfo) {
		this.tccIpConfigInfo = tccIpConfigInfo;
	}

	public String getShareFlg() {
		return shareFlg;
	}

	public void setShareFlg(String shareFlg) {
		this.shareFlg = shareFlg;
	}

	public String getHostLoginUsername() {
		return hostLoginUsername;
	}

	public void setHostLoginUsername(String hostLoginUsername) {
		this.hostLoginUsername = hostLoginUsername;
	}

	public String getHostLoginPassword() {
		return hostLoginPassword;
	}

	public void setHostLoginPassword(String hostLoginPassword) {
		this.hostLoginPassword = hostLoginPassword;
	}

	public String getHardwareTypeCdName() {
		return hardwareTypeCdName;
	}

	public void setHardwareTypeCdName(String hardwareTypeCdName) {
		this.hardwareTypeCdName = hardwareTypeCdName;
	}

	public String getOsVersionCdName() {
		return osVersionCdName;
	}

	public void setOsVersionCdName(String osVersionCdName) {
		this.osVersionCdName = osVersionCdName;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public Long getOrginResourceID() {
		return orginResourceID;
	}

	public void setOrginResourceID(Long orginResourceID) {
		this.orginResourceID = orginResourceID;
	}

	public String getHostSuperUsername() {
		return hostSuperUsername;
	}

	public void setHostSuperUsername(String hostSuperUsername) {
		this.hostSuperUsername = hostSuperUsername;
	}

	public String getHostSuperPassword() {
		return hostSuperPassword;
	}

	public void setHostSuperPassword(String hostSuperPassword) {
		this.hostSuperPassword = hostSuperPassword;
	}

	public String getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getVolumeUuid() {
		return this.volumeUuid;
	}

	public void setVolumeUuid(String volumeUuid) {
		this.volumeUuid = volumeUuid;
	}

	public String getNasStorageAddreess() {
		return nasStorageAddreess;
	}

	public void setNasStorageAddreess(String nasStorageAddreess) {
		this.nasStorageAddreess = nasStorageAddreess;
	}

	public Set<TccSnapshotCfg> getTccSnapshotCfgs() {
		return tccSnapshotCfgs;
	}

	public void setTccSnapshotCfgs(Set<TccSnapshotCfg> tccSnapshotCfgs) {
		this.tccSnapshotCfgs = tccSnapshotCfgs;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getTemplateSize() {
		return templateSize;
	}

	public void setTemplateSize(String templateSize) {
		this.templateSize = templateSize;
	}

	public Date getCloseDttm() {
		return closeDttm;
	}

	public void setCloseDttm(Date closeDttm) {
		this.closeDttm = closeDttm;
	}

	/**
	 * @return the exe_time_cycle
	 */
	public String getExe_time_cycle() {
		return exe_time_cycle;
	}

	/**
	 * @param exe_time_cycle the exe_time_cycle to set
	 */
	public void setExe_time_cycle(String exe_time_cycle) {
		this.exe_time_cycle = exe_time_cycle;
	}

	/**
	 * @return the quart_enable
	 */
	// public Integer getQuart_enable() {
	// return quart_enable;
	// }

	/**
	 * @param quart_enable the quart_enable to set
	 */
	// public void setQuart_enable(Integer quart_enable) {
	// this.quart_enable = quart_enable;
	// }

	public String getNetworkUuid() {
		return networkUuid;
	}

	public void setNetworkUuid(String networkUuid) {
		this.networkUuid = networkUuid;
	}

	public String getQuartTip() {
		return quartTip;
	}

	public void setQuartTip(String quartTip) {
		this.quartTip = quartTip;
	}

	public String getOriginalRootPassword() {
		return originalRootPassword;
	}

	public void setOriginalRootPassword(String originalRootPassword) {
		this.originalRootPassword = originalRootPassword;
	}

	public Set<TccVipSrt> getTccVipSrties() {
		return tccVipSrties;
	}

	public void setTccVipSrties(Set<TccVipSrt> tccVipSrties) {
		this.tccVipSrties = tccVipSrties;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
