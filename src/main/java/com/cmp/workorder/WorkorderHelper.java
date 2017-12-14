package com.cmp.workorder;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("workorderHelper")
public class WorkorderHelper {
	
	@Resource
	private AppWorkorderHandler appWorkorderHandler;

	@Resource
	private OperWorkorderHandler operWorkorderHandler;

	//通过不同的工单类型，选择工单处理器
	public IWorkorderHandler instance(String workorderType) {
		if (workorderType == null || workorderType.length() == 0) {
			return null;
		}
		IWorkorderHandler handler = null;
		switch (workorderType) {
		case "1":
			handler = appWorkorderHandler;
			break;
		case "2":
			handler = operWorkorderHandler;
			break;
		default:
			handler = null;
			break;
		}
		return handler;
	} 
	
}
