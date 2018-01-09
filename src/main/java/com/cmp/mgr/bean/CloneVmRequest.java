package com.cmp.mgr.bean;

public class CloneVmRequest {

	private String dcName;

	private String vmName;

	private String tplName;

	private String rpName;

	private int cpuSize;

	private long ramSize;

	public String getDcName() {
		return dcName;
	}

	public void setDcName(String dcName) {
		this.dcName = dcName;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getTplName() {
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getRpName() {
		return rpName;
	}

	public void setRpName(String rpName) {
		this.rpName = rpName;
	}

	public int getCpuSize() {
		return cpuSize;
	}

	public void setCpuSize(int cpuSize) {
		this.cpuSize = cpuSize;
	}

	public long getRamSize() {
		return ramSize;
	}

	public void setRamSize(long ramSize) {
		this.ramSize = ramSize;
	}

}
