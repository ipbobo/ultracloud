package com.cmp.entity.tcc;

import java.util.Date;

public class TccsrtEmployeeAndRoleVo {

	private String	srtStatusCd;		// 工单状态
	private Long	crtUserId;			// 工单创建者编号
	private Long	srtId;				// 工单编号
	private Date	crtDttm;			// 工单创建日期
	private String	actTypeCd;			// 工单类型
	private String	crtUserName;		// 工单创建用户名称
	private Long	roleId;				// 用户类型编号
	private String	roleName;			// 用户类型
	private String	hostName;			// 虚拟机名称
	private String	roleTypeCd;			// 用户类型码
	private Date	lastUpdateDate;		// 工单上次修改时间
	private String	applyResourceId;	// 工单资源编号
	private String	IPAddress;			// IP地址
	private String	OSName;				// 镜像名称
	private String	ramSize;			// 内存大小
	private String	CPUCoreCount;		// CPU核心数
	private String	storageSize;		// 存储大小
	private String	runStatusCD;		// 运行状态码
	private String	physicsIp;			// 物理机IP
	private String	physicsName;		// 物理机名称
	private String	CPUCoreTotalCount;	// 物理机cpu总核心数
	private String	RAMTotalSize;		// 物理机总内存大小

	public String getSrtStatusCd() {
		return srtStatusCd;
	}

	public void setSrtStatusCd(String srtStatusCd) {
		if ("21".equals(srtStatusCd)) {
			this.srtStatusCd = "正在退回";
		} else if ("01".equals(srtStatusCd)) {
			this.srtStatusCd = "处理中";
		} else if ("02".equals(srtStatusCd)) {
			this.srtStatusCd = "完成";
		} else if ("03".equals(srtStatusCd)) {
			this.srtStatusCd = "取消";
		} else if ("04".equals(srtStatusCd)) {
			this.srtStatusCd = "关闭";
		}
	}

	public Long getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Long getSrtId() {
		return srtId;
	}

	public void setSrtId(Long srtId) {
		this.srtId = srtId;
	}

	public Date getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public String getActTypeCd() {
		return actTypeCd;
	}

	public void setActTypeCd(String actTypeCd) {
		if ("01".equals(actTypeCd)) {
			this.actTypeCd = "申请资源工单";
		} else if ("02".equals(actTypeCd)) {
			this.actTypeCd = "永久释放工单";
		} else if ("03".equals(actTypeCd)) {
			this.actTypeCd = "扩容工单";
		} else if ("04".equals(actTypeCd)) {
			this.actTypeCd = "临时释放工单";
		} else if ("05".equals(actTypeCd)) {
			this.actTypeCd = "资源恢复工单";
		} else if ("09".equals(actTypeCd)) {
			this.actTypeCd = "克隆工单";
		} else if ("10".equals(actTypeCd)) {
			this.actTypeCd = "改期工单";
		} else if ("11".equals(actTypeCd)) {
			this.actTypeCd = "运维服务工单";
		} else if ("12".equals(actTypeCd)) {
			this.actTypeCd = "故障处理工单";
		} else if ("06".equals(actTypeCd)) {
			this.actTypeCd = "X86自服务工单";
		} else if ("07".equals(actTypeCd)) {
			this.actTypeCd = "申请压测工单";
		} else if ("08".equals(actTypeCd)) {
			this.actTypeCd = "预约压测工单";
		} else if ("13".equals(actTypeCd)) {
			this.actTypeCd = "创建镜像工单";
		} else if ("14".equals(actTypeCd)) {
			this.actTypeCd = "租户配额申请工单";
		} else if ("15".equals(actTypeCd)) {
			this.actTypeCd = "租户申请资源工单";
		}

	}

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleTypeCd() {
		return roleTypeCd;
	}

	public void setRoleTypeCd(String roleTypeCd) {
		this.roleTypeCd = roleTypeCd;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getApplyResourceId() {
		return applyResourceId;
	}

	public void setApplyResourceId(String applyResourceId) {
		this.applyResourceId = applyResourceId;
	}

	public String getIPAddress() {
		return IPAddress;
	}

	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}

	public String getOSName() {
		return OSName;
	}

	public void setOSName(String oSName) {
		OSName = oSName;
	}

	public String getRamSize() {
		return ramSize;
	}

	public void setRamSize(String ramSize) {
		this.ramSize = ramSize;
	}

	public String getCPUCoreCount() {
		return CPUCoreCount;
	}

	public void setCPUCoreCount(String cPUCoreCount) {
		CPUCoreCount = cPUCoreCount;
	}

	public String getStorageSize() {
		return storageSize;
	}

	public void setStorageSize(String storageSize) {
		this.storageSize = storageSize;
	}

	public String getRunStatusCD() {
		return runStatusCD;
	}

	public void setRunStatusCD(String runStatusCD) {
		this.runStatusCD = runStatusCD;
	}

	public String getPhysicsIp() {
		return physicsIp;
	}

	public void setPhysicsIp(String physicsIp) {
		this.physicsIp = physicsIp;
	}

	public String getPhysicsName() {
		return physicsName;
	}

	public void setPhysicsName(String physicsName) {
		this.physicsName = physicsName;
	}

	public String getCPUCoreTotalCount() {
		return CPUCoreTotalCount;
	}

	public void setCPUCoreTotalCount(String cPUCoreTotalCount) {
		String[] arr = cPUCoreTotalCount.split("\\.");
		if (null != arr && arr.length > 0) {
			this.CPUCoreTotalCount = arr[0];
		} else {
			this.CPUCoreTotalCount = cPUCoreTotalCount;
		}
	}

	public String getRAMTotalSize() {
		return RAMTotalSize;
	}

	public void setRAMTotalSize(String rAMTotalSize) {
		if (rAMTotalSize.indexOf("\\.") != -1 && null != rAMTotalSize.split("\\.")
				&& rAMTotalSize.split("\\.").length > 0) {
			String subStr = rAMTotalSize.substring(0, (rAMTotalSize.split("\\.")[0].length() + 3));
			this.RAMTotalSize = subStr;
		} else {
			this.RAMTotalSize = rAMTotalSize;
		}
	}

}
