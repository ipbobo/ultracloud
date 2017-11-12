package com.cmp.activiti;

import javax.annotation.Resource;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;

public class CustomGroupEntityManagerFactory implements SessionFactory {
	@Resource
    private CustomGroupEntityManager customGroupEntityManager;
	
    public Class<?> getSessionType() {
        return GroupIdentityManager.class;//返回原始的GroupManager类型
    }
 
    public Session openSession() {
        return customGroupEntityManager;//返回自定义的GroupManager实例
    }
}