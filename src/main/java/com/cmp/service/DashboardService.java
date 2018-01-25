package com.cmp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cmp.activiti.CustomGroupEntityManager;
import com.cmp.sid.CmpAxis;
import com.cmp.sid.CmpRes;
import com.cmp.util.HttpUtil;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

import net.sf.json.JSONObject;

//仪表盘服务
@Service
public class DashboardService {
	private static Logger logger = Logger.getLogger(CustomGroupEntityManager.class);
	private static String ZABBIX_JSONSTR="{\"jsonrpc\": \"2.0\", \"method\": null, \"params\": null, \"auth\": null, \"id\": 1}";
	private static String ZABBIX_TOKEN=null;//Zabbix请求token
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Value("${zabbix.url}")
	private String ZABBIX_URL;//Zabbix请求地址
	@Value("${zabbix.user}")
	private String ZABBIX_USER;//Zabbix用户名
	@Value("${zabbix.password}")
	private String ZABBIX_PASSWORD;//Zabbix密码

	//审核组查询
	@SuppressWarnings("unchecked")
	public Map<String, String> getAuditMap(String applyUserId) throws Exception {
		Map<String, String> map=new HashMap<String, String>();
		List<PageData> list=(List<PageData>)dao.findForList("DashboardMapper.getAuditList", applyUserId);
		if(list!=null && !list.isEmpty()){
			for(PageData pd: list){
				map.put(pd.getString("type"), pd.getString("userGroupId"));
			}
		}
		
		return map;
	}
	
