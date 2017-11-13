package com.cmp.ehcache.listener;

import com.cmp.ehcache.AbstractDao;
import com.cmp.ehcache.AbsDaoContext;
import com.cmp.ehcache.cache.CacheConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class CacheListener implements ServletContextListener {
	public static final Logger logger = LoggerFactory.getLogger(CacheListener.class);

	public void contextInitialized(ServletContextEvent event) {
		try {
			CacheConfig.configure();//加载缓存配置
			AbsDaoContext.setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext()));
			long time = System.currentTimeMillis();
			for (AbstractDao<?, ?> dao : AbsDaoContext.getDaoList()) {//加载继承AbstractDao的bean
				dao.loadData();//加载数据
			}
			
			logger.info("加载缓存完毕,共用时" + (System.currentTimeMillis() - time) + "ms");
		} catch (Exception e) {
			logger.error("加载缓存时错误", e);
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
	}
}