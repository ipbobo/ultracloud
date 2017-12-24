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
		PageData pd = new PageData();
		String bizviewTypeName="全部数据中心";
		if(!StringUtils.isBlank(bizviewType)){
			pd.put("id", bizviewType);
			bizviewTypeName=(String)datacenterService.findById(pd).get("name");
		}
		
		cmpRes.setBizviewTypeName(bizviewTypeName);
		cmpRes.setSubBizviewType(subBizviewType);
		cmpRes.setSubBizviewTypeName(null);
		if("cal".equals(operType)){//计算
			cmpRes.setCpuTotalNum("10");
			cmpRes.setCpuUseNum("5");
			cmpRes.setStoreUseNum("5");
			cmpRes.setMemTotalNum("10");
			cmpRes.setMemUseNum("5");
		}else if("store".equals(operType)){//存储
			cmpRes.setStoreTotalNum("10");
			cmpRes.setStoreUseNum("5");
			cmpRes.setStoreAssignNum("3");
		}
		
		return cmpRes;
	}
	
	//物理机列表分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getHostPageList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ResviewMapper.getHostPageList", page);
	}
}