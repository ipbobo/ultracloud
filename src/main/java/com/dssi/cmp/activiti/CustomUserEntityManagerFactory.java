package com.dssi.cmp.activiti;

import javax.annotation.Resource;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;

public class CustomUserEntityManagerFactory implements SessionFactory {
	@Resource
    private CustomUserEntityManager customUserEntityManager;

    public Class<?> getSessionType() {
        return UserIdentityManager.class;//返回原始的UserManager类型
    }
 
    public Session openSession() {
        return customUserEntityManager;
    }
}