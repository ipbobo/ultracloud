package com.cmp.entity.tcc;

import java.util.Date;

/**
 * 动态配置类
 */
public class TccDynamicCfg implements java.io.Serializable {

	private static final long serialVersionUID = 1883149672827621776L;

	private Long id;// 主键ID

	private String dynamicKey;// 动态配置key

	private String dynamicValue;// 动态配置的内容

	private String dynamicDesc;// 说明

	private Long crtUserId;// 创建人ID

	private Date crtDttm;// 创建时间

	private String crtUserName;// 创建人姓名

	private String crtTime;// 创建时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDynamicKey() {
		return dynamicKey;
	}

	public void setDynamicKey(String dynamicKey) {
		this.dynamicKey = dynamicKey;
	}

	public String getDynamicValue() {
		return dynamicValue;
	}

	public void setDynamicValue(String dynamicValue) {
		this.dynamicValue = dynamicValue;
	}

	public String getDynamicDesc() {
		return dynamicDesc;
	}

	public void setDynamicDesc(String dynamicDesc) {
		this.dynamicDesc = dynamicDesc;
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

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public String getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}

}
