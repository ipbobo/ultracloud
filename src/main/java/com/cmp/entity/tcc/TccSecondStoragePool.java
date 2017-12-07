package com.cmp.entity.tcc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TCcSecondStoragePool 云二级存储数据库持久类
 */

public class TccSecondStoragePool implements java.io.Serializable {

	private static final long serialVersionUID = 7382077285730688260L;
	// Fields

	private Long		sstorageId;
	private String		name;
	private String		uuid;
	private String		zoneId;
	private BigDecimal	avaliableBytes;
	private BigDecimal	capacityBytes;
	private String		hostIpaddress;
	private String		path;
	private String		status;
	private String		storageDesc;
	private String		memo;
	private Long		crtUserId;
	private Date		crtDttm;
	private Date		lastuptDttm;
	private Long		lastuptUserId;
	private String		enableFlg;

	// Constructors

	// Property accessors

	public Long getSstorageId() {
		return this.sstorageId;
	}

	public void setSstorageId(Long sstorageId) {
		this.sstorageId = sstorageId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public BigDecimal getAvaliableBytes() {
		return this.avaliableBytes;
	}

	public void setAvaliableBytes(BigDecimal avaliableBytes) {
		this.avaliableBytes = avaliableBytes;
	}

	public BigDecimal getCapacityBytes() {
		return this.capacityBytes;
	}

	public void setCapacityBytes(BigDecimal capacityBytes) {
		this.capacityBytes = capacityBytes;
	}

	public String getHostIpaddress() {
		return this.hostIpaddress;
	}

	public void setHostIpaddress(String hostIpaddress) {
		this.hostIpaddress = hostIpaddress;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStorageDesc() {
		return storageDesc;
	}

	public void setStorageDesc(String storageDesc) {
		this.storageDesc = storageDesc;
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

}
