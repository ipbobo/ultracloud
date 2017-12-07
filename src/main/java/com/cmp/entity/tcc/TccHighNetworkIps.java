package com.cmp.entity.tcc;

import java.io.Serializable;

public class TccHighNetworkIps implements Serializable {

	private static final long serialVersionUID = -3737622513955056869L;

	private Long id;
	// ip地址的Id
	private String ipAddressId;
	// ip地址
	private String ip;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpAddressId() {
		return ipAddressId;
	}

	public void setIpAddressId(String ipAddressId) {
		this.ipAddressId = ipAddressId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
