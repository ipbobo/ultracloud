package com.cmp.mgr;

import java.util.List;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.vmware.vim25.mo.Datacenter;

public interface CloudArchManager {

	public List<Datacenter> getDatacenters(TccCloudPlatform platform);

}
