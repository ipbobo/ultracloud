package com.cmp.workflow.lintener;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.el.FixedValue;
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
public class UpdateStatusExecutionListener implements ExecutionListener {

	private static final long serialVersionUID = -3374534225221434770L;
	
	private FixedValue status = null;

	@Override
	public void notify(DelegateExecution execution) {
		String appNo = (String) execution.getVariable("appNo");
		WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

		CmpWorkOrderService cmpWorkOrderService = (CmpWorkOrderService) webctx.getBean("cmpWorkOrderService");
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("status", status.getExpressionText());
			cmpWorkOrderService.updateWorkOrder(appNo, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FixedValue getStatus() {
		return status;
	}

	public void setStatus(FixedValue status) {
		this.status = status;
	}

}
