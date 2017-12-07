package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 被HMC管理物理小机简易信息
 */
public class TccHmcManagedSystem implements Serializable {

	private static final long serialVersionUID = 3118992312002729421L;

	/**
	 * 主键ID
	 */

	private Long id;

	/**
	 * 服务器名称
	 */

	private String systemName;

	/**
	 * 小机IP地址
	 */

	private String ipAddress;

	/**
	 * 服务器序列号
	 */

	private String serialNum;

	/**
	 * 主机连接状态
	 */

	private String isConnected;

	/**
	 * 主机MAC地址
	 */

	private String macAddress;

	/**
	 * 数据来源类型
	 * 0表示同步；1表示手动新增
	 */

	private String sourceType;

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
	 * HMC硬件管理控制台
	 */

	private TccHmc hmc;

	/**
	 * 服务器各LED集合
	 */

	private List<TccHmcLed> hmcLeds;

	private Double cpuCoreTotalCount; // CPU总核数

	private Double ramTotalSize; // 内存总大小(单位为G)

	private Double cpuCoreRemainCount;// CPU剩余总核数

	private Long toStCpu; // 分配给压测池的CPU大小

	private Long toStMem; // 分配给压测池的MEM大小

	private Double ramRemainSize; // 内存剩余大小(单位为G)

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

	private String unitTypeNo; // 设备型号

	private String enclosureInfo; // 机柜信息

	private String serialNo; // 序列号

	private String contractNo; // 合同号

	private String	model;
	/**
	 * 品牌
	 */

	private String	brand;
	/**
	 * 楼层
	 */

	private String	floor;
	/**
	 * 楼层号
	 */

	private Integer	floorNum;
	/**
	 * 外壳
	 */

	private String	enclosureU;
	/**
	 * 房间号
	 */

	private Integer	roomNum;

	private Long clusterId;

	private Long zoneId;

	private String account;// 物理机的账号

	private String password; // 物理机的密码

	/**
	 * 集群
	 */
	private TccClusterConfig tccCluster;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(String isConnected) {
		this.isConnected = isConnected;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
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

	public TccHmc getHmc() {
		return hmc;
	}

	public void setHmc(TccHmc hmc) {
		this.hmc = hmc;
	}

	public List<TccHmcLed> getHmcLeds() {
		return hmcLeds;
	}

	public void setHmcLeds(List<TccHmcLed> hmcLeds) {
		this.hmcLeds = hmcLeds;
	}

	public Double getCpuCoreTotalCount() {
		return cpuCoreTotalCount;
	}

	public void setCpuCoreTotalCount(Double cpuCoreTotalCount) {
		this.cpuCoreTotalCount = cpuCoreTotalCount;
	}

	public Double getRamTotalSize() {
		return ramTotalSize;
	}

	public void setRamTotalSize(Double ramTotalSize) {
		this.ramTotalSize = ramTotalSize;
	}

	public Double getCpuCoreRemainCount() {
		return cpuCoreRemainCount;
	}

	public void setCpuCoreRemainCount(Double cpuCoreRemainCount) {
		this.cpuCoreRemainCount = cpuCoreRemainCount;
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

	public Double getRamRemainSize() {
		return ramRemainSize;
	}

	public void setRamRemainSize(Double ramRemainSize) {
		this.ramRemainSize = ramRemainSize;
	}

	public String getHardwareTypeCd() {
		return hardwareTypeCd;
	}

	public void setHardwareTypeCd(String hardwareTypeCd) {
		this.hardwareTypeCd = hardwareTypeCd;
	}

	public String getHardwareTypeCdName() {
		return hardwareTypeCdName;
	}

	public void setHardwareTypeCdName(String hardwareTypeCdName) {
		this.hardwareTypeCdName = hardwareTypeCdName;
	}

	public String getOsCd() {
		return osCd;
	}

	public void setOsCd(String osCd) {
		this.osCd = osCd;
	}

	public String getOsVersionCd() {
		return osVersionCd;
	}

	public void setOsVersionCd(String osVersionCd) {
		this.osVersionCd = osVersionCd;
	}

	public String getVirtualFlag() {
		return virtualFlag;
	}

	public void setVirtualFlag(String virtualFlag) {
		this.virtualFlag = virtualFlag;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUsageFlag() {
		return usageFlag;
	}

	public void setUsageFlag(String usageFlag) {
		this.usageFlag = usageFlag;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getPhysicsBelong() {
		return physicsBelong;
	}

	public void setPhysicsBelong(String physicsBelong) {
		this.physicsBelong = physicsBelong;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public TccClusterConfig getTccCluster() {
		return tccCluster;
	}

	public void setTccCluster(TccClusterConfig tccCluster) {
		this.tccCluster = tccCluster;
	}

}
