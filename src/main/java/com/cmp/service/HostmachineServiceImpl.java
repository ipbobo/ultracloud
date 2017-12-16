package com.cmp.service;

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
	public void save(PageData pd) throws Exception {
		dao.save("HostmachineMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("HostmachineMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("HostmachineMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HostmachineMapper.datalistPage", page);
	}
	
	/**
	 * KVM列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listKVM(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HostmachineMapper.datalistPageKVM", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HostmachineMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HostmachineMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("HostmachineMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 查询虚拟机列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listVirtual(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HostmachineMapper.datalistPageVirtual", page);
	}

}
