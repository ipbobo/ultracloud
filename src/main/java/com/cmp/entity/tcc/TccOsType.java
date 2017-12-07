package com.cmp.entity.tcc;

public class TccOsType implements java.io.Serializable {

	private static final long serialVersionUID = 1429640120749158930L;

	private Long osTypeId;

	private String osTypeName;

	private String memo;

	private Long osBits;

	private String osFamily;

	public Long getOsTypeId() {
		return osTypeId;
	}

	public void setOsTypeId(Long osTypeId) {
		this.osTypeId = osTypeId;
	}

	public String getOsTypeName() {
		return osTypeName;
	}

	public void setOsTypeName(String osTypeName) {
		this.osTypeName = osTypeName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getOsBits() {
		return osBits;
	}

	public void setOsBits(Long osBits) {
		this.osBits = osBits;
	}

	public String getOsFamily() {
		return osFamily;
	}

	public void setOsFamily(String osFamily) {
		this.osFamily = osFamily;
	}

}
