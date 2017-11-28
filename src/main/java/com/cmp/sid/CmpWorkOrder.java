package com.cmp.sid;

public class CmpWorkOrder {
	private String appNo;//申请编号
	private String createTime;//创建时间
	private String lastUpdateTime;//最后修改时间
	private String orderNo;//清单编号
	private String appType;//申请类型：1-资源申请；2-运维服务申请
	private String status;//状态：0-待提交；1-审批中；2-审批通过；3-审批不通过
	private String procInstId;//流程实例ID
	private String applyUserId;//申请者
	private String areaCode;//地域代码
	private String platType;//平台类型
	private String deployType;//部署类型
	private String envCode;//环境代码
	private String resType;//资源类型
	private String virName;//虚拟机名称
	private String virIp;//虚拟机IP
	private String cpu;//CPU
	private String memory;//内存
	private String diskType;//磁盘类型，多个用英文逗号分隔
	private String diskSize;//磁盘大小，多个用英文逗号分隔
	private String diskEncrypt;//磁盘加密，多个用英文逗号分隔
	private String softName;//软件名称，多个用英文逗号分隔
	private String softVer;//软件版本，多个用英文逗号分隔
	private String softParam;//软件参数，多个用英文逗号分隔
	private String projectCode;//项目代码
	private String osType;//操作系统类型
	private String osBitNum;//操作系统位数
	private String imgCode;//镜像代码
	private String imgUserName;//镜像用户名
	private String imgUserPass;//镜像用户密码
	private String imgPath;//镜像路径
	private String imgExpireDate;//镜像到期时间
	private String expireDate;//到期时间
	private String virNum;//虚拟机数量

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPlatType() {
		return platType;
	}

	public void setPlatType(String platType) {
		this.platType = platType;
	}

	public String getDeployType() {
		return deployType;
	}

	public void setDeployType(String deployType) {
		this.deployType = deployType;
	}

	public String getEnvCode() {
		return envCode;
	}

	public void setEnvCode(String envCode) {
		this.envCode = envCode;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getVirName() {
		return virName;
	}

	public void setVirName(String virName) {
		this.virName = virName;
	}

	public String getVirIp() {
		return virIp;
	}

	public void setVirIp(String virIp) {
		this.virIp = virIp;
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

	public String getDiskType() {
		return diskType;
	}

	public void setDiskType(String diskType) {
		this.diskType = diskType;
	}

	public String getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}

	public String getDiskEncrypt() {
		return diskEncrypt;
	}

	public void setDiskEncrypt(String diskEncrypt) {
		this.diskEncrypt = diskEncrypt;
	}

	public String getSoftName() {
		return softName;
	}

	public void setSoftName(String softName) {
		this.softName = softName;
	}

	public String getSoftVer() {
		return softVer;
	}

	public void setSoftVer(String softVer) {
		this.softVer = softVer;
	}

	public String getSoftParam() {
		return softParam;
	}

	public void setSoftParam(String softParam) {
		this.softParam = softParam;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getOsBitNum() {
		return osBitNum;
	}

	public void setOsBitNum(String osBitNum) {
		this.osBitNum = osBitNum;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getImgUserName() {
		return imgUserName;
	}

	public void setImgUserName(String imgUserName) {
		this.imgUserName = imgUserName;
	}

	public String getImgUserPass() {
		return imgUserPass;
	}

	public void setImgUserPass(String imgUserPass) {
		this.imgUserPass = imgUserPass;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getImgExpireDate() {
		return imgExpireDate;
	}

	public void setImgExpireDate(String imgExpireDate) {
		this.imgExpireDate = imgExpireDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getVirNum() {
		return virNum;
	}

	public void setVirNum(String virNum) {
		this.virNum = virNum;
	}
}