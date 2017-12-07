package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方管理的存储磁盘IO
 */
public class TccStorageManageDiskIo implements Serializable {

	private static final long serialVersionUID = -6564562076577183453L;

	/**
	 * 主键Id
	 */

	private Long id;

	/**
	 * 磁盘Id
	 */

	private Long diskId;

	/**
	 * 千字节磁盘读写IO disk-io-kbps
	 */

	private Long diskIoKbps;

	/**
	 * 磁盘读写IO？ disk-iops
	 */

	private Long diskIops;

	/**
	 * 创建时间
	 */

	private Date crtDttm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiskId() {
		return diskId;
	}

	public void setDiskId(Long diskId) {
		this.diskId = diskId;
	}

	public Long getDiskIoKbps() {
		return diskIoKbps;
	}

	public void setDiskIoKbps(Long diskIoKbps) {
		this.diskIoKbps = diskIoKbps;
	}

	public Long getDiskIops() {
		return diskIops;
	}

	public void setDiskIops(Long diskIops) {
		this.diskIops = diskIops;
	}

	public Date getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

}
