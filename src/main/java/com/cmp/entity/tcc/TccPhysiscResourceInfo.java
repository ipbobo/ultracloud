package com.cmp.entity.tcc;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TccPhysiscResourceInfo implements java.io.Serializable {

	private static final long serialVersionUID = 6365242794006240699L;

	private Long resourceId; // 主键ID自增长

	private String physicsIp; // 冗余物理IP地址

	private String physicsName; // 物理主机名称

	private Double cpuCoreTotalCount; // CPU总核数

	private Double ramTotalSize; // 内存总大小(单位为MB)

	private Double cpuCoreRemainCount;// CPU剩余总核数

	private Long toStCpu; // 分配给压测池的CPU大小

	private Long toStMem; // 分配给压测池的MEM大小

	private Double ramRemainSize; // 内存剩余大小(单位为MB)

	private String hardwareTypeCd;

	private String hardwareTypeCdName;// 硬件类型CD,取参数表

	private String osCd; // 物理机操作系统ID,取参数表CD

	private String osVersionCd; // 物理操作系统版本,取参数表CD与操作系统类型形成父子关系

	private String virtualFlag; // 是否为虚似主机()(物理主机是否用于虚似化环境)

	private String uuid;

	private String usageFlag; // 主机使用类型(01:开发;02:压力测试)

	private String recordNo;

	private String physicsBelong; // 硬件归属(01:田林;02:张江;)

	private String memo; // 备注

	private Long crtUserId;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String enableFlg;

	private String unitTypeNo; // 设备型号

	private String enclosureInfo; // 机柜信息

	private String serialNo; // 序列号

	private String contractNo; // 合同号

	private String resourcePlat;// 资源平台 (cloudstack或者vcenter)

	private String account;// 物理机的账号

	private String password;// 物理机的密码

	private String powerState; // 裸金属物理机电源状态

	private String provisionState; // 裸金属物理机发放状态

	private Set<TccApplyedHostResource> tccApplyedHostResources = new HashSet<TccApplyedHostResource>(
			0);

	private TccIpConfigInfo tccIpConfigInfo;
	// private TccBatch tccBatch;

	private TccClusterConfig tccCluster;

	private String model;

	private String brand;

	private String floor;

	private Integer floorNum;

	private String enclosureU;

	private Integer roomNum;

	private Long clusterId;

	private Long zoneId;

	private Double storage; // 数据盘存储大小

	private TccCloudDatacenter tccCloudDatacenter;

	private String	osName;			// 操作系统
	/**
	 * 资源池Id
	 */

	private Long	cloudPlatformId;
	/**
	 * 是否裸金属物理机
	 * 0：不是
	 * 1：是
	 */

	private String	isBareBetal;

	private Date maintenanceDate;

	private boolean synch;

	/** 使用人 */

	private String usedUser;

	public String getUsedUser() {
		return usedUser;
	}

	public void setUsedUser(String usedUser) {
		this.usedUser = usedUser;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public boolean isSynch() {
		return synch;
	}

	public void setSynch(boolean synch) {
		this.synch = synch;
	}

	public String getPowerState() {
		return powerState;
	}

	public void setPowerState(String powerState) {
		this.powerState = powerState;
	}

	public String getProvisionState() {
		return provisionState;
	}

	public void setProvisionState(String provisionState) {
		this.provisionState = provisionState;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResourcePlat() {
		return resourcePlat;
	}

	public void setResourcePlat(String resourcePlat) {
		this.resourcePlat = resourcePlat;
	}

	public TccCloudDatacenter getTccCloudDatacenter() {
		return tccCloudDatacenter;
	}

	public void setTccCloudDatacenter(TccCloudDatacenter tccCloudDatacenter) {
		this.tccCloudDatacenter = tccCloudDatacenter;
	}

	public TccClusterConfig getTccCluster() {
		return tccCluster;
	}

	public void setTccCluster(TccClusterConfig tccCluster) {
		this.tccCluster = tccCluster;
	}

	/*
	 * public TccBatch getTccBatch() {
	 * return tccBatch;
	 * }
	 * 
	 * public void setTccBatch(TccBatch tccBatch) {
	 * this.tccBatch = tccBatch;
	 * }
	 */

	public Long getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getPhysicsIp() {
		return this.physicsIp;
	}

	public void setPhysicsIp(String physicsIp) {
		this.physicsIp = physicsIp;
	}

	public String getPhysicsName() {
		return this.physicsName;
	}

	public void setPhysicsName(String physicsName) {
		this.physicsName = physicsName;
	}

	public Double getCpuCoreTotalCount() {
		return this.cpuCoreTotalCount;
	}

	public void setCpuCoreTotalCount(Double cpuCoreTotalCount) {
		this.cpuCoreTotalCount = cpuCoreTotalCount;
	}

	public Double getRamTotalSize() {
		return this.ramTotalSize;
	}

	public void setRamTotalSize(Double ramTotalSize) {
		this.ramTotalSize = ramTotalSize;
	}

	public Double getCpuCoreRemainCount() {
		return this.cpuCoreRemainCount;
	}

	public void setCpuCoreRemainCount(Double cpuCoreRemainCount) {
		this.cpuCoreRemainCount = cpuCoreRemainCount;
	}

	public Double getRamRemainSize() {
		return this.ramRemainSize;
	}

	public void setRamRemainSize(Double ramRemainSize) {
		this.ramRemainSize = ramRemainSize;
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

	public String getVirtualFlag() {
		return this.virtualFlag;
	}

	public void setVirtualFlag(String virtualFlag) {
		this.virtualFlag = virtualFlag;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUsageFlag() {
		return this.usageFlag;
	}

	public void setUsageFlag(String usageFlag) {
		this.usageFlag = usageFlag;
	}

	public String getRecordNo() {
		return this.recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
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

	public Set<TccApplyedHostResource> getTccApplyedHostResources() {
		return this.tccApplyedHostResources;
	}

	public void setTccApplyedHostResources(
			Set<TccApplyedHostResource> tccApplyedHostResources) {
		this.tccApplyedHostResources = tccApplyedHostResources;
	}

	public TccIpConfigInfo getTccIpConfigInfo() {
		return this.tccIpConfigInfo;
	}

	public void setTccIpConfigInfo(TccIpConfigInfo tccIpConfigInfo) {
		this.tccIpConfigInfo = tccIpConfigInfo;
	}

	public String getPhysicsBelong() {
		return physicsBelong;
	}

	public void setPhysicsBelong(String physicsBelong) {
		this.physicsBelong = physicsBelong;
	}

	public String getUnitTypeNo() {
		return unitTypeNo;
	}

	public void setUnitTypeNo(String unitTypeNo) {
		this.unitTypeNo = unitTypeNo;
	}

	public String getEnclosureInfo() {
		return enclosureInfo;
	}

	public void setEnclosureInfo(String enclosureInfo) {
		this.enclosureInfo = enclosureInfo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Long getToStCpu() {
		return toStCpu;
	}

	public void setToStCpu(Long toStCpu) {
		this.toStCpu = toStCpu;
	}

	public Long getToStMem() {
		return toStMem;
	}

	public void setToStMem(Long toStMem) {
		this.toStMem = toStMem;
	}

	public String getHardwareTypeCdName() {
		return hardwareTypeCdName;
	}

	public void setHardwareTypeCdName(String hardwareTypeCdName) {
		this.hardwareTypeCdName = hardwareTypeCdName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Integer getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(Integer floorNum) {
		this.floorNum = floorNum;
	}

	public String getEnclosureU() {
		return enclosureU;
	}

	public void setEnclosureU(String enclosureU) {
		this.enclosureU = enclosureU;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public Double getStorage() {
		return storage;
	}

	public void setStorage(Double storage) {
		this.storage = storage;
	}

	public Long getCloudPlatformId() {
		return cloudPlatformId;
	}

	public void setCloudPlatformId(Long cloudPlatformId) {
		this.cloudPlatformId = cloudPlatformId;
	}

	public String getIsBareBetal() {
		return isBareBetal;
	}

	public void setIsBareBetal(String isBareBetal) {
		this.isBareBetal = isBareBetal;
	}

	public Date getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(Date maintenanceDate) throws ParseException {
		this.maintenanceDate = maintenanceDate;
	}

}
