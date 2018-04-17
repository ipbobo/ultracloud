package com.cmp.util;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;

import com.cmp.sid.zabbix.ReqBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.Logger;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

public class StringUtil {
	protected static Logger logger = Logger.getLogger(StringUtil.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
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
	
	//获取请求体
	public static String getReqBody(String method, JSONObject params, String auth){
		ReqBody rb=new ReqBody();
		rb.setMethod(method);
		rb.setParams(params);
		rb.setAuth(auth);
		return obj2Json(rb);
	}
	
	//获取请求参数
	public static JSONObject getParams(Object ... params) {
		JSONObject retObj = new JSONObject();
    	if(params!=null && params.length>0 && params.length%2==0){//key/value
    		for(int i=0;i<params.length/2;i++){
    			retObj.put(params[i*2], params[i*2+1]);
    		}
    	}
    	
        return retObj;
	}
	
	//获取整数
	public static int getInt(String str) {
		try{
			return Integer.parseInt(str);
		}catch(Exception e){
			return 0;
		}
	}
	
	//获取浮点数
	public static float getFloat(String str) {
		try{
			return Float.parseFloat(str);
		}catch(Exception e){
			return 0;
		}
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
	
	//获取登录用户
	public static String getUserName(){
		try{
			Session session = Jurisdiction.getSession();
			User user=(User)session.getAttribute(Const.SESSION_USER);
			return user.getUSERNAME();//获取登录用户
		} catch (Exception e) {
	    	return null;
	    }
	}
	
	//获取登录用户
	public static String getUserId(){
		try{
			Session session = Jurisdiction.getSession();
			User user=(User)session.getAttribute(Const.SESSION_USER);
			return user.getUSER_ID();//获取登录用户
		} catch (Exception e) {
	    	return null;
	    }
	}
	
	//获取登录用户
	public static User getUserInfo(){
		try{
			Session session = Jurisdiction.getSession();
			return (User)session.getAttribute(Const.SESSION_USER);
		} catch (Exception e) {
	    	return null;
	    }
	}
	
	//获取用户的角色类型
	public static String getRoleType(Map<String, String> auditMap){
		if(auditMap.containsKey("admin")){//管理员
			return "admin";
		}else if(auditMap.containsKey("executor")){//实施者
			return "executor";
		}else if(auditMap.containsKey("audit")){//审核者
			return "audit";
		}else{//申请者
			return "applicant";
		}
	}
	
	//获取指定格式的日期
	public static Date getFmtDate(Date date, String pattern) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat(pattern);
			return sdf.parse(sdf.format(date));//获取指定格式的日期
		} catch (ParseException e) {
			return null;
		}
	}
	
	//计算指定日期间隔num天的日期
	public static Date getRaiseDay(Date date, int field, int num){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, num);
		return cal.getTime();
	}
	
	//获取指定日期的星期
	public static String getWeekOfDate(Date date) {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return weekDays[cal.get(Calendar.DAY_OF_WEEK) - 1];
	}
	
	//获取指定日期前一周的星期字符串
	public static String getWeekStr(Date date) {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int indx=cal.get(Calendar.DAY_OF_WEEK)-1;//0-6，对应星期日到星期六
		String[] newWeekDays=new String[7];
		System.arraycopy(weekDays, (indx+1)%7, newWeekDays, 0, (6-indx==0?7:6-indx));
		System.arraycopy(weekDays, 0, newWeekDays, 6-indx, (indx+1)%7);
		return StringUtils.join(newWeekDays, ",");
	}
	
	//对象转换成json字符串
	public static String obj2Json(Object obj) {
		try {
			if (obj!=null){
				StringWriter writer = new StringWriter();
				ObjectMapper mapper = new ObjectMapper();
				mapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
				mapper.writeValue(writer, obj);
				return writer.toString();
			}
			
			return null;
		} catch (Exception e) {
			logger.error("json序列化异常"+e);
			return null;
		}
	}
	
	//json字符串转换成对象
	public static <T> T json2Obj(String jsonStr, Class<T> clzz) {
		try{
			return new Gson().fromJson(jsonStr, clzz);
		}catch(Exception e){
			logger.error("JSON字符串["+jsonStr+"]解析失败："+e);
			return null;
		}
	}
	
	// 获取请求参数：防止sql语句中'null'出现
	public static String getJsonStr(JSONObject jsonObj, String key) {
		if(jsonObj.containsKey(key)){
			return jsonObj.getString(key);
		}
		
		return null;
	}
	
	//json字符串转换成对象
	public static String getArrStr(String[] arr) {
		if(arr!=null && arr.length!=0){
			return StringUtils.join(arr, ",");
		}

		return null;
	}
}