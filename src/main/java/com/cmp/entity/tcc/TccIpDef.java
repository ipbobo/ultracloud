package com.cmp.entity.tcc;

import java.util.Date;

public class TccIpDef {

	private Long	id;
	private String	pubUUid;
	private String	pubIp;
	private Integer	pubPort;
	private Long	hostId;
	private Long	networkId;
	private String	priIp;
	private Integer	priPort;
	private String	priUUid;
	private Integer	state;
	private Long	domainId;
	private Long	zoneId;
	private Long	crtUserId;
	private Date	crtDttm;
	private Integer	lastUptUserId;
	private Date	lastUptDttm;
	private String	enableFlg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPubUUid() {
		return pubUUid;
	}

	public void setPubUUid(String pubUUid) {
		this.pubUUid = pubUUid;
	}

	public String getPubIp() {
		return pubIp;
	}

	public void setPubIp(String pubIp) {
		this.pubIp = pubIp;
	}

	public Integer getPubPort() {
		return pubPort;
	}

	public void setPubPort(Integer pubPort) {
		this.pubPort = pubPort;
	}

	public Long getHostId() {
		return hostId;
	}

	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}

	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}

	public String getPriIp() {
		return priIp;
	}

	public void setPriIp(String priIp) {
		this.priIp = priIp;
	}

	public Integer getPriPort() {
		return priPort;
	}

	public void setPriPort(Integer priPort) {
		this.priPort = priPort;
	}

	public String getPriUUid() {
		return priUUid;
	}

	public void setPriUUid(String priUUid) {
		this.priUUid = priUUid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public Long getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Integer getLastUptUserId() {
		return lastUptUserId;
	}

	public void setLastUptUserId(Integer lastUptUserId) {
		this.lastUptUserId = lastUptUserId;
	}

	public Date getLastUptDttm() {
		return lastUptDttm;
	}

	public void setLastUptDttm(Date lastUptDttm) {
		this.lastUptDttm = lastUptDttm;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}
}
