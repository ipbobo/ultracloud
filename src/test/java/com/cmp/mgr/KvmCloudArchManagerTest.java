package com.cmp.mgr;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.mgr.kvm.KvmCloudArchManager;

public class KvmCloudArchManagerTest implements CloudArchTest {

	private KvmCloudArchManager cloudArchManager;

	@Before
	public void setup() {
		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformUser("administrator@vsphere.local");
		platform.setCloudplatformPassword("123.comM");
		platform.setCloudplatformIp("118.242.40.216");

		cloudArchManager = new KvmCloudArchManager(platform);
	}

	@Test
	public void testGetVirtualMachines() {
		execute("GetVirtualMachines", () -> {
			cloudArchManager.getVirtualMachines().stream()
					.map(TccVirtualMachine::getUUID).forEach(System.out::println);
		});
	}

	@After
	public void cleanup() {
		cloudArchManager = null;
	}

}
