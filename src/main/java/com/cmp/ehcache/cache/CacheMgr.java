package com.cmp.ehcache.cache;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmp.ehcache.cache.indx.ICacheIndx;
import com.cmp.ehcache.cache.indx.impl.CacheIndxDefault;
import com.cmp.ehcache.cache.oper.CacheOperMgr;

public class CacheMgr {
	private static final Logger logger = LoggerFactory.getLogger(CacheMgr.class);
	protected CacheOperMgr cacheOperMgr;
	protected CacheIndxMgr cacheIndxMgr;

	public CacheMgr(CacheOperMgr cacheOperMgr) {
		this.cacheOperMgr = cacheOperMgr;
		this.cacheIndxMgr = new CacheIndxMgr();
	}

	public void createDefaultIndx(Class<?> entityClass, String indxName) throws CacheException {
		try {
			if(!entityClass.getName().equals(indxName)){//非类级别索引
				entityClass.getDeclaredField(indxName);
			}
		} catch (Exception e) {
			throw new CacheException("索引建立失败，" + entityClass.getName() + "无" + indxName + "属性!");
		}
		
		initCacheIndx(entityClass, new CacheIndxDefault(indxName));
	}
	
	public void createWholeIndx(Class<?> entityClass) throws CacheException {
		initCacheIndx(entityClass, new CacheIndxDefault(null));
	}

	private void initCacheIndx(Class<?> entityClass, ICacheIndx cacheIndx) throws CacheException {
		cacheIndxMgr.regsiterCacheIndx(entityClass, cacheIndx);
	}

	public void registerEntity(Class<?> entityClass) throws CacheException {
		cacheIndxMgr.registerEntity(entityClass);
	}

	public CacheIndxMgr getCacheIndxMgr() {
		return cacheIndxMgr;
	}

	public CacheOperMgr getCacheOperMgr() {
		return cacheOperMgr;
	}

	public void addEntity(Object id, Object entity) throws Exception {
		CacheException.asserts((id != null) && (entity != null), "添加的实体和主键值不可为NULL:" + entity.getClass().getName());
		if (entity instanceof INotCacheable) {//该实体类不需缓存
			return;
		}
		
		Object entityKey = cacheOperMgr.getEntityKey(id, entity.getClass());
		cacheOperMgr.putEntity(entityKey, entity);
		cacheIndxMgr.addEntity(entityKey, entity);
		CacheQuery.clearQueryCache(entity.getClass());
	}

	public boolean removeEntity(Object id, Class<?> entityClass) throws Exception {
		Object entityKey = cacheOperMgr.getEntityKey(id, entityClass);
		Object entity = cacheOperMgr.getEntity(entityClass, entityKey);
		if (entity == null) {
			return false;
		}
		
		boolean removed = false;
		if (!(entity instanceof INotCacheable)) {
			removed = cacheOperMgr.removeEntity(entityClass, entityKey);
		}
		
		cacheIndxMgr.removeEntity(entityKey, entity);
		CacheQuery.clearQueryCache(entityClass);
		return removed;
	}

	public void updateEntity(Object id, Object entity) throws Exception {
		if (removeEntity(id, entity.getClass()))
			addEntity(id, entity);
	}

	public void clear(Class<?> entityClass) throws Exception {
		long time = System.currentTimeMillis();
		long clearCount = cacheOperMgr.clear(entityClass);
		cacheIndxMgr.clear(entityClass);
		logger.info("清空" + entityClass.getSimpleName() + "了" + clearCount + "条数据,用时" + (System.currentTimeMillis() - time) + "ms");
	}

	public Object getEntity(Object id, Class<?> entityClass) throws Exception {
		Object entityKey = cacheOperMgr.getEntityKey(id, entityClass);
		return cacheOperMgr.getEntity(entityClass, entityKey);
	}

	public <E> CacheQuery<E> createQuery(Class<E> entityClass) throws CacheException {
		Map<String, ICacheIndx> cacheIndxMap = cacheIndxMgr.getAllCacheIndxMap().get(entityClass);
		CacheException.asserts(cacheIndxMap != null, entityClass.getName() + "没有建立索引");
		return new CacheQuery<E>(cacheIndxMap, entityClass);
	}
}