package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cmp.service.resourcemgt.ClusterService;
import com.cmp.service.resourcemgt.DatacenterService;
import com.cmp.service.servicemgt.EnvironmentService;
import com.cmp.sid.CmpRes;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.util.PageData;

@Service
public class ResviewService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource
	private CmpDictService cmpDictService;
	@Resource
	private EnvironmentService environmentService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private ProjectService projectService;
	@Resource
	private DatacenterService datacenterService;
	@Resource
	private ClusterService clusterService;
	
	//资源详细信息查询
	public CmpRes getCmpResDtl(String operType, String bizviewType, String subBizviewType) throws Exception{
		CmpRes cmpRes=new CmpRes();
		cmpRes.setBizviewType(bizviewType);
		cmpRes.setBizviewTypeName(StringUtils.isBlank(bizviewType)?"全部数据中心":(String)datacenterService.findById(new PageData("id", bizviewType)).get("name"));
		cmpRes.setSubBizviewType(subBizviewType);
		cmpRes.setSubBizviewTypeName(null);
		PageData pd = new PageData("bizviewType", bizviewType, "subBizviewType", subBizviewType);
		getTotalNum(pd, cmpRes);//总量查询(cpu、内存、磁盘)
		getUseNum(pd, cmpRes);//使用量查询(cpu、内存、磁盘)
		return cmpRes;
	}
	
	//总量查询(cpu、内存、磁盘)
	public void getTotalNum(PageData pd, CmpRes cmpRes) throws Exception {
		CmpRes cr=(CmpRes) dao.findForObject("ResviewMapper.getTotalNum", pd);
		cmpRes.setCpuTotalNum(cr.getCpuTotalNum());
		cmpRes.setMemTotalNum(cr.getMemTotalNum());
		cmpRes.setStoreTotalNum(cr.getStoreTotalNum());
		cmpRes.setStoreAssignNum(cr.getStoreAssignNum());
	}
	
	//使用量查询(cpu、内存、磁盘)
	public void getUseNum(PageData pd, CmpRes cmpRes) throws Exception {
		CmpRes cr=(CmpRes) dao.findForObject("ResviewMapper.getUseNum", pd);
		cmpRes.setCpuUseNum(cr.getCpuUseNum());
		cmpRes.setMemUseNum(cr.getMemUseNum());
		cmpRes.setStoreUseNum(cr.getStoreUseNum());
	}
	
	//物理机列表分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getHostPageList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ResviewMapper.getHostPageList", page);
	}
}