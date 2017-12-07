package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方管理的逻辑卷
 */

public class TccStorageManageVolume implements Serializable {

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
	 * 逻辑卷名称
	 */

	private String volumeName;

	/**
	 * UUID
	 */

	private String volumeUuid;

	/**
	 * 
	 */

	private String style;

	/**
	 * 逻辑卷类型
	 */

	private String type;

	/**
	 * 逻辑卷创建时间
	 */

	private Date creationTime;

	/**
	 * 逻辑卷描述
	 */

	private String comment;

	/**
	 * 物理使用
	 */

	private Double physicalUsed;

	/**
	 * 物理使用百分比
	 */

	private Long physicalUsedPercent;

	/**
	 * 物理空闲
	 */

	private Double physicalFree;

	/**
	 * 物理总量
	 */

	private Double physicalTotal;

	/**
	 * Filesystem size (in bytes) of the volume. This is the total usable size of the volume, not including WAFL
	 * reserve.
	 */

	private Double size;

	/**
	 * The size (in bytes) that is still available in the volume.
	 */

	private Double sizeAvailable;

	/**
	 * Total free space (in bytes) available in the volume and the snapshot reserve. If this value is 0, a new snapshot
	 * cannot be created.
	 */

	private Double sizeAvailableForSnapshots;

	/**
	 * Total usable size (in bytes) of the volume, not including WAFL reserve or volume snapshot reserve.
	 */

	private Double sizeTotal;

	/**
	 * The size (in bytes) that is used in the volume.
	 */

	private Double sizeUsed;

	/**
	 * The size (in bytes) that is used by snapshots in the volume.
	 */

	private Double sizeUsedBySnapshots;

	/**
	 * The size (in bytes) in the volume that has been set aside as reserve for snapshot usage.
	 */

	private Double snapshotReserveSize;

	/**
	 * 健康状态
	 */

	private String healthState;

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

	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	public String getVolumeUuid() {
		return volumeUuid;
	}

	public void setVolumeUuid(String volumeUuid) {
		this.volumeUuid = volumeUuid;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Double getPhysicalUsed() {
		return physicalUsed;
	}

	public void setPhysicalUsed(Double physicalUsed) {
		this.physicalUsed = physicalUsed;
	}

	public Long getPhysicalUsedPercent() {
		return physicalUsedPercent;
	}

	public void setPhysicalUsedPercent(Long physicalUsedPercent) {
		this.physicalUsedPercent = physicalUsedPercent;
	}

	public Double getPhysicalFree() {
		return physicalFree;
	}

	public void setPhysicalFree(Double physicalFree) {
		this.physicalFree = physicalFree;
	}

	public Double getPhysicalTotal() {
		return physicalTotal;
	}

	public void setPhysicalTotal(Double physicalTotal) {
		this.physicalTotal = physicalTotal;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public Double getSizeAvailable() {
		return sizeAvailable;
	}

	public void setSizeAvailable(Double sizeAvailable) {
		this.sizeAvailable = sizeAvailable;
	}

	public Double getSizeAvailableForSnapshots() {
		return sizeAvailableForSnapshots;
	}

	public void setSizeAvailableForSnapshots(Double sizeAvailableForSnapshots) {
		this.sizeAvailableForSnapshots = sizeAvailableForSnapshots;
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

	public Double getSizeUsedBySnapshots() {
		return sizeUsedBySnapshots;
	}

	public void setSizeUsedBySnapshots(Double sizeUsedBySnapshots) {
		this.sizeUsedBySnapshots = sizeUsedBySnapshots;
	}

	public Double getSnapshotReserveSize() {
		return snapshotReserveSize;
	}

	public void setSnapshotReserveSize(Double snapshotReserveSize) {
		this.snapshotReserveSize = snapshotReserveSize;
	}

	public String getHealthState() {
		return healthState;
	}

	public void setHealthState(String healthState) {
		this.healthState = healthState;
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
