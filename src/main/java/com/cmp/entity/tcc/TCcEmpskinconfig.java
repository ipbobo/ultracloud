package com.cmp.entity.tcc;

import java.util.Date;

public class TCcEmpskinconfig implements java.io.Serializable {

	private static final long serialVersionUID = -974289387159136969L;

	private Long empskinId;

	private Long skinId;

	private Long userPartyId;

	private String skinDir;

	private String skinDesc;

	private String enableFlg;

	private Date crtDate;

	private Date uptDate;

	// Constructors

	/** default constructor */
	public TCcEmpskinconfig() {
	}

	/** minimal constructor */
	public TCcEmpskinconfig(Long empskinId) {
		this.empskinId = empskinId;
	}

	/** full constructor */
	public TCcEmpskinconfig(Long empskinId, Long skinId, Long userPartyId,
			String skinDir, String skinDesc, String enableFlg, Date crtDate,
			Date uptDate) {
		this.empskinId = empskinId;
		this.skinId = skinId;
		this.userPartyId = userPartyId;
		this.skinDir = skinDir;
		this.skinDesc = skinDesc;
		this.enableFlg = enableFlg;
		this.crtDate = crtDate;
		this.uptDate = uptDate;
	}

	// Property accessors

	public Long getEmpskinId() {
		return this.empskinId;
	}

	public void setEmpskinId(Long empskinId) {
		this.empskinId = empskinId;
	}

	public Long getSkinId() {
		return this.skinId;
	}

	public void setSkinId(Long skinId) {
		this.skinId = skinId;
	}

	public Long getUserPartyId() {
		return this.userPartyId;
	}

	public void setUserPartyId(Long userPartyId) {
		this.userPartyId = userPartyId;
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

	public Date getUptDate() {
		return this.uptDate;
	}

	public void setUptDate(Date uptDate) {
		this.uptDate = uptDate;
	}

}
