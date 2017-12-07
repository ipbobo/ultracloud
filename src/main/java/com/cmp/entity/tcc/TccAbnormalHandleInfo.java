package com.cmp.entity.tcc;

import java.util.Date;

public class TccAbnormalHandleInfo implements java.io.Serializable {

	private static final long serialVersionUID = 3299921557828833348L;

	private Long id;

	private Long abnormalId;

	private Long hostId;

	private Long vmId;

	private Long handleResult;

	private Long crtUserId;

	private String memo;

	private String crtUserName;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String lastuptUserName;

	private String enableFlg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHostId() {
		return hostId;
	}

	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}

	public Long getAbnormalId() {
		return abnormalId;
	}

	public void setAbnormalId(Long abnormalId) {
		this.abnormalId = abnormalId;
	}

	public Long getVmId() {
		return vmId;
	}

	public void setVmId(Long vmId) {
		this.vmId = vmId;
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

	public Long getHandleResult() {
		return handleResult;
	}

	public void setHandleResult(Long handleResult) {
		this.handleResult = handleResult;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
