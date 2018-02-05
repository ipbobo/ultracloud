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
import com.cmp.sid.CmpDashboard;
import com.cmp.sid.zabbix.ResBean;
import com.cmp.sid.zabbix.ResBody;
import com.cmp.sid.zabbix.ResError;
import com.cmp.util.HttpUtil;
import com.cmp.util.StringUtil;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

import net.sf.json.JSONObject;

//仪表盘服务
@Service
public class DashboardService {
	private static Logger logger = Logger.getLogger(CustomGroupEntityManager.class);
	private static String ZABBIX_TOKEN=null;//Zabbix请求token
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Value("${zabbix.url}")
	private String ZABBIX_URL="http://180.169.225.158:86/zabbix/api_jsonrpc.php";//Zabbix请求地址
	@Value("${zabbix.user}")
	private String ZABBIX_USER="Admin";//Zabbix用户名
	@Value("${zabbix.password}")
	private String ZABBIX_PASSWORD="zabbix";//Zabbix密码

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
	
	//主机ID查询
	public String getIdStr(String operType, PageData pd) throws Exception {
		String sqlStr="DashboardMapper.getVirList";//虚拟机运行
		if("host".equals(operType)){//宿主机运行
			sqlStr="DashboardMapper.getHostList";
		}else if("phys".equals(operType)){//物理机运行
			sqlStr="DashboardMapper.getPhysList";
		}
		
		PageData pageData=(PageData)dao.findForObject(sqlStr, pd);
		String idStr=null;
		if(pageData!=null && (idStr=(String)pageData.get("idStr"))!=null && !StringUtils.isBlank(idStr)){
			return idStr;
		}
		
		return null;
	}
	
	//虚机详细信息查询
	public CmpDashboard getVirDtl(String idStr, PageData pd) throws Exception {
		if(idStr==null){
			return new CmpDashboard();
		}
		
		ResBody resBody=getZabbixJson("item.get", StringUtil.getParams("output", new String[]{"status"}, "hostids", idStr.split(",")));
		ResBean[] rbs=null;
		if(resBody==null || (rbs=resBody.getResult())==null){//接口返回错误
			return null;
		}
		
		CmpDashboard cd=new CmpDashboard();
		if(rbs!=null && rbs.length>0){
			for(ResBean rb: rbs){
				if("0".equals(rb.getStatus())){//运行
					cd.addRunRunnigNum(1);
				}else if("1".equals(rb.getStatus())){//挂起
					cd.addRunHangupNum(1);
				}else if("2".equals(rb.getStatus())){//关机
					cd.addRunCloseNum(1);
				}
			}
		}
		
		cd.addCpuUseNum(1);
		cd.addCpuTotalNum(10);
		cd.addMemUseNum(2);
		cd.addMemTotalNum(20);
		cd.addStoreUseNum(21);
		cd.addStoreTotalNum(100);
		return cd;
	}
	
	//物理机详细信息查询
	public CmpDashboard getPhysDtl() throws Exception {
		CmpDashboard cd=new CmpDashboard();
		cd.setCpuUseNum(3);
		cd.setCpuTotalNum(10);
		cd.setMemUseNum(28);
		cd.setMemTotalNum(36);
		cd.setStoreUseNum(60);
		cd.setStoreTotalNum(100);
		return cd;
	}
	
	//虚拟机负载
	public CmpDashboard getVirLoad() throws Exception {
		CmpDashboard cd=new CmpDashboard();
		cd.setLoadLittleNum(2);
		cd.setLoadMiddleNum(3);
		cd.setLoadHeightNum(4);
		cd.setLoadStopNum(5);
		return cd;
	}
	
	//宿主机负载
	public CmpDashboard getHostLoad() throws Exception {
		CmpDashboard cd=new CmpDashboard();
		cd.setLoadLittleNum(12);
		cd.setLoadMiddleNum(13);
		cd.setLoadHeightNum(14);
		cd.setLoadStopNum(15);
		return cd;
	}
	
	//物理机负载
	public CmpDashboard getPhysLoad() throws Exception {
		CmpDashboard cd=new CmpDashboard();
		cd.setLoadLittleNum(22);
		cd.setLoadMiddleNum(23);
		cd.setLoadHeightNum(24);
		cd.setLoadStopNum(25);
		return cd;
	}
	
