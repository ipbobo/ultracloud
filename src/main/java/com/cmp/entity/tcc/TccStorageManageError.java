package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方管理的存储故障
 */
public class TccStorageManageError implements Serializable {

	private static final long serialVersionUID = -6564562076577183453L;

	/**
	 * 主键Id
	 */

	private Long id;

	/**
	 * 存储服务Id
	 */

	private Long storageManageId;

	/**
	 * 故障Id
	 */

	private String errorId;

	/**
	 * 故障严重等级
	 */

	private String errorSeverity;

	/**
	 * 故障内容
	 */

	private String errorText;

	/**
	 * 故障类型
	 */

	private String errorType;

	/**
	 * 存储厂商类型
	 */

	private String vendorType;

	/**
	 * 创建时间
	 */

	private Date crtDttm;

	/**
	 * 修改时间
	 */

	private Date lastuptDttm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStorageManageId() {
		return storageManageId;
	}

	public void setStorageManageId(Long storageManageId) {
		this.storageManageId = storageManageId;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getErrorSeverity() {
		return errorSeverity;
	}

	public void setErrorSeverity(String errorSeverity) {
		this.errorSeverity = errorSeverity;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getVendorType() {
		return vendorType;
	}

	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
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

}
