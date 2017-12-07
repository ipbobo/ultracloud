package com.cmp.entity.tcc;

import java.sql.Timestamp;
import java.util.Date;

/**
 * TCcSkin entity. @author MyEclipse Persistence Tools
 */
public class TccSkin implements java.io.Serializable {

	private static final long serialVersionUID = -4538030860023859633L;

	private Long skinId;

	private String skinDir;

	private String skinDesc;

	private String enableFlg;// 0:不可用 1：可用 2：当前的默认设置

	private Date crtDate;

	private Long crtUserPartyId;

	private Date uptDate;

	private Long	uptUserPartyId;
	/**
	 * 用于页面显示
	 */

	private String	crtname;

	private String uptname;

	// Constructors

	public String getCrtname() {
		return crtname;
	}

	public void setCrtname(String crtname) {
		this.crtname = crtname;
	}

	public String getUptname() {
		return uptname;
	}

	public void setUptname(String uptname) {
		this.uptname = uptname;
	}

	/** default constructor */
	public TccSkin() {
	}

	/** minimal constructor */
	public TccSkin(Long skinId) {
		this.skinId = skinId;
	}

	/** full constructor */
	public TccSkin(Long skinId, String skinDir, String skinDesc, String enableFlg,
			Timestamp crtDate, Long crtUserPartyId, Timestamp uptDate, Long uptUserPartyId) {
		this.skinId = skinId;
		this.skinDir = skinDir;
		this.skinDesc = skinDesc;
		this.enableFlg = enableFlg;
		this.crtDate = crtDate;
		this.crtUserPartyId = crtUserPartyId;
		this.uptDate = uptDate;
		this.uptUserPartyId = uptUserPartyId;
	}

	// Property accessors

	public Long getSkinId() {
		return this.skinId;
	}

	public void setSkinId(Long skinId) {
		this.skinId = skinId;
	}

	public String getSkinDir() {
		return this.skinDir;
	}

	public void setSkinDir(String skinDir) {
		this.skinDir = skinDir;
	}

	public String getSkinDesc() {
		return this.skinDesc;
	}

	public void setSkinDesc(String skinDesc) {
		this.skinDesc = skinDesc;
	}

	public String getEnableFlg() {
		return this.enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Date getCrtDate() {
		return this.crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public Long getCrtUserPartyId() {
		return this.crtUserPartyId;
	}

	public void setCrtUserPartyId(Long crtUserPartyId) {
		this.crtUserPartyId = crtUserPartyId;
	}

	public Date getUptDate() {
		return this.uptDate;
	}

	public void setUptDate(Date uptDate) {
		this.uptDate = uptDate;
	}

	public Long getUptUserPartyId() {
		return this.uptUserPartyId;
	}

	public void setUptUserPartyId(Long uptUserPartyId) {
		this.uptUserPartyId = uptUserPartyId;
	}

}
