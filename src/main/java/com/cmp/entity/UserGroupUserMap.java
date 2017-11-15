package com.cmp.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * 用户组用户关联
 * 
 * @author liuweixing
 *
 */
public class UserGroupUserMap {
	private BigInteger id;
	private BigInteger usergroup_id;
	private String USER_ID;
	private Timestamp gmt_create;
	private Timestamp gmt_modified;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getUsergroup_id() {
		return usergroup_id;
	}

	public void setUsergroup_id(BigInteger usergroup_id) {
		this.usergroup_id = usergroup_id;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
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
