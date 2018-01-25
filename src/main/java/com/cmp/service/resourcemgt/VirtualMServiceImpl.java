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
		String platformId = "2186e5aa012711e895ab000c29cf4a01";
		CloudArchManager cloudArchManager = getCloudArchManager(platformId);

		String vmName = "centos" + new Random().nextInt(100);

		CreateVmRequest request = new CreateVmRequest();
		request.setVmName(vmName);
		request.setCupCount(1);
		request.setMemSizeMB(1024);
		request.setImagePath("/root/CentOS-6-x86_64-GenericCloud-1710.qcow2");

		cloudArchManager.createVirtualMachine(request);
		TccVirtualMachine vm = cloudArchManager.getVirtualMachineByName(vmName);
		PageData _pd_ = PageDataUtil.mapFromObject(vm);
		_pd_.put("platform", platformId);

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
		for (Integer id : ls) {
			PageData pd = findById(id);

			// Integer status = (Integer) pd.get("status");
			// if (Integer.valueOf(0).equals(status)) {
			// continue;
			// }

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.destroyVirtualMachine(pd.getString("name"));

			dao.delete("VirtualMMapper.deleteById", id);
		}
	}

	private CloudArchManager getCloudArchManager(String platformId) throws Exception {
		PageData pd = findPlatformById(platformId);

		TccCloudPlatform platform = new TccCloudPlatform();
		platform.setCloudplatformType(pd.getString("type"));
		platform.setCloudplatformIp(pd.getString("ip"));
		platform.setCloudplatformUser(pd.getString("username"));
		platform.setCloudplatformPassword(pd.getString("password"));

		return camAdapter.getCloudArchManagerAdaptee(platform);
	}

	private PageData findById(Integer id) throws Exception {
		return (PageData) dao.findForObject("VirtualMMapper.findById", id);
	}

	private PageData findPlatformById(String id) throws Exception {
		return (PageData) dao.findForObject("VirtualMMapper.findPlatformById", id);
	}

	private void updateVmStatusById(Integer id, Integer status) throws Exception {
		PageData pd = new PageData();
		pd.put("id", id);
		pd.put("status", status);

		dao.update("VirtualMMapper.updateStatus", pd);
	}

}
