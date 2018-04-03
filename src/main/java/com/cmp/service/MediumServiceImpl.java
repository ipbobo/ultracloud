package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.Medium;
import com.cmp.sid.CmpDict;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 介质业务层实现类
 * 
 * @version
 */
@Service("mediumService")
public class MediumServiceImpl implements MediumService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("MediumMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("MediumMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("MediumMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("MediumMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MediumMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MediumMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("MediumMapper.deleteAll", ArrayDATA_IDS);
	}
	
	@SuppressWarnings("unchecked")
	public List<Medium> listAllMediumByPId(PageData pd) throws Exception {
		return (List<Medium>) dao.findForList("MediumMapper.listAllMediumByPId", pd);
	}
	
	/**
	 * 查询所有软件类型
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllSoftType(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MediumMapper.listAllSoftType", pd);
	}

	//软件列表查询
	@SuppressWarnings("unchecked")
	public List<CmpDict> getSoftList() throws Exception {
		return (List<CmpDict>) dao.findForList("MediumMapper.getSoftList", null);
	}
	
	//软件参数列表查询
	@SuppressWarnings("unchecked")
	public List<CmpDict> getSoftParamList(String softCode) throws Exception{
		return (List<CmpDict>) dao.findForList("MediumMapper.getSoftParamList", softCode);
	}
}