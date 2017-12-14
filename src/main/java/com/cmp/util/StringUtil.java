package com.cmp.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;

import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.Logger;

import net.sf.json.JSONObject;

public class StringUtil {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	//获取返回字符串
	public static String getRetStr(String retCode, String retMsg, Object ... params) throws Exception {
		JSONObject retObj = new JSONObject();
		retObj.put("retCode", retCode);
    	retObj.put("retMsg", retMsg);
    	if(params!=null && params.length>0 && params.length%2==0){//key/value
    		for(int i=0;i<params.length/2;i++){
    			retObj.put(params[i*2], params[i*2+1]);
    		}
    	}
    	
        return retObj.toString();
	}
	
	//获取登录用户
	public static String getUserId() {
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		return user.getUSERNAME();//获取登录用户
	}
	
	//获取客户端真实IP
	public static String getClientIp(HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
}