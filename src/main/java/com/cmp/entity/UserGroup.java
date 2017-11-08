package com.cmp.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * 用户组
 * 
 * @author liuweixing
 *
 */
public class UserGroup {

	private BigInteger id;
	private String name;
	private Timestamp gmt_create;
	private Timestamp gmt_modified;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
