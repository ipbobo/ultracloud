package com.cmp.entity.tcc;

import java.util.Date;

public class TccAlarmEvent implements java.io.Serializable {

	private static final long serialVersionUID = 7628182135621663528L;

	private Long id;

	private String triggerName;// 触发器名

	private String triggerId;// 触发器ID

	private String expression;// 触发器的表达式

	private String message;// 告警事件的信息

	private String hostName;// 主机名

	private String host;// zabbix中对应的host

	private String ip;// 主机IP

	private String status;// 状态，一般都为PROBLEM

	private String priority;// 告警级别

	private Date alertTime;// 告警触发时间

	private String indicator;// 告警指标

	private Long enableflg;

	private Long crtUserId;

	private Date crtDate;

	private Long uptUserId;

	private Date	uptDate;
	/**
	 * 告警的持续时间
	 */

	private String	durationTime;

	/**
	 * 用于前台显示
	 */

	private String crtName;

	private String uptName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getAlertTime() {
		return alertTime;
	}

	public void setAlertTime(Date alertTime) {
		this.alertTime = alertTime;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public Long getEnableflg() {
		return enableflg;
	}

	public void setEnableflg(Long enableflg) {
		this.enableflg = enableflg;
	}

	public Long getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public Long getUptUserId() {
		return uptUserId;
	}

	public void setUptUserId(Long uptUserId) {
		this.uptUserId = uptUserId;
	}

	public Date getUptDate() {
		return uptDate;
	}

	public void setUptDate(Date uptDate) {
		this.uptDate = uptDate;
	}

	public String getCrtName() {
		return crtName;
	}

	public void setCrtName(String crtName) {
		this.crtName = crtName;
	}

	public String getUptName() {
		return uptName;
	}

	public void setUptName(String uptName) {
		this.uptName = uptName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
