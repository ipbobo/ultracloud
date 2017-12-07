package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方管理的存储磁盘
 */
public class TccStorageManageDisk implements Serializable {

	private static final long serialVersionUID = -6564562076577183453L;

	/**
	 * 主键Id
	 */
	private Long id;

	/**
	 * 存储服务Id
	 */

	private Long storageManageId;

	/**
	 * 磁盘名称
	 */

	private String diskName;

	/**
	 * 序列号
	 */

	private String serialNumber;

	/**
	 * UUID
	 */

	private String diskUuid;

	/**
	 * 磁盘类型
	 */

	private String diskType;

	/**
	 * 磁盘转速
	 */

	private Long rpm;

	/**
	 * 总存储
	 * GB
	 */

	private String totalCapacity;

	/**
	 * 空闲存储
	 * GB
	 */

	private String freeCapacity;

	/**
	 * 共享状态
	 */

	private String isShared;

	/**
	 * 健康状态
	 */

	private String healthState;

	/**
	 * 磁盘IO状态
	 */

	private String ioStatus;

	/**
	 * 存储厂商类型
	 */

	private String vendorType;

	/**
	 * 删除标识 0删除 1可用
	 */

	private String enableFlg;

	/**
	 * 创建时间
	 */

	private Date crtDttm;

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

	public Long getStorageManageId() {
		return storageManageId;
	}

	public void setStorageManageId(Long storageManageId) {
		this.storageManageId = storageManageId;
	}

	public String getDiskName() {
		return diskName;
	}

	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDiskUuid() {
		return diskUuid;
	}

	public void setDiskUuid(String diskUuid) {
		this.diskUuid = diskUuid;
	}

	public String getDiskType() {
		return diskType;
	}

	public void setDiskType(String diskType) {
		this.diskType = diskType;
	}

	public Long getRpm() {
		return rpm;
	}

	public void setRpm(Long integer) {
		this.rpm = integer;
	}

	public String getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(String totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public String getFreeCapacity() {
		return freeCapacity;
	}

	public void setFreeCapacity(String freeCapacity) {
		this.freeCapacity = freeCapacity;
	}

	public String getIsShared() {
		return isShared;
	}

	public void setIsShared(String isShared) {
		this.isShared = isShared;
	}

	public String getHealthState() {
		return healthState;
	}

	public void setHealthState(String healthState) {
		this.healthState = healthState;
	}

	public String getIoStatus() {
		return ioStatus;
	}

	public void setIoStatus(String ioStatus) {
		this.ioStatus = ioStatus;
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

	public Date getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Date getLastuptDttm() {
		return lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

}
