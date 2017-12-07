package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TccSoftwareInstallInformation implements Serializable {

	private static final long serialVersionUID = 6218166368161215329L;

	/**
	 * 主键id
	 */

	private Long	id;
	/**
	 * 软件名称
	 */

	private String	softwareName;
	/**
	 * 软件版本
	 */

	private String	softwareEdition;
	/**
	 * 许可证数量
	 */

	private Integer	licenseNumber;
	/**
	 * 存放位置
	 */

	private String	storageLocation;
	/**
	 * 软件描述
	 */

	private String	softwareRemark;

	/**
	 * 准保终止日期
	 */

	private Date	terminationTime;
	/**
	 * 创建人id
	 */

	private Long	createPersonId;
	/**
	 * 创建人
	 */

	private String	createPerson;
	/**
	 * 创建时间
	 */

	private Date	createTime;

	private Set<TccSoftwareModifyRecord>	modifyRecords	= new HashSet<TccSoftwareModifyRecord>();
	/**
	 * 是否有效
	 */

	private Integer							enableflg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getSoftwareEdition() {
		return softwareEdition;
	}

	public void setSoftwareEdition(String softwareEdition) {
		this.softwareEdition = softwareEdition;
	}

	public Integer getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(Integer licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public String getSoftwareRemark() {
		return softwareRemark;
	}

	public void setSoftwareRemark(String softwareRemark) {
		this.softwareRemark = softwareRemark;
	}

	public Date getTerminationTime() {
		return terminationTime;
	}

	public void setTerminationTime(Date terminationTime) {
		this.terminationTime = terminationTime;
	}

	public Long getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(Long createPersonId) {
		this.createPersonId = createPersonId;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<TccSoftwareModifyRecord> getModifyRecords() {
		return modifyRecords;
	}

	public void setModifyRecords(Set<TccSoftwareModifyRecord> modifyRecords) {
		this.modifyRecords = modifyRecords;
	}

	public Integer getEnableflg() {
		return enableflg;
	}

	public void setEnableflg(Integer enableflg) {
		this.enableflg = enableflg;
	}

}
