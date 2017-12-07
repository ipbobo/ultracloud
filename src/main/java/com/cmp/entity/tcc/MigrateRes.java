package com.cmp.entity.tcc;

import java.util.Date;

public class MigrateRes implements java.io.Serializable {

	private static final long serialVersionUID = 3293718970064974663L;

	private Long migrateId;

	private TccApplyedHostinfo applyedHostinfo;

	private String uuid;

	private Long beforePhysicsId;

	private Long changePhysicsId;

	private Date changeTime;

	public Long getMigrateId() {
		return migrateId;
	}

	public void setMigrateId(Long migrateId) {
		this.migrateId = migrateId;
	}

	public TccApplyedHostinfo getApplyedHostinfo() {
		return applyedHostinfo;
	}

	public void setApplyedHostinfo(TccApplyedHostinfo applyedHostinfo) {
		this.applyedHostinfo = applyedHostinfo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getBeforePhysicsId() {
		return beforePhysicsId;
	}

	public void setBeforePhysicsId(Long beforePhysicsId) {
		this.beforePhysicsId = beforePhysicsId;
	}

	public Long getChangePhysicsId() {
		return changePhysicsId;
	}

	public void setChangePhysicsId(Long changePhysicsId) {
		this.changePhysicsId = changePhysicsId;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
