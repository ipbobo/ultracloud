package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

/**
 * 云主机资源使用监控统计策略类
 */
public class TccMonitorPolicy implements Serializable {

	private static final long serialVersionUID = 1625131473761877351L;

	private Long policyId;

	/** 监控指标 */

	private String monitorIndex;

	/** 统计天数 */

	private Integer countDays;

	/** 使用谷值 */

	private Integer minSize;

	/** 使用峰值 */

	private Integer maxSize;

	/** 数据有效标志位 */

	private String enableFlg;

	/** 记录修改者 */

	private Long updateUser;

	/** 记录修改时间 */

	private Date updateDate;

	/** 记录创建者 */

	private Long createUser;

	/** 记录创建时间 */

	private Date createDate;

	/** 监控指标名称 */

	private String indexName;

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public String getMonitorIndex() {
		return monitorIndex;
	}

	public void setMonitorIndex(String monitorIndex) {
		this.monitorIndex = monitorIndex;
	}

	public Integer getCountDays() {
		return countDays;
	}

	public void setCountDays(Integer countDays) {
		this.countDays = countDays;
	}

	public Integer getMinSize() {
		return minSize;
	}

	public void setMinSize(Integer minSize) {
		this.minSize = minSize;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

}
