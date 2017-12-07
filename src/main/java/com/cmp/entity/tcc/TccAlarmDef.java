package com.cmp.entity.tcc;

import java.util.Date;
import java.util.Set;

public class TccAlarmDef implements java.io.Serializable {

	private static final long serialVersionUID = 4484793992727974886L;

	private Long id; // ID

	private String funPoint; // 功能点

	private String alarmIndex; // 指标

	private String alarmType; // 告警类型

	private String commSetting; // 统一通讯设置

	private Long alarmNum; // 警告次数

	private Long alarmInterval; // 警告次数

	private Long alarmRoat; // 频率

	private Long crtUserId; // 创建人

	private Date crtDttm; // 创建时间

	private Long lastuptUserId; // 最后修改人

	private Date lastuptDttm; // 最后更新时间

	private String enableFlg; // 数据是否有效(0:无效;1:有效)
	// private Set tccAlarmInfos = new HashSet(0);

	private String startinterval;

	private String endinterval;

	/**
	 * 从页面获取数组
	 */

	private String[] startTimes;

	private String[] endTimes;

	private String[] settingTypes;

	private String[] minNums;

	private String[] maxNums;

	private String[] equalNums;

	private String[] noEqualNums;

	private String countNum;

	// Constructors

	/**
	 * @return the startTimes
	 */
	public String[] getStartTimes() {
		return startTimes;
	}

	/**
	 * @param startTimes the startTimes to set
	 */
	public void setStartTimes(String[] startTimes) {
		this.startTimes = startTimes;
	}

	/**
	 * @return the endTimes
	 */
	public String[] getEndTimes() {
		return endTimes;
	}

	/**
	 * @param endTimes the endTimes to set
	 */
	public void setEndTimes(String[] endTimes) {
		this.endTimes = endTimes;
	}

	/**
	 * @return the settingTypes
	 */
	public String[] getSettingTypes() {
		return settingTypes;
	}

	/**
	 * @param settingTypes the settingTypes to set
	 */
	public void setSettingTypes(String[] settingTypes) {
		this.settingTypes = settingTypes;
	}

	/**
	 * @return the minNums
	 */
	public String[] getMinNums() {
		return minNums;
	}

	/**
	 * @param minNums the minNums to set
	 */
	public void setMinNums(String[] minNums) {
		this.minNums = minNums;
	}

	/**
	 * @return the maxNums
	 */
	public String[] getMaxNums() {
		return maxNums;
	}

	/**
	 * @param maxNums the maxNums to set
	 */
	public void setMaxNums(String[] maxNums) {
		this.maxNums = maxNums;
	}

	/**
	 * @return the equalNums
	 */
	public String[] getEqualNums() {
		return equalNums;
	}

	/**
	 * @param equalNums the equalNums to set
	 */
	public void setEqualNums(String[] equalNums) {
		this.equalNums = equalNums;
	}

	/**
	 * @return the noEqualNums
	 */
	public String[] getNoEqualNums() {
		return noEqualNums;
	}

	/**
	 * @param noEqualNums the noEqualNums to set
	 */
	public void setNoEqualNums(String[] noEqualNums) {
		this.noEqualNums = noEqualNums;
	}

	/**
	 * @return the countNum
	 */
	public String getCountNum() {
		return countNum;
	}

	/**
	 * @param countNum the countNum to set
	 */
	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}

	/** default constructor */
	public TccAlarmDef() {
	}

	/** minimal constructor */
	public TccAlarmDef(Long id) {
		this.id = id;
	}

	/** full constructor */
	@SuppressWarnings("rawtypes")
	public TccAlarmDef(Long id, String funPoint, String alarmIndex,
			String alarmType, String commSetting, Long alarmNum,
			Long alarmInterval, Long alarmRoat, Long crtUserId, Date crtDttm,
			Long lastuptUserId, Date lastuptDttm, String enableFlg,
			Set tccAlarmInfos, Set tccAlaTimInts) {
		this.id = id;
		this.funPoint = funPoint;
		this.alarmIndex = alarmIndex;
		this.alarmType = alarmType;
		this.commSetting = commSetting;
		this.alarmNum = alarmNum;
		this.alarmInterval = alarmInterval;
		this.alarmRoat = alarmRoat;
		this.crtUserId = crtUserId;
		this.crtDttm = crtDttm;
		this.lastuptUserId = lastuptUserId;
		this.lastuptDttm = lastuptDttm;
		this.enableFlg = enableFlg;
		// this.tccAlarmInfos = tccAlarmInfos;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFunPoint() {
		return this.funPoint;
	}

	public void setFunPoint(String funPoint) {
		this.funPoint = funPoint;
	}

	public String getAlarmIndex() {
		return this.alarmIndex;
	}

	public void setAlarmIndex(String alarmIndex) {
		this.alarmIndex = alarmIndex;
	}

	public String getAlarmType() {
		return this.alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getCommSetting() {
		return this.commSetting;
	}

	public void setCommSetting(String commSetting) {
		this.commSetting = commSetting;
	}

	public Long getAlarmNum() {
		return this.alarmNum;
	}

	public void setAlarmNum(Long alarmNum) {
		this.alarmNum = alarmNum;
	}

	public Long getAlarmInterval() {
		return this.alarmInterval;
	}

	public void setAlarmInterval(Long alarmInterval) {
		this.alarmInterval = alarmInterval;
	}

	public Long getAlarmRoat() {
		return this.alarmRoat;
	}

	public void setAlarmRoat(Long alarmRoat) {
		this.alarmRoat = alarmRoat;
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

	// public Set getTccAlarmInfos() {
	// return tccAlarmInfos;
	// }
	//
	// public void setTccAlarmInfos(Set tccAlarmInfos) {
	// this.tccAlarmInfos = tccAlarmInfos;
	// }

	public String getStartinterval() {
		return startinterval;
	}

	public void setStartinterval(String startinterval) {
		this.startinterval = startinterval;
	}

	public String getEndinterval() {
		return endinterval;
	}

	public void setEndinterval(String endinterval) {
		this.endinterval = endinterval;
	}

}
