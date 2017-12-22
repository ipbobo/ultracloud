package com.cmp.entity.tcc;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TccCloudPod implements java.io.Serializable {

	private static final long serialVersionUID = 873454073342100041L;

	private Long podId; // sjcloud平台云基础架构提供点主键

	private String podName; // 提供点名称

	private String uuid; // cloudstack为提供点生成的uuid

	private String podDesc; // 提供点描述字段

	private String zoneId; // 提供点的区域id

	private String status; // 提供点的状态，该状态与cloudstack平台状态一致

	private String gateway; // 提供点网关

	private String netmask; // 提供点掩码

	private String startIp; // 提供点开始ip

	private String endIp; // 提供点结束ip

	private String memo; // 备注字段

	private Long crtUserId; // 创建人的id

	private Date crtDttm; // 创建时间

	private Date lastuptDttm; // 最后修改时间

	private Long lastuptUserId; // 最后修改人

	private String enableFlg; // 逻辑删除标识位

	private Set<TccCluster> tccClusterConfig = new HashSet<TccCluster>(
			0);

	private TccDatacenter tccCloudDatacenter;

	public Long getPodId() {
		return this.podId;
	}

	public void setPodId(Long podId) {
		this.podId = podId;
	}

	public String getPodName() {
		return this.podName;
	}

	public void setPodName(String podName) {
		this.podName = podName;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPodDesc() {
		return this.podDesc;
	}

	public void setPodDesc(String podDesc) {
		this.podDesc = podDesc;
	}

	public String getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getCrtUserId() {
		return this.crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtDttm() {
		return this.crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Date getLastuptDttm() {
		return this.lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public Long getLastuptUserId() {
		return this.lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public String getEnableFlg() {
		return this.enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public String getGateway() {
		return this.gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getNetmask() {
		return this.netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public String getStartIp() {
		return this.startIp;
	}

	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}

	public String getEndIp() {
		return this.endIp;
	}

	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	public Set<TccCluster> getTccClusterConfig() {
		return tccClusterConfig;
	}

	public void setTccClusterConfig(Set<TccCluster> tccClusterConfig) {
		this.tccClusterConfig = tccClusterConfig;
	}

	public TccDatacenter getTccCloudDatacenter() {
		return tccCloudDatacenter;
	}

	public void setTccCloudDatacenter(TccDatacenter tccCloudDatacenter) {
		this.tccCloudDatacenter = tccCloudDatacenter;
	}

}
