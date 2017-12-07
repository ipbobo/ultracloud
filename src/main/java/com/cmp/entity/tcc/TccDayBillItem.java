package com.cmp.entity.tcc;

import java.util.Date;

public class TccDayBillItem implements java.io.Serializable {

	private static final long serialVersionUID = 8083136797529762687L;

	private Long id;

	private TccResPrice TccResPrice;

	private Long dayId;

	private Double amount;

	private Double price;

	private Long num;

	private Long crtUserId;

	private Date crtDttm;

	private Long lastuptUserId;

	private Date lastuptDttm;

	private String enableFlg;

	private String resName;

	private String evnCode;

	private String appType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TccResPrice getTccResPrice() {
		return TccResPrice;
	}

	public void setTccResPrice(TccResPrice tccResPrice) {
		TccResPrice = tccResPrice;
	}

	public Long getDayId() {
		return dayId;
	}

	public void setDayId(Long dayId) {
		this.dayId = dayId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
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

	public Long getLastuptUserId() {
		return lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public Date getLastuptDttm() {
		return lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getEvnCode() {
		return evnCode;
	}

	public void setEvnCode(String evnCode) {
		this.evnCode = evnCode;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

}
