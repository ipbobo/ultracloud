package com.cmp.activiti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CustomUserEntityManager extends UserEntityManager {
	private static Logger logger = Logger.getLogger(CustomUserEntityManager.class);
	
	@Override
    public UserEntity findUserById(final String userId) {
        try {
        	if (userId == null){
        		return null;
        	}
        	
        	/*SysUser user=userDao.getLoginUser(userId);
        	if(user!=null){
        		UserEntity ue=new UserEntity();
        		ue.setId(userId);
        		ue.setLastName(user.getName());
        		return ue;
        	}*/
        	
            return null;
        } catch (Exception e) {
        	logger.error("查询用户信息时失败："+e);
            return null;
        }
    }

    @Override
    public List<Group> findGroupsByUser(final String userId) {
    	try{
    		if (userId == null){
    			return null;
    		}
    		
    		List<Group> list=new ArrayList<Group>();
    		/*List<SysRole> roleList=roleDao.getUserRoleList(userId);
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
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        throw new RuntimeException("not implement method.");
    }
    
    @Override
	public List<User> findPotentialStarterUsers(String proceDefId) {
    	throw new RuntimeException("not implement method.");
	}

    @Override
	public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
    	throw new RuntimeException("not implement method.");
	}

    @Override
	public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
    	throw new RuntimeException("not implement method.");
	}
}