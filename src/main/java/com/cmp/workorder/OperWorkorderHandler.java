package com.cmp.workorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.service.CmpOpServeService;
import com.cmp.service.CmpOrderService;
import com.cmp.service.CmpWorkOrderService;
import com.cmp.sid.CmpOpServe;
import com.cmp.sid.CmpOrder;
import com.cmp.sid.CmpWorkOrder;
import com.fh.util.PageData;

@Service("operWorkorderHandler")
public class OperWorkorderHandler implements IWorkorderHandler {
	
	
	@Resource
	private CmpWorkOrderService cmpWorkOrderService;
	
	@Resource
	private CmpOpServeService cmpOpServeService;

	@Override
	public Map<String, Object> toWorkorderView(CmpWorkOrder cmpWorkorder) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap = buildViewInfo(cmpWorkorder, resMap);
		resMap.put("toPageUrl", "workorder/operview");
		return resMap;
	}

	@Override
	public Map<String, Object> toWorkorderCheck(CmpWorkOrder cmpWorkorder) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> toWorkorderExecute(CmpWorkOrder cmpWorkorder) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> executeWork(PageData pd, CmpWorkOrder workOrder) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<String, Object> buildViewInfo(CmpWorkOrder cmpWorkorder, Map<String, Object> resMap) throws Exception {
		//工单信息
		CmpOpServe opServe = cmpOpServeService.findByOrderNo(cmpWorkorder.getOrderNo());
		try {
			cmpOpServeService.encase(opServe);  //中文填充
		} catch (Exception e) {
			e.printStackTrace();
		}
		resMap.put("opServe", opServe);
		resMap.put("workorder", cmpWorkorder);
		String operType = opServe.getOperType();
		return resMap;
	}

}
