package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.Organization;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * 
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Organization> listAllOrganization(String parentId) throws Exception {
		List<Organization> OrganizationList = this.listSubOrganizationByParentId(parentId);
		for (Organization depar : OrganizationList) {
			depar.setTreeurl("organization/list.do?DEPARTMENT_ID=" + depar.getDEPARTMENT_ID());
			depar.setSubDepartment(this.listAllOrganization(depar.getDEPARTMENT_ID()));
			depar.setTarget("treeFrame");
			depar.setIcon("static/images/user.gif");
		}
		return OrganizationList;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrganizationMapper.datalistPage", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrganizationMapper.findById", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Organization> listSubOrganizationByParentId(String parentId) throws Exception {
		return (List<Organization>) dao.findForList("OrganizationMapper.listSubOrganizationByParentId", parentId);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OrganizationMapper.save", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OrganizationMapper.edit", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("OrganizationMapper.delete", pd);
	}
	
	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByBianma(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrganizationMapper.findByBianma", pd);
	}
}
