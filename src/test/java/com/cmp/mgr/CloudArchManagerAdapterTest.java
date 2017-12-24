package com.cmp.mgr;

import java.util.List;

import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.entity.tcc.TccHostMachine;

public class CloudArchManagerAdapterTest {

	@Test
	public void testGetAdaptee() {
		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformUser("administrator@vsphere.local");
		platform.setCloudplatformPassword("123.comM");
		platform.setCloudplatformIp("118.242.40.216");
		platform.setPlatformManagerType("com.cmp.mgr.impl.VMWareCloudArchManager");

		CloudArchManagerAdapter adapter = new CloudArchManagerAdapter();
		List<TccHostMachine> hosts = adapter.getCloudArchManagerAdaptee(platform).getHostMachines();

		hosts.stream().map(TccHostMachine::getHostName).forEach(System.out::println);
	}

}
