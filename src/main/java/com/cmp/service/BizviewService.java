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
		PageData pd = new PageData();
		String subBizviewTypeName="全部"+cmpRes.getBizviewTypeName();
		if("env".equals(bizviewType)){//环境
			if(!StringUtils.isBlank(subBizviewType)){
				pd.put("id", subBizviewType);
				subBizviewTypeName=(String)environmentService.findById(pd).get("name");
			}
		}else if("dept".equals(bizviewType)){//部门
			if(!StringUtils.isBlank(subBizviewType)){
				pd.put("DEPARTMENT_ID", subBizviewType);
				subBizviewTypeName=(String)departmentService.findById(pd).get("NAME");
			}
		}else if("proj".equals(bizviewType)){//项目
			if(!StringUtils.isBlank(subBizviewType)){
				pd.put("id", subBizviewType);
				subBizviewTypeName=(String)projectService.findById(pd).get("name");
			}
		}
		
		cmpRes.setSubBizviewTypeName(subBizviewTypeName);
		if("cal".equals(operType)){//计算
			cmpRes.setCpuTotalNum("10");
			cmpRes.setCpuAppNum("2");
			cmpRes.setCpuUseNum("5");
			cmpRes.setCpuRestNum("3");
			cmpRes.setMemTotalNum("10");
			cmpRes.setMemAppNum("2");
			cmpRes.setMemUseNum("5");
			cmpRes.setMemRestNum("3");
		}else if("store".equals(operType)){//存储
			cmpRes.setStoreTotalNum("10");
			cmpRes.setStoreAppNum("2");
			cmpRes.setStoreUseNum("5");
			cmpRes.setStoreRestNum("3");
		}
		
		return cmpRes;
	}
	
	//云主机列表分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getCloudHostPageList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("BizviewMapper.getCloudHostPageList", page);
	}
}