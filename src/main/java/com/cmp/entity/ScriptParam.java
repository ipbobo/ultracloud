package com.cmp.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * 脚本参数
 * 
 * @author liuweixing
 *
 */
public class ScriptParam {

	private BigInteger id;
	private BigInteger script_id;
	private String param_key;
	private String name;
	private String value;
	private String number;
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

	public BigInteger getScript_id() {
		return script_id;
	}

	public void setScript_id(BigInteger script_id) {
		this.script_id = script_id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getParam_key() {
		return param_key;
	}

	public void setParam_key(String param_key) {
		this.param_key = param_key;
	}
}
