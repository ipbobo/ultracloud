package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

public class TccNovaInfo implements Serializable {

	private static final long serialVersionUID = 1787198645221933650L;
	// nova组件id

	private Integer id;
	// 对应的平台id

	private Integer platformId;
	// nova组件名

	private String name;
	// nova组件对应的主机

	private String host;
	// nova组件对应的域

	private String zone;
	// 组件状态:up/down

	private String state;
	// 组件状态:enable/unenable

	private String statu;
	// 最后更新日期

	private Date updateTime;

	private String Uuid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getUuid() {
		return Uuid;
	}

	public void setUuid(String uuid) {
		Uuid = uuid;
	}

}
