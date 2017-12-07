package com.cmp.entity.tcc;

import java.util.Date;

public class TccIsoCase implements java.io.Serializable {

	private static final long serialVersionUID = 1323L;

	private Long isoId;

	private String isoName;

	private String memo;

	private Long crtUserId;

	private Long osId;

	private String crtUserName;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;
	// 模板数量

	private Long templateNum;

	private String lastuptUserName;

	private String enableFlg;
	// 启用标签 1:启用 0:禁用

	private Long usageFlag;

	private Long imageId;
	// 镜像类型 1:普遍镜像 2:存量镜像,3:场景镜像

	private Long isoType;

	private TccOs os;

	private TccOsType osType;

	private String imageName;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Long getIsoId() {
		return isoId;
	}

	public void setIsoId(Long isoId) {
		this.isoId = isoId;
	}

	public String getIsoName() {
		return isoName;
	}

	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public Date getLastuptDttm() {
		return lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public Long getLastuptUserId() {
		return lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public TccOs getOs() {
		return os;
	}

	public void setOs(TccOs os) {
		this.os = os;
	}

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public String getLastuptUserName() {
		return lastuptUserName;
	}

	public void setLastuptUserName(String lastuptUserName) {
		this.lastuptUserName = lastuptUserName;
	}

	public Long getOsId() {
		return osId;
	}

	public void setOsId(Long osId) {
		this.osId = osId;
	}

	public TccOsType getOsType() {
		return osType;
	}

	public void setOsType(TccOsType osType) {
		this.osType = osType;
	}

	public Long getUsageFlag() {
		return usageFlag;
	}

	public void setUsageFlag(Long usageFlag) {
		this.usageFlag = usageFlag;
	}

	public Long getIsoType() {
		return isoType;
	}

	public void setIsoType(Long isoType) {
		this.isoType = isoType;
	}

	public Long getTemplateNum() {
		return templateNum;
	}

	public void setTemplateNum(Long templateNum) {
		this.templateNum = templateNum;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

}
