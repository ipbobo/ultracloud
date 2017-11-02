package com.cmp.service;

import java.util.List;

import com.fh.entity.system.Department;

public interface OrganizationService {

	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * 
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Department> listAllDepartment(String parentId) throws Exception;
}
