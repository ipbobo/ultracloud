package com.cmp.mgr.impl;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.CloudArchManager;
import com.sun.tools.javac.util.List;
import com.vmware.vim25.mo.Datacenter;

public class KvmCloudArchManager implements CloudArchManager {

	@Override
	public List<Datacenter> getDatacenters(TccCloudPlatform platform) {
		return null;
	}

}
