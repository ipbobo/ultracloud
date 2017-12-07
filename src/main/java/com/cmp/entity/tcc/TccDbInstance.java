package com.cmp.entity.tcc;

public class TccDbInstance implements java.io.Serializable {

	private static final long serialVersionUID = -2294363076929362985L;

	private Long	instanceId;
	private String	instanceName;
	private String	instanceEnconding;

	/** 编码名称 */
	private String instanceEncondingName;

	public Long getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public String getInstanceName() {
		return this.instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getInstanceEnconding() {
		return this.instanceEnconding;
	}

	public void setInstanceEnconding(String instanceEnconding) {
		this.instanceEnconding = instanceEnconding;
	}

	public String getInstanceEncondingName() {
		return instanceEncondingName;
	}

	public void setInstanceEncondingName(String instanceEncondingName) {
		this.instanceEncondingName = instanceEncondingName;
	}

}
