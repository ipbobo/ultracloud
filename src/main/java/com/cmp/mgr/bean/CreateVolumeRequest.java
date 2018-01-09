package com.cmp.mgr.bean;

public class CreateVolumeRequest {

	private String vmUUID;

	private String vmName;

	private String size;

	public String getVmUUID() {
		return vmUUID;
	}

	public void setVmUUID(String vmUUID) {
		this.vmUUID = vmUUID;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
