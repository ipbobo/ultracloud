package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cmp.service.servicemgt.EnvironmentService;
import com.cmp.sid.CmpRes;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.util.PageData;

@Service
public class BizviewService {
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
	
	//资源详细信息查询
	public CmpRes getCmpResDtl(String operType, String bizviewType, String subBizviewType) throws Exception{
		CmpRes cmpRes=new CmpRes();
		cmpRes.setBizviewType(bizviewType);
		cmpRes.setBizviewTypeName(cmpDictService.getDictValue("bizview_type", bizviewType));
		cmpRes.setSubBizviewType(subBizviewType);
		String subBizviewTypeName="全部"+cmpRes.getBizviewTypeName();
		if("env".equals(bizviewType)){//环境
			if(!StringUtils.isBlank(subBizviewType)){
				subBizviewTypeName=(String)environmentService.findById(new PageData("id", subBizviewType)).get("name");
			}
		}else if("dept".equals(bizviewType)){//部门
			if(!StringUtils.isBlank(subBizviewType)){
				subBizviewTypeName=(String)departmentService.findById(new PageData("DEPARTMENT_ID", subBizviewType)).get("NAME");
			}
		}else if("proj".equals(bizviewType)){//项目
			if(!StringUtils.isBlank(subBizviewType)){
				subBizviewTypeName=(String)projectService.findById(new PageData("id", subBizviewType)).get("name");
			}
		}
		
		cmpRes.setSubBizviewTypeName(subBizviewTypeName);
		PageData pd = new PageData("bizviewType", bizviewType, "subBizviewType", subBizviewType);
		getTotalNum(pd, cmpRes);//总量查询(cpu、内存、磁盘)
		getUseNum(pd, cmpRes);//使用量查询(cpu、内存、磁盘)
		getAppNum(pd, cmpRes);//申请中查询(cpu、内存、磁盘)
		cmpRes.setCpuRestNum(String.valueOf(Integer.parseInt(cmpRes.getCpuTotalNum())-Integer.parseInt(cmpRes.getCpuUseNum())-Integer.parseInt(cmpRes.getCpuAppNum())));
		cmpRes.setMemRestNum(String.valueOf(Integer.parseInt(cmpRes.getMemTotalNum())-Integer.parseInt(cmpRes.getMemUseNum())-Integer.parseInt(cmpRes.getMemAppNum())));
		cmpRes.setStoreRestNum(String.valueOf(Integer.parseInt(cmpRes.getStoreTotalNum())-Integer.parseInt(cmpRes.getStoreUseNum())-Integer.parseInt(cmpRes.getStoreAppNum())));
		return cmpRes;
	}
	
	//总量查询(cpu、内存、磁盘)
	public void getTotalNum(PageData pd, CmpRes cmpRes) throws Exception {
		CmpRes cr=(CmpRes) dao.findForObject("BizviewMapper.getTotalNum", pd);
		cmpRes.setCpuTotalNum(cr.getCpuTotalNum());
		cmpRes.setMemTotalNum(cr.getMemTotalNum());
		cmpRes.setStoreTotalNum(cr.getStoreTotalNum());
	}
	
	//使用量查询(cpu、内存、磁盘)
	public void getUseNum(PageData pd, CmpRes cmpRes) throws Exception {
		CmpRes cr=(CmpRes) dao.findForObject("BizviewMapper.getUseNum", pd);
		cmpRes.setCpuUseNum(cr.getCpuUseNum());
		cmpRes.setMemUseNum(cr.getMemUseNum());
		cmpRes.setStoreUseNum(cr.getStoreUseNum());
	}
	
	//申请中查询(cpu、内存、磁盘)
	public void getAppNum(PageData pd, CmpRes cmpRes) throws Exception {
		CmpRes cr=(CmpRes) dao.findForObject("BizviewMapper.getAppNum", pd);
		cmpRes.setCpuAppNum(cr.getCpuAppNum());
		cmpRes.setMemAppNum(cr.getMemAppNum());
		cmpRes.setStoreAppNum(cr.getStoreAppNum());
	}
	
	//云主机列表分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getCloudHostPageList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("BizviewMapper.getCloudHostPageList", page);
	}
}