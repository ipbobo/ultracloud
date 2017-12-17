package com.fh.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fh.entity.Page;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());

	public PageData getPageData() {
		return new PageData(this.getRequest());
	}
	
	//简化Page赋值操作，采用key/value，add by kfzx-chenwm
	public PageData getPageData(Page page, Object ... params) {
		PageData pd=new PageData(this.getRequest());
		if(params!=null && params.length>0 && params.length%2==0){//key/value
    		for(int i=0;i<params.length/2;i++){
    			pd.put(params[i*2], params[i*2+1]);
    		}
    	}
		
		page.setPd(pd);
		return pd;
	}

	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

	public String get32UUID() {
		return UuidUtil.get32UUID();
	}

	public Page getPage() {
		return new Page();
	}

	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}

	public static void logAfter(Logger logger) {
		logger.info("end");
		logger.info("");
	}

}
