package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;

public class TccNetwork implements Serializable {

	private static final long serialVersionUID = 4830920316790373592L;

	private Long id;

	private Long zoneId;

	private String uuid;

	private String networkName;

	private String gateWay;

	private String netMark;

	private Integer netOffId;

	private Integer state;

	private Long sysId;

	private Integer netType;

	private String cipr;

	private String account;

	private Long domainId;

	private Long crtUserId;

	private Date crtDttm;

	private Long lastuptUserId;

	private Date lastUptDttm;

	private String enableFlg;

	private String zoneUuid;

	/** 网络子网绑定的路由器ID */

	private String routerId;

	/**
	 * 资源平台Id
	 */

	private Long cloudPlatformId;

	/**
	 * 子网掩码
	 */

	private String	subnetMask;
	/**
	 * DNS
	 */

	private String	dns;

	/**
	 * 网络区域Id
	 */

	private Integer networkAreaId;

	/**
	 * 创建人名字用于页面显示
	 */

	private String crtName;

	/**
	 * 资源池名称用户页面显示
	 */

	private String cloudPlatformName;

	/**
	 * IP管理平台类别区分
	 */

	private String cloudPlatformType;

	/**
	 * 开始IP
	 */

	private String startIp;

	/**
	 * 结束IP
	 */

	private String endIp;

	private String ipRange; // IP段

	private String networkTag; // 网络标签

	private String networkstatus;

	private String subnetName;

	private String dhcp;

	// 路由器名称

	private String routerName;

	/**
	 * 是否是外网
	 */

	private Integer external;

	/**
	 * 是否共享
	 */

	private Integer shared;

	/**
	 * 网络状态 active 。。。。。四种状态
	 */

	private String	status;
	/**
	 * 网络类型 vlan 和 loca
	 */

	private String	netWorkType;

	/**
	 * 物理网络
	 */

	private String physicsNetWork;

	private Integer vlanId;

	public Integer getShared() {
		return shared;
	}

	public void setShared(Integer shared) {
		this.shared = shared;
	}

	public String getPhysicsNetWork() {
		return physicsNetWork;
	}

	public void setPhysicsNetWork(String physicsNetWork) {
		this.physicsNetWork = physicsNetWork;
	}

	public Integer getVlanId() {
		return vlanId;
	}

	public void setVlanId(Integer vlanId) {
		this.vlanId = vlanId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNetWorkType() {
		return netWorkType;
	}

	public void setNetWorkType(String netWorkType) {
		this.netWorkType = netWorkType;
	}

	public TccNetwork() {
		super();
	}

	public TccNetwork(Long zoneId, String networkName, String zoneUuid) {
		super();
		this.zoneId = zoneId;
		this.networkName = networkName;
		this.zoneUuid = zoneUuid;
	}

	public String getNetworkTag() {
		return networkTag;
	}

	public Integer getExternal() {
		return external;
	}

	public void setExternal(Integer external) {
		this.external = external;
	}

	public void setNetworkTag(String networkTag) {
		this.networkTag = networkTag;
	}

	public String getIpRange() {
		return ipRange;
	}

	public void setIpRange(String ipRange) {
		this.ipRange = ipRange;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getGateWay() {
		return gateWay;
	}

	public void setGateWay(String gateWay) {
		this.gateWay = gateWay;
	}

	public String getNetMark() {
		return netMark;
	}

	public void setNetMark(String netMark) {
		this.netMark = netMark;
	}

	public Integer getNetOffId() {
		return netOffId;
	}

//	public String getNetworkArea() {
//		return networkAreaId != null
//				? BusinessEnvironment.OFFI_NETTYPE_INVERT_MAP.get(networkAreaId.toString()) : null;
//	}

	public void setNetOffId(Integer netOffId) {
		this.netOffId = netOffId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getNetType() {
		return netType;
	}

	public void setNetType(Integer netType) {
		this.netType = netType;
	}

	public String getCipr() {
		return cipr;
	}

	public void setCipr(String cipr) {
		this.cipr = cipr;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
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

	public Date getLastUptDttm() {
		return lastUptDttm;
	}

	public void setLastUptDttm(Date lastUptDttm) {
		this.lastUptDttm = lastUptDttm;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public String getZoneUuid() {
		return zoneUuid;
	}

	public void setZoneUuid(String zoneUuid) {
		this.zoneUuid = zoneUuid;
	}

	public Long getCloudPlatformId() {
		return cloudPlatformId;
	}

	public void setCloudPlatformId(Long cloudPlatformId) {
		this.cloudPlatformId = cloudPlatformId;
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

	public String getDns() {
		return dns;
	}

	public void setDns(String dns) {
		this.dns = dns;
	}

	public String getCrtName() {
		return crtName;
	}

	public void setCrtName(String crtName) {
		this.crtName = crtName;
	}

	public String getCloudPlatformName() {
		return cloudPlatformName;
	}

	public void setCloudPlatformName(String cloudPlatformName) {
		this.cloudPlatformName = cloudPlatformName;
	}

	public String getCloudPlatformType() {
		return cloudPlatformType;
	}

	public void setCloudPlatformType(String cloudPlatformType) {
		this.cloudPlatformType = cloudPlatformType;
	}

	public Integer getNetworkAreaId() {
		return networkAreaId;
	}

	public void setNetworkAreaId(Integer networkAreaId) {
		this.networkAreaId = networkAreaId;
	}

	public String getStartIp() {
		return startIp;
	}

	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}

	public String getEndIp() {
		return endIp;
	}

	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	public String getNetworkstatus() {
		return networkstatus;
	}

	public void setNetworkstatus(String networkstatus) {
		this.networkstatus = networkstatus;
	}

	public String getSubnetName() {
		return subnetName;
	}

	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}

	public String getDhcp() {
		return dhcp;
	}

	public void setDhcp(String dhcp) {
		this.dhcp = dhcp;
	}

	public String getRouterId() {
		return routerId;
	}

	public void setRouterId(String routerId) {
		this.routerId = routerId;
	}

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

}
