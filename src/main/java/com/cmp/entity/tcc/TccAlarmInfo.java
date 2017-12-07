package com.cmp.entity.tcc;

import java.util.Date;

public class TccAlarmInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1499072048542691454L;

	private Long id;
	// private TccAlarmDef tccAlarmDef; // 告警设置外键

	private String alarmContent; // 告警内容

	private String alarmType; // 告警类型

	private String alarmDate; // 告警日期

	private Long crtUserId; // 发给谁

	private Date crtDttm; // 创建时间

	private Long lastuptUserId; // 最后修改人

	private Date lastuptDttm; // 最后更新时间

	private String enableFlg; // 数据是否有效(0:无效;1:有效)

	// Constructors

	/** default constructor */
	public TccAlarmInfo() {
	}

	/** minimal constructor */
	public TccAlarmInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TccAlarmInfo(Long id, TccAlarmDef tccAlarmDef,
			String alarmContent, String alarmType, String alarmDate,
			Long crtUserId, Date crtDttm, Long lastuptUserId,
			Date lastuptDttm, String enableFlg) {
		this.id = id;
		// this.tccAlarmDef = tccAlarmDef;
		this.alarmContent = alarmContent;
		this.alarmType = alarmType;
		this.alarmDate = alarmDate;
		this.crtUserId = crtUserId;
		this.crtDttm = crtDttm;
		this.lastuptUserId = lastuptUserId;
		this.lastuptDttm = lastuptDttm;
		this.enableFlg = enableFlg;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// public TccAlarmDef getTccAlarmDef() {
	// return tccAlarmDef;
	// }
	//
	// public void setTccAlarmDef(TccAlarmDef tccAlarmDef) {
	// this.tccAlarmDef = tccAlarmDef;
	// }

	public String getAlarmContent() {
		return this.alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getAlarmType() {
		return this.alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmDate() {
		return this.alarmDate;
	}

	public void setAlarmDate(String alarmDate) {
		this.alarmDate = alarmDate;
	}

	public Long getCrtUserId() {
		return this.crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtDttm() {
		return this.crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Long getLastuptUserId() {
		return this.lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public Date getLastuptDttm() {
		return this.lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public String getEnableFlg() {
		return this.enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

}
