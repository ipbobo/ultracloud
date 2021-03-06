package com.cmp.sid;

/**
 * 运维服务实体类
 *
 */
public class CmpOpServe {
	private String id;
	private String serviceType;
	private String operType;
	private String vm;
	private String vmMsg;
	private String middleware;
	private String middlewareMsg;
	private String appmsg;//申请说明
	private String createTime;//创建时间
	private String status;//流程状态  0:启动   1:审核完成  2:工单完成
	private String deploySoftId; //软件部署ID
	private String serviceTypeName;
	private String operTypeName;
	private String operName; //操作
	private String workflow; //工作流
	private String breakdownTime;//故障时间
	private String breakdownInfo;//故障信息，描述
	private String exceptSolveTime;//期望解决时间
	private String exceptResult; //期望结果
	private String breakdownLevel;//故障级别
	private String partitionInfo; //分区信息
	private String directory; //挂载磁盘指定目录
	private String expTime;	//期限
	private String vipNum; //VIP数量
	private String breakdownLevelName; //故障级别名称
	private String expansionType; //扩展类型
	private String expansionSize; //扩展大小
	private String remark1;	//业务备用字段1
	private String remark2; //业务备用字段2
	
	
	public static final String OP_MIDDLEWATE_RESTART = "middleware_restart";
	public static final String OP_SOFTWARE_INSTALL = "software_install";
	public static final String OP_FAULT_HANDLE = "fault_handle";
	public static final String OP_SYSTEM_PARTITION = "system_partition";
	public static final String OP_CREATE_SYSTEMFILE = "create_systemfile";
	public static final String OP_MOUNT_DISK = "mount_disk";
	public static final String OP_ROOT_APPLY = "root_apply";
	public static final String OP_VIP_ADD = "vip_add";
	public static final String OP_EXPANSION = "sys_expansion";
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getVm() {
		return vm;
	}
	public void setVm(String vm) {
		this.vm = vm;
	}
	public String getVmMsg() {
		return vmMsg;
	}
	public void setVmMsg(String vmMsg) {
		this.vmMsg = vmMsg;
	}
	public String getMiddleware() {
		return middleware;
	}
	public void setMiddleware(String middleware) {
		this.middleware = middleware;
	}
	public String getMiddlewareMsg() {
		return middlewareMsg;
	}
	public void setMiddlewareMsg(String middlewareMsg) {
		this.middlewareMsg = middlewareMsg;
	}
	public String getAppmsg() {
		return appmsg;
	}
	public void setAppmsg(String appmsg) {
		this.appmsg = appmsg;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public String getOperTypeName() {
		return operTypeName;
	}
	public void setOperTypeName(String operTypeName) {
		this.operTypeName = operTypeName;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getWorkflow() {
		return workflow;
	}
	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
	public String getDeploySoftId() {
		return deploySoftId;
	}
	public void setDeploySoftId(String deploySoftId) {
		this.deploySoftId = deploySoftId;
	}
	public String getBreakdownTime() {
		return breakdownTime;
	}
	public void setBreakdownTime(String breakdownTime) {
		this.breakdownTime = breakdownTime;
	}
	public String getBreakdownInfo() {
		return breakdownInfo;
	}
	public void setBreakdownInfo(String breakdownInfo) {
		this.breakdownInfo = breakdownInfo;
	}
	public String getExceptSolveTime() {
		return exceptSolveTime;
	}
	public void setExceptSolveTime(String exceptSolveTime) {
		this.exceptSolveTime = exceptSolveTime;
	}
	public String getExceptResult() {
		return exceptResult;
	}
	public void setExceptResult(String exceptResult) {
		this.exceptResult = exceptResult;
	}
	public String getBreakdownLevel() {
		return breakdownLevel;
	}
	public void setBreakdownLevel(String breakdownLevel) {
		this.breakdownLevel = breakdownLevel;
	}
	public String getPartitionInfo() {
		return partitionInfo;
	}
	public void setPartitionInfo(String partitionInfo) {
		this.partitionInfo = partitionInfo;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getExpTime() {
		return expTime;
	}
	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}
	public String getVipNum() {
		return vipNum;
	}
	public void setVipNum(String vipNum) {
		this.vipNum = vipNum;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getBreakdownLevelName() {
		if (breakdownLevel != null && breakdownLevel.equals("1")) {
			return "一般";
		}
		if (breakdownLevel != null && breakdownLevel.equals("2")) {
			return "中等";
		}
		if (breakdownLevel != null && breakdownLevel.equals("3")) {
			return "严重";
		}
		return breakdownLevelName;
	}
	public void setBreakdownLevelName(String breakdownLevelName) {
		
		this.breakdownLevelName = breakdownLevelName;
	}
	public String getExpansionType() {
		if (expansionType != null && expansionType.equals("cpu")) {
			return "CPU";
		}
		if (breakdownLevel != null && breakdownLevel.equals("memory")) {
			return "内存";
		}
		return expansionType;
	}
	public void setExpansionType(String expansionType) {
		this.expansionType = expansionType;
	}
	public String getExpansionSize() {
		return expansionSize;
	}
	public void setExpansionSize(String expansionSize) {
		this.expansionSize = expansionSize;
	}
	
	
}
