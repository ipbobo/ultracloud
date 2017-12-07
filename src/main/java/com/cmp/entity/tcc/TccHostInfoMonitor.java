package com.cmp.entity.tcc;

import java.util.Date;

public class TccHostInfoMonitor {

	private Long infoMonitorId;
	// 非本地hostid

	private String oldHostId;
	// 非本地host名称

	private String oldHostName;
	// 本地host

	private String newHostId;
	// 本地host名称

	private String newHostName;
	// 是否为非本地比本地多出的host

	private String hostAddLate;
	// 是否为本地比远程多出的host

	private String hostMore;
	// 变更字段

	private String	changedField;
	/** 创建人id */

	private Long	crtUserId;
	/** 创建人名称 */

	private String	crtUserName;
	/** 创建时间 */

	private Date	crtDttm;
	/** 最后修改时间 */

	private Date	lastuptDttm;
	/** 最后修改人id */

	private Long	lastuptUserId;
	/** 最后修改人名称 */

	private String	lastuptUserName;

	public Long getInfoMonitorId() {
		return infoMonitorId;
	}

	public void setInfoMonitorId(Long infoMonitorId) {
		this.infoMonitorId = infoMonitorId;
	}

	public String getOldHostId() {
		return oldHostId;
	}

	public void setOldHostId(String oldHostId) {
		this.oldHostId = oldHostId;
	}

	public String getOldHostName() {
		return oldHostName;
	}

	public void setOldHostName(String oldHostName) {
		this.oldHostName = oldHostName;
	}

	public String getNewHostId() {
		return newHostId;
	}

	public void setNewHostId(String newHostId) {
		this.newHostId = newHostId;
	}

	public String getNewHostName() {
		return newHostName;
	}

	public void setNewHostName(String newHostName) {
		this.newHostName = newHostName;
	}

	public String getHostAddLate() {
		return hostAddLate;
	}

	public void setHostAddLate(String hostAddLate) {
		this.hostAddLate = hostAddLate;
	}

	public String getHostMore() {
		return hostMore;
	}

	public void setHostMore(String hostMore) {
		this.hostMore = hostMore;
	}

	public String getChangedField() {
		return changedField;
	}

	public void setChangedField(String changedField) {
		this.changedField = changedField;
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

}
