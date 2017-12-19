package com.cmp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.CloudArchManager;
import com.cmp.mgr.CloudArchManagerAdapter;
import com.cmp.mgr.impl.OpenstatckCloudArchManager;
import com.cmp.mgr.impl.VMWareCloudArchManager;
import com.cmp.service.resourcemgt.ClusterService;
import com.cmp.service.resourcemgt.DatacenterService;
import com.cmp.service.resourcemgt.DatacenternetworkService;
import com.cmp.service.resourcemgt.HostmachineService;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.Network;

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

	@Resource(name = "storageService")
	private StorageService storageService;

	@Resource(name = "datacenternetworkService")
	private DatacenternetworkService datacenternetworkService;

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
		String platformManagerType = null;
		if("vmware".equals(cloudPD.getString("type"))) {
			List<PageData> preDatacenterList = datacenterService.listAll(cloudPD);
			List<PageData> preClusterList = clusterService.listAll(cloudPD);
			
			platformManagerType = VMWareCloudArchManager.class.getName();
			
			TccCloudPlatform platform = new TccCloudPlatform();
			platform.setCloudplatformUser(cloudPD.getString("username"));
			platform.setCloudplatformPassword(cloudPD.getString("password"));
			platform.setCloudplatformIp(cloudPD.getString("ip"));
			platform.setPlatformManagerType(platformManagerType);

			CloudArchManagerAdapter adapter = new CloudArchManagerAdapter();
			CloudArchManager cloudArchManager = adapter.getCloudArchManagerAdaptee(platform);
			
			List<Datacenter> datacenterList = cloudArchManager.getDatacenters();
			List<PageData> dcList = new ArrayList<PageData>();
			List<PageData> hostmachineList = new ArrayList<PageData>();
			List<PageData> storeList = new ArrayList<PageData>();
			List<PageData> networkList = new ArrayList<PageData>();
			Map<String, String> dcIdMap = new HashMap<String, String>();
			
			for(Datacenter dc : datacenterList) {
				String datacenterId = this.existDatacenterId(dc.getName(), preDatacenterList);
				if(null == datacenterId) {
					PageData dcPD = new PageData();
					datacenterId = UuidUtil.get32UUID();
					dcPD.put("id", datacenterId);
					dcPD.put("name", dc.getName());
					dcPD.put("type", "vmware");
					dcPD.put("cpf_id", cloudPD.getString("id"));
					dcPD.put("version", cloudPD.getString("version"));
					dcList.add(dcPD);
				} 
				dcIdMap.put(dc.getName(), datacenterId);
				
				Datastore[] store = dc.getDatastores();
				
				for(int i = 0; i< store.length; i++) {
					PageData storePD = new PageData();
					storePD.put("id", UuidUtil.get32UUID());
					storePD.put("name", store[i].getName());
					storePD.put("type", "vmware");
					storePD.put("cpf_id", cloudPD.getString("id"));
					storePD.put("datacenter_id", datacenterId);
					storePD.put("version", cloudPD.getString("version"));
					storeList.add(storePD);
				}
				
				Network[] network = dc.getNetworks();
				
				for(int i = 0; i< network.length; i++) {
					PageData networkPD = new PageData();
					networkPD.put("id", UuidUtil.get32UUID());
					networkPD.put("name", network[i].getName());
					networkPD.put("type", "vmware");
					networkPD.put("cpf_id", cloudPD.getString("id"));
					networkPD.put("datacenter_id", datacenterId);
					networkPD.put("version", cloudPD.getString("version"));
					networkList.add(networkPD);
				}
			}
			
			List<ClusterComputeResource> clusterList = cloudArchManager.getClusters();
			List<PageData> cluList = new ArrayList<PageData>();
			for(ClusterComputeResource cluster : clusterList) {
				String clusterId = existClusterId(cluster.getName(), preClusterList);
				if(null == clusterId) {
					PageData clusterPD = new PageData();
					clusterId = UuidUtil.get32UUID();
					clusterPD.put("id", clusterId);
					clusterPD.put("name", cluster.getName());
					clusterPD.put("type", "vmware");
					clusterPD.put("cpf_id", cloudPD.getString("id"));
					clusterPD.put("datacenter_id", dcIdMap.get(cluster.getParent().getParent().getName()));
					clusterPD.put("version", cloudPD.getString("version"));
					cluList.add(clusterPD);
				}
				
				
				HostSystem[] host = cluster.getHosts();
				for(int i = 0; i< host.length; i++) {
					PageData hostmachinePD = new PageData();
					hostmachinePD.put("id", UuidUtil.get32UUID());
					hostmachinePD.put("name", host[i].getName());
					hostmachinePD.put("type", "vmware");
					hostmachinePD.put("cpf_id", cloudPD.getString("id"));
					hostmachinePD.put("datacenter_id", dcIdMap.get(cluster.getParent().getParent().getName()));
					hostmachinePD.put("cluster_id", clusterId);
					hostmachinePD.put("version", cloudPD.getString("version"));
					hostmachineList.add(hostmachinePD);
				}
			}
			
			this.initCloud(dcList, cluList, hostmachineList, storeList, networkList);
		} else if("OpenStack".equals(cloudPD.getString("type"))) {
			platformManagerType = OpenstatckCloudArchManager.class.getName();
			//ToDo
		}
	}
	
	/**
	 * 更新同步数据为选中并复制到正式表中
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
			for(PageData pd : hostmachineList) {
				dao.save("HostmachineMapper.save", pd);
			}
		}
		
		if (null != storageIds && !"".equals(storageIds)) {
			String storageIds_array[] = storageIds.split(",");
			dao.update("StorageSyncMapper.updateSelectedAll", storageIds_array);
			List<PageData> storageList = (List<PageData>) dao.findForList("StorageSyncMapper.listAllByArray", storageIds_array);
			for(PageData pd : storageList) {
				dao.save("StorageMapper.save", pd);
			}
		}
		
		if (null != dcnIds && !"".equals(dcnIds)) {
			String dcnIds_array[] = dcnIds.split(",");
			dao.update("DatacenternetworkSyncMapper.updateSelectedAll", dcnIds_array);
			List<PageData> storageList = (List<PageData>) dao.findForList("DatacenternetworkSyncMapper.listAllByArray", dcnIds_array);
			for(PageData pd : storageList) {
				dao.save("DatacenternetworkMapper.save", pd);
			}
		}
	}
	
	/**
	 * 云平台同步数据保存
	 * @param dcList
	 * @param cluList
	 * @param hostmachineList
	 * @param storeList
	 * @param networkList
	 * @throws Exception 
	 */
	private void initCloud(List<PageData> dcList, List<PageData> cluList, List<PageData> hostmachineList, List<PageData> storeList, List<PageData> networkList) throws Exception {
		for(PageData pd : dcList) {
			datacenterService.save(pd);
		}
		for(PageData pd : cluList) {
			clusterService.save(pd);
		}
		for(PageData pd : hostmachineList) {
			hostmachineService.save(pd, true);
		}
		for(PageData pd : storeList) {
			storageService.save(pd, true);
		}
		for(PageData pd : networkList) {
			datacenternetworkService.save(pd, true);
		}
	}

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("DocumentMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("DocumentMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("DocumentMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DocumentMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DocumentMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DocumentMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("DocumentMapper.deleteAll", ArrayDATA_IDS);
	}
	
	private String existDatacenterId(String datacenterName, List<PageData> preDatacenterList) {
		for(PageData pd : preDatacenterList) {
			if(datacenterName.equals(pd.getString("name"))) {
				return pd.getString("id");
			}
		}
		
		return null;
	}
	
	private String existClusterId(String clusterName, List<PageData> preClusterList) {
		for(PageData pd : preClusterList) {
			if(clusterName.equals(pd.getString("name"))) {
				return pd.getString("id");
			}
		}
		
		return null;
	}
}
