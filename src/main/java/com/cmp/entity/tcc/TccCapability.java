package com.cmp.entity.tcc;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class TccCapability {

	public int supportedVcpus;

	public long supportedMemory;

	public long supportedStorage;

	public int getSupportedVcpus() {
		return supportedVcpus;
	}

	public void setSupportedVcpus(int supportedVcpus) {
		this.supportedVcpus = supportedVcpus;
	}

	public long getSupportedMemory() {
		return supportedMemory;
	}

	public void setSupportedMemory(long supportedMemory) {
		this.supportedMemory = supportedMemory;
	}

	public long getSupportedStorage() {
		return supportedStorage;
	}

	public void setSupportedStorage(long supportedStorage) {
		this.supportedStorage = supportedStorage;
	}

	@Override
	public String toString() {
		return reflectionToString(this, MULTI_LINE_STYLE);
	}

}
