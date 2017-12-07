package com.cmp.entity.tcc;

import java.io.Serializable;

/**
 * 环境数据中心关联关系
 */
public class TccEvnDatacenterAss implements Serializable {

	private static final long serialVersionUID = -1053876504933196311L;

	/**
	 * 伪主键Id
	 */
	private Long	id;
	/**
	 * 环境Id
	 */

	private Long	evnId;
	/**
	 * 区域/数据中心Id
	 */

	private Long	zoneId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEvnId() {
		return evnId;
	}

	public void setEvnId(Long evnId) {
		this.evnId = evnId;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

}
