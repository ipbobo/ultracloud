package com.cmp.sid;

import java.util.List;

public class AutoDeployNode {
	private String id;
	private String name;
	private String detail;
	private String orderNum;
	private String configId;
	List<AutoDeployScriptNode> scriptNodeList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public List<AutoDeployScriptNode> getScriptNodeList() {
		return scriptNodeList;
	}
	public void setScriptNodeList(List<AutoDeployScriptNode> scriptNodeList) {
		this.scriptNodeList = scriptNodeList;
	}
	
	
}
