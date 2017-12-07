package com.cmp.entity.tcc;

import java.io.Serializable;

public class TccExtranet implements Serializable {

	/**
	* 
	*/
	private static final long	serialVersionUID	= 1927908508592481623L;
	/**
	 * 外网id，内部维护
	 */

	private Long				id;

	/**
	 * 外网id，创建成功返回
	 */

	private String	uuidStr;
	/**
	 * 网络名称
	 */

	private String	extranetName;
	/**
	 * ip版本 4或者6
	 */

	private Integer	ipVersion;

	/**
	 * 所属网络
	 */
	private TccVlanNetwork network;

	/**
	 * 合法的CIDR格式
	 */

	private String	cidr;
	/**
	 * 网关ip
	 */

	private String	gatewayIp;

	/**
	 * 租户id
	 */

	private String	tenantId;
	/**
	 * 是否启动dhcp
	 */

	private Boolean	enableDhcp;

	/**
	 * IPv6地址选项
	 */

	private String	ipv6RaMode;
	/**
	 * IPv6地址选项
	 */

	private String	ipv6AddressMode;

	public String getIpv6RaMode() {
		return ipv6RaMode;
	}

	public void setIpv6RaMode(String ipv6RaMode) {
		this.ipv6RaMode = ipv6RaMode;
	}

	public String getIpv6AddressMode() {
		return ipv6AddressMode;
	}

	public void setIpv6AddressMode(String ipv6AddressMode) {
		this.ipv6AddressMode = ipv6AddressMode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuidStr() {
		return uuidStr;
	}

	public void setUuidStr(String uuidStr) {
		this.uuidStr = uuidStr;
	}

	public String getExtranetName() {
		return extranetName;
	}

	public void setExtranetName(String extranetName) {
		this.extranetName = extranetName;
	}

	public Integer getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(Integer ipVersion) {
		this.ipVersion = ipVersion;
	}

	public TccVlanNetwork getNetwork() {
		return network;
	}

	public void setNetwork(TccVlanNetwork network) {
		this.network = network;
	}

	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public String getGatewayIp() {
		return gatewayIp;
	}

	public void setGatewayIp(String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Boolean getEnableDhcp() {
		return enableDhcp;
	}

	public void setEnableDhcp(Boolean enableDhcp) {
		this.enableDhcp = enableDhcp;
	}

}
