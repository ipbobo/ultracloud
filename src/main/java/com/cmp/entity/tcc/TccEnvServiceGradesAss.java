package com.cmp.entity.tcc;

import java.io.Serializable;

/**
 * 环境和服务等级关联关系表
 * 环境 n-n 服务等级
 */
public class TccEnvServiceGradesAss implements Serializable {

	private static final long serialVersionUID = -878360947547204819L;

	/**
	 * 伪主键Id
	 */
	private Long id;

	/**
	 * 环境Id
	 */

	private Long envId;

	/**
	 * 服务等级Id
	 */

	private Integer serviceGradeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEnvId() {
		return envId;
	}

	public void setEnvId(Long envId) {
		this.envId = envId;
	}

	public Integer getServiceGradeId() {
		return serviceGradeId;
	}

	public void setServiceGradeId(Integer serviceGradeId) {
		this.serviceGradeId = serviceGradeId;
	}

}
