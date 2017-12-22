package com.cmp.entity.tcc;

import java.io.Serializable;

public class TccCluster implements Serializable {

	private static final long serialVersionUID = 6365135792006240699L;

	private Long id;

	private String uuid;

	private String clusterName;

	private String vcenter;

	private String oldFlag;

	private String vtype;// 1 is KVM, VMware,4

	private String vcip;

	private String vcaccount;

	private String vcpassword;

	private String dcname;

	private String zoneId;

	private Integer cpuExceedRate;// cpu超分率

	private Integer ramExceedRate;

	private Integer storageExceedRate;

	private Long resourceGradeId;

	private TccDatacenter tccCloudDatacenter;

	public TccDatacenter getTccCloudDatacenter() {
		return tccCloudDatacenter;
	}

	public void setTccCloudDatacenter(TccDatacenter tccCloudDatacenter) {
		this.tccCloudDatacenter = tccCloudDatacenter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getVcenter() {
		return vcenter;
	}

	public void setVcenter(String vcenter) {
		this.vcenter = vcenter;
	}

	public String getOldFlag() {
		return oldFlag;
	}

	public void setOldFlag(String oldFlag) {
		this.oldFlag = oldFlag;
	}

	public String getVtype() {
		return vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public String getVcip() {
		return vcip;
	}

	public void setVcip(String vcip) {
		this.vcip = vcip;
	}

	public String getVcaccount() {
		return vcaccount;
	}

	public void setVcaccount(String vcaccount) {
		this.vcaccount = vcaccount;
	}

	public String getVcpassword() {
		return vcpassword;
	}

	public void setVcpassword(String vcpassword) {
		this.vcpassword = vcpassword;
	}

	public String getDcname() {
		return dcname;
	}

	public void setDcname(String dcname) {
		this.dcname = dcname;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public Integer getCpuExceedRate() {
		return cpuExceedRate;
	}

	public void setCpuExceedRate(Integer cpuExceedRate) {
		this.cpuExceedRate = cpuExceedRate;
	}

	public Integer getRamExceedRate() {
		return ramExceedRate;
	}

	public void setRamExceedRate(Integer ramExceedRate) {
		this.ramExceedRate = ramExceedRate;
	}

	public Integer getStorageExceedRate() {
		return storageExceedRate;
	}

	public void setStorageExceedRate(Integer storageExceedRate) {
		this.storageExceedRate = storageExceedRate;
	}

	public Long getResourceGradeId() {
		return resourceGradeId;
	}

	public void setResourceGradeId(Long resourceGradeId) {
		this.resourceGradeId = resourceGradeId;
	}

}
