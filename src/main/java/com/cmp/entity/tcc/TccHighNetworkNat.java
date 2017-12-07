package com.cmp.entity.tcc;

import java.io.Serializable;

public class TccHighNetworkNat implements Serializable {

	private static final long serialVersionUID = -6865050324838578289L;

	private Long id;

	private String vmId;
	// 共有IP
	private String publicIP;
	// 私有IP
	private String privateIp;

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getPublicIP() {
		return publicIP;
	}

	public void setPublicIP(String publicIP) {
		this.publicIP = publicIP;
	}

	public String getPrivateIp() {
		return privateIp;
	}

	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
