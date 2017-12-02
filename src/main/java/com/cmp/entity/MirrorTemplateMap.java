package com.cmp.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * 镜像模板关联
 * 
 * @author liuweixing
 *
 */
public class MirrorTemplateMap {
	private BigInteger id;
	private BigInteger mirror_id;
	private BigInteger mirrortemplate_id;
	private Timestamp gmt_create;
	private Timestamp gmt_modified;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getMirror_id() {
		return mirror_id;
	}

	public void setMirror_id(BigInteger mirror_id) {
		this.mirror_id = mirror_id;
	}

	public BigInteger getMirrortemplate_id() {
		return mirrortemplate_id;
	}

	public void setMirrortemplate_id(BigInteger mirrortemplate_id) {
		this.mirrortemplate_id = mirrortemplate_id;
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
