package com.cmp.entity.tcc;

import java.util.Date;

public class TccConfigCase implements java.io.Serializable {

	private static final long serialVersionUID = -6971228789760893163L;

	private Long configCaseId;

	private String configCaseName;

	private Double cpuCoreCount;

	private Double ramSize;

	private String momo;

	private String serviceOfferingId;

	private Long crtUserId;

	private String crtUserName;

	private Date crtDttm;

	private Date lastuptDttm;

	private Long lastuptUserId;

	private String lastuptUserName;

	private String enableFlg;

	private String useageFlage;

	private String useageFlageName;

	private String customFlag;

	private String cpuHz;

	private String platformId;

	private String platformName;

	private Long isRecommend;

	private String recommendName;

	private Long recommendSortNum;

	private String zoneid;

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getZoneid() {
		return zoneid;
	}

	public void setZoneid(String zoneid) {
		this.zoneid = zoneid;
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

	public Date getLastuptDttm() {
		return lastuptDttm;
	}

	public void setLastuptDttm(Date lastuptDttm) {
		this.lastuptDttm = lastuptDttm;
	}

	public Long getLastuptUserId() {
		return lastuptUserId;
	}

	public void setLastuptUserId(Long lastuptUserId) {
		this.lastuptUserId = lastuptUserId;
	}

	public String getEnableFlg() {
		return enableFlg;
	}

	public void setEnableFlg(String enableFlg) {
		this.enableFlg = enableFlg;
	}

	public Long getConfigCaseId() {
		return this.configCaseId;
	}

	public void setConfigCaseId(Long configCaseId) {
		this.configCaseId = configCaseId;
	}

	public String getConfigCaseName() {
		return this.configCaseName;
	}

	public void setConfigCaseName(String configCaseName) {
		this.configCaseName = configCaseName;
	}

	public Double getCpuCoreCount() {
		return this.cpuCoreCount;
	}

	public void setCpuCoreCount(Double cpuCoreCount) {
		this.cpuCoreCount = cpuCoreCount;
	}

	public Double getRamSize() {
		return this.ramSize;
	}

	public void setRamSize(Double ramSize) {
		this.ramSize = ramSize;
	}

	public String getMomo() {
		return this.momo;
	}

	public void setMomo(String momo) {
		this.momo = momo;
	}

	public String getServiceOfferingId() {
		return serviceOfferingId;
	}

	public void setServiceOfferingId(String serviceOfferingId) {
		this.serviceOfferingId = serviceOfferingId;
	}

	public String getUseageFlage() {
		return useageFlage;
	}

	public void setUseageFlage(String useageFlage) {
		this.useageFlage = useageFlage;
	}

	public String getUseageFlageName() {
		return useageFlageName;
	}

	public void setUseageFlageName(String useageFlageName) {
		this.useageFlageName = useageFlageName;
	}

	public String getCustomFlag() {
		return customFlag;
	}

	public void setCustomFlag(String customFlag) {
		this.customFlag = customFlag;
	}

	public String getCpuHz() {
		return cpuHz;
	}

	public void setCpuHz(String cpuHz) {
		this.cpuHz = cpuHz;
	}

	public Long getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Long isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public Long getRecommendSortNum() {
		return recommendSortNum;
	}

	public void setRecommendSortNum(Long recommendSortNum) {
		this.recommendSortNum = recommendSortNum;
	}

	public void setRamSizeLabel(Double ramSizeLabel) {
	}

	public Double getRamSizeLabel() {
		if (getRamSize() != null) {
			long l1 = Math.round(getRamSize() / 1024 * 100); // 四舍五入
			double ret = l1 / 100.00;
			return ret;
		}
		return 0d;
	}

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public String getLastuptUserName() {
		return lastuptUserName;
	}

	public void setLastuptUserName(String lastuptUserName) {
		this.lastuptUserName = lastuptUserName;
	}

}
