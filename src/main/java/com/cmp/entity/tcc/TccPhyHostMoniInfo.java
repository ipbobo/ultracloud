package com.cmp.entity.tcc;

import java.util.Date;

public class TccPhyHostMoniInfo implements java.io.Serializable {

	private static final long serialVersionUID = 3299921557828833348L;

	private Long id;

	private Long hostId;

	private String hostIp;

	private String hostName;

	private Date time;

	private Long clusterId;

	private Long datacenterId;

	private Long status;

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

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}

	public Long getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(Long datacenterId) {
		this.datacenterId = datacenterId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
