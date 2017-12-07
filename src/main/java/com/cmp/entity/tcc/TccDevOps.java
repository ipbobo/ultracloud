package com.cmp.entity.tcc;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TccDevOps extends TccSrt implements java.io.Serializable {

	private static final long serialVersionUID = -197755027867434495L;
	// 服务工单类型: 0运维服务, 1软件安装服务，2故障申报服务

	private String serType;

	private Long applyResourceId;

	private String serJob;// 启动oracle、停止oracle

	private String softName;

	private String softVersion;

	private String insType;

	private String insPath;

	private Long crtUserId;

	private Date crtDttm;

	private String enableFlg;
	// 操作状态: 1未操作, 2操作成功, 3操作失败

	private String serState;

	/************************************************* 故障处理 **********************************************/
	/** 故障描述 */

	private String	troubleDesc;
	/** 故障发生时间 */

	private Date	troubleTime;
	/** 期望解决时间 **/

	private Date	troubleExpectationTime;
	/** 故障期望结果 */

	private String	troubleExpectation;
	/** 备注 */

	private String	remark;
	/**
	 * 故障解决结果
	 * 1：未解决
	 * 2：解决中
	 * 3：已结局
	 */

	private String	troubleSolveResult;
	/** 故障解决时间 */

	private Date	troubleSolveTime;
	/** 紧急程度 */

	private String	urgency;
	/** 故障解决人 */

	private String	troubleSolvedMan;

	/** 故障解决人ID */
	private Long troubleSolvedManId;

	/** root密码使用期限 临时存放页面上的数据所用 */

	private String duration;

	/** VIP占用需求添加start */

	private Set<TccVipSrt> tccVipSrties = new HashSet<TccVipSrt>(0);

	private Long vipNum;

	/** VIP占用需求添加end */

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

	public String getEnableFlg() {
		return this.enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public Long getApplyResourceId() {
		return applyResourceId;
	}

	public void setApplyResourceId(Long applyResourceId) {
		this.applyResourceId = applyResourceId;
	}

	public String getSerJob() {
		return serJob;
	}

	public void setSerJob(String serJob) {
		this.serJob = serJob;
	}

	public String getSoftName() {
		return softName;
	}

	public void setSoftName(String softName) {
		this.softName = softName;
	}

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}

	public String getInsPath() {
		return insPath;
	}

	public void setInsPath(String insPath) {
		this.insPath = insPath;
	}

	public String getSerState() {
		return serState;
	}

	public void setSerState(String serState) {
		this.serState = serState;
	}

	public String getTroubleDesc() {
		return troubleDesc;
	}

	public void setTroubleDesc(String troubleDesc) {
		this.troubleDesc = troubleDesc;
	}

	public Date getTroubleTime() {
		return troubleTime;
	}

	public void setTroubleTime(Date troubleTime) {
		this.troubleTime = troubleTime;
	}

	public Date getTroubleExpectationTime() {
		return troubleExpectationTime;
	}

	public void setTroubleExpectationTime(Date troubleExpectationTime) {
		this.troubleExpectationTime = troubleExpectationTime;
	}

	public String getTroubleExpectation() {
		return troubleExpectation;
	}

	public void setTroubleExpectation(String troubleExpectation) {
		this.troubleExpectation = troubleExpectation;
	}

	public String getTroubleSolveResult() {
		return troubleSolveResult;
	}

	public void setTroubleSolveResult(String troubleSolveResult) {
		if (null == troubleSolveResult || "".equals(troubleSolveResult)) {
			this.troubleSolveResult = "1";
		} else {
			this.troubleSolveResult = troubleSolveResult;
		}
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getTroubleSolveTime() {
		return troubleSolveTime;
	}

	public void setTroubleSolveTime(Date troubleSolveTime) {
		this.troubleSolveTime = troubleSolveTime;
	}

	public String getTroubleSolvedMan() {
		return troubleSolvedMan;
	}

	public void setTroubleSolvedMan(String troubleSolvedMan) {
		this.troubleSolvedMan = troubleSolvedMan;
	}

	public Long getTroubleSolvedManId() {
		return troubleSolvedManId;
	}

	public void setTroubleSolvedManId(Long troubleSolvedManId) {
		this.troubleSolvedManId = troubleSolvedManId;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Set<TccVipSrt> getTccVipSrties() {
		return tccVipSrties;
	}

	public void setTccVipSrties(Set<TccVipSrt> tccVipSrties) {
		this.tccVipSrties = tccVipSrties;
	}

	public Long getVipNum() {
		return vipNum;
	}

	public void setVipNum(Long vipNum) {
		this.vipNum = vipNum;
	}

}
