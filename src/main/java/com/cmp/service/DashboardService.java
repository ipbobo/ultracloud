package com.cmp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cmp.activiti.CustomGroupEntityManager;
import com.cmp.sid.CmpAxis;
import com.cmp.sid.CmpCdLoad;
import com.cmp.sid.CmpDashboard;
import com.cmp.sid.VirtualMachine;
import com.cmp.sid.zabbix.ResBean;
import com.cmp.sid.zabbix.ResBody;
import com.cmp.sid.zabbix.ResError;
import com.cmp.util.HttpUtil;
import com.cmp.util.StringUtil;
import com.fh.dao.DaoSupport;
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
	@Value("${zabbix.loadMiddle}")
	private Float ZABBIX_LOADMIDDLE;//中度负载(%)
	@Value("${zabbix.loadHeight}")
	private Float ZABBIX_LOADHEIGHT;//高度负载(%)
	@Value("${zabbix.loadStop}")
	private Float ZABBIX_LOADSTOP;//停机负载(%)
	
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
	
	//主机ID列表查询
	@SuppressWarnings("unchecked")
	public Map<String, String[]> getHostIdsMap(PageData pd) throws Exception {
		Map<String, String[]> map=new HashMap<String, String[]>();
		List<PageData> list=(List<PageData>)dao.findForList("DashboardMapper.getHostIdList", pd);//主机ID列表查询
		if(list!=null && !list.isEmpty()){
			for(PageData pageData: list){
				String idStr=null;
				if(list!=null && (idStr=(String)pageData.get("idStr"))!=null && !StringUtils.isBlank(idStr)){
					String key=(String)pageData.get("operType");
					map.put(key, idStr.split(","));
					String hostIdStr=(String)pageData.get("hostIdStr");
					map.put(key+"Str", hostIdStr.split(","));
				}
			}
		}
		
		return map;
	}
	
	//资源详细信息查询
	public CmpDashboard getResDtl(String resType, String[] hostIds) throws Exception {
		if(hostIds!=null && hostIds.length>0){//主机ID列表不为空
			String sql="DashboardMapper.getVirResDtl";
			if("physRes".equals(resType)){//物理资源
				sql="DashboardMapper.getPhysResDtl";
			}
			
			String hostIdStr=StringUtils.join(hostIds, ",");
			return (CmpDashboard)dao.findForObject(sql, hostIdStr);//主机ID列表查询
		}
		
		return new CmpDashboard();
	}
	
	//负载情况
	public CmpDashboard getLoadDtl(Map<String, CmpCdLoad> loadMap, String[] hostIds, String resType) throws Exception {
		CmpDashboard cd=new CmpDashboard();
		if(hostIds!=null && hostIds.length>0){//主机ID列表不为空
			//CPU
			ResBean[] rbs=getZabbixJson("item.get", StringUtil.getParams("output", new String[]{"hostid", "lastvalue"}, "hostids", hostIds, "search", StringUtil.getParams("key_", "system.cpu.util[all, user, avg5]")));
			if(rbs!=null && rbs.length>0){
				for(ResBean rb: rbs){
					addLoadMap("cpu", loadMap, rb.getHostid(), rb.getLastvalue(), resType);//获取负载情况
				}
			}
			
			//内存
			rbs=getZabbixJson("item.get", StringUtil.getParams("output", new String[]{"hostid", "lastvalue"}, "hostids", hostIds, "search", StringUtil.getParams("key_", "vm.memory.size[pused]")));
			if(rbs!=null && rbs.length>0){
				for(ResBean rb: rbs){
					addLoadMap("mem", loadMap, rb.getHostid(), rb.getLastvalue(), resType);//获取负载情况
				}
			}
			
			//磁盘
			rbs=getZabbixJson("item.get", StringUtil.getParams("output", new String[]{"hostid", "lastvalue"}, "hostids", hostIds, "search", StringUtil.getParams("key_", "vfs.fs.size[*, pused]")));
			if(rbs!=null && rbs.length>0){
				for(ResBean rb: rbs){
					addLoadMap("store", loadMap, rb.getHostid(), rb.getLastvalue(), resType);//获取负载情况
				}
			}
			
			//统计负载
			if(loadMap!=null && !loadMap.isEmpty()){
				Iterator<String> it=loadMap.keySet().iterator();
				while(it.hasNext()){
					addLoadNum(cd, loadMap.get(it.next()));//增加负载数
				}
			}
		}
		
		return cd;
	}
	
	//运行情况
	public CmpDashboard getRunDtl(String[] hostIds) throws Exception {
		CmpDashboard cd=new CmpDashboard();
		if(hostIds!=null && hostIds.length>0){//主机ID列表不为空
			ResBean[] rbs=getZabbixJson("host.get", StringUtil.getParams("output", new String[]{"status"}, "filter", StringUtil.getParams("hostid", hostIds)));
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
		}
		
		return cd;
	}
	
	//CPU资源使用量趋势
	public CmpAxis getResRate(String resType, String timeType, String[] hostIds) throws Exception {
		String key="system.cpu.util[all, user, avg5]";//CPU
		if("mem".equals(resType)){//内存
			key="vm.memory.size[pused]";
		}else if("store".equals(resType)){//磁盘
			key="vfs.fs.size[*, pused]";
		}
		
		CmpAxis cmpAxis=new CmpAxis(timeType);
		cmpAxis.setYaxis1(getYaxis(hostIds, key, cmpAxis.getXaxis1().getTime(), cmpAxis.getXaxis1().getTime()));
		cmpAxis.setYaxis2(getYaxis(hostIds, key, cmpAxis.getXaxis2().getTime(), cmpAxis.getXaxis2().getTime()));
		cmpAxis.setYaxis3(getYaxis(hostIds, key, cmpAxis.getXaxis3().getTime(), cmpAxis.getXaxis3().getTime()));
		cmpAxis.setYaxis4(getYaxis(hostIds, key, cmpAxis.getXaxis4().getTime(), cmpAxis.getXaxis4().getTime()));
		cmpAxis.setYaxis5(getYaxis(hostIds, key, cmpAxis.getXaxis5().getTime(), cmpAxis.getXaxis5().getTime()));
		cmpAxis.setYaxis6(getYaxis(hostIds, key, cmpAxis.getXaxis6().getTime(), cmpAxis.getXaxis6().getTime()));
		return cmpAxis;
	}
	
	//资源使用列表
	@SuppressWarnings("unchecked")
	public List<CmpCdLoad> getResUseList(Map<String, CmpCdLoad> loadMap) throws Exception {
		if(loadMap!=null && !loadMap.isEmpty()){
			List<CmpCdLoad> list=new ArrayList<CmpCdLoad>(loadMap.values());
			Collections.sort(list);//排序
			int size=list.size();
			List<CmpCdLoad> subList= list.subList(0, size>=5?5:size);
			StringBuffer sb=new StringBuffer();
			for(CmpCdLoad ccl: subList){
				if(sb.length()==0){
					sb.append(ccl.getHostId());
				}else{
					sb.append(","+ccl.getHostId());
				}
			}
			
			String hostIdStr=sb.toString();
			Map<String, VirtualMachine> cloudHostMap=new HashMap<String, VirtualMachine>();
			List<VirtualMachine> cloudHostList=(List<VirtualMachine>) dao.findForList("DashboardMapper.getTop5CloudHostList", hostIdStr);
	        if(cloudHostList!=null && !cloudHostList.isEmpty()){
	        	for(VirtualMachine vm: cloudHostList){
	        		cloudHostMap.put(vm.getId(), vm);
				}
	        }
	        
	        for(CmpCdLoad ccl: subList){
	        	VirtualMachine vm=cloudHostMap.get(ccl.getHostId());
	        	if(vm!=null){
	        		ccl.setHostName(vm.getName());
	        		ccl.setHostIp(vm.getIp());
	        		ccl.setCpuNum(Float.parseFloat(vm.getCpu()));
	        		ccl.setMemNum(Float.parseFloat(vm.getMemory()));
	        		ccl.setStoreNum(Float.parseFloat(vm.getMountDiskSize()));
	        	}
			}
	        
	        return subList;
		}
		
        return null;
	}
	
	//调用Zabbix API
	public ResBean[] getZabbixJson(String method, JSONObject params){
		if(StringUtils.isBlank(ZABBIX_TOKEN) && !loginZabbix()){//调用Zabbix登录API
			return null;
		}
		
		String jsonStr=HttpUtil.sendHttpPost(ZABBIX_URL, StringUtil.getReqBody(method, params, ZABBIX_TOKEN), false);//发送post请求(JSON)
		//String jsonStr="{\"jsonrpc\": \"2.0\",\"result\": [{\"hostid\":\"10255\",\"lastvalue\":\"0.08\"}, {\"hostid\":\"10256\",\"lastvalue\":\"0.12\"}, {\"hostid\":\"10257\",\"lastvalue\":\"0.52\"}, {\"hostid\":\"10084\",\"lastvalue\":\"0.82\"}],\"id\": \"1\"}";
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
		
		ResBean[] rbs=null;
		if(resBody==null || (rbs=resBody.getResult())==null){//接口返回错误
			return null;
		}
		
		return rbs;
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
	
	//新增负载情况
	private void addLoadMap(String operType, Map<String, CmpCdLoad> loadMap, String hostId, String rate, String resType) {
		CmpCdLoad cmpCdLoad=null;
		if(loadMap.containsKey(hostId)){
			cmpCdLoad=loadMap.get(hostId);
		}else{
			cmpCdLoad=new CmpCdLoad();
			cmpCdLoad.setResType(resType);//资源类型
			cmpCdLoad.setHostId(hostId);//主机ID
			loadMap.put(hostId, cmpCdLoad);
		}
		
		if("cpu".equals(operType)){//CPU
			cmpCdLoad.setCpuLoad(StringUtil.getFloat(rate));
		}else if("mem".equals(operType)){//内存
			cmpCdLoad.setMemLoad(StringUtil.getFloat(rate));
		}else if("store".equals(operType)){//磁盘
			cmpCdLoad.setStoreLoad(StringUtil.getFloat(rate));
		}
	}
	
	//增加负载数
	private void addLoadNum(CmpDashboard cd, CmpCdLoad cmpCdLoad) {
		float maxLoad=cmpCdLoad.getMaxLoad();
		if(maxLoad<ZABBIX_LOADMIDDLE){//轻度负载(%)
			cd.addLoadLittleNum(1);
		}else if(ZABBIX_LOADMIDDLE<=maxLoad && maxLoad<ZABBIX_LOADHEIGHT){//中度负载(%)
			cd.addLoadMiddleNum(1);
		}else if(ZABBIX_LOADHEIGHT<=maxLoad && maxLoad<ZABBIX_LOADSTOP){//高度负载(%)
			cd.addLoadHeightNum(1);
		}else if(ZABBIX_LOADSTOP<=maxLoad){//停机负载(%)
			cd.addLoadStopNum(1);
		}
	}
	
	//获取资源使用量趋势
	private String getYaxis(String[] hostIds, String key, long timeFrom, long timeTill) throws Exception {
		ResBean[] rbs=getZabbixJson("history.get", StringUtil.getParams("output", "extend", "history", 0, "hostids", hostIds, "time_from", timeFrom, "time_till", timeTill, "search", StringUtil.getParams("key_", key)));
		if(rbs!=null && rbs.length>0){
			return rbs[0].getValue();
		}
		
		return "0.0";
	}
	
	public static void main(String[] args){
		DashboardService aa=new DashboardService();
		//JSONObject jsonObj=aa.getZabbixJson("item.get", StringUtil.getParams("hostids", new String[]{"10084", "10257"}));
		//JSONObject jsonObj=aa.getZabbixJson("host.get", StringUtil.getParams("filter", StringUtil.getParams("hostid", new String[]{"10084", "10257"})));
		//ResBody resBody=aa.getZabbixJson("host.get", StringUtil.getParams("output",  new String[]{"status"}, "filter", StringUtil.getParams("hostid", "10084,10257".split(","))));
		//ResBody resBody=aa.getZabbixJson("host.get", StringUtil.getParams("search", StringUtil.getParams("key_", "system.cpu.num")));//system.cpu.num[max]、system.cpu.util[,user]
		//ResBody resBody=aa.getZabbixJson("host.get", StringUtil.getParams("output",  new String[]{"hostid"}, "filter", StringUtil.getParams()));
		//aa.getZabbixJson("item.get", StringUtil.getParams("output", new String[]{"key_", "lastvalue"}, "search", StringUtil.getParams("key_", "system.cpu")));//查询system.cpu.num和system.cpu.util[,user]；"sortfield", "key_"
		//aa.getZabbixJson("item.get", StringUtil.getParams("output", new String[]{"key_", "lastvalue"}, "search", StringUtil.getParams("key_", "vfs.fs.size")));//查询system.cpu.num和system.cpu.util[,user]；"sortfield", "key_"
		//aa.getZabbixJson("item.get", StringUtil.getParams("output", new String[]{"key_", "lastvalue"}, "search", StringUtil.getParams("key_", "vm.memory.size")));//查询system.cpu.num和system.cpu.util[,user]；"sortfield", "key_"
		aa.getZabbixJson("item.get", StringUtil.getParams("output", new String[]{"hostid", "key_", "lastvalue"}, "search", StringUtil.getParams("key_", "system.cpu.load[percpu,avg5]")));//查询system.cpu.num和system.cpu.util[,user]和"sortfield", "key_"
	}
}