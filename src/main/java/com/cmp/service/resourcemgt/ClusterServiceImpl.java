package com.cmp.service.resourcemgt;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 集群业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("clusterService")
public class ClusterServiceImpl implements ClusterService {

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
			dao.save("ClusterSyncMapper.save", pd);
		} else {
			dao.save("ClusterMapper.save", pd);
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
			dao.delete("ClusterSyncMapper.delete", pd);
		} else {
			dao.delete("ClusterMapper.delete", pd);
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
			dao.update("ClusterSyncMapper.edit", pd);
		} else {
			dao.update("ClusterMapper.edit", pd);
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
			return (List<PageData>) dao.findForList("ClusterSyncMapper.datalistPage", page);
		} else {
			return (List<PageData>) dao.findForList("ClusterMapper.datalistPage", page);
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
			return (List<PageData>) dao.findForList("ClusterSyncMapper.listAll", pd);
		} else {
			return (List<PageData>) dao.findForList("ClusterMapper.listAll", pd);
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
			return (PageData) dao.findForObject("ClusterSyncMapper.findById", pd);
		} else {
			return (PageData) dao.findForObject("ClusterMapper.findById", pd);
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
			dao.delete("ClusterSyncMapper.deleteAll", ArrayDATA_IDS);
		} else {
			dao.delete("ClusterMapper.deleteAll", ArrayDATA_IDS);
		}
	}

}
