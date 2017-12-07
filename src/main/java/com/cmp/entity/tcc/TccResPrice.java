package com.cmp.entity.tcc;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TccResPrice entity. @author MyEclipse Persistence Tools
 */

public class TccResPrice implements java.io.Serializable {

	private static final long serialVersionUID = -2129913848813202912L;

	private Long id;

	private String resName;

	private String chargeUnit;

	private Double currentPrice;

	private Double newPrice;

	private Double oldPrice;

	private Integer isEff;

	private Date effectiveDate;

	private Long crtUserId;

	private Date crtDttm;

	private Long lastuptUserId;

	private String lastuptUserName;

	private Date lastuptDttm;

	private String enableFlg;

	private String code;

	private String effectiveDateStr;

	private Set<TccResPriceHistory> tccResPriceHostories = new HashSet<TccResPriceHistory>(0);

	// Constructors

	/** default constructor */
	public TccResPrice() {
	}

	/** full constructor */
	public TccResPrice(String resName, String chargeUnit, Double currentPrice, Double newPrice,
			Double oldPrice, int isEff, Date effectiveDate, Long crtUserId, Date crtDttm,
			Long lastuptUserId,
			Date lastuptDttm, String enableFlg, Set<TccResPriceHistory> tccResPriceHostories) {
		this.resName = resName;
		this.chargeUnit = chargeUnit;
		this.currentPrice = currentPrice;
		this.newPrice = newPrice;
		this.oldPrice = oldPrice;
		this.isEff = isEff;
		this.effectiveDate = effectiveDate;
		this.crtUserId = crtUserId;
		this.crtDttm = crtDttm;
		this.lastuptUserId = lastuptUserId;
		this.lastuptDttm = lastuptDttm;
		this.enableFlg = enableFlg;
		this.tccResPriceHostories = tccResPriceHostories;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResName() {
		return this.resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getChargeUnit() {
		return this.chargeUnit;
	}

	public void setChargeUnit(String chargeUnit) {
		this.chargeUnit = chargeUnit;
	}

	public Double getCurrentPrice() {
		return this.currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Double getNewPrice() {
		return this.newPrice;
	}

	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}

	public Double getOldPrice() {
		return this.oldPrice;
	}

	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Integer getIsEff() {
		return this.isEff;
	}

	public void setIsEff(Integer isEff) {
		this.isEff = isEff;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
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

	public Long getLastuptUserId() {
		return this.lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public Date getLastuptDttm() {
		return this.lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public String getEnableFlg() {
		return this.enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Set<TccResPriceHistory> getTccResPriceHostories() {
		return this.tccResPriceHostories;
	}

	public void setTccResPriceHostories(Set<TccResPriceHistory> tccResPriceHostories) {
		this.tccResPriceHostories = tccResPriceHostories;
	}

	public String getLastuptUserName() {
		return lastuptUserName;
	}

	public void setLastuptUserName(String lastuptUserName) {
		this.lastuptUserName = lastuptUserName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEffectiveDateStr() {
		return effectiveDateStr;
	}

	public void setEffectiveDateStr(String effectiveDateStr) {
		this.effectiveDateStr = effectiveDateStr;
	}

}
