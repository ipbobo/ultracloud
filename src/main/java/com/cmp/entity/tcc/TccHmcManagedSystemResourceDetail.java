package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

/**
 * 物理小机资源详情
 */
public class TccHmcManagedSystemResourceDetail implements Serializable {

	private static final long serialVersionUID = -1033924849889841301L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 所属物理机Id
	 */

	private Long hmcManagedSystemId;

	/**
	 * 激活的处理器核数
	 */

	private Double configurableSysCpu;

	/**
	 * 当前可用的处理器核数
	 */

	private Double currAvailCpu;

	/**
	 * 当前已使用的处理器核数
	 * currUsedCpu = configurableSysCpu - currAvailCpu
	 */

	private Double currUsedCpu;

	/**
	 * 机器上安装的处理器核数， 有可能安装了16个，只买了8个的license， 所以只有8个激活的能用
	 */

	private Double installedSysCpu;

	/**
	 * 激活的内存
	 */

	private Double configurableSysMem;

	/**
	 * 当前可用的内存
	 */

	private Double currAvailMem;

	/**
	 * 当前已使用的内存
	 * currUsedMem = configurableSysMem - currAvailMem
	 */

	private Double currUsedMem;

	/**
	 * 安装的内存
	 */

	private Double installedSysMem;

	/**
	 * 资源使用情况采集时间
	 */

	private Date collectTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHmcManagedSystemId() {
		return hmcManagedSystemId;
	}

	public void setHmcManagedSystemId(Long hmcManagedSystemId) {
		this.hmcManagedSystemId = hmcManagedSystemId;
	}

	public Double getConfigurableSysCpu() {
		return configurableSysCpu;
	}

	public void setConfigurableSysCpu(Double configurableSysCpu) {
		this.configurableSysCpu = configurableSysCpu;
	}

	public Double getCurrAvailCpu() {
		return currAvailCpu;
	}

	public void setCurrAvailCpu(Double currAvailCpu) {
		this.currAvailCpu = currAvailCpu;
	}

	public Double getCurrUsedCpu() {
		return currUsedCpu;
	}

	public void setCurrUsedCpu(Double currUsedCpu) {
		this.currUsedCpu = currUsedCpu;
	}

	public Double getInstalledSysCpu() {
		return installedSysCpu;
	}

	public void setInstalledSysCpu(Double installedSysCpu) {
		this.installedSysCpu = installedSysCpu;
	}

	public Double getConfigurableSysMem() {
		return configurableSysMem;
	}

	public void setConfigurableSysMem(Double configurableSysMem) {
		this.configurableSysMem = configurableSysMem;
	}

	public Double getCurrAvailMem() {
		return currAvailMem;
	}

	public void setCurrAvailMem(Double currAvailMem) {
		this.currAvailMem = currAvailMem;
	}

	public Double getCurrUsedMem() {
		return currUsedMem;
	}

	public void setCurrUsedMem(Double currUsedMem) {
		this.currUsedMem = currUsedMem;
	}

	public Double getInstalledSysMem() {
		return installedSysMem;
	}

	public void setInstalledSysMem(Double installedSysMem) {
		this.installedSysMem = installedSysMem;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

}
