package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 存储业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("storageService")
public class StorageServiceImpl implements StorageService {

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
			dao.save("StorageSyncMapper.save", pd);
		} else {
			dao.save("StorageMapper.save", pd);
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
			dao.delete("StorageSyncMapper.delete", pd);
		} else {
			dao.delete("StorageMapper.delete", pd);
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
			dao.update("StorageSyncMapper.edit", pd);
		} else {
			dao.update("StorageMapper.edit", pd);
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
			return (List<PageData>) dao.findForList("StorageSyncMapper.datalistPage", page);
		} else {
			return (List<PageData>) dao.findForList("StorageMapper.datalistPage", page);
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
			return (List<PageData>) dao.findForList("StorageSyncMapper.listAll", pd);
		} else {
			return (List<PageData>) dao.findForList("StorageMapper.listAll", pd);
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
			return (PageData) dao.findForObject("StorageSyncMapper.findById", pd);
		} else {
			return (PageData) dao.findForObject("StorageMapper.findById", pd);
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
			dao.delete("StorageSyncMapper.deleteAll", ArrayDATA_IDS);
		} else {
			dao.delete("StorageMapper.deleteAll", ArrayDATA_IDS);
		}
	}

}
