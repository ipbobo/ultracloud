package com.cmp.entity.tcc;

public class TCcActUnit implements java.io.Serializable {

	private static final long serialVersionUID = 2395949354311711657L;

	private long id;

	private String unitName;

	private short unitSort;

	private String modifyTime;

	// Constructors

	/** default constructor */
	public TCcActUnit() {
	}

	/** full constructor */
	public TCcActUnit(String unitName, short unitSort,
			String modifyTime) {
		this.unitName = unitName;
		this.unitSort = unitSort;
		this.modifyTime = modifyTime;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public short getUnitSort() {
		return unitSort;
	}

	public void setUnitSort(short unitSort) {
		this.unitSort = unitSort;
	}

	public String getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

}
