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
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.ArrayUtils;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.bean.CreateVmRequest;
import com.vmware.vim25.AboutInfo;
import com.vmware.vim25.Description;
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ObjectContent;
import com.vmware.vim25.ObjectSpec;
import com.vmware.vim25.PropertyFilterSpec;
import com.vmware.vim25.PropertySpec;
import com.vmware.vim25.SelectionSpec;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualLsiLogicController;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineFileInfo;
import com.vmware.vim25.VirtualPCNet32;
import com.vmware.vim25.VirtualSCSISharing;
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
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServerConnection;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
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

	@Override
	public List<ClusterComputeResource> getClusters() {
		return searchManagedEntities(ClusterComputeResource.class);
	}

	@Override
	public List<ResourcePool> getResourcePools() {
		return searchManagedEntities(ResourcePool.class);
	}

	@Override
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

	@Override
	public List<VirtualMachine> getVirtualMachines() {
		return getVirtualMachinesNoVerify().stream()
				.filter(this::isVirtualMachine).collect(toList());
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

	@SuppressWarnings("deprecation")
	public void createVirtualMachine(CreateVmRequest request) {
		try {
			String dcName = request.getDcName();
			String vmName = request.getVmName();
			int cupCount = request.getCupCount();
			long memSizeMB = request.getMemSizeMB();
			String guestOsId = request.getGuestOs();
			long diskSizeKB = request.getDiskSizeKB();

			String diskMode = request.getDiskMode();
			String datastoreName = request.getDsName();
			String netName = request.getNetName();
			String nicName = request.getNicName();
			String rpName = request.getRpName();

			Datacenter dc = searchManagedEntity(Datacenter.class, dcName).get();

			List<ResourcePool> mes = getResourcePools();

			ResourcePool rp = null;
			for (ResourcePool tmp : mes) {
				if (tmp.getName().equalsIgnoreCase(rpName)) {
					rp = tmp;
					break;
				}
			}

			Folder vmFolder = dc.getVmFolder();

			VirtualMachineConfigSpec vmSpec = new VirtualMachineConfigSpec();
			vmSpec.setName(vmName);
			vmSpec.setAnnotation("VirtualMachine Annotation");
			vmSpec.setMemoryMB(memSizeMB);
			vmSpec.setNumCPUs(cupCount);
			vmSpec.setGuestId(guestOsId);

			int cKey = 1000;
			VirtualDeviceConfigSpec scsiSpec = createScsiSpec(cKey);
			VirtualDeviceConfigSpec diskSpec = createDiskSpec(datastoreName, cKey,
					diskSizeKB, diskMode);
			VirtualDeviceConfigSpec nicSpec = createNicSpec(netName, nicName);

			vmSpec.setDeviceChange(new VirtualDeviceConfigSpec[] { scsiSpec,
					diskSpec, nicSpec });

			// create vm file info for the vmx file
			VirtualMachineFileInfo vmfi = new VirtualMachineFileInfo();
			vmfi.setVmPathName("[" + datastoreName + "]");
			vmSpec.setFiles(vmfi);

			// call the createVM_Task method on the vm folder
			Task task = vmFolder.createVM_Task(vmSpec, rp, null);
			task.waitForMe();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private VirtualDeviceConfigSpec createScsiSpec(int cKey) {
		VirtualLsiLogicController scsiCtrl = new VirtualLsiLogicController();

		VirtualDeviceConfigSpec scsiSpec = new VirtualDeviceConfigSpec();
		scsiSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
		scsiCtrl.setKey(cKey);
		scsiCtrl.setBusNumber(0);
		scsiCtrl.setSharedBus(VirtualSCSISharing.noSharing);
		scsiSpec.setDevice(scsiCtrl);

		return scsiSpec;
	}

	private VirtualDeviceConfigSpec createDiskSpec(String dsName, int cKey,
			long diskSizeKB, String diskMode) {

		VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
		diskSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
		diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.create);

		VirtualDisk vd = new VirtualDisk();
		vd.setCapacityInKB(diskSizeKB);
		diskSpec.setDevice(vd);
		vd.setKey(0);
		vd.setUnitNumber(0);
		vd.setControllerKey(cKey);

		VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();
		String fileName = "[" + dsName + "]";
		diskfileBacking.setFileName(fileName);
		diskfileBacking.setDiskMode(diskMode);
		diskfileBacking.setThinProvisioned(true);
		vd.setBacking(diskfileBacking);

		return diskSpec;
	}

	private VirtualDeviceConfigSpec createNicSpec(String netName, String nicName)
			throws Exception {

		VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
		nicSpec.setOperation(VirtualDeviceConfigSpecOperation.add);

		VirtualEthernetCard nic = new VirtualPCNet32();
		VirtualEthernetCardNetworkBackingInfo nicBacking = new VirtualEthernetCardNetworkBackingInfo();
		nicBacking.setDeviceName(netName);

		Description info = new Description();
		info.setLabel(nicName);
		info.setSummary(netName);
		nic.setDeviceInfo(info);

		// type: "generated", "manual", "assigned" by VC
		nic.setAddressType("generated");
		nic.setBacking(nicBacking);
		nic.setKey(0);

		nicSpec.setDevice(nic);

		return nicSpec;
	}

	public List<VirtualDevice> getVirtualDevices() {
		return searchManagedEntities(VirtualDevice.class);
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

	public <T> Optional<T> searchManagedEntity(Class<T> clazz, String name) {
		try {
			ServiceInstance serviceInstance = getServiceInstance();
			ManagedEntity managedEntity = new InventoryNavigator(
					serviceInstance.getRootFolder()).searchManagedEntity(
							clazz.getSimpleName(), name);

			return Optional.ofNullable(managedEntity).map(clazz::cast);
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
		return getVirtualMachinesNoVerify().stream().filter(this::isTemplate).collect(toList());
	}

	@Override
	public List<VirtualMachineSnapshot> getVmSnapshots() {
		return getVirtualMachines().stream().map(VirtualMachine::getRootSnapshot)
				.flatMap(Arrays::stream).collect(toList());
	}

	@Override
	public void startVirtualMachine(String name) {
		searchManagedEntity(VirtualMachine.class, name).ifPresent(vm -> {
			try {
				vm.powerOnVM_Task(null);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public void stopVirtualMachine(String name) {
		searchManagedEntity(VirtualMachine.class, name).ifPresent(vm -> {
			try {
				vm.powerOffVM_Task();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public void rebootVirtualMachine(String name) {
		searchManagedEntity(VirtualMachine.class, name).ifPresent(vm -> {
			try {
				vm.rebootGuest();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public void resetVirtualMachine(String name) {
		searchManagedEntity(VirtualMachine.class, name).ifPresent(vm -> {
			try {
				vm.resetVM_Task();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public void deleteVirtualMachine(String name) {
		searchManagedEntity(VirtualMachine.class, name).ifPresent(vm -> {
			try {
				vm.destroy_Task();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public void createSnapshot(String name, String vmName, String desc, boolean memoryFlag) {
		searchManagedEntity(VirtualMachine.class, vmName).ifPresent(vm -> {
			try {
				vm.createSnapshot_Task(name, desc, memoryFlag, false);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public void deleteSnapshot(String snapshotUUID) {
		try {
			ManagedObjectReference mor = new ManagedObjectReference();
			mor.setType(VirtualMachineSnapshot.class.getSimpleName());
			mor.set_value(snapshotUUID);

			VirtualMachineSnapshot snapshot = new VirtualMachineSnapshot(
					getServiceInstance().getServerConnection(), mor);

			snapshot.removeSnapshot_Task(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void revertToSnapshot(String snapshotUUID, String hostMachineUUID) {
		try {
			ServerConnection connection = getServiceInstance().getServerConnection();

			ManagedObjectReference hostSystemMor = new ManagedObjectReference();
			hostSystemMor.setType(HostSystem.class.getSimpleName());
			hostSystemMor.setVal(hostMachineUUID);

			ManagedObjectReference snapshotMor = new ManagedObjectReference();
			snapshotMor.setType(VirtualMachineSnapshot.class.getSimpleName());
			snapshotMor.setVal(snapshotUUID);

			VirtualMachineSnapshot snapshot = new VirtualMachineSnapshot(connection, snapshotMor);
			HostSystem hostSystem = new HostSystem(connection, hostSystemMor);

			snapshot.revertToSnapshot_Task(hostSystem, false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isVirtualMachine(VirtualMachine vm) {
		return vm != null && vm.getConfig() != null && !vm.getConfig().isTemplate();
	}

	private boolean isTemplate(VirtualMachine vm) {
		return vm != null && vm.getConfig() != null && vm.getConfig().isTemplate();
	}

}