	//运行情况
	public CmpDashboard getRunDtl(String idStr, PageData pd) throws Exception {
		if(idStr==null){//主机ID为空
			return new CmpDashboard();
		}
		
		ResBody resBody=getZabbixJson("host.get", StringUtil.getParams("output", new String[]{"status"}, "filter", StringUtil.getParams("hostid", idStr.split(","))));
		ResBean[] rbs=null;
		if(resBody==null || (rbs=resBody.getResult())==null){//接口返回错误
			return null;
		}
		
		CmpDashboard cd=new CmpDashboard();
		if(rbs!=null && rbs.length>0){
			for(ResBean rb: rbs){
				if("0".equals(rb.getStatus())){//运行
					cd.addRunRunnigNum(1);
				}else if("1".equals(rb.getStatus())){//挂起
					cd.addRunHangupNum(1);
				}else if("2".equals(rb.getStatus())){//关机
					cd.addRunCloseNum(1);
				}
			}
		}
		
		return cd;
	}
	
	//CPU资源使用量趋势
	public CmpAxis getCpuResRate(String timeType) throws Exception {
		CmpAxis cmpAxis=new CmpAxis(timeType);
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
		CmpAxis cmpAxis=new CmpAxis(timeType);
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
		CmpAxis cmpAxis=new CmpAxis(timeType);
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
	public ResBody getZabbixJson(String method, JSONObject params){
		if(StringUtils.isBlank(ZABBIX_TOKEN) && !loginZabbix()){//调用Zabbix登录API
			return null;
		}
		
		String jsonStr=HttpUtil.sendHttpPost(ZABBIX_URL, StringUtil.getReqBody(method, params, ZABBIX_TOKEN), false);//发送post请求(JSON)
		ResBody resBody=null;
		if(StringUtils.isBlank(jsonStr) || (resBody=StringUtil.json2Obj(jsonStr, ResBody.class))==null){//接口返回错误
			logger.error("调用Zabbix API时错误："+jsonStr);
			return null;
		}
		
		ResError resError=resBody.getError();
		if(resError!=null){//接口返回错误
			String code=resError.getCode();
			String data=resError.getData();
			if(data!=null && "-32602".equals(code) && data.indexOf("re-login")>=0){//Zabbix token失效
				if(loginZabbix()){//调用Zabbix登录API
					return getZabbixJson(method, params);
				}
			}
			
			logger.error("调用Zabbix API时错误：code=["+code+"]、message=["+resError.getMessage()+"]、data=["+data+"]");
			return null;
		}
		
		return resBody;
	}
	
	//调用Zabbix登录API
	public boolean loginZabbix(){
		String retJsonStr=HttpUtil.sendHttpPost(ZABBIX_URL, StringUtil.getReqBody("user.login", StringUtil.getParams("user", ZABBIX_USER, "password", ZABBIX_PASSWORD), ZABBIX_TOKEN), false);//发送post请求(JSON)
		JSONObject retJsonObj=null;
		if(StringUtils.isBlank(retJsonStr) || (retJsonObj=JSONObject.fromObject(retJsonStr))==null || retJsonObj.isNullObject()){//接口返回错误
			logger.error("调用Zabbix登录API时错误："+retJsonStr);
			return false;
		}
		
		JSONObject errorJsonObj=retJsonObj.getJSONObject("error");
		if(errorJsonObj!=null && !errorJsonObj.isNullObject()){//接口返回错误
			logger.error("调用Zabbix登录API时错误：code=["+errorJsonObj.getString("code")+"]、message=["+errorJsonObj.getString("message")+"]、data=["+errorJsonObj.getString("data")+"]");
			return false;
		}
		
		ZABBIX_TOKEN=retJsonObj.getString("result");//获取Zabbix token
		return true;
	}
	
	public static void main(String[] args){
		DashboardService aa=new DashboardService();
		//JSONObject jsonObj=aa.getZabbixJson("item.get", StringUtil.getParams("hostids", new String[]{"10084", "10257"}));
		//JSONObject jsonObj=aa.getZabbixJson("host.get", StringUtil.getParams("filter", StringUtil.getParams("hostid", new String[]{"10084", "10257"})));
		//ResBody resBody=aa.getZabbixJson("host.get", StringUtil.getParams("output",  new String[]{"status"}, "filter", StringUtil.getParams("hostid", "10084,10257".split(","))));
		ResBody resBody=aa.getZabbixJson("item.get", StringUtil.getParams("hostids", "10084,10257".split(","), "search", StringUtil.getParams("key_", "system.cpu.util")));
		System.out.println(resBody.toString());
	}
}