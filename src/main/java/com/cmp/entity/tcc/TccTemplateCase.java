package com.cmp.entity.tcc;

import java.util.Date;

public class TccTemplateCase implements java.io.Serializable {

	private static final long serialVersionUID = -5025227423899653958L;

	private Long templateCaseId;

	private String templateTypeCd;

	private String resourceType; // 资源类型:0:物理机 1:虚拟机

	private String templateTypeName;

	private String templateName;

	private String dbosVersionCd;

	private String osVersionCd;

	private String middlewareCd;

	private String templatePath;

	private String memo;

	private String username;

	private String zonename;

	private String passwd;

	private String showDisplayName;

	private String csTemplateOfferingId;

	private String vcTemplateOfferingId;

	private Long crtUserId;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String enableFlg;

	private String customFlag;

	private String virtualType;

	// 模板描述信息

	private String templateDesc;

	private Long isoId;

	private Long osId;

	/** 操作系统名称 */

	private String osVersionCdName;

	/** 中间件名称 */

	private String middlewareCdName;

	/** 数据库名称 */

	private String dbosVersionName;

	/*** 模板所在的域 ***/

	private String zoneId;

	/** 资源池 **/

	private String resourcePool;

	/** 模板大小 **/

	private String templateSize;

	/** 模版平台名 **/

	private String cloudplantformName;

	private String dataCenterId;

	/**
	 * 2016、09，21 GJH新增 模板类型
	 */

	private String osType;

	private TccCloudDatacenter tccCloudDatacenter;

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
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

	public String getZonename() {
		return zonename;
	}

	public void setZonename(String zonename) {
		this.zonename = zonename;
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

	public Long getTemplateCaseId() {
		return this.templateCaseId;
	}

	public void setTemplateCaseId(Long templateCaseId) {
		this.templateCaseId = templateCaseId;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getOsVersionCd() {
		return this.osVersionCd;
	}

	public void setOsVersionCd(String osVersionCd) {
		this.osVersionCd = osVersionCd;
	}

	public String getTemplateTypeCd() {
		return templateTypeCd;
	}

	public void setTemplateTypeCd(String templateTypeCd) {
		this.templateTypeCd = templateTypeCd;
	}

	public String getMiddlewareCd() {
		return middlewareCd;
	}

	public void setMiddlewareCd(String middlewareCd) {
		this.middlewareCd = middlewareCd;
	}

	public String getTemplatePath() {
		return this.templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCsTemplateOfferingId() {
		return csTemplateOfferingId;
	}

	public void setCsTemplateOfferingId(String csTemplateOfferingId) {
		this.csTemplateOfferingId = csTemplateOfferingId;
	}

	public String getVcTemplateOfferingId() {
		return vcTemplateOfferingId;
	}

	public void setVcTemplateOfferingId(String vcTemplateOfferingId) {
		this.vcTemplateOfferingId = vcTemplateOfferingId;
	}

	/**
	 * @return the osVersionCdName
	 */
	public String getOsVersionCdName() {
		return osVersionCdName;
	}

	/**
	 * @param osVersionCdName the osVersionCdName to set
	 */
	public void setOsVersionCdName(String osVersionCdName) {
		this.osVersionCdName = osVersionCdName;
	}

	/**
	 * @return the middlewareCdName
	 */
	public String getMiddlewareCdName() {
		return middlewareCdName;
	}

	/**
	 * @param middlewareCdName the middlewareCdName to set
	 */
	public void setMiddlewareCdName(String middlewareCdName) {
		this.middlewareCdName = middlewareCdName;
	}

	public String getDbosVersionCd() {
		return dbosVersionCd;
	}

	public void setDbosVersionCd(String dbosVersionCd) {
		this.dbosVersionCd = dbosVersionCd;
	}

	public String getDbosVersionName() {
		return dbosVersionName;
	}

	public void setDbosVersionName(String dbosVersionName) {
		this.dbosVersionName = dbosVersionName;
	}

	public String getTemplateTypeName() {
		return templateTypeName;
	}

	public void setTemplateTypeName(String templateTypeName) {
		this.templateTypeName = templateTypeName;
	}

	public String getCustomFlag() {
		return customFlag;
	}

	public void setCustomFlag(String customFlag) {
		this.customFlag = customFlag;
	}

	public String getTemplateDesc() {
		return templateDesc;
	}

	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}

	//
	// public String getVirtualType() {
	// return virtualType;
	// }
	//
	// public void setVirtualType(String virtualType) {
	// this.virtualType = virtualType;
	// }

	public String getVirtualType() {
		return virtualType;
	}

	public void setVirtualType(String virtualType) {
		this.virtualType = virtualType;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getShowDisplayName() {
		return showDisplayName;
	}

	public void setShowDisplayName(String showDisplayName) {
		this.showDisplayName = showDisplayName;
	}

	public String getResourcePool() {
		return resourcePool;
	}

	public void setResourcePool(String resourcePool) {
		this.resourcePool = resourcePool;
	}

	public String getTemplateSize() {
		return templateSize;
	}

	public void setTemplateSize(String templateSize) {
		this.templateSize = templateSize;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getCloudplantformName() {
		return cloudplantformName;
	}

	public void setCloudplantformName(String cloudplantformName) {
		this.cloudplantformName = cloudplantformName;
	}

	public Long getIsoId() {
		return isoId;
	}

	public void setIsoId(Long isoId) {
		this.isoId = isoId;
	}

	public Long getOsId() {
		return osId;
	}

	public void setOsId(Long osId) {
		this.osId = osId;
	}

	public TccCloudDatacenter getTccCloudDatacenter() {
		return tccCloudDatacenter;
	}

	public void setTccCloudDatacenter(TccCloudDatacenter tccCloudDatacenter) {
		this.tccCloudDatacenter = tccCloudDatacenter;
	}

}
