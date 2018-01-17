package com.cmp.sid;

//计算价格
public class CmpPrice {
	private String cpuPrice;//cpu单价
	private String memPrice;//内存单价
	private String storePrice;//磁盘单价

	public String getCpuPrice() {
		return cpuPrice;
	}

	public void setCpuPrice(String cpuPrice) {
		this.cpuPrice = cpuPrice;
	}

	public String getMemPrice() {
		return memPrice;
	}

	public void setMemPrice(String memPrice) {
		this.memPrice = memPrice;
	}

	public String getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(String storePrice) {
		this.storePrice = storePrice;
	}
}