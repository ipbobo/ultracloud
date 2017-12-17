package com.cmp.entity;

public class DeployedSoft {
	private String id;
	private String virtualmachineId;
	private String virtualmachineName;
	private String softName;
	private String softParams;
	private String softVersion;
	private String status;
	private String remark;
	
	
	public String getSoftVersion() {
		return softVersion;
	}
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVirtualmachineId() {
		return virtualmachineId;
	}
	public void setVirtualmachineId(String virtualmachineId) {
		this.virtualmachineId = virtualmachineId;
	}
	public String getVirtualmachineName() {
		return virtualmachineName;
	}
	public void setVirtualmachineName(String virtualmachineName) {
		this.virtualmachineName = virtualmachineName;
	}
	public String getSoftName() {
		return softName;
	}
	public void setSoftName(String softName) {
		this.softName = softName;
	}
	public String getSoftParams() {
		return softParams;
	}
	public void setSoftParams(String softParams) {
		this.softParams = softParams;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
