package com.cmp.mgr;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.bean.CreateVmRequest;
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
			cloudArchManager.getVirtualMachines().forEach(System.out::println);
		});
	}

	@Test
	public void testGreateVirtualMachine() {
		execute("GreateVirtualMachine", () -> {
			CreateVmRequest request = new CreateVmRequest();
			request.setVmName("centos62");
			request.setCupCount(1);
			request.setMemSizeMB(1024);
			request.setImagePath("/root/CentOS-6-x86_64-GenericCloud-1710.qcow2");

			cloudArchManager.createVirtualMachine(request);
		});
	}

	@Test
	public void testStartVirtualMachine() {
		execute("StartVirtualMachine", () -> {
			cloudArchManager.startVirtualMachine("centos6");
		});
	}

	@Test
	public void testStopVirtualMachine() {
		execute("StopVirtualMachine", () -> {
			cloudArchManager.stopVirtualMachine("centos6");
		});
	}

	@Test
	public void testRebootVirtualMachine() {
		execute("RebootVirtualMachine", () -> {
			cloudArchManager.rebootVirtualMachine("centos6");
		});
	}

	@Test
	public void testSuspendVirtualMachine() {
		execute("ResumeVirtualMachine", () -> {
			cloudArchManager.suspendVirtualMachine("centos6");
		});
	}

	@Test
	public void testResumeVirtualMachine() {
		execute("ResumeVirtualMachine", () -> {
			cloudArchManager.resumeVirtualMachine("centos6");
		});
	}

	@Test
	public void testDestroyVirtualMachine() {
		execute("DestroyVirtualMachine", () -> {
			cloudArchManager.destroyVirtualMachine("centos6");
		});
	}

	@After
	public void cleanup() {
		cloudArchManager = null;
	}

}
