package com.cmp.sid;

//私有云详情信息
public class CmpCloudInfo {
	
	
	public static final String SYS_DISK_SIZE = "300GB";
	private String osTypeName;	//系统名称
	private String sysDiskSize; //系统盘大小
	private String dataDiskSize; //数据盘大小
	private String cpu;			//cpu
	private String memory;      //内存
	private String osStatus; //系统安装状态
	private String soft; 		//软件安装
	private String softStatus;	//软件安装状态
	
	public String getOsTypeName() {
		return osTypeName;
	}
	public void setOsTypeName(String osTypeName) {
		this.osTypeName = osTypeName;
	}
	public String getSysDiskSize() {
		return sysDiskSize;
	}
	public void setSysDiskSize(String sysDiskSize) {
		this.sysDiskSize = sysDiskSize;
	}
	public String getDataDiskSize() {
		return dataDiskSize;
	}
	public void setDataDiskSize(String dataDiskSize) {
		this.dataDiskSize = dataDiskSize;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getOsStatus() {
		return osStatus;
	}
	public void setOsStatus(String osStatus) {
		this.osStatus = osStatus;
	}
	public String getSoft() {
		return soft;
	}
	public void setSoft(String soft) {
		this.soft = soft;
	}
	public String getSoftStatus() {
		return softStatus;
	}
	public void setSoftStatus(String softStatus) {
		this.softStatus = softStatus;
	}
	
	
}
