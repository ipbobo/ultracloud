package com.cmp.entity.tcc;

import java.util.Date;

public class NamingRule {

	private Long id;

	// 名称
	private String name;

	// 创建人
	private Long crtUser;

	private String crtUserName;

	// 创建时间
	private Date crtDate;

	// 前缀
	private Long firstRuleType;

	// 后缀
	private Long secondRuleType;

	// 是否使用
	private boolean useFlag;

	private String firstRuleTypeName;

	private String secondRuleTypeName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public boolean getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(boolean useFlag) {
		this.useFlag = useFlag;
	}

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public String getFirstRuleTypeName() {
		return firstRuleTypeName;
	}

	public void setFirstRuleTypeName(String firstRuleTypeName) {
		this.firstRuleTypeName = firstRuleTypeName;
	}

	public String getSecondRuleTypeName() {
		return secondRuleTypeName;
	}

	public void setSecondRuleTypeName(String secondRuleTypeName) {
		this.secondRuleTypeName = secondRuleTypeName;
	}

	public Long getCrtUser() {
		return crtUser;
	}

	public void setCrtUser(Long crtUser) {
		this.crtUser = crtUser;
	}

	public Long getFirstRuleType() {
		return firstRuleType;
	}

	public void setFirstRuleType(Long firstRuleType) {
		this.firstRuleType = firstRuleType;
	}

	public Long getSecondRuleType() {
		return secondRuleType;
	}

	public void setSecondRuleType(Long secondRuleType) {
		this.secondRuleType = secondRuleType;
	}

}
