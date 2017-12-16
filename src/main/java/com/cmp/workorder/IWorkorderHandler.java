package com.cmp.workorder;

import java.util.Map;

import com.cmp.sid.CmpWorkOrder;
import com.fh.util.PageData;

public interface IWorkorderHandler {
	public Map<String, Object> toWorkorderView(CmpWorkOrder cmpWorkorder) throws Exception;
	public Map<String, Object> toWorkorderCheck(CmpWorkOrder cmpWorkorder) throws Exception;
	public Map<String, Object> toWorkorderExecute(CmpWorkOrder cmpWorkorder) throws Exception;
	public Map<String, Object> executeWork(PageData pd, CmpWorkOrder workOrder) throws Exception;
}
