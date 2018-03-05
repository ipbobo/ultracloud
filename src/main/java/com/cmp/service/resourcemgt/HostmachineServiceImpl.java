package com.cmp.service.resourcemgt;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd, boolean isSyncTable) throws Exception {
		if(isSyncTable) {
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
		if(isSyncTable) {
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
		if(isSyncTable) {
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
		if(isSyncTable) {
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
		if(isSyncTable) {
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
		if(isSyncTable) {
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
		if(isSyncTable) {
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
		if(isSyncTable) {
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
		if(isSyncTable) {
			return (List<PageData>) dao.findForList("HostmachineSyncMapper.datalistPageVirtual", page);
		} else {
			return (List<PageData>) dao.findForList("HostmachineMapper.datalistPageVirtual", page);
		}
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listAllHostId() throws Exception {
		return (List<PageData>) dao.findForList("HostmachineMapper.listAllHostId", null);
	}

}
