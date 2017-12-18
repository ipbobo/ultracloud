package com.cmp.mgr;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.impl.VMWareCloudArchManager;
import com.vmware.vim25.VirtualMachineSnapshotInfo;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.VirtualMachine;

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
			// ignore
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
					.map(VirtualMachineSnapshotInfo::getDynamicType).forEach(System.out::println);
		});
	}

	@After
	public void cleanup() {
		cloudArchManager = null;
	}

}
