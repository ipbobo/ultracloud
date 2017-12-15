package com.cmp.service.resourcemgt;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.cmp.service.ClusterService;
import com.cmp.service.ProjectService;
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
	}

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("VirtualBindingMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("VirtualBindingMapper.delete", pd);
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
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("VirtualBindingMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("VirtualBindingMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("VirtualBindingMapper.deleteAll", ArrayDATA_IDS);
	}

}
