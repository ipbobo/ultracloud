package com.cmp.entity;

/**
 * 区域
 * 
 * @author liuweixing
 *
 */
public class Area {

	private String id;

	private String name;

	private Integer num;

	private String environment_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnvironment_name() {
		return environment_name;
	}

	public void setEnvironment_name(String environment_name) {
		this.environment_name = environment_name;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
