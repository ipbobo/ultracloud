package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

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
			if (Integer.valueOf(0).equals(status)) {
				continue;
			}

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.startVirtualMachine(pd.getString("name"));

			dao.update("CloudHostMapper.updateStatus", status);
		}
	}

	@Override
	public void stop(List<Integer> ls) throws Exception {
		for (Integer id : ls) {
			PageData pd = findById(id);

			Integer status = (Integer) pd.get("status");
			if (Integer.valueOf(0).equals(status)) {
				continue;
			}

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.stopVirtualMachine(pd.getString("name"));

			dao.update("CloudHostMapper.updateStatus", status);
		}
	}

	@Override
	public void restart(List<Integer> ls) throws Exception {
		for (Integer id : ls) {
			PageData pd = findById(id);

			Integer status = (Integer) pd.get("status");
			if (Integer.valueOf(0).equals(status)) {
				continue;
			}

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.resetVirtualMachine(pd.getString("name"));

			dao.update("CloudHostMapper.updateStatus", status);
		}
	}

	@Override
	public void suspend(List<Integer> ls) throws Exception {
		for (Integer id : ls) {
			PageData pd = findById(id);

			Integer status = (Integer) pd.get("status");
			if (Integer.valueOf(0).equals(status)) {
				continue;
			}

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.suspendVirtualMachine(pd.getString("name"));

			dao.update("CloudHostMapper.updateStatus", status);
		}
	}

	@Override
	public void resume(List<Integer> ls) throws Exception {
		for (Integer id : ls) {
			PageData pd = findById(id);

			Integer status = (Integer) pd.get("status");
			if (Integer.valueOf(0).equals(status)) {
				continue;
			}

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.resumeVirtualMachine(pd.getString("name"));

			dao.update("CloudHostMapper.updateStatus", status);
		}
	}

	@Override
	public void destroy(List<Integer> ls) throws Exception {
		for (Integer id : ls) {
			PageData pd = findById(id);

			Integer status = (Integer) pd.get("status");
			if (Integer.valueOf(0).equals(status)) {
				continue;
			}

			String platformId = pd.getString("platform");
			if (StringUtils.isBlank(platformId)) {
				continue;
			}

			CloudArchManager cloudArchManager = getCloudArchManager(platformId);
			cloudArchManager.destroyVirtualMachine(pd.getString("name"));

			dao.update("CloudHostMapper.updateStatus", status);
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
		return (PageData) dao.findForObject("CloudHostMapper.findById", id);
	}

	private PageData findPlatformById(String id) throws Exception {
		return (PageData) dao.findForObject("CloudHostMapper.findPlatformById", id);
	}

}
