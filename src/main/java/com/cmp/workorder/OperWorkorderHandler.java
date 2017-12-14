package com.cmp.workorder;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.cmp.sid.CmpWorkOrder;

@Service("operWorkorderHandler")
public class OperWorkorderHandler implements IWorkorderHandler {

	@Override
	public Map<String, Object> toWorkorderView(CmpWorkOrder cmpWorkorder) throws Exception {
		// TODO Auto-generated method stub
		return null;
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

}
