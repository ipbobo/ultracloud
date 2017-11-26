package com.cmp.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * 项目
 * 
 * @author liuweixing
 *
 */
public class Project {
	private String id;
	private String name;
	private String shortname;
	private String level;
	private BigInteger parent_id;
	private String DEPARTMENT_ID;
	private String USERNAME;
	private BigInteger usergroup_id;
	private String detail;
	private Timestamp gmt_create;
	private Timestamp gmt_modified;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public BigInteger getParent_id() {
		return parent_id;
	}

	public void setParent_id(BigInteger parent_id) {
		this.parent_id = parent_id;
	}

	public String getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}

	public void setDEPARTMENT_ID(String dEPARTMENT_ID) {
		DEPARTMENT_ID = dEPARTMENT_ID;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public BigInteger getUsergroup_id() {
		return usergroup_id;
	}

	public void setUsergroup_id(BigInteger usergroup_id) {
		this.usergroup_id = usergroup_id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Timestamp getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(Timestamp gmt_create) {
		this.gmt_create = gmt_create;
	}

	public Timestamp getGmt_modified() {
		return gmt_modified;
	}

	public void setGmt_modified(Timestamp gmt_modified) {
		this.gmt_modified = gmt_modified;
	}
}
