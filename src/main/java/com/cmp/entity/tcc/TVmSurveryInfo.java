package com.cmp.entity.tcc;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TVmSurveryInfo implements java.io.Serializable {

	private static final long serialVersionUID = 4222892158453147901L;

	private Long id;

	private String ip;

	private String hostName;

	private String hostid;

	private String vlanId;

	private String cpuMemory;

	private String cpuTop;

	private String memoryTop;

	private String pcpuFree;

	private String pmemoryFree;

	private String date;

	private String enableFlag;

	private String createUser;

	private Date createDate;

	private String updateUser;

	private Date updateDate;

	private String vfsTop;

	private String pvfsFree;

	// 统计CPU使用率
	private String pcpuUsed;

	// 统计内存使用率
	private String pmemUsed;

	// 统计存储使用率
	private String pvfsUsed;

	// 实施建议类型(1建议减容或释放,2建议扩容)
	private String optimizeType;

	// 是否已进行优化(1是减容优化, 2是扩容优化)
	private String optimizeFlg;

	private Set<TVmSurveryDetail> TVmSurveryDetails = new HashSet<TVmSurveryDetail>(0);

	private TccApplyedHostinfo tccApplyedHostinfo;

	private String registerString;// 监控中，监控失连，未注册，注册失败

	// Constructors

	/** default constructor */
	public TVmSurveryInfo() {
	}

	/** minimal constructor */
	public TVmSurveryInfo(Long id, Date createDate, Date updateDate) {
		this.id = id;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public String getRegisterString() {
		return registerString;
	}

	public void setRegisterString(String registerString) {
		this.registerString = registerString;
	}

	/** full constructor */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TVmSurveryInfo(Long id, /* Long vmId, */String ip, String hostName,
			String hostid, String vlanId, String cpuMemory, String cpuTop,
			String memoryTop, String pcpuFree, String pmemoryFree, String date,
			String enableFlag, String createUser, Date createDate,
			String updateUser, Date updateDate, String vfsTop, String pvfsFree,
			Set TVmSurveryDetails) {
		this.id = id;
		// this.vmId = vmId;
		this.ip = ip;
		this.hostName = hostName;
		this.hostid = hostid;
		this.vlanId = vlanId;
		this.cpuMemory = cpuMemory;
		this.cpuTop = cpuTop;
		this.memoryTop = memoryTop;
		this.pcpuFree = pcpuFree;
		this.pmemoryFree = pmemoryFree;
		this.date = date;
		this.enableFlag = enableFlag;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
		this.vfsTop = vfsTop;
		this.pvfsFree = pvfsFree;
		this.TVmSurveryDetails = TVmSurveryDetails;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public TccApplyedHostinfo getTccApplyedHostinfo() {
		return tccApplyedHostinfo;
	}

	public void setTccApplyedHostinfo(TccApplyedHostinfo tccApplyedHostinfo) {
		this.tccApplyedHostinfo = tccApplyedHostinfo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * public Long getVmId() { return this.vmId; }
	 * 
	 * public void setVmId(Long vmId) { this.vmId = vmId; }
	 */
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostid() {
		return this.hostid;
	}

	public void setHostid(String hostid) {
		this.hostid = hostid;
	}

	public String getVlanId() {
		return this.vlanId;
	}

	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}

	public String getCpuMemory() {
		return this.cpuMemory;
	}

	public void setCpuMemory(String cpuMemory) {
		this.cpuMemory = cpuMemory;
	}

	public String getCpuTop() {
		return this.cpuTop;
	}

	public void setCpuTop(String cpuTop) {
		this.cpuTop = cpuTop;
	}

	public String getMemoryTop() {
		return this.memoryTop;
	}

	public void setMemoryTop(String memoryTop) {
		this.memoryTop = memoryTop;
	}

	public String getPcpuFree() {
		return this.pcpuFree;
	}

	public void setPcpuFree(String pcpuFree) {
		this.pcpuFree = pcpuFree;
	}

	public String getPmemoryFree() {
		return this.pmemoryFree;
	}

	public void setPmemoryFree(String pmemoryFree) {
		this.pmemoryFree = pmemoryFree;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEnableFlag() {
		return this.enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getVfsTop() {
		return this.vfsTop;
	}

	public void setVfsTop(String vfsTop) {
		this.vfsTop = vfsTop;
	}

	public String getPvfsFree() {
		return this.pvfsFree;
	}

	public void setPvfsFree(String pvfsFree) {
		this.pvfsFree = pvfsFree;
	}

	@SuppressWarnings("rawtypes")
	public Set getTVmSurveryDetails() {
		return this.TVmSurveryDetails;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setTVmSurveryDetails(Set TVmSurveryDetails) {
		this.TVmSurveryDetails = TVmSurveryDetails;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOptimizeType() {
		return optimizeType;
	}

	public void setOptimizeType(String optimizeType) {
		this.optimizeType = optimizeType;
	}

	public String getPcpuUsed() {
		return pcpuUsed;
	}

	public void setPcpuUsed(String pcpuUsed) {
		this.pcpuUsed = pcpuUsed;
	}

	public String getPmemUsed() {
		return pmemUsed;
	}

	public void setPmemUsed(String pmemUsed) {
		this.pmemUsed = pmemUsed;
	}

	public String getPvfsUsed() {
		return pvfsUsed;
	}

	public void setPvfsUsed(String pvfsUsed) {
		this.pvfsUsed = pvfsUsed;
	}

	public String getOptimizeFlg() {
		return optimizeFlg;
	}

	public void setOptimizeFlg(String optimizeFlg) {
		this.optimizeFlg = optimizeFlg;
	}

}
