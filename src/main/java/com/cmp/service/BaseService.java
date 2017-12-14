package com.cmp.service;

import java.util.List;

public interface BaseService{

	/**
     * 保存资源操作日志
     * @param operType
     * @param operObjType
     * @param operObjectId
     * @param remark
     * @return
     */
    public Long saveResOperLog(String operType,String operObjType,Long operObjectId,String remark);
	
	/**
	 * 根据主键  get 对象
	 * @param clazz
	 * @param primaryKeyValue
	 * @return
	 */
	public <T> T getEntity(Class<T> clazz,Long primaryKeyValue);
	
	/**
	 * 通过部门ID 返回 部门ID以及子部门ID 集合
	 * @param orgId
	 * @return
	 */
	public List<Long> getOrgIdAndSubIdList(Long orgId);
	
	/**
	 * 取当前登陆用户有权限的项目ID集合  ,  没有权限返回一个包含 0l 项目ID的集合 ，
	 * @return
	 */
	public List<Long> getPermProjectIdList();
	
	/**
	 * 取当前登陆用户有权限的部门及子部门ID集合  ,  没有权限返回一个包含 0l 部门ID的集合 ，
	 * @return
	 */
	public List<Long> getPermOrganIdList();
	
	/**
	 * 获取当前用户有权限可见的用户ID集合
	 * @return
	 */
	public List<Long> getPermUserList();
	
	/**
	 * 取当前用户有权限的环境ID集合
	 * @return
	 */
	public List<Long> getPermEvnList();
	
	/**
	 * 取当前用户有权限的数据中心ID集合
	 * @return
	 */
	public List<Long> getPermDataCenterList();
	
	
	/**
	 * 取当前用户有权限的集群ID集合
	 * @return
	 */
	public List<Long> getPermClusterList();
	/**
	 * 取当前用户有权限的网络ID集合
	 * @return
	 */
	public List<Long> getPermNetworkList();
}
