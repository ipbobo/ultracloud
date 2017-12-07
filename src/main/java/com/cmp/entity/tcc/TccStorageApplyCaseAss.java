package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

public class TccStorageApplyCaseAss implements Serializable {

	private static final long serialVersionUID = -8790274379016545600L;

	private Long id;

	private Long assId;

	private Long srtId;

	private Double storageSize;

	private Integer sortNo;

	private Integer enableFlg;

	private Long crtUserId;

	private Date crtDttm;

	private Integer stateNo;// 启用状态 1:是 ; 0:否

	private String uuid;

	public TccStorageApplyCaseAss() {
	}

	public TccStorageApplyCaseAss(Long assId, Long srtId, Double storageSize, Integer enableFlg,
			Long crtUserId, Date crtDttm) {
		this.assId = assId;
		this.srtId = srtId;
		this.storageSize = storageSize;
		this.enableFlg = enableFlg;
		this.crtUserId = crtUserId;
		this.crtDttm = crtDttm;
		this.stateNo = 0;

	}

	public TccStorageApplyCaseAss(Long assId, Long srtId, Double storageSize, Integer enableFlg,
			Long crtUserId, Date crtDttm, Integer stateNo) {
		this.assId = assId;
		this.srtId = srtId;
		this.storageSize = storageSize;
		this.enableFlg = enableFlg;
		this.crtUserId = crtUserId;
		this.crtDttm = crtDttm;
		this.stateNo = stateNo;

	}

	public TccStorageApplyCaseAss(Long assId, Long srtId, Double storageSize, Integer sortNo,
			Integer enableFlg,
			Long crtUserId, Date crtDttm, Integer stateNo, String uuid) {
		this.assId = assId;
		this.srtId = srtId;
		this.storageSize = storageSize;
		this.sortNo = sortNo;
		this.enableFlg = enableFlg;
		this.crtUserId = crtUserId;
		this.crtDttm = crtDttm;
		this.stateNo = stateNo;
		this.uuid = uuid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssId() {
		return assId;
	}

	public void setAssId(Long assId) {
		this.assId = assId;
	}

	public Double getStorageSize() {
		return storageSize;
	}

	public void setStorageSize(Double storageSize) {
		this.storageSize = storageSize;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Integer getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(Integer enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Long getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Integer getStateNo() {
		return stateNo;
	}

	public void setStateNo(Integer stateNo) {
		this.stateNo = stateNo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getSrtId() {
		return srtId;
	}

	public void setSrtId(Long srtId) {
		this.srtId = srtId;
	}

}
