package com.cmp.service.resourcemgt;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 虚拟机同步  机业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("virtualMachineSyncService")
public class VirtualMachineSyncServiceImpl implements VirtualMachineSyncService {

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
			dao.save("VirtualMachineSyncMapper.save", pd);
		} else {
			dao.save("VirtualMachineSyncMapper.saveVM", pd);
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
			dao.delete("VirtualMachineSyncMapper.delete", pd);
		} else {
			dao.delete("VirtualMachineMapper.delete", pd);
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
			dao.update("VirtualMachineSyncMapper.edit", pd);
		} else {
			dao.update("VirtualMachineMapper.edit", pd);
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
			return (List<PageData>) dao.findForList("VirtualMachineSyncMapper.datalistPage", page);
		} else {
			return (List<PageData>) dao.findForList("VirtualMachineMapper.datalistPage", page);
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
			return (List<PageData>) dao.findForList("VirtualMachineSyncMapper.datalistPageKVM", page);
		} else {
			return (List<PageData>) dao.findForList("VirtualMachineMapper.datalistPageKVM", page);
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
			return (List<PageData>) dao.findForList("VirtualMachineSyncMapper.listAll", pd);
		} else {
			return (List<PageData>) dao.findForList("VirtualMachineSyncMapper.listAllVM", pd);
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
			return (PageData) dao.findForObject("VirtualMachineSyncMapper.findById", pd);
		} else {
			return (PageData) dao.findForObject("VirtualMachineMapper.findById", pd);
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
			dao.delete("VirtualMachineSyncMapper.deleteAll", ArrayDATA_IDS);
		} else {
			dao.delete("VirtualMachineMapper.deleteAll", ArrayDATA_IDS);
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
			return (List<PageData>) dao.findForList("VirtualMachineSyncMapper.datalistPageVirtual", page);
		} else {
			return (List<PageData>) dao.findForList("VirtualMachineMapper.datalistPageVirtual", page);
		}
	}

	@Override
	public List<PageData> listBIVirtualBillByDay() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void saveVM(PageData pd) throws Exception {
		dao.save("VirtualMachineSyncMapper.saveVM", pd);
	}

}
