package com.cmp.service;

import static java.util.stream.Collectors.toList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.entity.tcc.TccDatastore;
import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.mgr.CloudArchManager;
import com.cmp.mgr.CloudArchManagerAdapter;
import com.cmp.mgr.vmware.VMWareCloudArchManager;
import com.cmp.service.resourcemgt.ClusterService;
import com.cmp.service.resourcemgt.DatacenterService;
import com.cmp.service.resourcemgt.DatacenternetworkService;
import com.cmp.service.resourcemgt.HostmachineService;
import com.cmp.service.resourcemgt.VirtualBindingService;
import com.cmp.service.resourcemgt.VirtualMachineSyncService;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * 资源管理业务层实现类
 * 
 * @version
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "datacenterService")
	private DatacenterService datacenterService;

	@Resource(name = "clusterService")
	private ClusterService clusterService;

	@Resource(name = "hostmachineService")
	private HostmachineService hostmachineService;

	@Resource(name = "virtualMachineSyncService")
	private VirtualMachineSyncService virtualMachineSyncService;

	@Resource(name = "virtualBindingService")
	private VirtualBindingService virtualBindingService;

	@Resource(name = "storageService")
	private StorageService storageService;

	@Resource(name = "datacenternetworkService")
	private DatacenternetworkService datacenternetworkService;

	/**
	 * 连接云平台
	 * 
	 * @param cloudPD
	 * @return
	 */
	public CloudArchManager initCloud(PageData cloudPD) {
		String platformManagerType = null;
		CloudArchManager cloudArchManager = null;
		if ("vmware".equals(cloudPD.getString("type"))) {
			platformManagerType = VMWareCloudArchManager.class.getName();

			TccCloudPlatform platform = new TccCloudPlatform();
			platform.setCloudplatformUser(cloudPD.getString("username"));
			platform.setCloudplatformPassword(cloudPD.getString("password"));
			platform.setCloudplatformIp(cloudPD.getString("ip"));
			platform.setPlatformManagerType(platformManagerType);

			CloudArchManagerAdapter adapter = new CloudArchManagerAdapter();
			cloudArchManager = adapter.getCloudArchManagerAdaptee(platform);
		}

		return cloudArchManager;
	}

	public List<Datacenter> syncDatacenter(CloudArchManager cloudArchManager) {
		return cloudArchManager.getDatacenters();
	}

	public List<TccDatastore> syncDataStore(Datacenter datacenter) {
		// 同步存储
		Datastore[] store = datacenter.getDatastores();
		List<TccDatastore> list = new ArrayList<TccDatastore>();
		for (int i = 0; i < store.length; i++) {
			TccDatastore ds = new TccDatastore();
			ds.setCapacity(store[i].getSummary().getCapacity());
			ds.setFreeSpace(store[i].getSummary().getFreeSpace() / 1024 / 1024 / 1024);
			ds.setMaxFileSize(store[i].getSummary().getCapacity() / 1024 / 1024 / 1024);
			ds.setName(store[i].getName());
			ds.setUuid(store[i].getMOR().get_value());

			list.add(ds);
		}

		return list;
	}

	/**
	 * 同步云平台数据
	 * 
	 * @param type
	 * @param ip
	 * @param useranme
	 * @param password
	 * @throws Exception
	 */
	public void syncCloudData(PageData cloudPD) throws Exception {
		if ("vmware".equals(cloudPD.getString("type"))) {
			List<PageData> preDatacenterList = datacenterService.listAll(cloudPD);
			List<PageData> preClusterList = clusterService.listAll(cloudPD);
			List<PageData> preHostmachineList = hostmachineService.listAll(cloudPD, false);
			List<PageData> preVirtualMachineList = virtualMachineSyncService.listAll(cloudPD, true);
			List<PageData> preStorageList = storageService.listAll(cloudPD, false);
			List<PageData> preDatacenterNetworkList = datacenternetworkService.listAll(cloudPD, true);

			// 连接云平台
			CloudArchManager cloudArchManager = this.initCloud(cloudPD);

			List<Datacenter> datacenterList = cloudArchManager.getDatacenters();
			List<PageData> dcList = new ArrayList<PageData>();
			List<PageData> cluList = new ArrayList<PageData>();
			List<PageData> hostmachineList = new ArrayList<PageData>();
			List<PageData> vmList = new ArrayList<PageData>();
			List<PageData> storeList = new ArrayList<PageData>();
			List<PageData> networkList = new ArrayList<PageData>();
			Map<String, String> dcIdMap = new HashMap<String, String>();

			// 同步数据中心
			for (Datacenter dc : datacenterList) {
				String datacenterId = this.existUuid(dc.getMOR().get_value(), preDatacenterList);
				if (null == datacenterId) {
					PageData dcPD = new PageData();
					datacenterId = UuidUtil.get32UUID();
					dcPD.put("id", datacenterId);
					dcPD.put("name", dc.getName());
					dcPD.put("uuid", dc.getMOR().get_value());
					dcPD.put("type", "vmware");
					dcPD.put("cpf_id", cloudPD.getString("id"));
					dcPD.put("version", cloudPD.getString("version"));
					dcList.add(dcPD);
				}
				dcIdMap.put(dc.getName(), datacenterId);

				// 同步集群
				ManagedEntity[] childEntitys = dc.getHostFolder().getChildEntity();
				List<ClusterComputeResource> clusterList = Arrays.stream(childEntitys).map(ClusterComputeResource.class::cast).collect(toList());
				for (ClusterComputeResource cluster : clusterList) {
					String clusterUuid = cluster.getMOR().get_value();
					String clusterId = this.existUuid(clusterUuid, preClusterList);
					if (null == clusterId) {
						PageData clusterPD = new PageData();
						clusterId = UuidUtil.get32UUID();
						clusterPD.put("id", clusterId);
						clusterPD.put("name", cluster.getName());
						clusterPD.put("uuid", clusterUuid);
						clusterPD.put("type", "vmware");
						clusterPD.put("cpf_id", cloudPD.getString("id"));
						clusterPD.put("datacenter_id", datacenterId);
						clusterPD.put("version", cloudPD.getString("version"));
						cluList.add(clusterPD);
					}

					// 同步宿主机
					HostSystem[] host = cluster.getHosts();
					for (int i = 0; i < host.length; i++) {
						PageData hostmachinePD = new PageData();
						String hostmachineUuid = host[i].getMOR().get_value();
						String hostmachineId = this.existUuid(hostmachineUuid, preHostmachineList);
						// 总cpu数
						double cpuTotal = Double.valueOf(host[i].getHardware().getCpuInfo().getNumCpuCores())
								* (host[i].getHardware().getCpuInfo().getHz() / 1000 / 1000 / 1000);
						// 剩余cpu数
						// double cpuCoreRemainCount =
						// Double.valueOf(host[i].getHardware().getCpuInfo().getNumCpuCores());
						// 内存
						double memorySize = new Double(host[i].getHardware().getMemorySize() / 1024 / 1024 / 1024);
						if (null == hostmachineId) {
							hostmachineId = UuidUtil.get32UUID();
							hostmachinePD.put("id", hostmachineId);
							hostmachinePD.put("name", host[i].getName());
							hostmachinePD.put("uuid", hostmachineUuid);
							hostmachinePD.put("type", "vmware");
							hostmachinePD.put("cpf_id", cloudPD.getString("id"));
							hostmachinePD.put("datacenter_id", datacenterId);
							hostmachinePD.put("cluster_id", clusterId);
							hostmachinePD.put("version", cloudPD.getString("version"));
							hostmachinePD.put("ip", host[i].getName());
							hostmachinePD.put("cpu", cpuTotal);
							hostmachinePD.put("memory", memorySize);
							hostmachineList.add(hostmachinePD);
						}

						// 同步虚拟机
						VirtualMachine[] virtualMachine = host[i].getVms();
						for (int j = 0; j < virtualMachine.length; j++) {
							PageData vmPD = new PageData();
							String vmUuid = virtualMachine[j].getMOR().get_value();
							BigInteger vmId = this.existUuid2(vmUuid, preVirtualMachineList);
							if (null == vmId) {
								vmPD.put("name", virtualMachine[j].getName());
								vmPD.put("uuid", vmUuid);
								vmPD.put("type", "vmware");
								vmPD.put("cpf_id", cloudPD.getString("id"));
								vmPD.put("datacenter_id", datacenterId);
								vmPD.put("cluster_id", clusterId);
								vmPD.put("hostmachine_id", hostmachineId);
								vmPD.put("version", cloudPD.getString("version"));
								vmPD.put("ip", virtualMachine[j].getGuest().getIpAddress());
								vmPD.put("cpu", virtualMachine[j].getConfig().getHardware().getNumCPU());
								vmPD.put("memory", virtualMachine[j].getConfig().getHardware().getMemoryMB() / 1024);

								// 虚拟机磁盘已用空间
								long committed = virtualMachine[j].getSummary().storage.committed;
								vmPD.put("use_datadisk", committed / 1024 / 1024 / 1024);
								// 虚拟机磁盘部空间
								long uncommitted = virtualMachine[j].getSummary().storage.uncommitted;
								vmPD.put("datadisk", (committed + uncommitted) / 1024 / 1024 / 1024);

								vmPD.put("platform", cloudPD.get("id"));
								String statusStr = virtualMachine[j].getGuest().getGuestState();
								Integer status = null;
								if ("running".equals(statusStr)) {
									status = 0;
								} else if ("notRunning".equals(statusStr)) {
									status = 2;
								} else {
									status = 1;
								}
								vmPD.put("status", status);
								vmList.add(vmPD);
							}
						}

					}

				}

				// 同步存储
				Datastore[] store = dc.getDatastores();
				for (int i = 0; i < store.length; i++) {
					PageData storePD = new PageData();
					String storeUuid = store[i].getMOR().get_value();
					String storeId = this.existUuid(storeUuid, preStorageList);
					if (null == storeId) {
						storeId = UuidUtil.get32UUID();
						storePD.put("id", storeId);
						storePD.put("name", store[i].getName());
						storePD.put("uuid", storeUuid);
						storePD.put("type", "vmware");
						storePD.put("cpf_id", cloudPD.getString("id"));
						storePD.put("datacenter_id", datacenterId);
						storePD.put("version", cloudPD.getString("version"));
						storePD.put("allspace", store[i].getSummary().getCapacity() / 1024 / 1024 / 1024);
						storePD.put("freespace", store[i].getSummary().getFreeSpace() / 1024 / 1024 / 1024);
						storeList.add(storePD);
					}
				}

				// 同步网络标签
				Network[] network = dc.getNetworks();
				for (int i = 0; i < network.length; i++) {
					PageData networkPD = new PageData();
					String networkUuid = network[i].getMOR().get_value();
					String networkId = this.existUuid(networkUuid, preDatacenterNetworkList);

					if (null == networkId) {
						networkId = UuidUtil.get32UUID();
						networkPD.put("id", networkId);
						networkPD.put("label", network[i].getName());
						networkPD.put("uuid", networkUuid);
						networkPD.put("type", "vmware");
						networkPD.put("cpf_id", cloudPD.getString("id"));
						networkPD.put("datacenter_id", datacenterId);
						// networkPD.put("label", network[i].getMOR().getVal());

						networkList.add(networkPD);
					}

				}
			}

			this.initCloud(dcList, cluList, hostmachineList, storeList, networkList, vmList, cloudPD);
		} else if ("OpenStack".equals(cloudPD.getString("type"))) {
			// platformManagerType = OpenstatckCloudArchManager.class.getName();
			// ToDo
		}
	}

	/**
	 * 同步镜像模板
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<TccVirtualMachine> syncMirroTemplate(PageData cloudPD) throws Exception {
		String platformManagerType = null;
		List<TccVirtualMachine> vmtList = null;
		if ("vmware".equals(cloudPD.getString("type"))) {

			platformManagerType = VMWareCloudArchManager.class.getName();

			TccCloudPlatform platform = new TccCloudPlatform();
			platform.setCloudplatformUser(cloudPD.getString("username"));
			platform.setCloudplatformPassword(cloudPD.getString("password"));
			platform.setCloudplatformIp(cloudPD.getString("ip"));
			platform.setPlatformManagerType(platformManagerType);

			CloudArchManagerAdapter adapter = new CloudArchManagerAdapter();
			CloudArchManager cloudArchManager = adapter.getCloudArchManagerAdaptee(platform);
			vmtList = cloudArchManager.getVmTemplates();

		} else if ("OpenStack".equals(cloudPD.getString("type"))) {
			// ToDo
		}

		return vmtList;
	}

	/**
	 * 更新同步数据为选中并复制到正式表中
	 * 
	 * @param hostmachineIds
	 * @param storageIds
	 * @param dcnIds
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateSelectData(String hostmachineIds, String storageIds, String dcnIds) throws Exception {
		if (null != hostmachineIds && !"".equals(hostmachineIds)) {
			String hostmachineIds_array[] = hostmachineIds.split(",");
			dao.update("HostmachineSyncMapper.updateSelectedAll", hostmachineIds_array);
			List<PageData> hostmachineList = (List<PageData>) dao.findForList("HostmachineSyncMapper.listAllByArray", hostmachineIds_array);
			hostmachineList.forEach(e -> {
				try {
					dao.save("HostmachineMapper.save", e);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			});
		}

		if (null != storageIds && !"".equals(storageIds)) {
			String storageIds_array[] = storageIds.split(",");
			dao.update("StorageSyncMapper.updateSelectedAll", storageIds_array);
			List<PageData> storageList = (List<PageData>) dao.findForList("StorageSyncMapper.listAllByArray", storageIds_array);
			storageList.forEach(e -> {
				try {
					dao.save("StorageMapper.save", e);
				} catch (Exception e3) {
					throw new RuntimeException(e3);
				}
			});
		}

		// if (null != dcnIds && !"".equals(dcnIds)) {
		// String dcnIds_array[] = dcnIds.split(",");
		// dao.update("DatacenternetworkLabelMapper.updateSelectedAll",
		// dcnIds_array);
		// List<PageData> storageList = (List<PageData>)
		// dao.findForList("DatacenternetworkLabelMapper.listAllByArray",
		// dcnIds_array);
		// for (PageData pd : storageList) {
		// dao.save("DatacenternetworkMapper.save", pd);
		// }
		// }
	}

	/**
	 * 云平台同步数据保存
	 * 
	 * @param dcList
	 * @param cluList
	 * @param hostmachineList
	 * @param storeList
	 * @param networkList
	 * @throws Exception
	 */
	private void initCloud(List<PageData> dcList, List<PageData> cluList, List<PageData> hostmachineList, List<PageData> storeList,
			List<PageData> networkList, List<PageData> vmList, PageData cloudPD) throws Exception {
		for (PageData pd : dcList) {
			datacenterService.save(pd);
		}
		for (PageData pd : cluList) {
			clusterService.save(pd);
		}
		for (PageData pd : hostmachineList) {
			hostmachineService.save(pd, true);
		}
		for (PageData pd : storeList) {
			storageService.save(pd, true);
		}
		for (PageData pd : networkList) {
			datacenternetworkService.save(pd, true);
		}
		for (PageData pd : vmList) {
			List<PageData> alreadyBindedVM = virtualBindingService.datalistAlreadyBinded(cloudPD);
			Map<String, PageData> abVMMap = new HashMap<String, PageData>();
			if (null != alreadyBindedVM) {
				for (PageData abVM : alreadyBindedVM) {
					abVMMap.put(abVM.getString("uuid"), abVM);
				}
			}

			String uuid = pd.getString("uuid");
			PageData tempPD = abVMMap.get(uuid);
			if (null != tempPD) {
				pd.put("appNo", tempPD.getString("appNo"));
				pd.put("user", tempPD.getString("user"));
				pd.put("project_id", tempPD.getString("project_id"));
			}

			virtualMachineSyncService.save(pd, true);
		}
	}

	/**
	 * 删除虚拟机时进行资源释放
	 * 
	 * @param vmId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void releaseResource(String type, String[] vmId) throws Exception {
		switch (type) {
		case "vmware":
			List<PageData> vmwareVMList = (List<PageData>) dao.findForList("VirtualMachineSyncMapper.listVmwareByvmId", vmId);
			Map<String, PageData> cloudMap = new HashMap<String, PageData>();
			for (PageData pd : vmwareVMList) {
				String cpf_id = pd.getString("cpf_id");
				cloudMap.put(cpf_id, pd);
			}

			for (Map.Entry<String, PageData> entity : cloudMap.entrySet()) {
				PageData cloudPD = entity.getValue();
				CloudArchManager cloudArchManager = this.initCloud(cloudPD);
				List<Datacenter> dcList = cloudArchManager.getDatacenters();
				if (null != dcList) {
					for (Datacenter dc : dcList) {
						List<TccDatastore> list = this.syncDataStore(dc);
						for(TccDatastore ds : list) {
							
						}
						
						List<PageData> preStorageList = storageService.listAll(cloudPD, false);

					}
				}
			}

			break;
		case "kvm":
			List<PageData> kvmVMList = (List<PageData>) dao.findForList("VirtualMachineSyncMapper.listKVMByvmId", vmId);
			// ToDo
			break;
		default:
			break;
		}
	}

	private String existUuid(String uuid, List<PageData> preList) {
		for (PageData pd : preList) {
			if (uuid.equals(pd.getString("uuid"))) {
				return pd.getString("id");
			}
		}

		return null;
	}

	private BigInteger existUuid2(String uuid, List<PageData> preList) {
		for (PageData pd : preList) {
			if (uuid.equals(pd.getString("uuid"))) {
				return (BigInteger) pd.get("id");
			}
		}

		return null;
	}
}
