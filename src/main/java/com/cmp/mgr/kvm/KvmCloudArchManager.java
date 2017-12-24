package com.cmp.mgr.kvm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;

import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.mgr.PlatformBindedCloudArchManager;
import com.cmp.mgr.bean.CloneVmRequest;
import com.cmp.mgr.libvirt.LibvirtConnect;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;

public class KvmCloudArchManager extends PlatformBindedCloudArchManager {

	@Override
	public List<Datacenter> getDatacenters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ClusterComputeResource> getClusters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ResourcePool> getResourcePools() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<HostSystem> getHostMachines() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TccVirtualMachine> getVirtualMachines() {
		List<TccVirtualMachine> ls = new ArrayList<>();

		Connect conn = null;
		try {
			conn = getLibvirtConnect();
			int[] idArr = conn.listDomains();
			for (int id : idArr) {
				Domain dom = conn.domainLookupByID(id);

				TccVirtualMachine vm = new TccVirtualMachine();
				vm.setId(dom.getID());
				vm.setName(dom.getName());
				vm.setOSType(dom.getOSType());
				vm.setMaxVcpus(dom.getMaxVcpus());
				vm.setXmlDesc(dom.getXMLDesc(0));
				vm.setUUID(dom.getUUIDString());
				vm.setDomainInfo(dom.getInfo().toString());
				vm.setMemoryStatistic((Arrays.asList(dom.memoryStats(8))).toString());
				vm.setCpuInfo((Arrays.asList(dom.getVcpusInfo())).toString());
				ls.add(vm);

				dom.free();
			}

			String[] definedNames = conn.listDefinedDomains();
			for (String name : definedNames) {
				Domain dom = conn.domainLookupByName(name);

				TccVirtualMachine vm = new TccVirtualMachine();
				vm.setId(dom.getID());
				vm.setName(dom.getName());
				vm.setOSType(dom.getOSType());
				vm.setMaxVcpus(dom.getMaxVcpus());
				vm.setMaxMemory(dom.getMaxMemory());
				vm.setXmlDesc(dom.getXMLDesc(0));
				vm.setUUID(dom.getUUIDString());
				ls.add(vm);

				dom.free();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			release(conn);
		}

		return ls;
	}

	private Connect getLibvirtConnect() throws LibvirtException {
		return new LibvirtConnect("qemu+ssh://" + platform.getCloudplatformIp() + "/system", false);
	}

	private void release(Connect conn) {
		try {
			conn.close();
		} catch (Throwable e) {
			// ignore
		} finally {
			conn = null;
		}
	}

	@Override
	public List<Datastore> getDatastores() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Network> getNetworks() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<VirtualMachine> getVmTemplates() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<VirtualMachineSnapshot> getVmSnapshots() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void startVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			if (dom.isActive() == 0) {
				dom.resume();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void stopVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			if (dom.isActive() == 1) {
				dom.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void rebootVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			if (dom.isActive() == 1) {
				dom.reboot(0);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void resetVirtualMachine(String name) {
	}

	@Override
	public void deleteVirtualMachine(String name) {
		try {
			Connect conn = getLibvirtConnect();
			Domain dom = conn.domainLookupByName(name);
			dom.destroy();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void cloneVirtualMachine(CloneVmRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void createSnapshot(String name, String vmName, String desc, boolean memoryFlag) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteSnapshot(String snapshotUUID) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void revertToSnapshot(String snapshotUUID, String hostMachineUUID) {
		throw new UnsupportedOperationException();
	}

}
