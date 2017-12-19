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
	public void syncCloudData(String type, String ip, String username, String password, String cpf_id, String version) throws Exception {
		String platformManagerType = null;
		if("vmware".equals(type)) {
			platformManagerType = VMWareCloudArchManager.class.getName();
			
			TccCloudPlatform platform = new TccCloudPlatform();
			platform.setCloudplatformUser(username);
			platform.setCloudplatformPassword(password);
			platform.setCloudplatformIp(ip);
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
				PageData dcPD = new PageData();
				String dcId = UuidUtil.get32UUID();
				dcPD.put("id", dcId);
				dcPD.put("name", dc.getName());
				dcPD.put("type", "vmware");
				dcPD.put("cpf_id", cpf_id);
				dcPD.put("version", version);
				dcList.add(dcPD);
				dcIdMap.put(dc.getName(), dcId);
				
				Datastore[] store = dc.getDatastores();
				
				for(int i = 0; i< store.length; i++) {
					PageData storePD = new PageData();
					storePD.put("id", UuidUtil.get32UUID());
					storePD.put("name", store[i].getName());
					storePD.put("type", "vmware");
					storePD.put("cpf_id", cpf_id);
					storePD.put("datacenter_id", dcId);
					storePD.put("version", version);
					storeList.add(storePD);
				}
				
				Network[] network = dc.getNetworks();
				
				for(int i = 0; i< network.length; i++) {
					PageData networkPD = new PageData();
					networkPD.put("id", UuidUtil.get32UUID());
					networkPD.put("name", network[i].getName());
					networkPD.put("type", "vmware");
					networkPD.put("cpf_id", cpf_id);
					networkPD.put("datacenter_id", dcId);
					networkPD.put("version", version);
					networkList.add(networkPD);
				}
			}
			
			List<ClusterComputeResource> clusterList = cloudArchManager.getClusters();
			List<PageData> cluList = new ArrayList<PageData>();
			for(ClusterComputeResource cluster : clusterList) {
				PageData clusterPD = new PageData();
				String cluster_id = UuidUtil.get32UUID();
				clusterPD.put("id", cluster_id);
				clusterPD.put("name", cluster.getName());
				clusterPD.put("type", "vmware");
				clusterPD.put("cpf_id", cpf_id);
				clusterPD.put("datacenter_id", dcIdMap.get(cluster.getParent().getParent().getName()));
				clusterPD.put("version", version);
				cluList.add(clusterPD);
				
				HostSystem[] host = cluster.getHosts();
				for(int i = 0; i< host.length; i++) {
					PageData hostmachinePD = new PageData();
					hostmachinePD.put("id", UuidUtil.get32UUID());
					hostmachinePD.put("name", host[i].getName());
					hostmachinePD.put("type", "vmware");
					hostmachinePD.put("cpf_id", cpf_id);
					hostmachinePD.put("datacenter_id", dcIdMap.get(cluster.getParent().getParent().getName()));
					hostmachinePD.put("cluster_id", cluster_id);
					hostmachinePD.put("version", version);
					hostmachineList.add(hostmachinePD);
				}
			}
			
			this.initCloud(dcList, cluList, hostmachineList, storeList, networkList);
		} else if("OpenStack".equals(type)) {
			platformManagerType = OpenstatckCloudArchManager.class.getName();
			//ToDo
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
			datacenterService.save(pd, true);
		}
		for(PageData pd : cluList) {
			clusterService.save(pd, true);
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
}
