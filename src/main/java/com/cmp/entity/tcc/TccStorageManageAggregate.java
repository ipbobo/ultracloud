package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方虚拟化存储聚合
 */
public class TccStorageManageAggregate implements Serializable {

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
	 * 聚合名称
	 */

	private String aggregateName;

	/**
	 * 存储UUID
	 */

	private String aggregateUuid;

	/**
	 * 逻辑卷数量
	 */

	private Long volumeCount;

	/**
	 * 磁盘数量
	 */

	private Long diskCount;

	/**
	 * 可用空间
	 */

	private Double sizeAvailable;

	/**
	 * 总空间
	 */

	private Double sizeTotal;

	/**
	 * 已用空间
	 */

	private Double sizeUsed;

	/**
	 * 连接状态
	 */

	private String connectivityStatus;

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

	public String getAggregateName() {
		return aggregateName;
	}

	public void setAggregateName(String aggregateName) {
		this.aggregateName = aggregateName;
	}

	public String getAggregateUuid() {
		return aggregateUuid;
	}

	public void setAggregateUuid(String aggregateUuid) {
		this.aggregateUuid = aggregateUuid;
	}

	public Long getVolumeCount() {
		return volumeCount;
	}

	public void setVolumeCount(Long volumeCount) {
		this.volumeCount = volumeCount;
	}

	public Long getDiskCount() {
		return diskCount;
	}

	public void setDiskCount(Long diskCount) {
		this.diskCount = diskCount;
	}

	public Double getSizeAvailable() {
		return sizeAvailable;
	}

	public void setSizeAvailable(Double sizeAvailable) {
		this.sizeAvailable = sizeAvailable;
	}

	public Double getSizeTotal() {
		return sizeTotal;
	}

	public void setSizeTotal(Double sizeTotal) {
		this.sizeTotal = sizeTotal;
	}

	public Double getSizeUsed() {
		return sizeUsed;
	}

	public void setSizeUsed(Double sizeUsed) {
		this.sizeUsed = sizeUsed;
	}

	public String getConnectivityStatus() {
		return connectivityStatus;
	}

	public void setConnectivityStatus(String connectivityStatus) {
		this.connectivityStatus = connectivityStatus;
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
