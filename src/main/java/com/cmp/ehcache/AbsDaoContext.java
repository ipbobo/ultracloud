package com.cmp.ehcache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

public class AbsDaoContext {
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		AbsDaoContext.applicationContext = applicationContext;
	}

	public static AbstractDao<?, ?> getDal(String id) {
		return (AbstractDao<?, ?>) applicationContext.getBean(id);
	}

	public static List<AbstractDao<?, ?>> getDaoList() {
		List<AbstractDao<?, ?>> list = new ArrayList<AbstractDao<?, ?>>();
		String[] beanNames = applicationContext.getBeanNamesForType(AbstractDao.class);
		for (String beanName : beanNames) {
			list.add((AbstractDao<?, ?>) applicationContext.getBean(beanName));
		}
		
		return list;
	}
}
