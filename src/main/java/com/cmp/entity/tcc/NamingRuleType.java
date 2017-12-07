package com.cmp.entity.tcc;

public class NamingRuleType implements java.io.Serializable {

	private static final long serialVersionUID = -355193833501840685L;

	/** id */
	private Long id;

	/** 名称： */
	private String name;

	/** 类型：0前缀，后缀 */
	private String type;

	/** 类型：0前缀，后缀(时间戳，日期+时间戳，日期+自增长) */
	private String ruleType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

}
