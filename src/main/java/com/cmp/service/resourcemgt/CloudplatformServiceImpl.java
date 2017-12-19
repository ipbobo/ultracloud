package com.cmp.service.resourcemgt;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 云平台   业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("cloudplatformService")
public class CloudplatformServiceImpl implements CloudplatformService {

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
			dao.save("CloudplatformSyncMapper.save", pd);
		} else {
			dao.save("CloudplatformMapper.save", pd);
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
			dao.delete("CloudplatformSyncMapper.delete", pd);
		} else {
			dao.delete("CloudplatformMapper.delete", pd);
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
			dao.update("CloudplatformSyncMapper.edit", pd);
		} else {
			dao.update("CloudplatformMapper.edit", pd);
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
			return (List<PageData>) dao.findForList("CloudplatformSyncMapper.datalistPage", page);
		} else {
			return (List<PageData>) dao.findForList("CloudplatformMapper.datalistPage", page);
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
			return (List<PageData>) dao.findForList("CloudplatformSyncMapper.listAll", pd);
		} else {
			return (List<PageData>) dao.findForList("CloudplatformMapper.listAll", pd);
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
			return (PageData) dao.findForObject("CloudplatformSyncMapper.findById", pd);
		} else {
			return (PageData) dao.findForObject("CloudplatformMapper.findById", pd);
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
			dao.delete("CloudplatformSyncMapper.deleteAll", ArrayDATA_IDS);
		} else {
			dao.delete("CloudplatformMapper.deleteAll", ArrayDATA_IDS);
		}
	}

}
