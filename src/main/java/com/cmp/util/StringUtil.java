package com.cmp.util;

import net.sf.json.JSONObject;

public class StringUtil {
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
	
	
	//字符串中提取数字
	public static String getInteger(String str) {
		String str2 = "";
		if(str != null && !"".equals(str)){
			for(int i=0;i<str.length();i++){
				if(str.charAt(i)>=48 && str.charAt(i)<=57){
				str2+=str.charAt(i);
				}
			}
		}
		return str2;
	}
}