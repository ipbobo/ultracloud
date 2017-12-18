package com.cmp.mgr.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.CloudArchManager;

public abstract class PlatformBindedCloudArchManager implements CloudArchManager {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected TccCloudPlatform platform;

	public TccCloudPlatform getPlatform() {
		return platform;
	}

	public void setPlatform(TccCloudPlatform platform) {
		this.platform = platform;
	}

}
