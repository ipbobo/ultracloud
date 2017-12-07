package com.cmp.entity.tcc;

import java.util.Date;

public class TVmSurveryDetail implements java.io.Serializable {

	private static final long serialVersionUID = 4630808083860804963L;

	private Long id;

	private String name;

	private String type;

	private String pfree;

	private String ptop;

	private String enableFlag;

	private String createUser;

	private Date createDate;

	private String updateUser;

	private Date updateDate;

	private TVmSurveryInfo TVmSurveryInfo;

	// Constructors

	/** default constructor */
	public TVmSurveryDetail() {
	}

	/** minimal constructor */
	public TVmSurveryDetail(Long id, Date createDate, Date updateDate) {
		this.id = id;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public TVmSurveryDetail(Long id, TVmSurveryInfo TVmSurveryInfo,
			String name, String type, String pfree, String ptop,
			String enableFlag, String createUser, Date createDate,
			String updateUser, Date updateDate) {
		this.id = id;
		this.TVmSurveryInfo = TVmSurveryInfo;
		this.name = name;
		this.type = type;
		this.pfree = pfree;
		this.ptop = ptop;
		this.enableFlag = enableFlag;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TVmSurveryInfo getTVmSurveryInfo() {
		return this.TVmSurveryInfo;
	}

	public void setTVmSurveryInfo(TVmSurveryInfo TVmSurveryInfo) {
		this.TVmSurveryInfo = TVmSurveryInfo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPfree() {
		return this.pfree;
	}

	public void setPfree(String pfree) {
		this.pfree = pfree;
	}

	public String getPtop() {
		return this.ptop;
	}

	public void setPtop(String ptop) {
		this.ptop = ptop;
	}

	public String getEnableFlag() {
		return this.enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
