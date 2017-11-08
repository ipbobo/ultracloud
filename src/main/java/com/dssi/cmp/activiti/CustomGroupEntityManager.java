package com.dssi.cmp.activiti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CustomGroupEntityManager extends GroupEntityManager {
	private static Logger logger = Logger.getLogger(CustomGroupEntityManager.class);
	
    @Override
    public List<Group> findGroupsByUser(final String userId) {
    	try{
    		if (userId == null){
    			return null;
    		}
    		
    		List<Group> list=new ArrayList<Group>();
    		/*SysUser user=userDao.getLoginUser(userId);
    		List<SysRole> roleList=roleDao.getUserRoleList(user.getId());
    		if(roleList!=null && !roleList.isEmpty()){
    			for(SysRole role: roleList){
    				GroupEntity ge = new GroupEntity();
    				ge.setRevision(1);
    				ge.setType("assignment");
    				ge.setId(role.getId());
    				ge.setName(role.getName());
    				list.add(ge);
    			}
    		}*/
    		
    		return list;
    	}catch(Exception e){
    		logger.error("查询用户角色列表时失败："+e);
    		return null;
    	}
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        throw new RuntimeException("not implement method.");
    }
    
    @Override
    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
    	throw new RuntimeException("not implement method.");
    }

    @Override
    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
    	throw new RuntimeException("not implement method.");
    }
}