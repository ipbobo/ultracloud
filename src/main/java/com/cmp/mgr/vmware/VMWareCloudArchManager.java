package com.cmp.mgr.vmware;

import com.cmp.entity.tcc.*;
import com.cmp.mgr.PlatformBindedCloudArchManager;
import com.cmp.mgr.bean.CloneVmRequest;
import com.cmp.mgr.bean.CloneVmResponse;
import com.cmp.mgr.bean.CreateVmRequest;
import com.cmp.mgr.bean.CreateVolumeRequest;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;
import com.vmware.vim25.mo.util.MorUtil;
import com.vmware.vim25.mo.util.PropertyCollectorUtil;
import com.vmware.vim25.mox.VirtualMachineDeviceManager;
import org.apache.commons.lang3.ArrayUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@SuppressWarnings({"unused"})
public class VMWareCloudArchManager extends PlatformBindedCloudArchManager {

	private VMWareConvertors converters = new VMWareConvertors();

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
	public List<TccDatacenter> getDatacenter() {
		return searchManagedEntities(Datacenter.class).stream()
				.map(converters.toDatacenter()).collect(toList());
	}

	@Override
	public List<TccCluster> getClusters() {
		return searchManagedEntities(ClusterComputeResource.class)
				.stream().map(converters.toCluster()).collect(toList());
	}

	@Override
	public List<TccResourcePool> getResourcePools() {
		return searchManagedEntities(ResourcePool.class).stream()
				.map(converters.toResourcePool()).collect(toList());
	}

	@Override
	public List<TccDatastore> getDatastores() {
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
				.map(converters.toDatastore())
				.collect(toList());
	}

	@Override
	public List<TccVirtualMachine> getVirtualMachines() {
		return getVirtualMachinesNoVerify().stream()
				.filter(this::isVirtualMachine)
				.map(converters.toVirtualMachine())
				.collect(toList());
	}

