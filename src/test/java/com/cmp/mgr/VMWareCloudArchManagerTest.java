package com.cmp.mgr;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.entity.tcc.TccCluster;
import com.cmp.entity.tcc.TccDatastore;
import com.cmp.entity.tcc.TccHostMachine;
import com.cmp.entity.tcc.TccNetwork;
import com.cmp.entity.tcc.TccResourcePool;
import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.entity.tcc.TccVmSnapshot;
import com.cmp.mgr.bean.CloneVmRequest;
import com.cmp.mgr.bean.CreateVmRequest;
import com.cmp.mgr.vmware.VMWareCloudArchManager;
import com.vmware.vim25.Description;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.mo.Datacenter;

public class VMWareCloudArchManagerTest implements CloudArchTest {

	private VMWareCloudArchManager cloudArchManager;

	@Before
	public void setup() {
		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformUser("administrator@vsphere.local");
		platform.setCloudplatformPassword("123.comM");
		platform.setCloudplatformIp("180.169.225.158");

		cloudArchManager = new VMWareCloudArchManager(platform);
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
					.map(TccCluster::getClusterName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetHostMachines() {
		execute("GetHostMachines", () -> {
			cloudArchManager.getHostMachines().stream()
					.map(TccHostMachine::getHostName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetVirtualMachines() {
		execute("GetVirtualMachines", () -> {
			cloudArchManager.getVirtualMachines().stream()
					.map(TccVirtualMachine::getUUID).forEach(System.out::println);
		});
	}

	@Test
	public void testGetVirtualMachineByName() {
		execute("GetVirtualMachineByName", () -> {
			Optional.ofNullable(cloudArchManager.getVirtualMachineByName("cmp_54"))
					.map(TccVirtualMachine::getUUID).ifPresent(System.out::println);
		});
	}

	@Test
	public void testGetTemplates() {
		execute("GetTemplates", () -> {
			cloudArchManager.getVmTemplates().stream()
					.map(TccVirtualMachine::getName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetDatastores() {
		execute("GetDatastores", () -> {
			cloudArchManager.getDatastores().stream()
					.map(TccDatastore::getName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetResourcePools() {
		execute("GetResourcePools", () -> {
			cloudArchManager.getResourcePools().stream()
					.map(TccResourcePool::getName).forEach(System.out::println);
		});
	}

	// @Test
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
					.map(TccNetwork::getNetworkName).forEach(System.out::println);
		});
	}

	@Test
	public void testGetVmSnapshots() {
		execute("GetVmSnapshots", () -> {
			cloudArchManager.getVmSnapshots(null).stream()
					.map(TccVmSnapshot::getName).forEach(System.out::println);
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

	// @Test
	public void testCloneVM() {
		execute("CloneVirtualMachine", () -> {
			CloneVmRequest request = new CloneVmRequest();
			request.setVmName("TestVM2");
			request.setTplName("rhel6.0_x64_template");
			request.setDcName("DC1");
			request.setRpName("Resources");
			request.setCpuSize(1);
			request.setRamSize(768);

			cloudArchManager.cloneVirtualMachine(request);
		});
	}

	// @Test
	public void testDeleteVM() {
		execute("DeleteVirtualMachine", () -> {
			cloudArchManager.destroyVirtualMachine("TestVM");
		});
	}

	@After
	public void cleanup() {
		cloudArchManager = null;
	}

}
