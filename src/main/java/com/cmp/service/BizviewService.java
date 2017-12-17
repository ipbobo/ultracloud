package com.cmp.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cmp.service.servicemgt.EnvironmentService;
import com.cmp.sid.CmpRes;
import com.fh.dao.DaoSupport;
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
	public CmpRes getCmpResDtl(String bizviewType, String subBizviewType) throws Exception{
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
		cmpRes.setCpuTotalNum("5");
		cmpRes.setCpuAppNum("0");
		cmpRes.setCpuUseNum("5");
		cmpRes.setCpuRestNum("0");
		cmpRes.setMemTotalNum("5");
		cmpRes.setMemAppNum("0");
		cmpRes.setMemUseNum("5");
		cmpRes.setMemRestNum("0");
		return cmpRes;
	}
}