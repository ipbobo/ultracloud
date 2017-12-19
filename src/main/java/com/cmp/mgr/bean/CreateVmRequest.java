package com.cmp.mgr.bean;

public class CreateVmRequest {

	private String dcName;

	private String vmName;

	private int cupCount;

	private long memSizeMB;

	private String guestOs;

	private long diskSizeKB;

	private String diskMode;

	private String dsName;

	private String netName;

	private String nicName;

	private String rpName;

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

	public int getCupCount() {
		return cupCount;
	}

	public void setCupCount(int cupCount) {
		this.cupCount = cupCount;
	}

	public long getMemSizeMB() {
		return memSizeMB;
	}

	public void setMemSizeMB(long memSizeMB) {
		this.memSizeMB = memSizeMB;
	}

	public String getGuestOs() {
		return guestOs;
	}

	public void setGuestOs(String guestOs) {
		this.guestOs = guestOs;
	}

	public long getDiskSizeKB() {
		return diskSizeKB;
	}

	public void setDiskSizeKB(long diskSizeKB) {
		this.diskSizeKB = diskSizeKB;
	}

	public String getDiskMode() {
		return diskMode;
	}

	public void setDiskMode(String diskMode) {
		this.diskMode = diskMode;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	public String getNicName() {
		return nicName;
	}

	public void setNicName(String nicName) {
		this.nicName = nicName;
	}

	public String getRpName() {
		return rpName;
	}

	public void setRpName(String rpName) {
		this.rpName = rpName;
	}

}
