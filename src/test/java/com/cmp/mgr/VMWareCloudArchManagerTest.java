package com.cmp.mgr;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.bean.CloneVmRequest;
import com.cmp.mgr.bean.CreateVmRequest;
import com.cmp.mgr.vmware.VMWareCloudArchManager;
import com.vmware.vim25.Description;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;

public class VMWareCloudArchManagerTest {

	private VMWareCloudArchManager cloudArchManager;

	@Before
	public void setup() {
		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformUser("administrator@vsphere.local");
		platform.setCloudplatformPassword("123.comM");
		platform.setCloudplatformIp("118.242.40.216");

		cloudArchManager = new VMWareCloudArchManager(platform);
	}

	public void execute(String flag, Runnable runnable) {
		String info = String.format("== Action: %s ", flag);
		info = StringUtils.rightPad(info, 50, "=");

		System.err.println(info);
		try {
			runnable.run();
			Thread.sleep(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	@Test
	public void testGetDatacenters() {
		execute("GetDatacenters", () -> {
			cloudArchManager.getDatacenters().stream()
					.map(Datacenter::getName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetClusters() {
		execute("GetClusters", () -> {
			cloudArchManager.getClusters().stream()
					.map(ClusterComputeResource::getName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetHostMachines() {
		execute("GetHostMachines", () -> {
			cloudArchManager.getHostMachines().stream()
					.map(HostSystem::getName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetVirtualMachines() {
		execute("GetVirtualMachines", () -> {
			cloudArchManager.getVirtualMachines().stream()
					.map(VirtualMachine::getName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetTemplates() {
		execute("GetTemplates", () -> {
			cloudArchManager.getVmTemplates().stream()
					.map(VirtualMachine::getName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetDatastores() {
		execute("GetDatastores", () -> {
			cloudArchManager.getDatastores().stream()
					.map(Datastore::getName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetResourcePools() {
		execute("GetResourcePools", () -> {
			cloudArchManager.getResourcePools().stream()
					.map(ResourcePool::getName).forEach(System.out::println);
		});
	}

//	@Test
	public void testGetVirtualDevices() {
		execute("GetVirtualDevices", () -> {
			cloudArchManager.getVirtualDevices().stream()
					.map(VirtualDevice::getDeviceInfo)
					.map(Description::getLabel)
					.forEach(System.out::println);
		});
	}

	@Test
	public void testGetNetWorks() {
		execute("GetNetWorks", () -> {
			cloudArchManager.getNetworks().stream()
					.map(Network::getName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetVmSnapshots() {
		execute("GetVmSnapshots", () -> {
			cloudArchManager.getVmSnapshots().stream()
					.map(VirtualMachineSnapshot::getConfig)
					.map(VirtualMachineConfigInfo::getName)
					.forEach(System.out::println);
		});
	}

	// @Test
	public void testCreateVM() {
		execute("CreateVirtualMachine", () -> {
			CreateVmRequest request = new CreateVmRequest();
			request.setCupCount(1);
			request.setMemSizeMB(1024);
			request.setDiskSizeKB(16 * 1024 * 1024);
			request.setDiskMode("persistent");
			request.setDcName("DC1");
			request.setVmName("TestVM");
			request.setNetName("VM Network");
			request.setRpName("Resources");
			request.setNicName("VMXNET 3");
			request.setDsName("datastore2-raid5-2.5t");
			request.setGuestOs("rhel6_64Guest");

			cloudArchManager.createVirtualMachine(request);
		});
	}

	@Test
	public void testCloneVM() {
		execute("CloneVirtualMachine", () -> {
			CloneVmRequest request = new CloneVmRequest();
			request.setVmName("TestVM2");
			request.setTplName("rhel6.0_x64_template");
			request.setDcName("DC1");
			request.setRpName("Resources");

			cloudArchManager.cloneVirtualMachine(request);
		});
	}

	// @Test
	public void testDeleteVM() {
		execute("DeleteVirtualMachine", () -> {
			cloudArchManager.deleteVirtualMachine("TestVM");
		});
	}

	@After
	public void cleanup() {
		cloudArchManager = null;
	}

}
