package com.cmp.service.resourcemgt;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.mgr.CloudArchManager;
import com.cmp.mgr.CloudArchManagerAdapter;
import com.cmp.mgr.bean.CreateVmRequest;
import com.cmp.util.PageDataUtil;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 虚拟机 机业务层实现类
 * 
 * @author liuweixing
 *
 */
@SuppressWarnings("Duplicates")
@Service("virtualMService")
public class VirtualMServiceImpl implements VirtualMService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource
	private CloudArchManagerAdapter camAdapter;

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("VirtualMMapper.datalistPage", page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> vmList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("VirtualMMapper.vmlistPage", page);
	}

	@Override
	public void createVm(PageData pd) throws Exception {
		String hostId = pd.getString("host");
		CloudArchManager cloudArchManager = getCloudArchManager(hostId);

		String vname = pd.getString("vname");

		CreateVmRequest request = new CreateVmRequest();
		request.setVmName(vname);
		request.setCupCount(Integer.valueOf(pd.getString("vcpus")));
		request.setMemSizeMB(Long.valueOf(pd.getString("vmemory")));
		request.setDiskSizeKB(Long.valueOf(pd.getString("vdisk")));
		request.setImagePath(pd.getString("vimage"));

		cloudArchManager.createVirtualMachine(request);
		TccVirtualMachine vm = cloudArchManager.getVirtualMachineByName(vname);
		PageData _pd_ = PageDataUtil.mapFromObject(vm);
		_pd_.put("platform", hostId);

		dao.save("VirtualMMapper.addVirtualMachine", _pd_);
	}

	@Override
	public void startVm(List<Integer> ls) throws Exception {
		for (Integer id : ls) {
			PageData pd = findById(id);

			Integer status = (Integer) pd.get("status");
			if (!Integer.valueOf(2).equals(status)) { // 非停止状态不可启动
				continue;
			}

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.startVirtualMachine(pd.getString("name"));

			updateVmStatusById(id, 0);
		}
	}

	@Override
	public void stopVm(List<Integer> ls) throws Exception {
		for (Integer id : ls) {
			PageData pd = findById(id);

			Integer status = (Integer) pd.get("status");
			if (!Integer.valueOf(0).equals(status)) { // 非运行状态不可停止
				continue;
			}

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.stopVirtualMachine(pd.getString("name"));

			updateVmStatusById(id, 2);
		}
	}

	@Override
	public void rebootVm(List<Integer> ls) throws Exception {
		for (Integer id : ls) {
			PageData pd = findById(id);

			Integer status = (Integer) pd.get("status");
			if (!Integer.valueOf(0).equals(status)) { // 非运行状态不可重启
				continue;
			}

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.rebootVirtualMachine(pd.getString("name"));

			updateVmStatusById(id, 0);
		}
	}

	@Override
	public void suspendVm(List<Integer> ls) throws Exception {
		for (Integer id : ls) {
			PageData pd = findById(id);

			Integer status = (Integer) pd.get("status");
			if (!Integer.valueOf(0).equals(status)) { // 非运行状态不可挂起
				continue;
			}

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.suspendVirtualMachine(pd.getString("name"));

			updateVmStatusById(id, 1);
		}
	}

	@Override
	public void resumeVm(List<Integer> ls) throws Exception {
		for (Integer id : ls) {
			PageData pd = findById(id);

			Integer status = (Integer) pd.get("status");
			if (!Integer.valueOf(1).equals(status)) { // 非挂起状态不可恢复
				continue;
			}

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.resumeVirtualMachine(pd.getString("name"));

			updateVmStatusById(id, 0);
		}
	}

	@Override
	public void destroyVm(List<Integer> ls) throws Exception {
		//System.setProperty("jna.library.path", "C:\\Program Files\\VirtViewer v6.0-256\\bin");
		for (Integer id : ls) {
			PageData pd = findById(id);

			// Integer status = (Integer) pd.get("status");
			// if (Integer.valueOf(0).equals(status)) {
			// continue;
			// }
			String platformId = pd.getString("hostmachine_id");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.destroyVirtualMachine(pd.getString("name"));

			dao.delete("VirtualMMapper.deleteById", id);
		}
	}

	private CloudArchManager getCloudArchManager(String platformId) throws Exception {
		PageData pd = findHostMachineById(platformId);

		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformIp(pd.getString("ip"));
		platform.setCloudplatformType("kvm");

		return camAdapter.getCloudArchManagerAdaptee(platform);
	}

	private PageData findById(Integer id) throws Exception {
		return (PageData) dao.findForObject("VirtualMMapper.findById", id);
	}

	private PageData findHostMachineById(String id) throws Exception {
		return (PageData) dao.findForObject("VirtualMMapper.findHostMachineById", id);
	}

	private void updateVmStatusById(Integer id, Integer status) throws Exception {
		PageData pd = new PageData();
		pd.put("id", id);
		pd.put("status", status);

		dao.update("VirtualMMapper.updateStatus", pd);
	}

}
