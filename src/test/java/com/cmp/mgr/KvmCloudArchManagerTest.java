package com.cmp.mgr;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.mgr.kvm.KvmCloudArchManager;

public class KvmCloudArchManagerTest implements CloudArchTest {

	private KvmCloudArchManager cloudArchManager;

	static {
		System.setProperty("jna.library.path", "C:/Program Files/VirtViewer v6.0-256/bin");
	}

	@Before
	public void setup() {
		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformIp("180.169.225.158");

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
