package com.cmp.entity.tcc;

import java.io.Serializable;

public class TccHignNetwork implements Serializable {

	private static final long serialVersionUID = 4848958524826432590L;

	private Integer	id;
	private String	networkName;
	private String	networkGateway;
	private String	networkMask;
	private String	networkCidr;
	private String	networkZoneId;
	private String	networkZoneName;
	private String	state;
	private String	networkOfferingIds;
	private String	networkId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getNetworkGateway() {
		return networkGateway;
	}

	public void setNetworkGateway(String networkGateway) {
		this.networkGateway = networkGateway;
	}

	public String getNetworkMask() {
		return networkMask;
	}

	public void setNetworkMask(String networkMask) {
		this.networkMask = networkMask;
	}

	public String getNetworkCidr() {
		return networkCidr;
	}

	public void setNetworkCidr(String networkCidr) {
		this.networkCidr = networkCidr;
	}

	public String getNetworkZoneId() {
		return networkZoneId;
	}

	public void setNetworkZoneId(String networkZoneId) {
		this.networkZoneId = networkZoneId;
	}

	public String getNetworkZoneName() {
		return networkZoneName;
	}

	public void setNetworkZoneName(String networkZoneName) {
		this.networkZoneName = networkZoneName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getNetworkOfferingIds() {
		return networkOfferingIds;
	}

	public void setNetworkOfferingIds(String networkOfferingIds) {
		this.networkOfferingIds = networkOfferingIds;
	}
}
