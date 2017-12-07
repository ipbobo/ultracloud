package com.cmp.entity.tcc;

import java.util.Date;

public class TccStorageAddRecord implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = -8884122891857774106L;

	private Long recordId;

	private Double storeSize;

	private String	storeName;	// 存储名称
	/**
	 * 对于CloudStack：标识主存储本地OR共享类型 local本地存储/share共享存储
	 */

	private String	storeType;	// 存储介质类型(1:多功能存储;2:中端存储;3:低端存储)

	private String storeOwn; // 容量归属01:开发测试;02:压力测试

	private String recordNo; // 采购批次号

	private String serialNo; // 序列号

	private String contractNo; // 合同号

	private String enclosureInfo; // 机柜

	private String osVersionCd;

	private String memo;

	private Long crtUserId;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String enableFlg;

	private String belong;// 01张江、02田林

	private TccStorageDevide tccStorageDevide;

	private TccClusterConfig tccCluster;

	private String model;

	private String floor;

	private String floorNum;

	private String enclosureU;

	private String roomNum;

	private Long clusterId;

	private String storageType;

	private Long zoneId;

	private String ip;

	private String path;

	private String uuid;

	private Double used;

	private String hypervisor;

	private Double available;// 可用容量

	private String strategy;

	private String strategySwitch;

	public Double getAvailable() {
		return available;
	}

	public void setAvailable(Double available) {
		this.available = available;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getStrategySwitch() {
		return strategySwitch;
	}

	public void setStrategySwitch(String strategySwitch) {
		this.strategySwitch = strategySwitch;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Double getStoreSize() {
		return this.storeSize;
	}

	public void setStoreSize(Double storeSize) {
		this.storeSize = storeSize;
	}

	public String getStoreType() {
		return this.storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
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

	public String getStoreOwn() {
		return storeOwn;
	}

	public void setStoreOwn(String storeOwn) {
		this.storeOwn = storeOwn;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getEnclosureInfo() {
		return enclosureInfo;
	}

	public void setEnclosureInfo(String enclosureInfo) {
		this.enclosureInfo = enclosureInfo;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public TccStorageDevide getTccStorageDevide() {
		return tccStorageDevide;
	}

	public void setTccStorageDevide(TccStorageDevide tccStorageDevide) {
		this.tccStorageDevide = tccStorageDevide;
	}

	public TccClusterConfig getTccCluster() {
		return tccCluster;
	}

	public void setTccCluster(TccClusterConfig tccCluster) {
		this.tccCluster = tccCluster;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}

	public String getEnclosureU() {
		return enclosureU;
	}

	public void setEnclosureU(String enclosureU) {
		this.enclosureU = enclosureU;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}

	public String getOsVersionCd() {
		return osVersionCd;
	}

	public void setOsVersionCd(String osVersionCd) {
		this.osVersionCd = osVersionCd;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getHypervisor() {
		return hypervisor;
	}

	public void setHypervisor(String hypervisor) {
		this.hypervisor = hypervisor;
	}

	public Double getUsed() {
		return used;
	}

	public void setUsed(Double used) {
		this.used = used;
	}

	@Override
	public TccStorageAddRecord clone() throws CloneNotSupportedException {
		return (TccStorageAddRecord) super.clone();
	}

}
