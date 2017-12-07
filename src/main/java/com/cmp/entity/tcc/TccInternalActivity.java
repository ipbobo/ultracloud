package com.cmp.entity.tcc;

import java.util.Date;

public class TccInternalActivity implements java.io.Serializable,
		Comparable<TccInternalActivity> {

	private static final long serialVersionUID = 8818661049293788458L;

	private Long actId;

	private Long srtOrignalId;

	private Long srtProfileId;

	private String actDesc;

	private String actPriority;

	private Date actPauseDttm;

	private Date actRestoreDttm;

	private String actStatusCd;

	private Date actLimitDate;

	private String actResult;

	private String actMemo;

	private Long ownerid;

	private Long ownerGrp;

	private String actTypeCd;

	private String errRetTypecd;

	private String errRetReason;

	private Long crtUserId;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String enableFlg;

	private TccSrt	tccSrt;
	/** 任务处理人名称 */

	private String	ownerName;
	/** 任务处理组名称 */

	private String	ownerGrpName;
	/** 任务类型 */

	private String	actTypeName;
	/** 任务状态名称 */

	private String	actStatusCdName;
	/** 是否是安装任务 */

	private boolean	isImplementGroup;
	/** 显示压测预约的状态 */

	private String	schduleFlag;

	public String getSchduleFlag() {
		return schduleFlag;
	}

	public void setSchduleFlag(String schduleFlag) {
		this.schduleFlag = schduleFlag;
	}

	public Long getActId() {
		return this.actId;
	}

	public void setActId(Long actId) {
		this.actId = actId;
	}

	public Long getSrtOrignalId() {
		return this.srtOrignalId;
	}

	public void setSrtOrignalId(Long srtOrignalId) {
		this.srtOrignalId = srtOrignalId;
	}

	public Long getSrtProfileId() {
		return this.srtProfileId;
	}

	public void setSrtProfileId(Long srtProfileId) {
		this.srtProfileId = srtProfileId;
	}

	public String getActDesc() {
		return this.actDesc;
	}

	public void setActDesc(String actDesc) {
		this.actDesc = actDesc;
	}

	public String getActPriority() {
		return this.actPriority;
	}

	public void setActPriority(String actPriority) {
		this.actPriority = actPriority;
	}

	public Date getActPauseDttm() {
		return this.actPauseDttm;
	}

	public void setActPauseDttm(Date actPauseDttm) {
		this.actPauseDttm = actPauseDttm;
	}

	public Date getActRestoreDttm() {
		return this.actRestoreDttm;
	}

	public void setActRestoreDttm(Date actRestoreDttm) {
		this.actRestoreDttm = actRestoreDttm;
	}

	public String getActStatusCd() {
		return this.actStatusCd;
	}

	public void setActStatusCd(String actStatusCd) {
		this.actStatusCd = actStatusCd;
	}

	public Date getActLimitDate() {
		return this.actLimitDate;
	}

	public void setActLimitDate(Date actLimitDate) {
		this.actLimitDate = actLimitDate;
	}

	public String getActResult() {
		return this.actResult;
	}

	public void setActResult(String actResult) {
		this.actResult = actResult;
	}

	public String getActMemo() {
		return this.actMemo;
	}

	public void setActMemo(String actMemo) {
		this.actMemo = actMemo;
	}

	public Long getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Long ownerid) {
		this.ownerid = ownerid;
	}

	public Long getOwnerGrp() {
		return this.ownerGrp;
	}

	public void setOwnerGrp(Long ownerGrp) {
		this.ownerGrp = ownerGrp;
	}

	public String getActTypeCd() {
		return this.actTypeCd;
	}

	public void setActTypeCd(String actTypeCd) {
		this.actTypeCd = actTypeCd;
	}

	public String getErrRetTypecd() {
		return this.errRetTypecd;
	}

	public void setErrRetTypecd(String errRetTypecd) {
		this.errRetTypecd = errRetTypecd;
	}

	public String getErrRetReason() {
		return this.errRetReason;
	}

	public void setErrRetReason(String errRetReason) {
		this.errRetReason = errRetReason;
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

	public Date getLastuptDttm() {
		return this.lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public Long getLastuptUserId() {
		return this.lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public String getEnableFlg() {
		return this.enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public TccSrt getTccSrt() {
		return this.tccSrt;
	}

	public void setTccSrt(TccSrt tccSrt) {
		this.tccSrt = tccSrt;
	}

	public boolean getIsImplementGroup() {
		return isImplementGroup;
	}

	public void setIsImplementGroup(boolean isImplementGroup) {
		this.isImplementGroup = isImplementGroup;
	}

	public String getActStatusCdName() {
		return actStatusCdName;
	}

	public void setActStatusCdName(String actStatusCdName) {
		this.actStatusCdName = actStatusCdName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getActTypeName() {
		return actTypeName;
	}

	public void setActTypeName(String actTypeName) {
		this.actTypeName = actTypeName;
	}

	public void setImplementGroup(boolean isImplementGroup) {
		this.isImplementGroup = isImplementGroup;
	}

	public String getOwnerGrpName() {
		return ownerGrpName;
	}

	public void setOwnerGrpName(String ownerGrpName) {
		this.ownerGrpName = ownerGrpName;
	}

	@Override
	public int compareTo(TccInternalActivity act) {

		if (this.actId.longValue() > act.actId.longValue()) {
			return 1;
		} else {
			return -1;
		}
	}

}
