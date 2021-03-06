package com.fh.controller.system.login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.CmpDictService;
import com.cmp.service.DashboardService;
import com.cmp.service.SysConfigService;
import com.cmp.sid.CmpAxis;
import com.cmp.sid.CmpCdLoad;
import com.cmp.sid.DashboardRequest;
import com.cmp.sid.SysConfigInfo;
import com.cmp.util.StringUtil;
import com.fh.controller.base.BaseController;
import com.fh.service.fhoa.datajur.DatajurManager;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.buttonrights.ButtonrightsManager;
import com.fh.service.system.fhbutton.FhbuttonManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.loginimg.LogInImgManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;

import net.sf.json.JSONObject;

//用户登录
@Controller
public class LoginController extends BaseController {
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="buttonrightsService")
	private ButtonrightsManager buttonrightsService;
	@Resource(name="fhbuttonService")
	private FhbuttonManager fhbuttonService;
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	@Resource(name="datajurService")
	private DatajurManager datajurService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="loginimgService")
	private LogInImgManager loginimgService;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private DashboardService dashboardService;
	@Resource
	private CmpDictService cmpDictService;
	
	//仪表盘
	@RequestMapping(value="/login_default")
	public ModelAndView defaultPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DashboardRequest dbReq=getReqParam(request);//获取请求参数
		String errMsg = checkParam(dbReq);//校验必填参数
		if (errMsg != null) {
			ModelAndView mv = this.getModelAndView();
			mv.setViewName("system/index/default");
			return mv;
		}
		
		String applyUserId=StringUtil.getUserName();
		Map<String, String> auditMap=dashboardService.getAuditMap(applyUserId);//审核组查询
		String roleType=StringUtil.getRoleType(auditMap);//获取用户的角色类型
		PageData pd = getPageData("applyUserId", applyUserId, "roleType", roleType, "audit", auditMap.get("audit"));
		Map<String, String[]> hostIdsMap=dashboardService.getHostIdsMap(pd);//主机ID列表查询
		Map<String, CmpCdLoad> virLoadMap=new HashMap<String, CmpCdLoad>();//虚拟机负载
		ModelAndView mv = this.getModelAndView();
		mv.addObject("hostIdStr", StringUtil.getArrStr(hostIdsMap.get("virStr")));//主机ID列表
		mv.addObject("cpuTimeType", dbReq.getCpuTimeType());//CPU时间类型
		mv.addObject("memTimeType", dbReq.getMemTimeType());//内存时间类型
		mv.addObject("storeTimeType", dbReq.getStoreTimeType());//磁盘时间类型
		mv.addObject("resType", dbReq.getResType());//资源类型：cpu-CPU、mem-内存、store-磁盘
		mv.addObject("chkFlag", dbReq.getChkFlag());//复选框是否选中：0-否；1-是
		mv.addObject("timeTypeList", cmpDictService.getCmpDictList("dashboard_time_type"));//仪表盘时间类型列表
		mv.addObject("resTypeList", cmpDictService.getCmpDictList("dashboard_res_type"));//仪表盘资源类型列表
		mv.addObject("virNum", dashboardService.getVirNum(pd));//虚机总量
		mv.addObject("hostNum", dashboardService.getHostNum(pd));//宿主机总量
		mv.addObject("physNum", dashboardService.getPhysNum(pd));//物理机总量
		mv.addObject("userNum", dashboardService.getUserNum(pd));//用户总数
		mv.addObject("projNum", dashboardService.getProjNum(pd));//项目总数
		mv.addObject("workOrderNum", dashboardService.getWorkOrderNum(pd));//工单总数
		mv.addObject("vir", dashboardService.getResDtl("virRes", hostIdsMap.get("virStr")));//虚拟资源详细信息查询
		mv.addObject("phys", dashboardService.getResDtl("physRes", hostIdsMap.get("virStr")));//物理资源详细信息查询
		mv.addObject("virLoad", dashboardService.getLoadDtl(virLoadMap, hostIdsMap.get("vir"), dbReq.getResType()));//虚拟机负载
		mv.addObject("hostLoad", dashboardService.getLoadDtl(new HashMap<String, CmpCdLoad>(), hostIdsMap.get("host"), dbReq.getResType()));//宿主机负载
		mv.addObject("physLoad", dashboardService.getLoadDtl(new HashMap<String, CmpCdLoad>(), hostIdsMap.get("phys"), dbReq.getResType()));//物理机负载
		mv.addObject("virRun", dashboardService.getRunDtl(hostIdsMap.get("vir")));//虚拟机运行
		mv.addObject("hostRun", dashboardService.getRunDtl(hostIdsMap.get("host")));//宿主机运行
		mv.addObject("physRun", dashboardService.getRunDtl(hostIdsMap.get("phys")));//物理机运行
		//mv.addObject("cpuResRate", dashboardService.getResRate("cpu", dbReq.getCpuTimeType(), hostIdsMap.get("vir")));//CPU资源使用量趋势
		//mv.addObject("memResRate", dashboardService.getResRate("mem", dbReq.getMemTimeType(), hostIdsMap.get("vir")));//存储资源使用量趋势
		//mv.addObject("storeResRate", dashboardService.getResRate("store", dbReq.getStoreTimeType(), hostIdsMap.get("vir")));//磁盘资源使用量趋势
		mv.addObject("resUseList", dashboardService.getResUseList(virLoadMap));//资源使用列表
		mv.setViewName("system/index/default");
		return mv;
	}
	
	//资源使用量趋势查询
	@RequestMapping(value="/getResRate" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getResRate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BufferedReader br = null;
        try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
            
			String reqStr = sb.toString();//请求参数字符串
            logger.info("请求参数：" + reqStr);
            JSONObject jsonObj=JSONObject.fromObject(reqStr);
            DashboardRequest dbReq=getJsonReqParam(jsonObj);//获取请求参数
    		String errMsg = checkJsonParam(dbReq);//校验必填参数
    		if (errMsg != null) {
    			return StringUtil.getRetStr("-1", "仪表盘查询时错误："+errMsg);
    		}
    		
			CmpAxis resRate=new CmpAxis("hour");
			String hostIdStr=dbReq.getHostIdStr();
			if(!StringUtils.isBlank(hostIdStr)){//主机列表不为空
				String[] hostIds=hostIdStr.split(",");//拼接字符串转换成字符串数组
				String timeType=dbReq.getCpuTimeType();//CPU
				if("mem".equals(dbReq.getResType())){//内存
					timeType=dbReq.getMemTimeType();
				}else if("store".equals(dbReq.getResType())){//磁盘
					timeType=dbReq.getStoreTimeType();
				}
				
				resRate=dashboardService.getResRate(dbReq.getResType(), timeType, hostIds);//资源使用量趋势
			}
			
			return StringUtil.getRetStr("0", "仪表盘查询成功", "resRate", resRate);
		} catch (Exception e) {
			logger.error("仪表盘查询时错误："+e);
			return StringUtil.getRetStr("-1", "仪表盘查询时错误："+e);
	    }finally {
	        if(br!=null){
	            try {
	                br.close();
	                br=null;
	            } catch (Exception e) {
	            	br=null;
	            }
	        }
	    }
	}
	
	//资源使用列表查询
	@RequestMapping(value="/getResUseList" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getResUseList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			String reqStr = sb.toString();//请求参数字符串
			logger.info("请求参数：" + reqStr);
			JSONObject jsonObj=JSONObject.fromObject(reqStr);
			String resType=StringUtil.getJsonStr(jsonObj, "resType");//资源类型：cpu-CPU、mem-内存、store-磁盘
			if(StringUtils.isBlank(resType)){//资源类型：cpu-CPU、mem-内存、store-磁盘
				return StringUtil.getRetStr("-1", "仪表盘查询时错误：资源类型不能为空");
			}
			
			List<CmpCdLoad> resUseList=null;
			String hostIdStr=StringUtil.getJsonStr(jsonObj, "hostIdStr");//主机ID列表
			if(!StringUtils.isBlank(hostIdStr)){//主机列表不为空
				Map<String, CmpCdLoad> virLoadMap=new HashMap<String, CmpCdLoad>();//虚拟机负载
				dashboardService.getLoadDtl(virLoadMap, hostIdStr.split(","), resType);//虚拟机负载
				resUseList=dashboardService.getResUseList(virLoadMap);//资源使用列表
			}
			
			return StringUtil.getRetStr("0", "仪表盘查询成功", "resUseList", resUseList);
		} catch (Exception e) {
			logger.error("仪表盘查询时错误："+e);
			return StringUtil.getRetStr("-1", "仪表盘查询时错误："+e);
		}finally {
			if(br!=null){
				try {
					br.close();
					br=null;
				} catch (Exception e) {
					br=null;
				}
			}
		}
	}
	
	/**访问登录页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_toLogin")
	public ModelAndView toLogin()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = this.setLoginPd(pd);	//设置登录页面的配置参数
		mv.setViewName("system/index/login");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**请求登录，验证用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_login" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object login()throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "";
		String KEYDATA[] = pd.getString("KEYDATA").replaceAll("qq313596790fh", "").replaceAll("QQ978336446fh", "").split(",fh,");
		if(null != KEYDATA && KEYDATA.length == 3){
			Session session = Jurisdiction.getSession();
			//String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
			//String code = sessionCode;//KEYDATA[2];注释掉验证码先，便于测试
//			if(null == code || "".equals(code)){//判断效验码
//				errInfo = "nullcode"; 			//效验码为空
//			}else{
				String USERNAME = KEYDATA[0];	//登录过来的用户名
				String PASSWORD  = KEYDATA[1];	//登录过来的密码
				pd.put("USERNAME", USERNAME);
//				if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){		//判断登录验证码
					String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();	//密码加密
					pd.put("PASSWORD", passwd);
					pd = userService.getUserByNameAndPwd(pd);	//根据用户名和密码去读取用户信息
					if(pd != null){
						this.removeSession(USERNAME);//请缓存
						pd.put("LAST_LOGIN",DateUtil.getTime().toString());
						userService.updateLastLogin(pd);
						User user = new User();
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));
						user.setROLE_ID(pd.getString("ROLE_ID"));
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						user.setDEPARTMENT_ID(pd.getString("DEPARTMENT_ID"));
						user.setRole(roleService.getRoleById(pd.getString("ROLE_ID")));
						session.setAttribute(Const.SESSION_USER, user);			//把用户信息放session中
						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//清除登录验证码的session
						//shiro加入身份验证
						Subject subject = SecurityUtils.getSubject(); 
					    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD); 
					    try { 
					        subject.login(token); 
					    } catch (AuthenticationException e) { 
					    	errInfo = "身份验证失败！";
					    }
					}else{
						errInfo = "usererror"; 				//用户名或密码有误
						logBefore(logger, USERNAME+"登录系统密码或用户名错误");
						FHLOG.save(USERNAME, "登录系统密码或用户名错误");
					}
//				}else{
//					errInfo = "codeerror";				 	//验证码输入有误
//				}
				if(Tools.isEmpty(errInfo)){
					errInfo = "success";					//验证成功
					logBefore(logger, USERNAME+"登录系统");
					FHLOG.save(USERNAME, "登录系统");
				}
//			}
		}else{
			errInfo = "error";	//缺少参数
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**访问系统首页
	 * @param changeMenu：切换菜单参数
	 * @return
	 */
	@RequestMapping(value="/main/{changeMenu}")
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if (user != null) {
				User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
				if(null == userr){
					user = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
					session.setAttribute(Const.SESSION_USERROL, user);						//存入session	
				}else{
					user = userr;
				}
				String USERNAME = user.getUSERNAME();
				Role role = user.getRole();													//获取用户角色
				String roleRights = role!=null ? role.getRIGHTS() : "";						//角色权限(菜单权限)
				session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); 	//将角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);						//放入用户名到session
				this.setAttributeToAllDEPARTMENT_ID(session, USERNAME);						//把用户的组织机构权限放到session里面
				List<Menu> allmenuList = new ArrayList<Menu>();
				allmenuList = this.getAttributeMenu(session, USERNAME, roleRights);			//菜单缓存
				List<Menu> menuList = new ArrayList<Menu>();
				menuList = this.changeMenuF(allmenuList, session, USERNAME, changeMenu);	//切换菜单
				if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
					session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));//按钮权限放到session中
				}
				this.getRemortIP(USERNAME);	//更新登录IP
				mv.setViewName("system/index/main");
				mv.addObject("user", user);
				mv.addObject("menuList", menuList);
			}else {
				mv.setViewName("system/index/login");//session失效后跳转登录页面
			}
			SysConfigInfo  sysConfigInfo = SysConfigInfo.getInstance();
			pd.put("logo", sysConfigInfo.getLogo());
		} catch(Exception e){
			mv.setViewName("system/index/login");
			logger.error(e.getMessage(), e);
		}
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.addObject("pd",pd);
		return mv;
	}
	
	
	/**菜单缓存
	 * @param session
	 * @param USERNAME
	 * @param roleRights
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getAttributeMenu(Session session, String USERNAME, String roleRights) throws Exception{
		List<Menu> allmenuList = new ArrayList<Menu>();
		if(null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)){	
			allmenuList = menuService.listAllMenuQx("0");							//获取所有菜单
			if(Tools.notEmpty(roleRights)){
				allmenuList = this.readMenu(allmenuList, roleRights);				//根据角色权限获取本权限的菜单列表
			}
			session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);//菜单权限放入session中
		}else{
			allmenuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList);
		}
		return allmenuList;
	}
	
	/**根据角色权限获取本权限的菜单列表(递归处理)
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			if(menuList.get(i).isHasMenu()){		//判断是否有此菜单权限
				this.readMenu(menuList.get(i).getSubMenu(), roleRights);//是：继续排查其子菜单
			}
		}
		return menuList;
	}
	
	/**切换菜单处理
	 * @param allmenuList
	 * @param session
	 * @param USERNAME
	 * @param changeMenu
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> changeMenuF(List<Menu> allmenuList, Session session, String USERNAME, String changeMenu){
		List<Menu> menuList = new ArrayList<Menu>();
		if(null == session.getAttribute(USERNAME + Const.SESSION_menuList) || ("yes".equals(changeMenu))){
			List<Menu> menuList1 = new ArrayList<Menu>();
			List<Menu> menuList2 = new ArrayList<Menu>();
			for(int i=0;i<allmenuList.size();i++){//拆分菜单
				Menu menu = allmenuList.get(i);
				if("1".equals(menu.getMENU_TYPE())){
					menuList1.add(menu);
				}else{
					menuList2.add(menu);
				}
			}
			session.removeAttribute(USERNAME + Const.SESSION_menuList);
			if("2".equals(session.getAttribute("changeMenu"))){
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList1);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "1");
				menuList = menuList1;
			}else{
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList2);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "2");
				menuList = menuList2;
			}
		}else{
			menuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_menuList);
		}
		return menuList;
	}
	
	/**把用户的组织机构权限放到session里面
	 * @param session
	 * @param USERNAME
	 * @return
	 * @throws Exception 
	 */
	public void setAttributeToAllDEPARTMENT_ID(Session session, String USERNAME) throws Exception{
		String DEPARTMENT_IDS = "0",DEPARTMENT_ID = "0";
		if(!"admin".equals(USERNAME)){
			PageData pd = datajurService.getDEPARTMENT_IDS(USERNAME);
			DEPARTMENT_IDS = null == pd?"无权":pd.getString("DEPARTMENT_IDS");
			DEPARTMENT_ID = null == pd?"无权":pd.getString("DEPARTMENT_ID");
		}
		session.setAttribute(Const.DEPARTMENT_IDS, DEPARTMENT_IDS);	//把用户的组织机构权限集合放到session里面
		session.setAttribute(Const.DEPARTMENT_ID, DEPARTMENT_ID);	//把用户的最高组织机构权限放到session里面
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/tab")
	public String tab(){
		return "system/index/tab";
	}
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout() throws Exception{
		String USERNAME = Jurisdiction.getUsername();	//当前登录的用户名
		logBefore(logger, USERNAME+"退出系统");
		FHLOG.save(USERNAME, "退出");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		this.removeSession(USERNAME);//请缓存
		//shiro销毁登录
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		pd = this.getPageData();
		pd.put("msg", pd.getString("msg"));
		pd = this.setLoginPd(pd);	//设置登录页面的配置参数
		mv.setViewName("system/index/login");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 清理session
	 */
	public void removeSession(String USERNAME){
		Session session = Jurisdiction.getSession();	//以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");
		session.removeAttribute("DEPARTMENT_IDS");
		session.removeAttribute("DEPARTMENT_ID");
	}
	
	/**设置登录页面的配置参数
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData setLoginPd(PageData pd) throws Exception{
		SysConfigInfo  sysConfigInfo = SysConfigInfo.getInstance();
		if (sysConfigInfo.getSysName() == null || sysConfigInfo.getSysName().length() == 0) {
			//第一次访问，初始化
			SysConfigInfo  dbSysConfigInfo = sysConfigService.getSysConfig();
			sysConfigInfo.setCompanyName(dbSysConfigInfo.getCompanyName());
			sysConfigInfo.setCompanyShortName(dbSysConfigInfo.getCompanyShortName());
			sysConfigInfo.setCopr(dbSysConfigInfo.getCopr());
			sysConfigInfo.setLogo(dbSysConfigInfo.getLogo());
			sysConfigInfo.setProductConsultion(dbSysConfigInfo.getProductConsultion());
			sysConfigInfo.setSysName(dbSysConfigInfo.getSysName());
			sysConfigInfo.setTel(dbSysConfigInfo.getTel());
			sysConfigInfo.setWebsite(dbSysConfigInfo.getWebsite());
		}
		
		pd.put("SYSNAME", sysConfigInfo.getSysName()); 		//读取系统名称
		pd.put("logo", sysConfigInfo.getLogo());
		pd.put("copr", sysConfigInfo.getCopr()); 
		String strLOGINEDIT = Tools.readTxtFile(Const.LOGINEDIT);	//读取登录页面配置
		if(null != strLOGINEDIT && !"".equals(strLOGINEDIT)){
			String strLo[] = strLOGINEDIT.split(",fh,");
			if(strLo.length == 2){
				pd.put("isZhuce", strLo[0]);
				pd.put("isMusic", strLo[1]);
			}
		}
		try {
			List<PageData> listImg = loginimgService.listAll(pd);	//登录背景图片
			pd.put("listImg", listImg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**获取用户权限
	 * @param session
	 * @return
	 */
	public Map<String, String> getUQX(String USERNAME){
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			pd.put("ROLE_ID", userService.findByUsername(pd).get("ROLE_ID").toString());//获取角色ID
			pd = roleService.findObjectById(pd);										//获取角色信息														
			map.put("adds", pd.getString("ADD_QX"));	//增
			map.put("dels", pd.getString("DEL_QX"));	//删
			map.put("edits", pd.getString("EDIT_QX"));	//改
			map.put("chas", pd.getString("CHA_QX"));	//查
			List<PageData> buttonQXnamelist = new ArrayList<PageData>();
			if("admin".equals(USERNAME)){
				buttonQXnamelist = fhbuttonService.listAll(pd);					//admin用户拥有所有按钮权限
			}else{
				buttonQXnamelist = buttonrightsService.listAllBrAndQxname(pd);	//此角色拥有的按钮权限标识列表
			}
			for(int i=0;i<buttonQXnamelist.size();i++){
				map.put(buttonQXnamelist.get(i).getString("QX_NAME"),"1");		//按钮权限
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		return map;
	}
	
	/** 更新登录用户的IP
	 * @param USERNAME
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {  
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {  
			ip = request.getRemoteAddr();  
	    }else{
	    	ip = request.getHeader("x-forwarded-for");  
	    }
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}  
	
	@RequestMapping(value = "/getSysConfig")
	@ResponseBody
	public Object getSysConfig() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String resultInfo = "";
		if (SysConfigInfo.getInstance().getSysName() != null && SysConfigInfo.getInstance().getSysName().length() >0) {
			map.put("sysConfigInfo", SysConfigInfo.getInstance());
		}else {
			//第一次访问，初始化
			SysConfigInfo  sysConfigInfo = sysConfigService.getSysConfig();
			SysConfigInfo initConfig = SysConfigInfo.getInstance();
			initConfig.setCompanyName(sysConfigInfo.getCompanyName());
			initConfig.setCompanyShortName(sysConfigInfo.getCompanyShortName());
			initConfig.setCopr(sysConfigInfo.getCopr());
			initConfig.setLogo(sysConfigInfo.getLogo());
			initConfig.setProductConsultion(sysConfigInfo.getProductConsultion());
			initConfig.setSysName(sysConfigInfo.getSysName());
			initConfig.setTel(sysConfigInfo.getTel());
			initConfig.setWebsite(sysConfigInfo.getWebsite());
			map.put("sysConfigInfo", initConfig);
		}
		
		resultInfo = "success";
		map.put("result", resultInfo);	
		return map;
	}
	
	//获取请求参数
	private DashboardRequest getReqParam(HttpServletRequest request){
		DashboardRequest dbReq=new DashboardRequest();
		dbReq.setCpuTimeType(request.getParameter("cpuTimeType"));//CPU时间类型
		dbReq.setMemTimeType(request.getParameter("memTimeType"));//内存时间类型
		dbReq.setStoreTimeType(request.getParameter("storeTimeType"));//磁盘时间类型
		dbReq.setResType(request.getParameter("resType"));//资源类型：cpu-CPU、mem-内存、store-磁盘
		dbReq.setChkFlag(request.getParameter("chkFlag"));//复选框是否选中：0-否；1-是
		return dbReq;
	}
	
	//获取JSON请求参数
	private DashboardRequest getJsonReqParam(JSONObject jsonObj){
		DashboardRequest dbReq=new DashboardRequest();
		dbReq.setResType(StringUtil.getJsonStr(jsonObj, "resType"));//资源类型：cpu-CPU、mem-内存、store-磁盘
		dbReq.setCpuTimeType(StringUtil.getJsonStr(jsonObj, "cpuTimeType"));//CPU时间类型
		dbReq.setMemTimeType(StringUtil.getJsonStr(jsonObj, "memTimeType"));//内存时间类型
		dbReq.setStoreTimeType(StringUtil.getJsonStr(jsonObj, "storeTimeType"));//磁盘时间类型
		dbReq.setHostIdStr(StringUtil.getJsonStr(jsonObj, "hostIdStr"));//主机ID列表
		return dbReq;
	}
	
	//校验必填参数
	private String checkParam(DashboardRequest dbReq){
		if(StringUtils.isBlank(dbReq.getCpuTimeType())){//CPU时间类型
			dbReq.setCpuTimeType("hour");
		}
		
		if(StringUtils.isBlank(dbReq.getMemTimeType())){//内存时间类型
			dbReq.setMemTimeType("hour");
		}
		
		if(StringUtils.isBlank(dbReq.getStoreTimeType())){//磁盘时间类型
			dbReq.setStoreTimeType("hour");
		}
		
		if(StringUtils.isBlank(dbReq.getResType())){//资源类型：cpu-CPU、mem-内存、store-磁盘
			dbReq.setResType("cpu");
		}
		
		if(StringUtils.isBlank(dbReq.getChkFlag())){//复选框是否选中：0-否；1-是
			dbReq.setChkFlag("1");
		}
		
		return null;
	}
	
	//校验JSON必填参数
	private String checkJsonParam(DashboardRequest dbReq){
		if(StringUtils.isBlank(dbReq.getCpuTimeType())){//CPU时间类型
			dbReq.setCpuTimeType("hour");
		}
		
		if(StringUtils.isBlank(dbReq.getMemTimeType())){//内存时间类型
			dbReq.setMemTimeType("hour");
		}
		
		if(StringUtils.isBlank(dbReq.getStoreTimeType())){//磁盘时间类型
			dbReq.setStoreTimeType("hour");
		}
		
		if(StringUtils.isBlank(dbReq.getResType())){//资源类型：cpu-CPU、mem-内存、store-磁盘
			dbReq.setResType("cpu");
		}
		
		return null;
	}
}
