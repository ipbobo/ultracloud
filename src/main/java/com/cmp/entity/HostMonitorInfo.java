package com.cmp.entity;

public class HostMonitorInfo {

	private String hostName; // 主机名称，对应平台中host，不能重复不能为空

	private String visibleName;//主机展示的名称

	private String osName; // 操作系统名称:linux windows 不能为空

	private Integer status; // 控制是否监控 :0为监控;1为不监控.默认选择0 不能为空

	private Integer monitorType; // 监控类型：1：agent监控；2：snmp监控；3：ipmi监控； 4：jmx监控

	// 主机监控选择1 不能为空
	private String ip; // IP地址 不能为空

	private String port; // 端口:agent端口为:10050;snmp端口为:161;ipmi端口为:623;jmx端口为:12345

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(Integer monitorType) {
		this.monitorType = monitorType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getVisibleName() {
		return visibleName;
	}

	public void setVisibleName(String visibleName) {
		this.visibleName = visibleName;
	}

}
