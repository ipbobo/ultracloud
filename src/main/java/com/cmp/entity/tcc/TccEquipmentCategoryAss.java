package com.cmp.entity.tcc;

public class TccEquipmentCategoryAss implements java.io.Serializable {

	private static final long serialVersionUID = -1175508724269733747L;

	private Long equipmentAssId; // ID

	private Long equipmentId; // 设备分类ID

	private Long applyResorceId; // 资源ID

	public Long getEquipmentAssId() {
		return equipmentAssId;
	}

	public void setEquipmentAssId(Long equipmentAssId) {
		this.equipmentAssId = equipmentAssId;
	}

	public Long getApplyResorceId() {
		return applyResorceId;
	}

	public void setApplyResorceId(Long applyResorceId) {
		this.applyResorceId = applyResorceId;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}
}
