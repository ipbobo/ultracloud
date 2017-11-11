package com.cmp.ehcache.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmp.ehcache.cache.oper.CacheOper;
import com.cmp.ehcache.cache.oper.CacheOperMgr;

public class CacheConfig{
	private static Logger logger = LoggerFactory.getLogger(CacheConfig.class);
	private static CacheMgr cacheMgr;
	private static List<Class<?>> entityClassList = new ArrayList<Class<?>>();
	private static Set<String> zipSet = new HashSet<String>();

	@SuppressWarnings("unchecked")
	public static void configure() throws Exception{
	    try {
	    	logger.info("开始加载Ehcache缓存");
	    	Document doc = new SAXReader().read(new File(CacheConfig.class.getResource("/").getPath() + "conf/ehcache-config.xml"));
	    	Element root = doc.getRootElement();
	    	CacheOperMgr cacheOperMgr = new CacheOperMgr();//缓存操作管理
	    	List<Element> cacheList = root.elements("cache");
	    	for (Element cache: cacheList) {
		        CacheOper cacheOper = (CacheOper)Class.forName(cache.attributeValue("class")).newInstance();//默认CacheOperEh
		        cacheOper.setCacheId(cache.attributeValue("id"));
		        cacheOper.init();//加载net.sf.ehcache.CacheManager
		        logger.info("缓存" + cache.attributeValue("id") + "初始化完毕");
		        cacheOperMgr.addCacheOper(cacheOper, "true".equals(cache.attributeValue("default")));
	    	}
	    	
	    	if (cacheOperMgr.getDefaultCacheOper() == null) {
	    		throw new CacheException("没有设置缺省缓存：cache节点没有配置“default=\"true\"”");
	    	}
	
	    	cacheMgr = new CacheMgr(cacheOperMgr);//缓存管理
	    	List<Element> entityList = root.elements("entity");//实体类
	    	for (Element entity : entityList){
		        Class<?> entityClass = Class.forName(entity.attributeValue("class"));
		        entityClassList.add(entityClass);
		        cacheMgr.registerEntity(entityClass);
		        cacheOperMgr.addEntityCacheMap(entityClass, entity.attributeValue("cache"));
		        List<Element> indxList = entity.elements("indx");
		        for(Element indx : indxList) {
		            String name = indx.attributeValue("name");
		            CacheException.asserts(name!=null, "缓存索引[" + entity.attributeValue("class") + "]：名称必须配置");
		            cacheMgr.createDefaultIndx(entityClass, name);
		            logger.info("缓存索引[" + entity.attributeValue("class") + "]：名称=[" + name + "]");
		        }
	    	}
	    }catch (DocumentException e) {
	    	logger.error("DAL配置异常:", e);
	    }
	}

	public static CacheMgr getCacheMgr() {
		return cacheMgr;
	}

	public static List<Class<?>> getEntityClassList() {
		return entityClassList;
	}

	public static boolean isZip(Class<?> entityClass) {
		return zipSet.contains(entityClass.getName());
	}
}