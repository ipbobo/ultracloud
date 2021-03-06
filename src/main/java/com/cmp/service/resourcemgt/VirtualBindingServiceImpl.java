package com.cmp.service.resourcemgt;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.ProjectService;
import com.cmp.service.servicemgt.EnvironmentService;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.user.impl.UserService;
import com.fh.util.PageData;

/**
 * 存量资源绑定 业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("virtualBindingService")
public class VirtualBindingServiceImpl implements VirtualBindingService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name = "clusterService")
	private ClusterService clusterService;
	
	@Resource(name = "projectService")
	private ProjectService projectService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "environmentService")
	private EnvironmentService environmentService;
	
	/**
	 * 加载初始化数据
	 * @param mv
	 * @param pd
	 * @throws Exception
	 */
	public void loadInitData(ModelAndView mv, PageData pd) throws Exception {
		List<PageData>	clusterList = clusterService.listAll(pd);
		mv.addObject("clusterList", clusterList);
		
		List<PageData>	projectList = projectService.listAll(pd);
		mv.addObject("projectList", projectList);
		
		List<PageData>	userList = userService.listAllUser(pd);
		mv.addObject("userList", userList);
		
		List<PageData> environmentList = environmentService.listAll(pd);
		mv.addObject("environmentList", environmentList);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("VirtualBindingMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("VirtualBindingMapper.datalistPage", page);
	}
	
	/**
	 * 查询已分配用户的虚拟机
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> datalistAlreadyBinded(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("VirtualBindingMapper.datalistAlreadyBinded", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> datalistByIds(String[] ArrayDATA_IDS) throws Exception {
		return (List<PageData>) dao.findForList("VirtualBindingMapper.datalistByIds", ArrayDATA_IDS);
	}
}
