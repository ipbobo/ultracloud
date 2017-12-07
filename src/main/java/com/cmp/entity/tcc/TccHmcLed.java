package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

/**
 * 被管理物理机及其LPAR LED指示灯情况
 */

public class TccHmcLed implements Serializable {

	private static final long serialVersionUID = 1131635490627737359L;

	/**
	 * 主键Id
	 */

	private Long id;

	/**
	 * 分区ID
	 */

	private String lparId;

	/**
	 * 分区名称
	 */

	private String lparName;

	/**
	 * led信号灯状态
	 * 0：off
	 * 1：on，开启说明有问题存在
	 */

	private String state;

	/**
	 * 查询类型
	 * -t phys | virtuallpar | virtualsys
	 */

	private String type;

	/**
	 * 各LED归属服务器
	 */

	private TccHmcManagedSystem hmcManagedSystem;

	/**
	 * 删除标识
	 * 0删除
	 * 1可用
	 */

	private String enableFlg;

	/**
	 * 创建人
	 */

	private Long crtUserId;

	/**
	 * 创建时间
	 */

	private Date crtDttm;

	/**
	 * 修改人
	 */

	private Long lastuptUserId;

	/**
	 * 修改时间
	 */

	private Date lastuptDttm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLparId() {
		return lparId;
	}

	public void setLparId(String lparId) {
		this.lparId = lparId;
	}

	public String getLparName() {
		return lparName;
	}

	public void setLparName(String lparName) {
		this.lparName = lparName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TccHmcManagedSystem getHmcManagedSystem() {
		return hmcManagedSystem;
	}

	public void setHmcManagedSystem(TccHmcManagedSystem hmcManagedSystem) {
		this.hmcManagedSystem = hmcManagedSystem;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Long getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Long getLastuptUserId() {
		return lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public Date getLastuptDttm() {
		return lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

}
