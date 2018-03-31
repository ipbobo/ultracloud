package com.cmp.sid;

import java.util.Date;

//数据字典
public class CmpDict implements Comparable<CmpDict> {
	private Long id;//ID
	private Date createTime;//创建时间
	private Date lastUpdateTime;//最后修改时间
	private String dictType;//字典表代码，按照字典实际意义来命名，如proj_stat，表示是项目状态字典表
	private String dictCode;//字典项代码，编号规则一般是99项之内用01，02....，超出99项的又是小于999项用001，002.....来命名,以此类推
	private String dictValue;//字典值，是字典项代码对应的字典内容，如项目储备（字典项01对应的中文解释），项目立项（字典项02对应的中文解释）
	private String dictDesc;//字典描述
	private String dictDefault;//是否默认：0-否；1是
	private int dictOrder;//字典顺序

	@Override
	public int compareTo(CmpDict obj) {
		if (this.getDictOrder() > obj.getDictOrder()) {
			return 1;
		} else if (this.getDictOrder() < obj.getDictOrder()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictDesc() {
		return dictDesc;
	}

	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}

	public String getDictDefault() {
		return dictDefault;
	}

	public void setDictDefault(String dictDefault) {
		this.dictDefault = dictDefault;
	}
	
	public int getDictOrder() {
		return dictOrder;
	}

	public void setDictOrder(int dictOrder) {
		this.dictOrder = dictOrder;
	}
}