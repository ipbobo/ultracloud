package com.cmp.entity.tcc;

public class TccVirtualMachine implements java.io.Serializable {

	private static final long serialVersionUID = 7988690078199521573L;

	private int		id;
	private String	name;
	private String	UUID;
	private long	memory;
	private int		vcpus;
	private String	OSType;
	private String	ipAddress;
	private int		active;
	private String	state;
	private int		persistent;
	private String	xmlDesc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public long getMemory() {
		return memory;
	}

	public void setMemory(long memory) {
		this.memory = memory;
	}

	public int getVcpus() {
		return vcpus;
	}

	public void setVcpus(int vcpus) {
		this.vcpus = vcpus;
	}

	public String getOSType() {
		return OSType;
	}

	public void setOSType(String oSType) {
		OSType = oSType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

	public String getXmlDesc() {
		return xmlDesc;
	}

	public void setXmlDesc(String xmlDesc) {
		this.xmlDesc = xmlDesc;
	}

}
