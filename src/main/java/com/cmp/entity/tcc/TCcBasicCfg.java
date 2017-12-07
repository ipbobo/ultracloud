package com.cmp.entity.tcc;

public class TCcBasicCfg implements java.io.Serializable {

	private static final long serialVersionUID = 6930961814455378062L;

	private long id;

	private String webTitle;

	private String copyright;

	private long uploadSize;

	private String uploadType;

	private String billingUnit;

	private String storageUnit;

	private long storageMaxvalue;

	private String flag;

	private String cmpName;

	private String cmpDesc;

	private String cmpDepTel;

	private String cmpPrdTel;

	private String cmpWebUrl;

	// Constructors

	public String getCmpDepTel() {
		return cmpDepTel;
	}

	public void setCmpDepTel(String cmpDepTel) {
		this.cmpDepTel = cmpDepTel;
	}

	public String getCmpPrdTel() {
		return cmpPrdTel;
	}

	public void setCmpPrdTel(String cmpPrdTel) {
		this.cmpPrdTel = cmpPrdTel;
	}

	public String getCmpWebUrl() {
		return cmpWebUrl;
	}

	public void setCmpWebUrl(String cmpWebUrl) {
		this.cmpWebUrl = cmpWebUrl;
	}

	public String getCmpName() {
		return cmpName;
	}

	public void setCmpName(String cmpName) {
		this.cmpName = cmpName;
	}

	public String getCmpDesc() {
		return cmpDesc;
	}

	public void setCmpDesc(String cmpDesc) {
		this.cmpDesc = cmpDesc;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/** default constructor */
	public TCcBasicCfg() {
	}

	/** full constructor */
	public TCcBasicCfg(String webTitle, String copyright, long uploadSize,
			String uploadType, String billingUnit, String storageUnit,
			long storageMaxvalue) {
		this.webTitle = webTitle;
		this.copyright = copyright;
		this.uploadSize = uploadSize;
		this.uploadType = uploadType;
		this.billingUnit = billingUnit;
		this.storageUnit = storageUnit;
		this.storageMaxvalue = storageMaxvalue;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWebTitle() {
		return this.webTitle;
	}

	public void setWebTitle(String webTitle) {
		this.webTitle = webTitle;
	}

	public String getCopyright() {
		return this.copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public long getUploadSize() {
		return this.uploadSize;
	}

	public void setUploadSize(long uploadSize) {
		this.uploadSize = uploadSize;
	}

	public String getUploadType() {
		return this.uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public String getBillingUnit() {
		return this.billingUnit;
	}

	public void setBillingUnit(String billingUnit) {
		this.billingUnit = billingUnit;
	}

	public String getStorageUnit() {
		return this.storageUnit;
	}

	public void setStorageUnit(String storageUnit) {
		this.storageUnit = storageUnit;
	}

	public long getStorageMaxvalue() {
		return this.storageMaxvalue;
	}

	public void setStorageMaxvalue(long storageMaxvalue) {
		this.storageMaxvalue = storageMaxvalue;
	}

}
