package com.cmp.entity.tcc;

public class TccVirtualMachine implements java.io.Serializable {

	private static final long serialVersionUID = 7988690078199521573L;

	private int		id;
	private long	maxMemory;
	private int		maxVcpus;
	private String	name;
	private String	OSType;
	private String	UUID;
	private String	xmlDesc;
	private int		active;
	private String	state;
	private int		persistent;
	private String	domainInfo;
	private String	memoryStatistic;
	private String	cpuInfo;
	private String	ipAddress;
	private String	xmlDesc0;
	private String	xmlDesc2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}

	public int getMaxVcpus() {
		return maxVcpus;
	}

	public void setMaxVcpus(int maxVcpus) {
		this.maxVcpus = maxVcpus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOSType() {
		return OSType;
	}

	public void setOSType(String oSType) {
		OSType = oSType;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getXmlDesc() {
		return xmlDesc;
	}

	public void setXmlDesc(String xmlDesc) {
		this.xmlDesc = xmlDesc;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPersistent() {
		return persistent;
	}

	public void setPersistent(int persistent) {
		this.persistent = persistent;
	}

	public String getDomainInfo() {
		return domainInfo;
	}

	public void setDomainInfo(String domainInfo) {
		this.domainInfo = domainInfo;
	}

	public String getMemoryStatistic() {
		return memoryStatistic;
	}

	public void setMemoryStatistic(String memoryStatistic) {
		this.memoryStatistic = memoryStatistic;
	}

	public String getCpuInfo() {
		return cpuInfo;
	}

	public void setCpuInfo(String cpuInfo) {
		this.cpuInfo = cpuInfo;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getXmlDesc0() {
		return xmlDesc0;
	}

	public void setXmlDesc0(String xmlDesc0) {
		this.xmlDesc0 = xmlDesc0;
	}

	public String getXmlDesc2() {
		return xmlDesc2;
	}

	public void setXmlDesc2(String xmlDesc2) {
		this.xmlDesc2 = xmlDesc2;
	}

}
