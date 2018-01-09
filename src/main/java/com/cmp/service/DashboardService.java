package com.cmp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;

//仪表盘服务
@Service
public class DashboardService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//虚机总量查询
	public Long getVirNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getVirNum", null);
	}
	
	//宿主机总量查询
	public Long getHostNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getHostNum", null);
	}
	
	//物理机总量查询
	public Long getPhysNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getPhysNum", null);
	}
	
	//用户总数查询
	public Long getUserNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getUserNum", null);
	}
	
	//项目总数查询
	public Long getProjNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getProjNum", null);
	}
	
	//工单总数查询
	public Long getWorkOrderNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getWorkOrderNum", null);
	}
}