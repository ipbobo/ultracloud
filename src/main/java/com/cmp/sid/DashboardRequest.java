package com.cmp.sid;

//仪表盘请求参数
public class DashboardRequest {
	private String cpuTimeType;//y轴1
	private String memTimeType;//y轴2
	private String storeTimeType;//y轴3
	private String resType;//y轴4
	private String chkFlag;//y轴5

	public String getCpuTimeType() {
		return cpuTimeType;
	}

	public void setCpuTimeType(String cpuTimeType) {
		this.cpuTimeType = cpuTimeType;
	}

	public String getMemTimeType() {
		return memTimeType;
	}

	public void setMemTimeType(String memTimeType) {
		this.memTimeType = memTimeType;
	}

	public String getStoreTimeType() {
		return storeTimeType;
	}

	public void setStoreTimeType(String storeTimeType) {
		this.storeTimeType = storeTimeType;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getChkFlag() {
		return chkFlag;
	}

	public void setChkFlag(String chkFlag) {
		this.chkFlag = chkFlag;
	}
}