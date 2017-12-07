package com.cmp.entity.tcc;

import java.util.Date;

public class TccSnapshotHistory implements java.io.Serializable {

	private static final long serialVersionUID = -7381609104779226980L;

	// 快照表的Id
	private Long snapshotId;
	// 快照的名字

	private String snapshotName;
	// 快照的uuid

	private String snapshotuuid;
	// 创建快照的实践

	private Date snapshotTime;
	// 虚拟机的id;

	private Long vmId;
	// 快照状态

	private int status;
	// 快照的创建人

	private Long createUserId;
	// 创建时间

	private Date createTime;
	// 最后修改人

	private Integer lastModifyUserId;
	// 最后更新时间

	private Date lastUpdateTime;
	// 是否可用("1"表示有效,"0"表示无效)

	private String enableFlg;
	// 快照说明

	private String description;
	// 快照类型 是否有内存快照(1表示内存内存快照 0表示非内存快照)

	private Integer snapshotType;

	// 根据做快照的方式，添加做快照的方式(0表示初始化快照，1代表手动快照，2代表自动快照)

	private Integer snapshot_mode;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSnapshotType() {
		return snapshotType;
	}

	public void setSnapshotType(Integer snapshotType) {
		this.snapshotType = snapshotType;
	}

	public String getSnapshotName() {
		return snapshotName;
	}

	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}

	public Date getSnapshotTime() {
		return snapshotTime;
	}

	public void setSnapshotTime(Date snapshotTime) {
		this.snapshotTime = snapshotTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getLastModifyUserId() {
		return lastModifyUserId;
	}

	public void setLastModifyUserId(Integer lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getSnapshotuuid() {
		return snapshotuuid;
	}

	public void setSnapshotuuid(String snapshotuuid) {
		this.snapshotuuid = snapshotuuid;
	}

	public Long getVmId() {
		return vmId;
	}

	public void setVmId(Long vmId) {
		this.vmId = vmId;
	}

	public Long getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(Long snapshotId) {
		this.snapshotId = snapshotId;
	}

	/**
	 * @return the snapshot_mode
	 */
	public Integer getSnapshot_mode() {
		return snapshot_mode;
	}

	/**
	 * @param snapshot_mode the snapshot_mode to set
	 */
	public void setSnapshot_mode(Integer snapshot_mode) {
		this.snapshot_mode = snapshot_mode;
	}

}
