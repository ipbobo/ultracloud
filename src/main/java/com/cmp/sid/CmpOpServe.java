package com.cmp.sid;

/**
 * 运维服务实体类
 *
 */
public class CmpOpServe {
	private Long id;
	private String serviceType;
	private String operType;
	private String vm;
	private String vmMsg;
	private String middleware;
	private String middlewareMsg;
	private String appmsg;//申请说明
	private String createTime;//创建时间
	private String status;//流程状态  0:启动   1:审核完成  2:工单完成
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	
	
	
}