	@SuppressWarnings("deprecation")
	private List<VirtualMachine> getVirtualMachinesNoVerify() {
		try {
			String type = VirtualMachine.class.getSimpleName();
			String[][] typeinfo = new String[][]{new String[]{type, "name",},};

			Folder rootEntity = getServiceInstance().getRootFolder();
			ServiceInstance serviceInstance = rootEntity.getServerConnection().getServiceInstance();

			PropertyCollector pc = serviceInstance.getPropertyCollector();
			AboutInfo ai = serviceInstance.getAboutInfo();

			SelectionSpec[] selectionSpecs;
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
			spec.setObjectSet(new ObjectSpec[]{os});
			spec.setPropSet(propspecary);

			ObjectContent[] objectContents = pc
					.retrieveProperties(new PropertyFilterSpec[]{spec});
			if (ArrayUtils.isEmpty(objectContents)) {
				return Collections.emptyList();
			}

			List<VirtualMachine> vmList = new ArrayList<>();
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

	public TccVirtualMachine getVirtualMachineByName(String name) {
		return searchManagedEntity(VirtualMachine.class, name)
				.map(converters.toVirtualMachine()).orElse(null);
	}

	@Override
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

			Datacenter dc = searchManagedEntity(Datacenter.class, dcName)
					.orElseThrow(error("Datacenter not found: " + dcName));
			List<ResourcePool> mes = searchManagedEntities(ResourcePool.class);

			ResourcePool rp = null;
			for (ResourcePool tmp : mes) {
				if (tmp.getName().equalsIgnoreCase(rpName)) {
					rp = tmp;
					break;
				}
			}

			if (rp == null) {
				throw error("No resource pool found").get();
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

			vmSpec.setDeviceChange(new VirtualDeviceConfigSpec[]{scsiSpec,
					diskSpec, nicSpec});

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

		VirtualDiskFlatVer2BackingInfo diskFileBacking = new VirtualDiskFlatVer2BackingInfo();
		String fileName = "[" + dsName + "]";
		diskFileBacking.setFileName(fileName);
		diskFileBacking.setDiskMode(diskMode);
		diskFileBacking.setThinProvisioned(true);
		vd.setBacking(diskFileBacking);

		return diskSpec;
	}

	private VirtualDeviceConfigSpec createNicSpec(String netName, String nicName) {
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

	private <T> List<T> searchManagedEntities(Class<T> clazz) {
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

	private <T> Optional<T> searchManagedEntity(Class<T> clazz, String name) {
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
				//noinspection ConstantConditions
				clusterSet.add((ClusterComputeResource) managedEntity);
			}

			if ("ComputeResource".equals(managedEntity.getMOR().type)) {
				//noinspection ConstantConditions
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
	public List<TccHostMachine> getHostMachines() {
		return searchManagedEntities(HostSystem.class).stream()
				.map(converters.toHostMachine()).collect(toList());
	}

	@Override
	public List<TccNetwork> getNetworks() {
		return searchManagedEntities(ComputeResource.class).stream()
				.map(ComputeResource::getNetworks)
				.flatMap(Arrays::stream)
				.map(converters.toNetwork())
				.collect(toList());
	}

	@Override
	public List<TccVirtualMachine> getVmTemplates() {
		return getVirtualMachinesNoVerify().stream()
				.filter(this::isTemplate)
				.map(converters.toVirtualMachine())
				.collect(toList());
	}

	@Override
	public List<TccVmSnapshot> getVmSnapshots(String uuid) {
		return getVirtualMachinesNoVerify().stream()
				.filter(this::isVirtualMachine)
				.map(VirtualMachine::getRootSnapshot)
				.flatMap(Arrays::stream)
				.map(converters.toVmSnapshot())
				.collect(toList());
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
	public void suspendVirtualMachine(String name) {
		searchManagedEntity(VirtualMachine.class, name).ifPresent(vm -> {
			try {
				vm.suspendVM_Task();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public void resumeVirtualMachine(String name) {
		searchManagedEntity(VirtualMachine.class, name).ifPresent(vm -> {
			try {
				vm.powerOnVM_Task(null);
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
	public void destroyVirtualMachine(String name) {
		searchManagedEntity(VirtualMachine.class, name).ifPresent(vm -> {
			try {
				vm.destroy_Task();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	@SuppressWarnings("deprecation")
	public CloneVmResponse cloneVirtualMachine(CloneVmRequest req) {
		String tplName = req.getTplName();
		String dcName = req.getDcName();

		try {
			String ip = Optional.ofNullable(req.getIp()).orElseThrow(
					error("No IP address specified"));

			ClusterComputeResource cluster = searchManagedEntities(
					ClusterComputeResource.class).stream().findFirst()
					.orElseThrow(error("No cluster found"));

			Datacenter dc = searchManagedEntity(Datacenter.class, dcName)
					.orElseThrow(error("Datacenter not found: " + dcName));

			VirtualMachine vm = searchManagedEntity(VirtualMachine.class, tplName)
					.orElseThrow(error("Template not found: " + tplName));

			Datastore datastore = getDatastoreWithMaxFreeSpaceByVM(vm)
					.orElseThrow(error("No datastore available"));

			HostSystem hostSystem = getHostWithLeastVMByClusterAndDataStore(cluster, datastore)
					.orElseThrow(error("No host machine available"));

			VirtualMachineConfigSpec configSpec = new VirtualMachineConfigSpec();
			configSpec.setNumCPUs(req.getCpuSize());
			configSpec.setMemoryMB(req.getRamSize());

			VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
			relocateSpec.setPool(cluster.getResourcePool().getMOR());
			relocateSpec.setDatastore(datastore.getMOR());
			relocateSpec.setHost(hostSystem.getMOR());

			CustomizationSpec customSpec = new CustomizationSpec();
			CustomizationLinuxOptions linuxOptions = new CustomizationLinuxOptions();
			customSpec.setOptions(linuxOptions);

			customSpec.setGlobalIPSettings(new CustomizationGlobalIPSettings());
			CustomizationLinuxPrep linuxPrep = new CustomizationLinuxPrep();
			linuxPrep.setDomain("localdomain");
			linuxPrep.setHwClockUTC(true);
			linuxPrep.setTimeZone("Asia/Shanghai");

			CustomizationFixedName fixedName = new CustomizationFixedName();
			fixedName.setName("localhost");
			linuxPrep.setHostName(fixedName);
			customSpec.setIdentity(linuxPrep);

			CustomizationGlobalIPSettings globalIPSettings = new CustomizationGlobalIPSettings();
			globalIPSettings.setDnsServerList(new String[]{"8.8.8.8", "8.8.4.4"});
			globalIPSettings.setDnsSuffixList(new String[]{"localhost.localdomain"});
			customSpec.setGlobalIPSettings(globalIPSettings);

			CustomizationFixedIp fixedIp = new CustomizationFixedIp();
			fixedIp.setIpAddress(ip);

			CustomizationIPSettings ipSettings = new CustomizationIPSettings();
			ipSettings.setIp(fixedIp);
			ipSettings.setSubnetMask("255.255.255.0");
			ipSettings.setGateway(new String[]{ip.replaceAll("[.]\\d+$", ".1")});

			CustomizationAdapterMapping adapterMapping = new CustomizationAdapterMapping();
			adapterMapping.setAdapter(ipSettings);

			CustomizationAdapterMapping[] adapterMappings = new CustomizationAdapterMapping[]{adapterMapping};
			customSpec.setNicSettingMap(adapterMappings);

			VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
			cloneSpec.setCustomization(customSpec);
			cloneSpec.setLocation(relocateSpec);
			cloneSpec.setConfig(configSpec);
			cloneSpec.setTemplate(false);
			cloneSpec.setPowerOn(true);

			vm.cloneVM_Task(dc.getVmFolder(), req.getVmName(), cloneSpec).waitForMe();

			CloneVmResponse cloneVmResponse = new CloneVmResponse();
			cloneVmResponse.setUuid(vm.getConfig().getUuid());
			cloneVmResponse.setHostMor(hostSystem.getMOR().getVal());

			return cloneVmResponse;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private Optional<Datastore> getDatastoreWithMaxFreeSpaceByVM(VirtualMachine vm) {
		Datastore datastore = null;
		try {
			for (Datastore dt : vm.getDatastores()) {
				if (null == datastore) {
					datastore = dt;
					continue;
				}

				if (dt.getSummary().accessible && "VMFS".equalsIgnoreCase(dt.getSummary().getType())
						&& dt.getInfo().getFreeSpace() > datastore.getInfo().getFreeSpace()) {
					datastore = dt;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(datastore);
	}

	private Optional<HostSystem> getHostWithLeastVMByClusterAndDataStore(
			ClusterComputeResource cluster, Datastore datastore) {

		HostSystem hostSystem = null;
		try {
			for (HostSystem hs : cluster.getHosts()) {
				if (null == hostSystem) {
					for (Datastore dt : hs.getDatastores()) {
						if (datastore.getMOR().getVal().equals(dt.getMOR().getVal())) {
							hostSystem = hs;
						}
					}
					continue;
				}

				for (Datastore store : hs.getDatastores()) {
					if (datastore.getMOR().getVal().equals(store.getMOR().getVal())
							&& hs.getVms().length < hostSystem.getVms().length) {
						hostSystem = hs;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(hostSystem);
	}

	@Override
	public void createVmSnapshot(String name, String vmName, String desc, boolean memoryFlag) {
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

	public void createVolume(CreateVolumeRequest request) {
		try {
			ServiceInstance si = getServiceInstance();
			VirtualMachine vm = null;
			if (request.getVmUUID() != null) {
				ManagedObjectReference mor = new ManagedObjectReference();
				mor.setType(VirtualMachine.class.getSimpleName());
				mor.setVal(request.getVmUUID());
				vm = new VirtualMachine(si.getServerConnection(), mor);
			}

			if (request.getVmName() != null) {
				vm = searchManagedEntity(VirtualMachine.class, request.getVmName())
						.orElseThrow(error("VM not found"));
			}

			vm = Objects.requireNonNull(vm, "VM not found");

			String vmName = vm.getName();
			VirtualMachineDeviceManager vmDeviceMgr = new VirtualMachineDeviceManager(vm);

			// Get all device
			VirtualDevice[] devices = vmDeviceMgr.getAllVirtualDevices();
			// first scsi controller
			VirtualSCSIController scsiCtrl = null;
			// scsi unit number is 16
			int scsiCountUnit = 16;

			// disk device
			//noinspection MismatchedQueryAndUpdateOfCollection
			Map<String, VirtualDisk> diskMap = new HashMap<>();
			// disk unit number list
			Set<Integer> diskUnitSet = new HashSet<>();

			Class<VirtualSCSIController> scsiClass = VirtualSCSIController.class;
			for (VirtualDevice device : devices) {
				if (scsiClass.isInstance(device)) {
					scsiCtrl = (VirtualSCSIController) device;
					break;
				}
			}

			if (scsiCtrl == null) {
				throw new Exception("Can't find scsi controller when add disk, vm name: " + vmName);
			}

			Class<VirtualDisk> diskClass = VirtualDisk.class;
			for (VirtualDevice device : devices) {
				if (device != null && device.controllerKey != null
						&& scsiCtrl.key == device.controllerKey
						&& diskClass.isInstance(device)) {
					VirtualDisk disk = (VirtualDisk) device;
					diskMap.put(getDeviceKey(disk), disk);
					diskUnitSet.add(disk.unitNumber);
				}
			}

			// get first free scsi unit number
			int firstFreeUnit = 0;
			for (int i = 0; i < scsiCountUnit; i++) {
				if (!diskUnitSet.contains(i)) {
					firstFreeUnit = i;
					break;
				}
			}

			// new backing
			VirtualDiskFlatVer2BackingInfo diskFileBacking = new VirtualDiskFlatVer2BackingInfo();
			diskFileBacking.setFileName("");
			diskFileBacking.setDiskMode(VirtualDiskMode.persistent.toString());
			diskFileBacking.setThinProvisioned(false);

			// new disk
			VirtualDisk disk = new VirtualDisk();
			disk.setControllerKey(scsiCtrl.key);
			disk.setUnitNumber(firstFreeUnit);
			disk.setBacking(diskFileBacking);
			disk.setCapacityInKB(Long.valueOf(request.getSize()) * 1024 * 1024);
			disk.setKey(-1);

			// disk spec
			VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
			diskSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
			diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.create);
			diskSpec.setDevice(disk);
			VirtualDeviceConfigSpec[] vdiskSpecArray = {diskSpec};

			// vm spec
			VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
			vmConfigSpec.setDeviceChange(vdiskSpecArray);
			Task task = vm.reconfigVM_Task(vmConfigSpec);
			if (Task.SUCCESS.equals(task.waitForTask())) {
				devices = vmDeviceMgr.getAllVirtualDevices();
				VirtualDisk addedDisk = null;
				for (VirtualDevice device : devices) {
					if (device.controllerKey != null
							&& scsiCtrl.key == device.controllerKey
							&& device.unitNumber == firstFreeUnit) {
						addedDisk = (VirtualDisk) device;
					}
				}
				if (addedDisk == null) {
					throw new Exception("New added disk not found, vm name: " + vmName);
				}
			} else {
				throw new Exception("Add new disk failed, vm name: " + vm);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public TccCapability getCapability() {
		List<HostSystem> hostMachines = searchManagedEntities(HostSystem.class);

		TccCapability capability = new TccCapability();
		for (HostSystem hostSystem : hostMachines) {
			capability.setSupportedVcpus(capability.getSupportedVcpus()
					+ hostSystem.getCapability().getMaxHostSupportedVcpus());
			capability.setSupportedMemory(capability.getSupportedMemory()
					+ hostSystem.getHardware().getMemorySize());
		}

		Function<HostSystem, HostDatastoreBrowser> getDatastoreBrowser = host -> {
			try {
				return host.getDatastoreBrowser();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};

		searchManagedEntities(HostSystem.class).stream()
				.map(getDatastoreBrowser)
				.map(HostDatastoreBrowser::getDatastores)
				.filter(ArrayUtils::isNotEmpty)
				.flatMap(Arrays::stream).forEach(datastore -> {
			capability.setSupportedStorage(capability.getSupportedStorage()
					+ datastore.getInfo().getFreeSpace());
		});

		return capability;
	}

	@Override
	public void createVolumeSnapshot(String volumeId, String name, String desc) {
		throw new UnsupportedOperationException();
	}

	@Override public String acquireTicket(String vmName) {
		return searchManagedEntity(VirtualMachine.class, vmName).map(vm -> {
			try {
				return vm.acquireTicket("webmks").getTicket();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return null;
		}).orElse("");
	}

	private static String getDeviceKey(VirtualDevice device) {
		return device.controllerKey + " -- " + device.getUnitNumber();
	}

	private boolean isVirtualMachine(VirtualMachine vm) {
		return vm != null && vm.getConfig() != null && !vm.getConfig().isTemplate();
	}

	private boolean isTemplate(VirtualMachine vm) {
		return vm != null && vm.getConfig() != null && vm.getConfig().isTemplate();
	}

}
