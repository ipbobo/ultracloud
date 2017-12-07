package com.cmp.entity.tcc;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TccStorageDevide implements Serializable {

	private static final long serialVersionUID = -8884122891357774106L;

	private Long	syncId;
	private String	stype;
	private Long	sizee;
	private Long	sizezj;
	private Long	sizetl;
	private Date	indate;

	private String	todevelopeflag;
	private String	tocompanyflag;

	private Long	developesize;
	private Long	testsize;

	private Long	developesizezj;
	private Long	developesizetl;
	private Long	testsizezj;
	private Long	testsizetl;

	private Long	groupsize;
	private Long	propertysize;
	private Long	lifesize;
	private Long	assetsize;

	private Long	groupsizezj;
	private Long	groupsizetl;

	private Long	propertysizezj;
	private Long	propertysizetl;

	private Long	lifesizezj;
	private Long	lifesizetl;

	private Long						assetsizezj;
	private Long						assetsizetl;
	private Set<TccStorageAddRecord>	tccStorageAddRecords	= new HashSet<TccStorageAddRecord>();

	public Long getSyncId() {
		return syncId;
	}

	public void setSyncId(Long syncId) {
		this.syncId = syncId;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public Long getSizee() {
		return sizee;
	}

	public void setSizee(Long sizee) {
		this.sizee = sizee;
	}

	public Long getSizezj() {
		return sizezj;
	}

	public void setSizezj(Long sizezj) {
		this.sizezj = sizezj;
	}

	public Long getSizetl() {
		return sizetl;
	}

	public void setSizetl(Long sizetl) {
		this.sizetl = sizetl;
	}

	public String getTodevelopeflag() {
		return todevelopeflag;
	}

	public void setTodevelopeflag(String todevelopeflag) {
		this.todevelopeflag = todevelopeflag;
	}

	public String getTocompanyflag() {
		return tocompanyflag;
	}

	public void setTocompanyflag(String tocompanyflag) {
		this.tocompanyflag = tocompanyflag;
	}

	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public Long getDevelopesizezj() {
		return developesizezj;
	}

	public void setDevelopesizezj(Long developesizezj) {
		this.developesizezj = developesizezj;
	}

	public Long getTestsizezj() {
		return testsizezj;
	}

	public void setTestsizezj(Long testsizezj) {
		this.testsizezj = testsizezj;
	}

	public Long getGroupsizezj() {
		return groupsizezj;
	}

	public void setGroupsizezj(Long groupsizezj) {
		this.groupsizezj = groupsizezj;
	}

	public Long getPropertysizezj() {
		return propertysizezj;
	}

	public void setPropertysizezj(Long propertysizezj) {
		this.propertysizezj = propertysizezj;
	}

	public Long getLifesizezj() {
		return lifesizezj;
	}

	public void setLifesizezj(Long lifesizezj) {
		this.lifesizezj = lifesizezj;
	}

	public Long getAssetsizezj() {
		return assetsizezj;
	}

	public void setAssetsizezj(Long assetsizezj) {
		this.assetsizezj = assetsizezj;
	}

	public Long getDevelopesizetl() {
		return developesizetl;
	}

	public void setDevelopesizetl(Long developesizetl) {
		this.developesizetl = developesizetl;
	}

	public Long getTestsizetl() {
		return testsizetl;
	}

	public void setTestsizetl(Long testsizetl) {
		this.testsizetl = testsizetl;
	}

	public Long getGroupsizetl() {
		return groupsizetl;
	}

	public void setGroupsizetl(Long groupsizetl) {
		this.groupsizetl = groupsizetl;
	}

	public Long getPropertysizetl() {
		return propertysizetl;
	}

	public void setPropertysizetl(Long propertysizetl) {
		this.propertysizetl = propertysizetl;
	}

	public Long getLifesizetl() {
		return lifesizetl;
	}

	public void setLifesizetl(Long lifesizetl) {
		this.lifesizetl = lifesizetl;
	}

	public Long getAssetsizetl() {
		return assetsizetl;
	}

	public void setAssetsizetl(Long assetsizetl) {
		this.assetsizetl = assetsizetl;
	}

	public Set<TccStorageAddRecord> getTccStorageAddRecords() {
		return tccStorageAddRecords;
	}

	public void setTccStorageAddRecords(
			Set<TccStorageAddRecord> tccStorageAddRecords) {
		this.tccStorageAddRecords = tccStorageAddRecords;
	}

	public Long getDevelopesize() {
		return developesize;
	}

	public void setDevelopesize(Long developesize) {
		this.developesize = developesize;
	}

	public Long getTestsize() {
		return testsize;
	}

	public void setTestsize(Long testsize) {
		this.testsize = testsize;
	}

	public Long getGroupsize() {
		return groupsize;
	}

	public void setGroupsize(Long groupsize) {
		this.groupsize = groupsize;
	}

	public Long getPropertysize() {
		return propertysize;
	}

	public void setPropertysize(Long propertysize) {
		this.propertysize = propertysize;
	}

	public Long getLifesize() {
		return lifesize;
	}

	public void setLifesize(Long lifesize) {
		this.lifesize = lifesize;
	}

	public Long getAssetsize() {
		return assetsize;
	}

	public void setAssetsize(Long assetsize) {
		this.assetsize = assetsize;
	}

}
