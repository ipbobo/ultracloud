package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

import com.cmp.util.DateUtil;



public class TccHmcManagedSystemEvent implements Serializable{

	private static final long serialVersionUID = 2818268534959404456L;
	
	/**
	 * 主键Id
	 */
	private Long id;
	
	/**
	 * 所属HMC
	 */
	private Long hmcId;
	
	/**
	 * 所属物理小机Id
	 */
	private Long hmcManagedSystemId;
    
	/**
	 * 事件序号
	 */
	private Long problemNum;
	
	
	private String pmhNum;

	/**
	 * 错误码
	 */
	private String refCode;
	
	/**
	 * 标识事件状态
	 * open/Closed
	 */
	private String status;
	
	/**
	 * 标识该故障第一次发生时间
	 */
	
	private Date firstTime;

	/**
	 * 标识该故障最后一次发生时间
	 */
	
	private Date lastTime;
	
	/**
	 * 物理小机名称
	 */
	
	private String sysName;
	
	/**
	 * 标识型号和序列号， MT是型号8205-E6C，SN标识序列号 062200T， IBM的序列号一般都是7位
	 * eg.8205-E6C/062200T
	 */
	
	private String sysMtms;
	
	/**
	 * 一台机器如果是由多个enclosure连接的，这里标识报错的enclosure的序列号； p740一台机器就是一个enclosure，这个不用理会
	 */
	
	private String enclosureMtms;
	
	/**
	 * 标识微码
	 */
	
	private String firmwareFix;
	
	/**
	 * 标识事件描述，不是事件内容
	 */
	
	private String text;
	
	/**
	 * 标识事件创建时间
	 */
	
	private Date createdTime;
	
	
	private String reportingName;
	
	
	private String reportingMtms;
	
	
	private String failingMtms;
	
	
	private String analyzingHmc;
	
	/**
	 * 事件发生时间
	 */
	
	private Date eventTime;
	
	/**
	 * 维修用的，dump log的相关内容
	 */
	
	private String files;
	
	/**
	 * 采集时间
	 */
	
	private Date collectTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHmcId() {
		return hmcId;
	}

	public void setHmcId(Long hmcId) {
		this.hmcId = hmcId;
	}

	public Long getHmcManagedSystemId() {
		return hmcManagedSystemId;
	}

	public void setHmcManagedSystemId(Long hmcManagedSystemId) {
		this.hmcManagedSystemId = hmcManagedSystemId;
	}

	public Long getProblemNum() {
		return problemNum;
	}

	public void setProblemNum(Long problemNum) {
		this.problemNum = problemNum;
	}

	public String getPmhNum() {
		return pmhNum;
	}

	public void setPmhNum(String pmhNum) {
		this.pmhNum = pmhNum;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFirstTime() {
		return firstTime;
	}
	
	public String getFirstTimeStr() {
		if(this.firstTime != null){
			return DateUtil.formatDate(firstTime, DateUtil.DEFAULT_FORMAT);
		}
		return "";
	}
	
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public String getLastTimeStr() {
		if(this.lastTime != null){
			return DateUtil.formatDate(lastTime, DateUtil.DEFAULT_FORMAT);
		}
		return "";
	}
	
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysMtms() {
		return sysMtms;
	}

	public void setSysMtms(String sysMtms) {
		this.sysMtms = sysMtms;
	}

	public String getEnclosureMtms() {
		return enclosureMtms;
	}

	public void setEnclosureMtms(String enclosureMtms) {
		this.enclosureMtms = enclosureMtms;
	}

	public String getFirmwareFix() {
		return firmwareFix;
	}

	public void setFirmwareFix(String firmwareFix) {
		this.firmwareFix = firmwareFix;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedTime() {
		return createdTime;
	}
	
	public String getCreatedTimeStr() {
		if(this.createdTime != null){
			return DateUtil.formatDate(createdTime, DateUtil.DEFAULT_FORMAT);
		}
		return "";
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getReportingName() {
		return reportingName;
	}

	public void setReportingName(String reportingName) {
		this.reportingName = reportingName;
	}

	public String getReportingMtms() {
		return reportingMtms;
	}

	public void setReportingMtms(String reportingMtms) {
		this.reportingMtms = reportingMtms;
	}

	public String getFailingMtms() {
		return failingMtms;
	}

	public void setFailingMtms(String failingMtms) {
		this.failingMtms = failingMtms;
	}

	public String getAnalyzingHmc() {
		return analyzingHmc;
	}

	public void setAnalyzingHmc(String analyzingHmc) {
		this.analyzingHmc = analyzingHmc;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public String getEventTimeStr() {
		if(this.eventTime != null){
			return DateUtil.formatDate(eventTime, DateUtil.DEFAULT_FORMAT);
		}
		return "";
	}
	
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public String getCollectTimeStr() {
		if(this.collectTime != null){
			return DateUtil.formatDate(collectTime, DateUtil.DEFAULT_FORMAT);
		}
		return "";
	}
	
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	
}
