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
	private int  cpu_quota;
	private int memory_quota;
	private int disk_quota;
	private int cpu_used = 0;
	private int memory_used = 0;
	private int disk_used = 0;

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

	public int getCpu_quota() {
		return cpu_quota;
	}

	public void setCpu_quota(int cpu_quota) {
		this.cpu_quota = cpu_quota;
	}

	public int getMemory_quota() {
		return memory_quota;
	}

	public void setMemory_quota(int memory_quota) {
		this.memory_quota = memory_quota;
	}

	public int getDisk_quota() {
		return disk_quota;
	}

	public void setDisk_quota(int disk_quota) {
		this.disk_quota = disk_quota;
	}

	public int getCpu_used() {
		return cpu_used;
	}

	public void setCpu_used(int cpu_used) {
		this.cpu_used = cpu_used;
	}

	public int getMemory_used() {
		return memory_used;
	}

	public void setMemory_used(int memory_used) {
		this.memory_used = memory_used;
	}

	public int getDisk_used() {
		return disk_used;
	}

	public void setDisk_used(int disk_used) {
		this.disk_used = disk_used;
	}

	
}
