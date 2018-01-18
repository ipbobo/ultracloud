package com.cmp.sid;

public class VirtualMachine {
	private String id;		//ID
	private String projectId; //项目ID
	private String name;    //虚拟机名称
	private String user;	//虚拟机用户
	private String ip;		//ip	
	private String cpu;		//CPU
	private String memory;  //内存
	private String datadisk; //系统盘大小
	private String status;	//虚拟机状态
	private String mountDiskSize; //挂载磁盘
	private String mountDiskType; //挂载磁盘类型
	private long hostmachineId; //宿主机ID
	private String platform;  //平台
	private String os;		//操作系统
	private String duedate;	//到期时间
	private String username; //用户名
	private String password; //密码
	private String gmt_create; //创建时间
	private String gmt_modified; //修改时间
	private String osStatus;   //操作系统安装状态
	private String soft;	  //软件
	private String softStatus;	//软件安装状态
	private String appNo;	//工单号
	private String type;    //类型
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public String getDatadisk() {
		return datadisk;
	}
	public void setDatadisk(String datadisk) {
		this.datadisk = datadisk;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getHostmachineId() {
		return hostmachineId;
	}
	public void setHostmachineId(long hostmachineId) {
		this.hostmachineId = hostmachineId;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}
	public String getGmt_modified() {
		return gmt_modified;
	}
	public void setGmt_modified(String gmt_modified) {
		this.gmt_modified = gmt_modified;
	}
	public String getOsStatus() {
		if (osStatus == null) {
			return "未安装";
		}
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
		if (softStatus == null) {
			return "未安装";
		}
		return softStatus;
	}
	public void setSoftStatus(String softStatus) {
		this.softStatus = softStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMountDiskSize() {
		return mountDiskSize;
	}
	public void setMountDiskSize(String mountDiskSize) {
		this.mountDiskSize = mountDiskSize;
	}
	public String getMountDiskType() {
		return mountDiskType;
	}
	public void setMountDiskType(String mountDiskType) {
		this.mountDiskType = mountDiskType;
	}
	
}