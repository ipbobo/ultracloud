package com.cmp.entity.tcc;

import java.util.Date;

public class TccApplyedHostInfoAutodistributionHistory implements java.io.Serializable {

	private static final long serialVersionUID = -5284435658912488276L;

	private long id; // 主键

	private long vmId; // 虚拟机编号

	private double cpuCoreBefore; // 扩容前cpu大小

	private double cpuCoreAfter; // 扩容后cpu大小

	private double ramSizeBefore; // 扩容前ram大小

	private double ramSizeAfter; // 扩容后ram大小

	private double storageSizeBefore; // 扩容前存储大小

	private double storageSizeAfter; // 扩容后存储大小

	private long evnId; // 环境编号

	private String evnName; // 环境名称

	private long projectId; // 项目编号

	private String projectName; // 项目名称

	private Date crtDttm; // 记录创建时间

	private String ipAddress;

	private String vmName;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVmId() {
		return vmId;
	}

	public void setVmId(long vmId) {
		this.vmId = vmId;
	}

	public double getCpuCoreBefore() {
		return cpuCoreBefore;
	}

	public void setCpuCoreBefore(double cpuCoreBefore) {
		this.cpuCoreBefore = cpuCoreBefore;
	}

	public double getCpuCoreAfter() {
		return cpuCoreAfter;
	}

	public void setCpuCoreAfter(double cpuCoreAfter) {
		this.cpuCoreAfter = cpuCoreAfter;
	}

	public double getRamSizeBefore() {
		return ramSizeBefore;
	}

	public void setRamSizeBefore(double ramSizeBefore) {
		this.ramSizeBefore = ramSizeBefore / 1024;
	}

	public double getRamSizeAfter() {
		return ramSizeAfter;
	}

	public void setRamSizeAfter(double ramSizeAfter) {
		this.ramSizeAfter = ramSizeAfter / 1024;
	}

	public double getStorageSizeBefore() {
		return storageSizeBefore;
	}

	public void setStorageSizeBefore(double storageSizeBefore) {
		this.storageSizeBefore = storageSizeBefore;
	}

	public double getStorageSizeAfter() {
		return storageSizeAfter;
	}

	public void setStorageSizeAfter(double storageSizeAfter) {
		this.storageSizeAfter = storageSizeAfter;
	}

	public long getEvnId() {
		return evnId;
	}

	public void setEvnId(long evnId) {
		this.evnId = evnId;
	}

	public String getEvnName() {
		return evnName;
	}

	public void setEvnName(String evnName) {
		this.evnName = evnName;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

}
