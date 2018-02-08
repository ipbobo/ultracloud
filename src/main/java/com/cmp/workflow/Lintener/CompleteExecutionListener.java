package com.cmp.workflow.Lintener;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.cmp.common.Constants;
import com.cmp.service.CmpWorkOrderService;

/**
 * 工单完成-执行监听器
 * 
 * @author liuweixing
 *
 */
public class CompleteExecutionListener implements ExecutionListener {

	private static final long serialVersionUID = -3374534225221434770L;

	@Override
	public void notify(DelegateExecution execution) {
		String appNo = (String) execution.getVariable("appNo");
		WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

		CmpWorkOrderService cmpWorkOrderService = (CmpWorkOrderService) webctx.getBean("cmpWorkOrderService");
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("status", Constants.WORKORDER_STATUS_90);
			cmpWorkOrderService.updateWorkOrder(appNo, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
