package com.cmp.entity.tcc;

import java.util.ArrayList;

// Generated 2013-2-6 14:54:20 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TccSrt implements java.io.Serializable {

	private static final long serialVersionUID = 5213023635995059376L;

	private Long srtId;

	private Long srtOrignalId;

	private Long srtManager;

	private String srtStatusCd;// 21：退回工单进行中

	private String srtPriority;

	private Long srtReopenNum;

	private Date srtDueDate;

	private Date srtHoperpyDate;

	private String srtRelatedEmail;

	private String srtRelatedTel;

	private String srtRelatedMobileTel;

	private String srtContext;

	private String srtMemo;

	private Date srtStDttm;

	private Date srtCloseDttm;

	private String srtCurFlg;

	private Long crtUserId;

	private String useageEnv;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String enableFlg;

	private String actTypeCd; // 工单状态 申请、扩容、释放、克隆、延期

	// private TccSrtStatusChg tccSrtStatusChg;

	private Long actionUserId;
	// private TccReleaseResource tccReleaseResource;

	private Set<TccInternalActivity> tccInternalActivities = new HashSet<TccInternalActivity>();

	private Set<TccSrtApplyedhostAss> tccSrtApplyedhostAsses = new HashSet<TccSrtApplyedhostAss>(0);

	/** 工单状态名称 */

	private String						srtStatusCdName;
	/** 环境分类 */

	private String						useageFlageCd;
	/** 环境分类名称 */

	private String						useageFlageName;
	/** 申请人名称 */

	private String						crtUserName;
	/** 申请部门名称 */

	private String						crtOrgName;
	/** 项目名称 */

	private String						projectName;
	/** 项目所属公司 */

	private String						projectOwnerComp;
	/** 记录工单产生的历史任务 */

	private List<TccInternalActivity>	srtHistoryTasks	= new ArrayList<TccInternalActivity>();
	/**
	 * 是否是部门内流程
	 * 1：是
	 * 0：不是
	 */

	private String						isInnerProcess;

	/**
	 * 接管部门ID 如果是部门内流程用户 则存入接管部门ID 如果不是部门内流程用户则为null
	 */

	private Long takeOverOrgan;

	public Long getSrtId() {
		return this.srtId;
	}

	public Set<TccSrtApplyedhostAss> getTccSrtApplyedhostAsses() {
		return tccSrtApplyedhostAsses;
	}

	public void setTccSrtApplyedhostAsses(Set<TccSrtApplyedhostAss> tccSrtApplyedhostAsses) {
		this.tccSrtApplyedhostAsses = tccSrtApplyedhostAsses;
	}

	public void setSrtId(Long srtId) {
		this.srtId = srtId;
	}

	public Long getSrtOrignalId() {
		return this.srtOrignalId;
	}

	public void setSrtOrignalId(Long srtOrignalId) {
		this.srtOrignalId = srtOrignalId;
	}

	public String getSrtStatusCd() {
		return this.srtStatusCd;
	}

	public void setSrtStatusCd(String srtStatusCd) {
		this.srtStatusCd = srtStatusCd;
	}

	public String getSrtPriority() {
		return this.srtPriority;
	}

	public Long getActionUserId() {
		return actionUserId;
	}

	public void setActionUserId(Long actionUserId) {
		this.actionUserId = actionUserId;
	}

	public void setSrtPriority(String srtPriority) {
		this.srtPriority = srtPriority;
	}

	public Long getSrtReopenNum() {
		return this.srtReopenNum;
	}

	public void setSrtReopenNum(Long srtReopenNum) {
		this.srtReopenNum = srtReopenNum;
	}

	public Date getSrtDueDate() {
		return this.srtDueDate;
	}

	public void setSrtDueDate(Date srtDueDate) {
		this.srtDueDate = srtDueDate;
	}

	public Date getSrtHoperpyDate() {
		return this.srtHoperpyDate;
	}

	public void setSrtHoperpyDate(Date srtHoperpyDate) {
		this.srtHoperpyDate = srtHoperpyDate;
	}

	public String getSrtRelatedEmail() {
		return this.srtRelatedEmail;
	}

	public void setSrtRelatedEmail(String srtRelatedEmail) {
		this.srtRelatedEmail = srtRelatedEmail;
	}

	public String getSrtRelatedTel() {
		return this.srtRelatedTel;
	}

	public void setSrtRelatedTel(String srtRelatedTel) {
		this.srtRelatedTel = srtRelatedTel;
	}

	public String getSrtRelatedMobileTel() {
		return this.srtRelatedMobileTel;
	}

	public void setSrtRelatedMobileTel(String srtRelatedMobileTel) {
		this.srtRelatedMobileTel = srtRelatedMobileTel;
	}

	public String getSrtContext() {
		return this.srtContext;
	}

	public void setSrtContext(String srtContext) {
		this.srtContext = srtContext;
	}

	public String getSrtMemo() {
		return this.srtMemo;
	}

	public void setSrtMemo(String srtMemo) {
		this.srtMemo = srtMemo;
	}

	public Date getSrtStDttm() {
		return this.srtStDttm;
	}

	public void setSrtStDttm(Date srtStDttm) {
		this.srtStDttm = srtStDttm;
	}

	public Date getSrtCloseDttm() {
		return this.srtCloseDttm;
	}

	public void setSrtCloseDttm(Date srtCloseDttm) {
		this.srtCloseDttm = srtCloseDttm;
	}

	public String getSrtCurFlg() {
		return this.srtCurFlg;
	}

	public void setSrtCurFlg(String srtCurFlg) {
		this.srtCurFlg = srtCurFlg;
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

	// public TccSrtStatusChg getTccSrtStatusChg() {
	// return this.tccSrtStatusChg;
	// }

	// public void setTccSrtStatusChg(TccSrtStatusChg tccSrtStatusChg) {
	// this.tccSrtStatusChg = tccSrtStatusChg;
	// }

	// public TccReleaseResource getTccReleaseResource() {
	// return this.tccReleaseResource;
	// }
	//
	// public void setTccReleaseResource(TccReleaseResource tccReleaseResource)
	// {
	// this.tccReleaseResource = tccReleaseResource;
	// }

	public Set<TccInternalActivity> getTccInternalActivities() {

		Set<TccInternalActivity> treeSet = new TreeSet<TccInternalActivity>();
		Iterator<TccInternalActivity> itor = this.tccInternalActivities.iterator();
		while (itor.hasNext()) {
			treeSet.add(itor.next());
		}

		return treeSet;
	}

	public void setTccInternalActivities(Set<TccInternalActivity> tccInternalActivities) {
		this.tccInternalActivities = tccInternalActivities;
	}

	public String getUseageFlageName() {
		return useageFlageName;
	}

	public void setUseageFlageName(String useageFlageName) {
		this.useageFlageName = useageFlageName;
	}

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public String getCrtOrgName() {
		return crtOrgName;
	}

	public void setCrtOrgName(String crtOrgName) {
		this.crtOrgName = crtOrgName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSrtStatusCdName() {
		return srtStatusCdName;
	}

	public void setSrtStatusCdName(String srtStatusCdName) {
		this.srtStatusCdName = srtStatusCdName;
	}

	public String getActTypeCd() {
		return actTypeCd;
	}

	public void setActTypeCd(String actTypeCd) {
		this.actTypeCd = actTypeCd;
	}

	public String getUseageFlageCd() {
		return useageFlageCd;
	}

	public void setUseageFlageCd(String useageFlageCd) {
		this.useageFlageCd = useageFlageCd;
	}

	public String getProjectOwnerComp() {
		return projectOwnerComp;
	}

	public void setProjectOwnerComp(String projectOwnerComp) {
		this.projectOwnerComp = projectOwnerComp;
	}

	public String getUseageEnv() {
		return useageEnv;
	}

	public void setUseageEnv(String useageEnv) {
		this.useageEnv = useageEnv;
	}

	public Long getSrtManager() {
		return srtManager;
	}

	public void setSrtManager(Long srtManager) {
		this.srtManager = srtManager;
	}

	public String getIsInnerProcess() {
		return isInnerProcess;
	}

	public void setIsInnerProcess(String isInnerProcess) {
		this.isInnerProcess = isInnerProcess;
	}

	public List<TccInternalActivity> getSrtHistoryTasks() {
		return srtHistoryTasks;
	}

	public void setSrtHistoryTasks(List<TccInternalActivity> srtHistoryTasks) {
		this.srtHistoryTasks = srtHistoryTasks;
	}

	public Long getTakeOverOrgan() {
		return takeOverOrgan;
	}

	public void setTakeOverOrgan(Long takeOverOrgan) {
		this.takeOverOrgan = takeOverOrgan;
	}

}
