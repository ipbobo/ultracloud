package com.cmp.controller;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.entity.UserGroupUserMap;
import com.cmp.service.UserGroupService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 用户组 控制层
 * 
 * @author liuweixing
 *
 */
@Controller
@RequestMapping(value = "/usergroup")
public class UserGroupController extends BaseController {

	String menuUrl = "permission/list.do"; //菜单地址(权限用)
	
	@Resource(name="userGroupService")
	private UserGroupService userGroupService;
	
	@Resource(name="roleService")
	private RoleManager roleService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Usergroup");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		userGroupService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Usergroup");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		userGroupService.delete(pd);
		out.write("success");
		out.close();
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除usergroup");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			userGroupService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改UserGroup");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		userGroupService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Usergroup");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = userGroupService.list(page);	//列出UserGroup列表
		mv.setViewName("permission/usergroup_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		
		PageData pd2 = new PageData();
		pd2.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd2);//列出所有系统用户角色
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
		mv.addObject("roleList", roleList);
		mv.setViewName("permission/usergroup_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
		pd = userGroupService.findById(pd);	//根据ID读取
		mv.addObject("roleList", roleList);
		mv.setViewName("permission/usergroup_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**绑定用户
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goUserbind")
	@ResponseBody
	public Object goUserbind() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String role_id = pd.getString("ROLE_ID");
		pd.put("ROLE_ID", role_id);
		
		List<PageData>	notBindingUserList = userService.listAllOutUserByPdId(pd);
		mv.addObject("notBindingUserList", notBindingUserList);
		List<PageData>	bindedUserList = userService.listAllInUserByUserGroupId(pd);
		mv.addObject("bindedUserList", bindedUserList);
		
		pd = userGroupService.findById(pd);	//根据ID读取
		mv.setViewName("permission/usergroup_userbind");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	 /**绑定用户
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/userbind")
	@ResponseBody
	public Object userbind() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"绑定用户");
		PageData pd = new PageData();		
		pd = this.getPageData();
		BigInteger id = new BigInteger(pd.getString("id"));
		String DATA_IDS = pd.getString("DATA_IDS");
		Map<String, String> userIdNameMap = new HashMap<String, String>();
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			List<UserGroupUserMap> newList = new ArrayList<UserGroupUserMap>();
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			
			List<PageData> userList = userService.listUserByUserIds(ArrayDATA_IDS);
			for(PageData userPD : userList) {
				userIdNameMap.put(userPD.getString("USER_ID"), userPD.getString("USERNAME"));
			}
			
			List<UserGroupUserMap> existList = userGroupService.listUserGroupUserMap(pd);
			List<BigInteger> deleteIdList = new ArrayList<BigInteger>();
			if(null != existList && existList.size() > 0) {
				StringBuffer sb = new StringBuffer();
				
				for(UserGroupUserMap userGroupUserMap : existList) {
					sb.append(userGroupUserMap.getUSER_ID()+",");
					if(!DATA_IDS.contains(userGroupUserMap.getUSER_ID())) {
						deleteIdList.add(userGroupUserMap.getId());
					} 
				}
				
				for(int i = 0; i < ArrayDATA_IDS.length; i++) {
					if(!sb.toString().contains(ArrayDATA_IDS[i])) {
						UserGroupUserMap uguMap = new UserGroupUserMap();
						uguMap.setUSER_ID(ArrayDATA_IDS[i]);
						uguMap.setUsergroup_id(id);
						uguMap.setUSERNAME(userIdNameMap.get(ArrayDATA_IDS[i]));
						newList.add(uguMap);
					}
				}
			} else {
				for(int i = 0; i < ArrayDATA_IDS.length; i++) {
						UserGroupUserMap uguMap = new UserGroupUserMap();
						uguMap.setUSER_ID(ArrayDATA_IDS[i]);
						uguMap.setUsergroup_id(id);
						uguMap.setUSERNAME(userIdNameMap.get(ArrayDATA_IDS[i]));
						newList.add(uguMap);
				}
			}
			
			if(deleteIdList.size() > 0) {
				userGroupService.deleteAllUsergroup(deleteIdList);
			}
			if(newList.size() > 0) {
				userGroupService.insertAllUsergroup(newList);
			}
		} else {
			userGroupService.deleteByUsergroupId(id);
		}
		
		ModelAndView mv = this.getModelAndView();
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
}
