package com.cmp.entity.tcc;

public class TccExpdCapacity extends TccSrt implements java.io.Serializable {

	private static final long serialVersionUID = -6680339023626415306L;

	/** 实例信息 */

	private Long instanceInfoId;

	/** 中端存储 */

	private Double storeSize;

	/** NAS存储 */

	private Double nasStoreSize;

	/** CPU */

	private Double cpuCoreCount;

	/** RAM */

	private Double	ramSize;
	/** 扩容类型 01.x86 02.小型机 03.数据库实例 */

	private String	expdType;

	/**
	 * GJH新增扩容类型
	 */

	private String proposalType;
	// public Long getSrtId() {
	// return srtId;
	// }
	//
	// public void setSrtId(Long srtId) {
	// this.srtId = srtId;
	// }

	public Long getInstanceInfoId() {
		return instanceInfoId;
	}

	public void setInstanceInfoId(Long instanceInfoId) {
		this.instanceInfoId = instanceInfoId;
	}

	public String getProposalType() {
		return proposalType;
	}

	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
	}

	public Double getStoreSize() {
		return storeSize;
	}

	public void setStoreSize(Double storeSize) {
		this.storeSize = storeSize;
	}

	public Double getNasStoreSize() {
		return nasStoreSize;
	}

	public void setNasStoreSize(Double nasStoreSize) {
		this.nasStoreSize = nasStoreSize;
	}

	public String getExpdType() {
		return expdType;
	}

	public void setExpdType(String expdType) {
		this.expdType = expdType;
	}

	public Double getCpuCoreCount() {
		return cpuCoreCount;
	}

	public void setCpuCoreCount(Double cpuCoreCount) {
		this.cpuCoreCount = cpuCoreCount;
	}

	public Double getRamSize() {
		return ramSize;
	}

	public void setRamSize(Double ramSize) {
		this.ramSize = ramSize;
	}

}