	//虚机总量查询
	public Long getVirNum(PageData pd) throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getVirNum", pd);
	}
	
	//宿主机总量查询
	public Long getHostNum(PageData pd) throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getHostNum", pd);
	}
	
	//物理机总量查询
	public Long getPhysNum(PageData pd) throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getPhysNum", pd);
	}
	
	//用户总数查询
	public Long getUserNum(PageData pd) throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getUserNum", pd);
	}
	
	//项目总数查询
	public Long getProjNum(PageData pd) throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getProjNum", pd);
	}
	
	//工单总数查询
	public Long getWorkOrderNum(PageData pd) throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getWorkOrderNum", pd);
	}
	
	//虚机详细信息查询
	public CmpRes getVirDtl() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setCpuUseNum("1");
		cmpRes.setCpuTotalNum("10");
		cmpRes.setMemUseNum("2");
		cmpRes.setMemTotalNum("20");
		cmpRes.setStoreUseNum("21");
		cmpRes.setStoreTotalNum("100");
		return cmpRes;
	}
	
	//物理机详细信息查询
	public CmpRes getPhysDtl() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setCpuUseNum("3");
		cmpRes.setCpuTotalNum("10");
		cmpRes.setMemUseNum("28");
		cmpRes.setMemTotalNum("36");
		cmpRes.setStoreUseNum("60");
		cmpRes.setStoreTotalNum("100");
		return cmpRes;
	}
	
	//虚拟机负载
	public CmpRes getVirLoad() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setLoadLittleNum("2");
		cmpRes.setLoadMiddleNum("3");
		cmpRes.setLoadHeightNum("4");
		cmpRes.setLoadStopNum("5");
		return cmpRes;
	}
	
	//宿主机负载
	public CmpRes getHostLoad() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setLoadLittleNum("12");
		cmpRes.setLoadMiddleNum("13");
		cmpRes.setLoadHeightNum("14");
		cmpRes.setLoadStopNum("15");
		return cmpRes;
	}
	
	//物理机负载
	public CmpRes getPhysLoad() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setLoadLittleNum("22");
		cmpRes.setLoadMiddleNum("23");
		cmpRes.setLoadHeightNum("24");
		cmpRes.setLoadStopNum("25");
		return cmpRes;
	}
	
	//虚拟机运行
	public CmpRes getVirRun() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setRunRunnigNum("2");
		cmpRes.setRunHangupNum("3");
		cmpRes.setRunCloseNum("4");
		return cmpRes;
	}
	
	//宿主机运行
	public CmpRes getHostRun() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setRunRunnigNum("12");
		cmpRes.setRunHangupNum("13");
		cmpRes.setRunCloseNum("14");
		return cmpRes;
	}
	
	//物理机运行
	public CmpRes getPhysRun() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setRunRunnigNum("22");
		cmpRes.setRunHangupNum("23");
		cmpRes.setRunCloseNum("24");
		return cmpRes;
	}
	
	//CPU资源使用量趋势
	public CmpAxis getCpuResRate(String timeType) throws Exception {
		CmpAxis cmpAxis=new CmpAxis();
		cmpAxis.setXaxis1("12/2");
		cmpAxis.setXaxis2("12/4");
		cmpAxis.setXaxis3("12/5");
		cmpAxis.setXaxis4("12/12");
		cmpAxis.setXaxis5("12/18");
		cmpAxis.setXaxis6("12/22");
		cmpAxis.setYaxis1("0.1");
		cmpAxis.setYaxis2("0.3");
		cmpAxis.setYaxis3("0.5");
		cmpAxis.setYaxis4("0.7");
		cmpAxis.setYaxis5("0.9");
		cmpAxis.setYaxis6("1");
		return cmpAxis;
	}
		
	//内存资源使用量趋势
	public CmpAxis getMemResRate(String timeType) throws Exception {
		CmpAxis cmpAxis=new CmpAxis();
		cmpAxis.setXaxis1("12/1");
		cmpAxis.setXaxis2("12/2");
		cmpAxis.setXaxis3("12/3");
		cmpAxis.setXaxis4("12/4");
		cmpAxis.setXaxis5("12/5");
		cmpAxis.setXaxis6("12/6");
		cmpAxis.setYaxis1("0.2");
		cmpAxis.setYaxis2("0.4");
		cmpAxis.setYaxis3("0.5");
		cmpAxis.setYaxis4("0.6");
		cmpAxis.setYaxis5("0.8");
		cmpAxis.setYaxis6("1");
		return cmpAxis;
	}
	
	//磁盘资源使用量趋势
	public CmpAxis getStoreResRate(String timeType) throws Exception {
		CmpAxis cmpAxis=new CmpAxis();
		cmpAxis.setXaxis1("12/11");
		cmpAxis.setXaxis2("12/12");
		cmpAxis.setXaxis3("12/13");
		cmpAxis.setXaxis4("12/14");
		cmpAxis.setXaxis5("12/15");
		cmpAxis.setXaxis6("12/16");
		cmpAxis.setYaxis1("0.1");
		cmpAxis.setYaxis2("0.5");
		cmpAxis.setYaxis3("0.6");
		cmpAxis.setYaxis4("0.7");
		cmpAxis.setYaxis5("0.9");
		cmpAxis.setYaxis6("1");
		return cmpAxis;
	}
	
	//资源使用列表
	@SuppressWarnings("unchecked")
	public List<PageData> getResUseList(String resType) throws Exception {
        List<PageData> list=(List<PageData>) dao.findForList("BizviewMapper.getCloudHostPageList", new Page());
        if(list!=null && !list.isEmpty()){
        	int size=list.size();
        	return list.subList(0, size>=5?5:size);
        }
        
        return null;
	}
	
	//调用Zabbix API
	public JSONObject getZabbixJson(String method, String params){
		ZABBIX_TOKEN=StringUtils.isBlank(ZABBIX_TOKEN)?getZabbixToken():ZABBIX_TOKEN;//获取Zabbix token
		JSONObject jsonObj=JSONObject.fromObject(ZABBIX_JSONSTR);//返回json对象
		jsonObj.put("method", method);
		jsonObj.put("params", params);
		jsonObj.put("auth", ZABBIX_TOKEN);
		String retJsonStr=HttpUtil.sendHttpPost(ZABBIX_URL, jsonObj.toString(), false);
		JSONObject retJsonObj=null;
		if(StringUtils.isBlank(retJsonStr) || (retJsonObj=JSONObject.fromObject(retJsonStr))==null || retJsonObj.isNullObject()){//接口返回错误
			logger.error("调用Zabbix API时错误："+retJsonStr);
			return null;
		}
		
		JSONObject errorJsonObj=retJsonObj.getJSONObject("error");
		if(errorJsonObj!=null && !errorJsonObj.isNullObject()){//接口返回错误
			String code=errorJsonObj.getString("code");
			String data=errorJsonObj.getString("data");
			if(data!=null && "-32602".equals(code) && data.indexOf("re-login")>=0){//Zabbix token失效
				if(getZabbixToken()!=null){//获取Zabbix token
					return getZabbixJson(method, params);
				}
			}
			
			logger.error("调用Zabbix API时错误：code=["+code+"]、message=["+errorJsonObj.getString("message")+"]、data=["+data+"]");
			return null;
		}
		
		return jsonObj;
	}
	
	//获取Zabbix token
	private String getZabbixToken(){
		String jsonStr="{\"jsonrpc\": \"2.0\", \"method\": \"user.login\", \"params\": {\"user\": \""+ZABBIX_USER+"\", \"password\": \""+ZABBIX_PASSWORD+"\"}, \"auth\": null, \"id\": 1}";
		String retJsonStr=HttpUtil.sendHttpPost(ZABBIX_URL, jsonStr, false);
		JSONObject retJsonObj=null;
		if(StringUtils.isBlank(retJsonStr) || (retJsonObj=JSONObject.fromObject(retJsonStr))==null || retJsonObj.isNullObject()){//接口返回错误
			logger.error("调用Zabbix登录API时错误："+retJsonStr);
			return null;
		}
		
		JSONObject errorJsonObj=retJsonObj.getJSONObject("error");
		if(errorJsonObj!=null && !errorJsonObj.isNullObject()){//接口返回错误
			logger.error("调用Zabbix登录API时错误：code=["+errorJsonObj.getString("code")+"]、message=["+errorJsonObj.getString("message")+"]、data=["+errorJsonObj.getString("data")+"]");
			return null;
		}
		
		ZABBIX_TOKEN=retJsonObj.getString("result");
		return ZABBIX_TOKEN;//Zabbix请求token
	}
}