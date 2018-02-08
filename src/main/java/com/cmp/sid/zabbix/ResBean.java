package com.cmp.sid.zabbix;

public class ResBean {
	private String hostid;
	private String itemid;
	private String status;
	private String key_;
	private String lastvalue;

	public String getHostid() {
		return hostid;
	}

	public void setHostid(String hostid) {
		this.hostid = hostid;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKey_() {
		return key_;
	}

	public void setKey_(String key_) {
		this.key_ = key_;
	}

	public String getLastvalue() {
		return lastvalue;
	}

	public void setLastvalue(String lastvalue) {
		this.lastvalue = lastvalue;
	}
}