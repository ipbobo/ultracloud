package com.cmp.entity.tcc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TccDatacenter implements java.io.Serializable {

	private static final long serialVersionUID = -6328782863355405884L;

	private String name;

	private Long zoneId; // 区域id

	private String zoneName;// 区域名称

	private String uuid; // cloudstack平台自动生成为zone生成的id号

	private String zoneDesc;// 区域描述

	private String status; // 区域状态

	private String memo; // 区域备注

	private Long crtUserId; // 创建用户id

	private Date crtDttm; // 创建时间

	private Date lastuptDttm;// 最后修改时间

	private Long lastuptUserId;// 最后修改人

	private String internetDns1;// 外网dns1

	private String internetDns2;// 外网dns2

	private String innerDns1; // 内网dns1

	private String innerDns2; // 内网dns2

	private String domain; // 区域所属域

	private String networkType; // 区域所使用的网络类型

	private String hypervisorType;// 区域采用的虚拟化类型

	private String enableFlg; // 逻辑删除标识符号

	private TccCloudPlatform cloudplatform;

	private Set<TccCluster> tccCluster = new HashSet<TccCluster>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<TccCluster> getTccCluster() {
		return tccCluster;
	}

	public void setTccCluster(Set<TccCluster> tccCluster) {
		this.tccCluster = tccCluster;
	}

	public TccCloudPlatform getCloudplatform() {
		return cloudplatform;
	}

	public void setCloudplatform(TccCloudPlatform cloudplatform) {
		this.cloudplatform = cloudplatform;
	}

	public Long getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return this.zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getZoneDesc() {
		return this.zoneDesc;
	}

	public void setZoneDesc(String zoneDesc) {
		this.zoneDesc = zoneDesc;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getCrtUserId() {
		return this.crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtDttm() {
		return this.crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public Date getLastuptDttm() {
		return this.lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public Long getLastuptUserId() {
		return this.lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public String getEnableFlg() {
		return this.enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public String getInternetDns1() {
		return this.internetDns1;
	}

	public void setInternetDns1(String internetDns1) {
		this.internetDns1 = internetDns1;
	}

	public String getInternetDns2() {
		return this.internetDns2;
	}

	public void setInternetDns2(String internetDns2) {
		this.internetDns2 = internetDns2;
	}

	public String getInnerDns1() {
		return this.innerDns1;
	}

	public void setInnerDns1(String innerDns1) {
		this.innerDns1 = innerDns1;
	}

	public String getInnerDns2() {
		return this.innerDns2;
	}

	public void setInnerDns2(String innerDns2) {
		this.innerDns2 = innerDns2;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getNetworkType() {
		return this.networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getHypervisorType() {
		return this.hypervisorType;
	}

	public void setHypervisorType(String hypervisorType) {
		this.hypervisorType = hypervisorType;
	}

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");
		map.put("5", "5");
		for (Integer i : list) {
			list.add(list.get(i));
		}
	}
}
