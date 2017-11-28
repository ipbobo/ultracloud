package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.sid.CmpWorkOrder;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
public class CmpWorkOrderService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//提交申请
	public void addWorkOrder(String appNo, String orderId, String applyUserId, String procInstId) throws Exception {
		PageData pd=new PageData();
		pd.put("orderId", orderId);//清单ID
		pd.put("appNo", appNo);//申请编号
		pd.put("appType", "1");//申请类型：1-资源申请；2-运维服务申请
		pd.put("status", "0");//状态：0-待提交；1-审批中；2-审批通过；3-审批不通过
		pd.put("procInstId", procInstId);//流程实例ID
		pd.put("applyUserId", applyUserId);//申请者
		dao.save("CmpWorkOrderMapper.addWorkOrder", pd);
	}
	
	//保存工单
	public void addWordOrder(CmpWorkOrder cmpWorkorder) throws Exception {
		dao.save("CmpWorkOrderMapper.saveWorkOrder", cmpWorkorder);
	}
	
	//查询个人申请 工单
	public List<PageData> listUserWorkorderByPd(Page page) throws Exception{
		return (List<PageData>)dao.findForList("CmpWorkOrderMapper.workorderlistPage", page);
	}
}