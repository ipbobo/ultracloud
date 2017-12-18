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
	
	
}
