package com.cmp.entity.tcc;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TccPhyCapacity implements java.io.Serializable {

	private static final long serialVersionUID = 3299921557828833348L;

	private Long id;

	private TccPhyCapacity parent;

	private String evnName;

	private String vcType;

	private String deviceType;

	private String deviceTypeName;

	private String vcTypeName;

	private double cpuCount;

	private double memCount;

	private Long num;

	private double sanStorage;

	private double nasStorage;

	private Double totailStorage;

	private double freeCpuCount;

	private double freeMemCount;

	private double freeSanStorage;

	private double freeNasStorage;

	private double freeStorage;

	private double useCpuCount;

	private double useMemCount;

	private double useSanStorage;

	private double useNasStorage;

	private double useStorage;

	private String envCode;

	private Long parentId;

	private long crtUserId;

	private Date crtDttm;

	private long lastuptUserId;

	private String lastUpName;

	private Date lastuptDttm;

	private String enableFlg;

	private Set<TccPhyCapacity> TccPhyCapacities = new HashSet<>(0);

	private Long orgId;

	private Long cloudPlatformId;

	private String cloudPlatformName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TccPhyCapacity getParent() {
		return parent;
	}

	public void setParent(TccPhyCapacity parent) {
		this.parent = parent;
	}

	public String getEvnName() {
		return evnName;
	}

	public void setEvnName(String evnName) {
		this.evnName = evnName;
	}

	public String getVcType() {
		return vcType;
	}

	public void setVcType(String vcType) {
		this.vcType = vcType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getVcTypeName() {
		return vcTypeName;
	}

	public void setVcTypeName(String vcTypeName) {
		this.vcTypeName = vcTypeName;
	}

	public double getCpuCount() {
		return cpuCount;
	}

	public void setCpuCount(double cpuCount) {
		this.cpuCount = cpuCount;
	}

	public double getMemCount() {
		return memCount;
	}

	public void setMemCount(double memCount) {
		this.memCount = memCount;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public double getSanStorage() {
		return sanStorage;
	}

	public void setSanStorage(double sanStorage) {
		this.sanStorage = sanStorage;
	}

	public double getNasStorage() {
		return nasStorage;
	}

	public void setNasStorage(double nasStorage) {
		this.nasStorage = nasStorage;
	}

	public Double getTotailStorage() {
		return totailStorage;
	}

	public void setTotailStorage(Double totailStorage) {
		this.totailStorage = totailStorage;
	}

	public double getFreeCpuCount() {
		return freeCpuCount;
	}

	public void setFreeCpuCount(double freeCpuCount) {
		this.freeCpuCount = freeCpuCount;
	}

	public double getFreeMemCount() {
		return freeMemCount;
	}

	public void setFreeMemCount(double freeMemCount) {
		this.freeMemCount = freeMemCount;
	}

	public double getFreeSanStorage() {
		return freeSanStorage;
	}

	public void setFreeSanStorage(double freeSanStorage) {
		this.freeSanStorage = freeSanStorage;
	}

	public double getFreeNasStorage() {
		return freeNasStorage;
	}

	public void setFreeNasStorage(double freeNasStorage) {
		this.freeNasStorage = freeNasStorage;
	}

	public double getFreeStorage() {
		return freeStorage;
	}

	public void setFreeStorage(double freeStorage) {
		this.freeStorage = freeStorage;
	}

	public double getUseCpuCount() {
		return useCpuCount;
	}

	public void setUseCpuCount(double useCpuCount) {
		this.useCpuCount = useCpuCount;
	}

	public double getUseMemCount() {
		return useMemCount;
	}

	public void setUseMemCount(double useMemCount) {
		this.useMemCount = useMemCount;
	}

	public double getUseSanStorage() {
		return useSanStorage;
	}

	public void setUseSanStorage(double useSanStorage) {
		this.useSanStorage = useSanStorage;
	}

	public double getUseNasStorage() {
		return useNasStorage;
	}

	public void setUseNasStorage(double useNasStorage) {
		this.useNasStorage = useNasStorage;
	}

	public double getUseStorage() {
		return useStorage;
	}

	public void setUseStorage(double useStorage) {
		this.useStorage = useStorage;
	}

	public String getEnvCode() {
		return envCode;
	}

	public void setEnvCode(String envCode) {
		this.envCode = envCode;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public long getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}

	public long getLastuptUserId() {
		return lastuptUserId;
	}

	public void setLastuptUserId(long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public String getLastUpName() {
		return lastUpName;
	}

	public void setLastUpName(String lastUpName) {
		this.lastUpName = lastUpName;
	}

	public Date getLastuptDttm() {
		return lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Set<TccPhyCapacity> getTccPhyCapacities() {
		return TccPhyCapacities;
	}

	public void setTccPhyCapacities(Set<TccPhyCapacity> tccPhyCapacities) {
		TccPhyCapacities = tccPhyCapacities;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCloudPlatformId() {
		return cloudPlatformId;
	}

	public void setCloudPlatformId(Long cloudPlatformId) {
		this.cloudPlatformId = cloudPlatformId;
	}

	public String getCloudPlatformName() {
		return cloudPlatformName;
	}

	public void setCloudPlatformName(String cloudPlatformName) {
		this.cloudPlatformName = cloudPlatformName;
	}

}
