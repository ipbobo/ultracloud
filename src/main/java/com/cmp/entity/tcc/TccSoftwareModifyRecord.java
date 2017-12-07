package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

/**
 * 软件追溯
 */

public class TccSoftwareModifyRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -649280488045862002L;

	/**
	 * 主键id
	 */

	private Long Id;

	/**
	 * 软件实体
	 */

	private TccSoftwareInstallInformation information;

	/**
	 * 操作说明
	 */

	private String operation;

	/**
	 * 操作人id
	 */

	private Long operationPersonId;

	/**
	 * 
	 */

	private String operationPerson;

	/**
	 * 操作时间
	 */

	private Date operationTime;

	private String softwareName;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public TccSoftwareInstallInformation getInformation() {
		return information;
	}

	public void setInformation(TccSoftwareInstallInformation information) {
		this.information = information;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Long getOperationPersonId() {
		return operationPersonId;
	}

	public void setOperationPersonId(Long operationPersonId) {
		this.operationPersonId = operationPersonId;
	}

	public String getOperationPerson() {
		return operationPerson;
	}

	public void setOperationPerson(String operationPerson) {
		this.operationPerson = operationPerson;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

}
