package com.cmp.entity.tcc;

import java.io.Serializable;

public class TccStoragePhyAss implements Serializable {

	private static final long serialVersionUID = -3551613250278351616L;

	private Long assId;

	private Long resourceId;

	private Long recordId;

	private Integer assType;// 0：VC；1：XEN

	private Integer zoneId;

	private Integer cpuExceedRate;// cpu超分率

	private Integer ramExceedRate;// ram超分率

	private Integer storageExceedRate;// 存储超分率

	private Integer enableFlg;

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
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

	public Long getAssId() {
		return assId;
	}

	public void setAssId(Long assId) {
		this.assId = assId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Integer getAssType() {
		return assType;
	}

	public void setAssType(Integer assType) {
		this.assType = assType;
	}

	public Integer getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(Integer enableFlg) {
		this.enableFlg = enableFlg;
	}

}
