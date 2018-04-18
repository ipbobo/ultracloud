package com.cmp.sid;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.annotation.Transient;

public class CmpOrder {
	private String orderNo;//清单编号
	private String appNo;//工单编号
	private String createTime;//创建时间
	private String lastUpdateTime;//最后修改时间
	private String status;//状态：0-待提交；1-已提交；T-套餐
	private String applyUserId;//申请者
	private String deptId;//部门ID
	private String areaCode;//地域代码
	private String areaCodeName;//地域代码名称
	private String platType;//平台类型
	private String platTypeName;//平台类型名称
	private String deployType;//部署类型
	private String deployTypeName;//部署类型名称
	private String envCode;//环境代码
	private String envCodeName;//环境代码名称
	private String resType;//资源类型
	private String resTypeName;//资源类型名称
	private String virName;//虚拟机名称
	private String virIp;//虚拟机IP
	private String cpu;//CPU
	private String memory;//内存
	private String diskType;//磁盘类型，多个用英文逗号分隔
	private String diskTypeName;//磁盘类型名称，多个用英文逗号分隔
	private String diskSize;//磁盘大小，多个用英文逗号分隔
	private String diskEncrypt;//磁盘加密，多个用英文逗号分隔
	private String softCode;//软件代码，多个用英文逗号分隔
	private String softName;//软件名称，多个用英文逗号分隔
	private String projectCode;//项目代码
	private String projectCodeName;//项目代码名称
	private String osType;//操作系统类型
	private String osTypeName;//操作系统类型名称
	private String osBitNum;//操作系统位数
	private String osBitNumName;//操作系统位数名称
	private String imgCode;//镜像代码
	private String imgCodeName;//镜像代码名称
	private String imgUserName;//镜像用户名
	private String imgUserPass;//镜像用户密码
	private String imgPath;//镜像路径
	private String expireDate;//到期时间
	private String virNum;//虚拟机数量
	private String totalAmt;//总价格
	private String fileName;//文件名称
	private String pckgName;//套餐名称
	private String executeStatus; //账单部署状态 
	
	@Transient
	private String softParam;//软件参数，多个用英文逗号分隔
	@Transient
	private String newOrderNo;//新清单编号
	@Transient
	private Map<String, String> diskTypeMap=new LinkedHashMap<String, String>();//套餐名称

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaCodeName() {
		return areaCodeName;
	}

	public void setAreaCodeName(String areaCodeName) {
		this.areaCodeName = areaCodeName;
	}

	public String getPlatType() {
		return platType;
	}

	public void setPlatType(String platType) {
		this.platType = platType;
	}

	public String getPlatTypeName() {
		return platTypeName;
	}

	public void setPlatTypeName(String platTypeName) {
		this.platTypeName = platTypeName;
	}

	public String getDeployType() {
		return deployType;
	}

	public void setDeployType(String deployType) {
		this.deployType = deployType;
	}

	public String getDeployTypeName() {
		return deployTypeName;
	}

	public void setDeployTypeName(String deployTypeName) {
		this.deployTypeName = deployTypeName;
	}

	public String getEnvCode() {
		return envCode;
	}

	public void setEnvCode(String envCode) {
		this.envCode = envCode;
	}

	public String getEnvCodeName() {
		return envCodeName;
	}

	public void setEnvCodeName(String envCodeName) {
		this.envCodeName = envCodeName;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getResTypeName() {
		return resTypeName;
	}

	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
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

	public String getDiskTypeName() {
		return diskTypeName;
	}

	public void setDiskTypeName(String diskTypeName) {
		this.diskTypeName = diskTypeName;
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

	public String getSoftCode() {
		return softCode;
	}

	public void setSoftCode(String softCode) {
		this.softCode = softCode;
	}

	public String getSoftName() {
		return softName;
	}

	public void setSoftName(String softName) {
		this.softName = softName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectCodeName() {
		return projectCodeName;
	}

	public void setProjectCodeName(String projectCodeName) {
		this.projectCodeName = projectCodeName;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getOsTypeName() {
		return osTypeName;
	}

	public void setOsTypeName(String osTypeName) {
		this.osTypeName = osTypeName;
	}

	public String getOsBitNum() {
		return osBitNum;
	}

	public void setOsBitNum(String osBitNum) {
		this.osBitNum = osBitNum;
	}

	public String getOsBitNumName() {
		return osBitNumName;
	}

	public void setOsBitNumName(String osBitNumName) {
		this.osBitNumName = osBitNumName;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getImgCodeName() {
		return imgCodeName;
	}

	public void setImgCodeName(String imgCodeName) {
		this.imgCodeName = imgCodeName;
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

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getPckgName() {
		return pckgName;
	}

	public void setPckgName(String pckgName) {
		this.pckgName = pckgName;
	}
	
	public String getSoftParam() {
		return softParam;
	}

	public void setSoftParam(String softParam) {
		this.softParam = softParam;
	}
	
	public String getNewOrderNo() {
		return newOrderNo;
	}

	public void setNewOrderNo(String newOrderNo) {
		this.newOrderNo = newOrderNo;
	}
	
	public void setDiskTypeMap(String key, String value) {
		this.diskTypeMap.put(key, value);
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}
	
	
	
	
}