package com.cmp.service.resourcemgt;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("datacenternetworkService")
public class DatacenternetworkServiceImpl implements DatacenternetworkService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd, boolean isSyncTable) throws Exception {
		if (isSyncTable) {
			dao.save("DatacenternetworkLabelMapper.save", pd);
		} else {
			dao.save("DatacenternetworkMapper.save", pd);
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
			dao.delete("DatacenternetworkLabelMapper.delete", pd);
		} else {
			dao.delete("DatacenternetworkMapper.delete", pd);
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
			dao.update("DatacenternetworkLabelMapper.edit", pd);
		} else {
			dao.update("DatacenternetworkMapper.edit", pd);
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
			return (List<PageData>) dao.findForList("DatacenternetworkLabelMapper.datalistPage", page);
		} else {
			return (List<PageData>) dao.findForList("DatacenternetworkMapper.datalistPage", page);
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
			return (List<PageData>) dao.findForList("DatacenternetworkLabelMapper.listAll", pd);
		} else {
			return (List<PageData>) dao.findForList("DatacenternetworkMapper.listAll", pd);
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
			return (PageData) dao.findForObject("DatacenternetworkLabelMapper.findById", pd);
		} else {
			return (PageData) dao.findForObject("DatacenternetworkMapper.findById", pd);
		}
	}

	@SuppressWarnings("unchecked")
	public List<PageData> findByDatacenterId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DatacenternetworkMapper.findByDatacenterId", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> findLabelByDatacenterId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DatacenternetworkLabelMapper.findLabelByDatacenterId", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS, boolean isSyncTable) throws Exception {
		if (isSyncTable) {
			dao.delete("DatacenternetworkLabelMapper.deleteAll", ArrayDATA_IDS);
		} else {
			dao.delete("DatacenternetworkMapper.deleteAll", ArrayDATA_IDS);
		}
	}

}
