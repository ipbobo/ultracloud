package com.cmp.service.resourcemgt;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.mgr.CloudArchManager;
import com.cmp.mgr.CloudArchManagerAdapter;
import com.cmp.mgr.kvm.KvmCloudArchManager;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 宿主机业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("hostmachineService")
public class HostmachineServiceImpl implements HostmachineService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "virtualMachineSyncService")
	private VirtualMachineSyncService virtualMachineSyncService;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd, boolean isSyncTable) throws Exception {
		if (isSyncTable) {
			dao.save("HostmachineSyncMapper.save", pd);
		} else {
			dao.save("HostmachineMapper.save", pd);
		}

	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd, boolean isSyncTable) throws Exception {
		if (isSyncTable) {
			dao.delete("HostmachineSyncMapper.delete", pd);
		} else {
			dao.delete("HostmachineMapper.delete", pd);
		}

	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd, boolean isSyncTable) throws Exception {
		if (isSyncTable) {
			dao.update("HostmachineSyncMapper.edit", pd);
		} else {
			dao.update("HostmachineMapper.edit", pd);
		}

	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page, boolean isSyncTable) throws Exception {
		if (isSyncTable) {
			return (List<PageData>) dao.findForList("HostmachineSyncMapper.datalistPage", page);
		} else {
			return (List<PageData>) dao.findForList("HostmachineMapper.datalistPage", page);
		}
	}

	/**
	 * KVM列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listKVM(Page page, boolean isSyncTable) throws Exception {
		if (isSyncTable) {
			return (List<PageData>) dao.findForList("HostmachineSyncMapper.datalistPageKVM", page);
		} else {
			return (List<PageData>) dao.findForList("HostmachineMapper.datalistPageKVM", page);
		}

	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd, boolean isSyncTable) throws Exception {
		if (isSyncTable) {
			return (List<PageData>) dao.findForList("HostmachineSyncMapper.listAll", pd);
		} else {
			return (List<PageData>) dao.findForList("HostmachineMapper.listAll", pd);
		}
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd, boolean isSyncTable) throws Exception {
		if (isSyncTable) {
			return (PageData) dao.findForObject("HostmachineSyncMapper.findById", pd);
		} else {
			return (PageData) dao.findForObject("HostmachineMapper.findById", pd);
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS, boolean isSyncTable) throws Exception {
		if (isSyncTable) {
			dao.delete("HostmachineSyncMapper.deleteAll", ArrayDATA_IDS);
		} else {
			dao.delete("HostmachineMapper.deleteAll", ArrayDATA_IDS);
		}
	}

	/**
	 * 查询虚拟机列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listVirtual(Page page, boolean isSyncTable) throws Exception {
		if (isSyncTable) {
			return (List<PageData>) dao.findForList("HostmachineSyncMapper.datalistPageVirtual", page);
		} else {
			return (List<PageData>) dao.findForList("HostmachineMapper.datalistPageVirtual", page);
		}
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listAllHostId() throws Exception {
		return (List<PageData>) dao.findForList("HostmachineMapper.listAllHostId", null);
	}

	@Override
	public void syncKVMData(PageData kvmPD) throws Exception {
		PageData kvmHostPD = new PageData();
		kvmHostPD.put("hostmachine_id", kvmPD.getString("id"));
		List<PageData> preVirtualMachineList = virtualMachineSyncService.listAll(kvmHostPD, false);

		String platformManagerType = KvmCloudArchManager.class.getName();

		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformUser(kvmPD.getString("username"));
		platform.setCloudplatformPassword(kvmPD.getString("password"));
		platform.setCloudplatformIp(kvmPD.getString("ip"));
		platform.setCloudplatformPort((Long) kvmPD.get("port") + "");
		platform.setPlatformManagerType(platformManagerType);

		CloudArchManagerAdapter adapter = new CloudArchManagerAdapter();
		CloudArchManager cloudArchManager = adapter.getCloudArchManagerAdaptee(platform);

		List<TccVirtualMachine> vmList = cloudArchManager.getVirtualMachines();
		if ((null != vmList) && vmList.size() > 0)
			for (int j = 0; j < vmList.size(); j++) {
				PageData vmPD = new PageData();
				String vmUuid = vmList.get(j).getUUID();
				BigInteger vmId = this.existUuid(vmUuid, preVirtualMachineList);
				if (null == vmId) {
					vmPD.put("name", vmList.get(j).getName());
					vmPD.put("uuid", vmUuid);
					vmPD.put("type", "kvm");
					vmPD.put("hostmachine_id", kvmPD.getString("id"));
					vmPD.put("ip", vmList.get(j).getIpAddress());
					vmPD.put("cpu", vmList.get(j).getVcpus());
					vmPD.put("memory", vmList.get(j).getMemory() * 1024);
					vmPD.put("status", (null == vmList.get(j).getState()) ? 2 : vmList.get(j).getState());
					virtualMachineSyncService.save(vmPD, false);
				}
			}
	}

	private BigInteger existUuid(String uuid, List<PageData> preList) {
		for (PageData pd : preList) {
			if (uuid.equals(pd.getString("uuid"))) {
				return (BigInteger) pd.get("id");
			}
		}

		return null;
	}

}
