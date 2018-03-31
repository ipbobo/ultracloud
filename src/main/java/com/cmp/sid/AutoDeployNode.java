package com.cmp.sid;

import java.util.List;

/*
 * 	自动部署节点
 * */
public class AutoDeployNode {
	private String id;	//id
	private String name; //节点名称
	private String detail;	//节点描述
	private String orderNum; //节点序列
	private String configId; //节点配置ID 
	private String mediumId; //软件ID
	private String scriptUrl; //自动部署脚本地址
	private String scriptId;  //软件安装脚本ID
	private String executeParams;	//自动部署脚本执行参数  
	private String modFlag;  //参数用户修改标志
	List<AutoDeployScriptNode> scriptNodeList;  //自动部署脚本入参列表
	
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
	public String getScriptUrl() {
		return scriptUrl;
	}
	public void setScriptUrl(String scriptUrl) {
		this.scriptUrl = scriptUrl;
	}
	
	public String getExecuteParams() {
		return executeParams;
	}
	public void setExecuteParams(String executeParams) {
		this.executeParams = executeParams;
	}
	public String getScriptId() {
		return scriptId;
	}
	public void setScriptId(String scriptId) {
		this.scriptId = scriptId;
	}
	public String getMediumId() {
		return mediumId;
	}
	public void setMediumId(String mediumId) {
		this.mediumId = mediumId;
	}
	public String getModFlag() {
		return modFlag;
	}
	public void setModFlag(String modFlag) {
		this.modFlag = modFlag;
	}

	
	
}
