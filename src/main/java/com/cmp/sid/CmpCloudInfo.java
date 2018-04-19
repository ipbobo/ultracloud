package com.cmp.sid;

//私有云详情信息
public class CmpCloudInfo {
	
	
	public static final String SYS_DISK_SIZE = "300GB";
	private String osTypeName;	//系统名称
	private String sysDiskSize; //系统盘大小
	private String dataDiskSize; //数据盘大小
	private String dataDiskInfo; //数据盘信息
	private String cpu;			//cpu
	private String memory;      //内存
	private String osStatus; //系统安装状态
	private String soft; 		//软件安装
	private String softStatus;	//软件安装状态
	private String expireDate;  //使用期限
	private String appNo;		//申请编号
	private String environment;  //环境
	private String imgCodeName;  //安装镜像名称
	private String resTypeName;  //类型名称 
	private String vmNum;	//数量
	private String orderNo;  //订单编号
	private String areaName;  //区域
	private String projectName; //项目
	private String applicant;	//申请人
	private String executeStatus; //订单执行状态
	
	
	public String getExecuteStatus() {
		return executeStatus;
	}
	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}
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
	public String getDataDiskInfo() {
		return dataDiskInfo;
	}
	public void setDataDiskInfo(String dataDiskInfo) {
		this.dataDiskInfo = dataDiskInfo;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getImgCodeName() {
		return imgCodeName;
	}
	public void setImgCodeName(String imgCodeName) {
		this.imgCodeName = imgCodeName;
	}
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	public String getVmNum() {
		return vmNum;
	}
	public void setVmNum(String vmNum) {
		this.vmNum = vmNum;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	
}
