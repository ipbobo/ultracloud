package com.cmp.mgr.impl;

import static java.util.stream.Collectors.toList;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.vmware.vim25.AboutInfo;
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ObjectContent;
import com.vmware.vim25.ObjectSpec;
import com.vmware.vim25.PropertyFilterSpec;
import com.vmware.vim25.PropertySpec;
import com.vmware.vim25.SelectionSpec;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostDatastoreBrowser;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.PropertyCollector;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;
import com.vmware.vim25.mo.util.MorUtil;
import com.vmware.vim25.mo.util.PropertyCollectorUtil;

@SuppressWarnings({ "unused" })
public class VMWareCloudArchManager extends PlatformBindedCloudArchManager {

	public VMWareCloudArchManager() {
	}

	public VMWareCloudArchManager(TccCloudPlatform platform) {
		setPlatform(platform);
	}

	@Override
	public List<Datacenter> getDatacenters() {
		return searchManagedEntities(Datacenter.class);
	}

	public List<ClusterComputeResource> getClusters() {
		return searchManagedEntities(ClusterComputeResource.class);
	}

	public List<Datastore> getDatastores() {
		Function<HostSystem, HostDatastoreBrowser> getDatastoreBrowser = host -> {
			try {
				return host.getDatastoreBrowser();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};

		return searchManagedEntities(HostSystem.class).stream()
				.map(getDatastoreBrowser)
				.map(HostDatastoreBrowser::getDatastores)
				.filter(ArrayUtils::isNotEmpty)
				.flatMap(Arrays::stream)
				.collect(toList());
	}

	public List<VirtualMachine> getVirtualMachines() {
		List<VirtualMachine> vms = getVirtualMachinesNoVerify();
		if (CollectionUtils.isEmpty(vms)) {
			return Collections.emptyList();
		}

		return vms.stream().filter(this::isVirtualMachine).collect(toList());
	}

	@SuppressWarnings("deprecation")
	public List<VirtualMachine> getVirtualMachinesNoVerify() {
		try {
			String type = VirtualMachine.class.getSimpleName();
			String[][] typeinfo = new String[][] { new String[] { type, "name", }, };

			Folder rootEntity = getServiceInstance().getRootFolder();
			ServiceInstance serviceInstance = rootEntity.getServerConnection().getServiceInstance();

			PropertyCollector pc = serviceInstance.getPropertyCollector();
			AboutInfo ai = serviceInstance.getAboutInfo();

			SelectionSpec[] selectionSpecs = null;
			if (ai.apiVersion.startsWith("4") || ai.apiVersion.startsWith("5")) {
				selectionSpecs = PropertyCollectorUtil.buildFullTraversalV4();
			} else {
				selectionSpecs = PropertyCollectorUtil.buildFullTraversal();
			}

			PropertySpec[] propspecary = PropertyCollectorUtil.buildPropertySpecArray(typeinfo);

			ObjectSpec os = new ObjectSpec();
			os.setObj(rootEntity.getMOR());
			os.setSkip(Boolean.FALSE);
			os.setSelectSet(selectionSpecs);

			PropertyFilterSpec spec = new PropertyFilterSpec();
			spec.setObjectSet(new ObjectSpec[] { os });
			spec.setPropSet(propspecary);

			ObjectContent[] objectContents = pc
					.retrieveProperties(new PropertyFilterSpec[] { spec });
			if (ArrayUtils.isEmpty(objectContents)) {
				return Collections.emptyList();
			}

			List<VirtualMachine> vmList = new ArrayList<VirtualMachine>();
			for (ObjectContent ocs : objectContents) {
				DynamicProperty[] propSet = ocs.getPropSet();
				if (ArrayUtils.isEmpty(propSet)) {
					continue;
				}

				ManagedObjectReference mor = ocs.getObj();
				VirtualMachine vm = (VirtualMachine) MorUtil.createExactManagedEntity(
						rootEntity.getServerConnection(), mor);
				vmList.add(vm);
			}

			return vmList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> List<T> searchManagedEntities(Class<T> clazz) {
		try {
			ServiceInstance serviceInstance = getServiceInstance();
			ManagedEntity[] managedEntities = new InventoryNavigator(
					serviceInstance.getRootFolder()).searchManagedEntities(clazz.getSimpleName());

			if (ArrayUtils.isEmpty(managedEntities)) {
				return Collections.emptyList();
			}

			return Arrays.stream(managedEntities).map(clazz::cast).collect(toList());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private ServiceInstance getServiceInstance() throws MalformedURLException, RemoteException {
		URL url = new URL("https://" + platform.getCloudplatformIp() + "/sdk");

		return new ServiceInstance(url, platform.getCloudplatformUser(),
				platform.getCloudplatformPassword(), true);
	}

	private List<ClusterComputeResource> getClusters(Datacenter datacenter) {
		try {
			ManagedEntity[] managedEntities = datacenter.getHostFolder().getChildEntity();

			Set<ClusterComputeResource> clusterSet = new HashSet<>();
			Set<HostSystem> hostSet = new HashSet<>();
			getClustersAndHosts(managedEntities, clusterSet, hostSet);

			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void getClustersAndHosts(ManagedEntity[] managedEntities,
			Set<ClusterComputeResource> clusterSet, Set<HostSystem> hostSet) throws Exception {

		if (ArrayUtils.isEmpty(managedEntities)) {
			return;
		}

		for (ManagedEntity managedEntity : managedEntities) {
			if ("Folder".equals(managedEntity.getMOR().type)) {
				Folder folder = (Folder) managedEntity;
				ManagedEntity[] childEntitys = folder.getChildEntity();
				getClustersAndHosts(childEntitys, clusterSet, hostSet);
			}

			if ("ClusterComputeResource".equals(managedEntity.getMOR().type)) {
				clusterSet.add((ClusterComputeResource) managedEntity);
			}

			if ("ComputeResource".equals(managedEntity.getMOR().type)) {
				hostSet.add((HostSystem) managedEntity);
			}
		}
	}

	private List<HostSystem> getHosts(ClusterComputeResource ccr) {
		HostSystem[] hosts = ccr.getHosts();
		if (ArrayUtils.isEmpty(hosts)) {
			return Collections.emptyList();
		}

		List<HostSystem> ls = new ArrayList<>();
		for (HostSystem host : hosts) {
			if (host.getMOR() == null ||
					host.getHardware() == null ||
					host.getHardware().getCpuInfo() == null) {
				continue;
			}
			ls.add(host);
		}

		return ls;
	}

	public List<VirtualMachine> getVirtualMachines(HostSystem host) throws Exception {
		VirtualMachine[] vms = host.getVms();
		if (ArrayUtils.isEmpty(vms)) {
			return Collections.emptyList();
		}

		List<VirtualMachine> ls = new ArrayList<>();
		for (VirtualMachine vm : vms) {
			if (!isVirtualMachine(vm)) {
				continue;
			}

			ls.add(vm);
		}

		return ls;
	}

	@Override
	public List<HostSystem> getHostMachines() {
		return searchManagedEntities(HostSystem.class);
	}

	@Override
	public List<Network> getNetworks() {
		return searchManagedEntities(Network.class);
	}

	@Override
	public List<VirtualMachine> getVmTemplates() {
		List<VirtualMachine> vms = getVirtualMachinesNoVerify();
		if (CollectionUtils.isEmpty(vms)) {
			return Collections.emptyList();
		}

		return vms.stream().filter(this::isTemplate).collect(toList());
	}

	@Override
	public List<VirtualMachineSnapshot> getVmSnapshots() {
		return null;
	}

	@Override
	public List<VirtualMachine> startVirtualMachine(String name) {
		return null;
	}

	@Override
	public List<VirtualMachine> stopVirtualMachine(String name) {
		return null;
	}

	@Override
	public List<VirtualMachine> rebootVirtualMachine(String name) {
		return null;
	}

	@Override
	public List<VirtualMachine> resetVirtualMachine(String name) {
		return null;
	}

	@Override
	public List<VirtualMachine> deleteVirtualMachine(String name) {
		return null;
	}

	@Override
	public void createSnapshot() {

	}

	@Override
	public void deleteSnapshot() {

	}

	@Override
	public void revertToSnapshot() {

	}

	private boolean isVirtualMachine(VirtualMachine vm) {
		return vm != null && vm.getConfig() != null && !vm.getConfig().isTemplate();
	}

	private boolean isTemplate(VirtualMachine vm) {
		if (vm == null) {
			return false;
		}

		if (vm.getConfig() == null) {
			return false;
		}

		return vm.getConfig().isTemplate();
	}

}
