package com.cmp.entity.tcc;

import java.util.Date;

public class TccResPriceHistory implements java.io.Serializable {

	private static final long serialVersionUID = -3896633726756501220L;

	private Long historyId;

	private TccResPrice tccResPrice;

	private Double price;

	private Date effectiveDate;

	private Long crtUserId;

	private Date crtDttm;

	private Long lastuptUserId;

	private Date lastuptDttm;

	private String enableFlg;

	// Constructors

	/** default constructor */
	public TccResPriceHistory() {
	}

	/** full constructor */
	public TccResPriceHistory(TccResPrice tccResPrice, Double price, Date effectiveDate,
			Long crtUserId, Date crtDttm, Long lastuptUserId, Date lastuptDttm, String enableFlg) {
		this.tccResPrice = tccResPrice;
		this.price = price;
		this.effectiveDate = effectiveDate;
		this.crtUserId = crtUserId;
		this.crtDttm = crtDttm;
		this.lastuptUserId = lastuptUserId;
		this.lastuptDttm = lastuptDttm;
		this.enableFlg = enableFlg;
	}

	// Property accessors

	public Long getHistoryId() {
		return this.historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public TccResPrice getTccResPrice() {
		return this.tccResPrice;
	}

	public void setTccResPrice(TccResPrice tccResPrice) {
		this.tccResPrice = tccResPrice;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

}
