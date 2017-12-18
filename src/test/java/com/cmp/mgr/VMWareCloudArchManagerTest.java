package com.cmp.mgr;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.impl.VMWareCloudArchManager;

public class VMWareCloudArchManagerTest {

	private CloudArchManager cloudArchManager;

	@Before
	public void setup() {
		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformUser("administrator@vsphere.local");
		platform.setCloudplatformPassword("123.comM");
		platform.setCloudplatformIp("118.242.40.216");

		cloudArchManager = new VMWareCloudArchManager(platform);
	}

	@Test
	public void testGetDatacenters() {
		cloudArchManager.getDatacenters().forEach(datacenter -> {
			System.out.println(datacenter.getName());
		});
	}

	@Test
	public void testGetClusters() {
		cloudArchManager.getClusters().forEach(cluster -> {
			System.out.println(cluster.getName());
		});
	}

	@Test
	public void testGetVirtualMachines() {
		cloudArchManager.getVirtualMachines().forEach(virtualMachine -> {
			System.out.println(virtualMachine.getName());
		});
	}

	@After
	public void cleanup() {
		cloudArchManager = null;
	}

}
