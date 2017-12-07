package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

public class TccBillDayDetail implements Serializable {

	private static final long serialVersionUID = 4360960556410678823L;

	private Long id;

	private Long resourceId;

	private String runCd;

	private Long prjId;

	private Long deptId;

	private Double amount;

	private Long empId;

	private Date crtDate;

	private String enableFlag;

	private String appType;

	private String appGrade;

	private String evnCode;

	private String osType;

	private Long cpuCount;

	private Long Storage;

	private Long ram;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getRunCd() {
		return runCd;
	}

	public void setRunCd(String runCd) {
		this.runCd = runCd;
	}

	public Long getPrjId() {
		return prjId;
	}

	public void setPrjId(Long prjId) {
		this.prjId = prjId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppGrade() {
		return appGrade;
	}

	public void setAppGrade(String appGrade) {
		this.appGrade = appGrade;
	}

	public String getEvnCode() {
		return evnCode;
	}

	public void setEvnCode(String evnCode) {
		this.evnCode = evnCode;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public Long getCpuCount() {
		return cpuCount;
	}

	public void setCpuCount(Long cpuCount) {
		this.cpuCount = cpuCount;
	}

	public Long getStorage() {
		return Storage;
	}

	public void setStorage(Long storage) {
		Storage = storage;
	}

	public Long getRam() {
		return ram;
	}

	public void setRam(Long ram) {
		this.ram = ram;
	}

}
