package com.cmp.entity.tcc;

import java.util.Date;

/**
 * Description:openstack磁盘快照表 TccOpenStackVolumeSnapshot.java Create on
 */
public class TccOpenStackVolumeSnapshot implements java.io.Serializable {

	private static final long serialVersionUID = 300100L;

	private Long volumeSnapshotId;

	/** 磁盘快照uuid */

	private String volumeSnapshotUuid;

	/** 虚拟机uuid */

	private String serverUuid;

	/** 虚拟机快照uuid */

	private String serverSnapshotUuid;

	/** 磁盘uuid */

	private String volumeUuid;

	/** 创建人id */

	private Long crtUserId;

	/** 创建人名称 */

	private String crtUserName;

	/** 创建时间 */

	private Date crtDttm;

	/** 最后修改时间 */

	private Date lastuptDttm;

	/** 最后修改人id */

	private Long lastuptUserId;

	/** 最后修改人名称 */

	private String lastuptUserName;

	/** 是否可用 */

	private String enableFlg;

	public Long getVolumeSnapshotId() {
		return volumeSnapshotId;
	}

	public void setVolumeSnapshotId(Long volumeSnapshotId) {
		this.volumeSnapshotId = volumeSnapshotId;
	}

	public String getVolumeSnapshotUuid() {
		return volumeSnapshotUuid;
	}

	public void setVolumeSnapshotUuid(String volumeSnapshotUuid) {
		this.volumeSnapshotUuid = volumeSnapshotUuid;
	}

	public String getServerUuid() {
		return serverUuid;
	}

	public void setServerUuid(String serverUuid) {
		this.serverUuid = serverUuid;
	}

	public String getServerSnapshotUuid() {
		return serverSnapshotUuid;
	}

	public void setServerSnapshotUuid(String serverSnapshotUuid) {
		this.serverSnapshotUuid = serverSnapshotUuid;
	}

	public String getVolumeUuid() {
		return volumeUuid;
	}

	public void setVolumeUuid(String volumeUuid) {
		this.volumeUuid = volumeUuid;
	}

	public Long getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
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

	public Long getLastuptUserId() {
		return lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public String getLastuptUserName() {
		return lastuptUserName;
	}

	public void setLastuptUserName(String lastuptUserName) {
		this.lastuptUserName = lastuptUserName;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}
}
