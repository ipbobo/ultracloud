package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import com.cmp.mgr.bean.CloneVmRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.CloudArchManager;
import com.cmp.mgr.CloudArchManagerAdapter;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("cloudHostService")
public class CloudHostServiceImpl implements CloudHostService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource
	private CloudArchManagerAdapter camAdapter;

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CloudHostMapper.listPage", page);
	}

	@Override
	public void start(List<Integer> ls) throws Exception {
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
	public void stop(List<Integer> ls) throws Exception {
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
	public void reboot(List<Integer> ls) throws Exception {
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
	public void suspend(List<Integer> ls) throws Exception {
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
	public void resume(List<Integer> ls) throws Exception {
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
	public void destroy(List<Integer> ls) throws Exception {
		for (Integer id : ls) {
			PageData pd = findById(id);

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.destroyVirtualMachine(pd.getString("name"));

			dao.delete("CloudHostMapper.deleteById", id);
		}
	}

	public void clone(List<Integer> ls) throws Exception {
		// TODO
		for (Integer id : ls) {
			PageData pd = findById(id);

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			String vmName = pd.getString("name");
			if (StringUtils.isBlank(vmName)) {
				continue;
			}

			CloneVmRequest request = new CloneVmRequest();
			request.setVmName("TestVM4");
			request.setTplName(vmName);
			request.setDcName("DC1");
			request.setCpuSize(1);
			request.setRamSize(1024);
			request.setIp("192.168.0.152");

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.cloneVirtualMachine(null);

			dao.delete("CloudHostMapper.deleteById", id);
		}
	}

	public void snapshot(List<Integer> ls) throws Exception {
		// TODO
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
		return (PageData) dao.findForObject("CloudHostMapper.findById", id);
	}

	private PageData findPlatformById(String id) throws Exception {
		return (PageData) dao.findForObject("CloudHostMapper.findPlatformById", id);
	}

	private void updateVmStatusById(Integer id, Integer status) throws Exception {
		PageData pd = new PageData();
		pd.put("id", id);
		pd.put("status", status);

		dao.update("CloudHostMapper.updateStatus", pd);
	}

}
