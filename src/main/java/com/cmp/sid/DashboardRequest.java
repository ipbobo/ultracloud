package com.cmp.sid;

//仪表盘请求参数
public class DashboardRequest {
	private String cpuTimeType;//CPU时间类型
	private String memTimeType;//内存时间类型
	private String storeTimeType;//磁盘时间类型
	private String resType;//资源类型：cpu-CPU、mem-内存、store-磁盘
	private String chkFlag;//复选框是否选中：0-否；1-是
	private String hostIdStr;//主机ID列表

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

	public String getHostIdStr() {
		return hostIdStr;
	}

	public void setHostIdStr(String hostIdStr) {
		this.hostIdStr = hostIdStr;
	}
}