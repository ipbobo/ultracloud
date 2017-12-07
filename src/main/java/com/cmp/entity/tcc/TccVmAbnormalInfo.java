package com.cmp.entity.tcc;

import java.util.Date;

public class TccVmAbnormalInfo implements java.io.Serializable {

	private static final long serialVersionUID = 3299921557828833348L;

	private Long id;

	private Long abnormalId;

	private Long hostId;

	private Long vmId;

	private Long lastuptUserId;

	private Date lastuptDttm;

	private Long businessStatus;

	private String dealDescription;

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

	public Long getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(Long businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getDealDescription() {
		return dealDescription;
	}

	public void setDealDescription(String dealDescription) {
		this.dealDescription = dealDescription;
	}

}
