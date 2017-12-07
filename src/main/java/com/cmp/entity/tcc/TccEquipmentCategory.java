package com.cmp.entity.tcc;

import java.util.Date;

public class TccEquipmentCategory implements java.io.Serializable {

	private static final long serialVersionUID = -1175508724269733747L;

	private Long equipmentId; // 设备分类ID

	private String equipmentCategoryName; // 设备分类名称

	private Long crtUserId; // 创建人

	private Date crtDttm; // 创建时间

	private Long lastuptUserId; // 最后修改人

	private Date lastuptDttm; // 最后修改时间

	private String enableFlag;

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentCategoryName() {
		return equipmentCategoryName;
	}

	public void setEquipmentCategoryName(String equipmentCategoryName) {
		this.equipmentCategoryName = equipmentCategoryName;
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

	public Long getLastuptUserId() {
		return lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public Date getLastuptDttm() {
		return lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
}
