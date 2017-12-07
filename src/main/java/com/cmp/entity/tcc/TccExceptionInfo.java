package com.cmp.entity.tcc;

import java.util.Date;

public class TccExceptionInfo implements java.io.Serializable {

	private static final long serialVersionUID = -7266397014731470358L;

	private Long	id;
	/** 主键 **/

	private String	exceptionContext;
	/*** 异常信息 ****/

	private Date	createTime;
	/**** 创建时间 *****/

	private String	createUserId;
	/**** 创建人的Id ******/

	private String	exceptionType;
	/******* 异常类型 ********/

	private String	parameters;
	/******* 异常参数 ***********/

	private String	operatorName;
	/******* 操作人名称 ********/

	private String	operatorRoleName;
	/******* 操作人角色名称 ********/

	private String	subSystemName;
	/******* 子系统名称 ********/

	private String	className;
	/******* 类名 ********/

	private String	methodName;
	/******* 方法名 ***********/

	private String	remark1;

	private String remark2;

	private String operationType;

	private String enableFlag;

	/** 1:表示可用 0:表示不可用 ******/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**** 操作类型 *******/
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

	public String getExceptionContext() {
		return exceptionContext;
	}

	public void setExceptionContext(String exceptionContext) {
		this.exceptionContext = exceptionContext;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorRoleName() {
		return operatorRoleName;
	}

	public void setOperatorRoleName(String operatorRoleName) {
		this.operatorRoleName = operatorRoleName;
	}

	public String getSubSystemName() {
		return subSystemName;
	}

	public void setSubSystemName(String subSystemName) {
		this.subSystemName = subSystemName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

}
