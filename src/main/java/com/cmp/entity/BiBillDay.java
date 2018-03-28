package com.cmp.entity;

import java.math.BigInteger;

public class BiBillDay {
	private String DEPARTMENT_ID;
	private String DEPARTMENT_NAME;
	private String project_id;
	private String project_name;
	private BigInteger vm_id;
	private String vm_name;
	private Integer cpu;
	private Long memory;
	private Integer datadisk;
	private String type;
	private String user;
	private String date;
	private Long account;

	public String getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}

	public void setDEPARTMENT_ID(String dEPARTMENT_ID) {
		DEPARTMENT_ID = dEPARTMENT_ID;
	}

	public String getDEPARTMENT_NAME() {
		return DEPARTMENT_NAME;
	}

	public void setDEPARTMENT_NAME(String dEPARTMENT_NAME) {
		DEPARTMENT_NAME = dEPARTMENT_NAME;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getVm_name() {
		return vm_name;
	}

	public void setVm_name(String vm_name) {
		this.vm_name = vm_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigInteger getVm_id() {
		return vm_id;
	}

	public void setVm_id(BigInteger vm_id) {
		this.vm_id = vm_id;
	}

	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	public Long getMemory() {
		return memory;
	}

	public void setMemory(Long memory) {
		this.memory = memory;
	}

	public Integer getDatadisk() {
		return datadisk;
	}

	public void setDatadisk(Integer datadisk) {
		this.datadisk = datadisk;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

}
