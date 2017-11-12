package com.cmp.ehcache;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmp.ehcache.cache.CacheException;
import com.cmp.ehcache.cache.CacheQuery;
import com.cmp.ehcache.util.StringUtil;
import com.fh.dao.DaoSupport;
import com.cmp.ehcache.cache.CacheConfig;

@SuppressWarnings("unchecked")
public abstract class AbstractDao<E, PK extends Serializable> {
	private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);
	protected Class<E> entityClass;
	@Resource
	private DaoSupport daoSupport;

	public AbstractDao() {
		entityClass = (Class<E>)StringUtil.getEntityClass(getClass());
	}

	public PK insert(E entity) throws CacheException {
		try {
			Object keyValue = StringUtil.getKeyValue(entity);
			CacheException.asserts(keyValue != null, "insert 实体ID不可为空");
			CacheConfig.getCacheMgr().addEntity(keyValue, entity);
			return (PK) keyValue;
		} catch (Exception e) {
			logger.error("插入单条实体错误", e);
			throw new CacheException(e);
		}
	}

	public void update(E entity) throws CacheException {
		try {
			Object keyValue = StringUtil.getKeyValue(entity);
			CacheConfig.getCacheMgr().updateEntity(keyValue, entity);
		} catch (Exception e) {
			logger.error("更新单条实体错误", e);
			throw new CacheException(e);
		}
	}

	public void deleteEntity(E entity) throws CacheException {
		try {
			Object keyValue = StringUtil.getKeyValue(entity);
			CacheConfig.getCacheMgr().removeEntity(keyValue, entityClass);
		} catch (Exception e) {
			logger.error("删除单条实体错误", e);
			throw new CacheException(e);
		}
	}

	public void deleteById(Object id) throws CacheException {
		try {
			E entity = getEntityById(id);
			deleteEntity(entity);
		} catch (Exception e) {
			logger.error("删除单条实体错误", e);
			throw new CacheException(e);
		}
	}

	public void deleteList(List<E> entities) throws CacheException {
		try {
			if (entities == null) {
				return;
			}
			for (E entity : entities)
				deleteEntity(entity);
		} catch (Exception e) {
			logger.error("删除多条实体错误", e);
			throw new CacheException(e);
		}
	}

	public void delete(CacheQuery<E> cacheQuery) throws CacheException {
		try {
			List<E> entities = cacheQuery.getList();
			deleteList(entities);
		} catch (Exception e) {
			logger.error("按条件删除错误", e);
			throw new CacheException(e);
		}
	}

	public E getEntityById(Object id) throws CacheException {
		try {
			Object entity = null;
			entity = CacheConfig.getCacheMgr().getCacheOperMgr().getEntityZip(entityClass, id);
			if (entity == null) {
				logger.debug("读取" + entityClass.getSimpleName() + "缓存返回数据为空，主键为：" + id);
			}
			return (E) entity;
		} catch (Exception e) {
			logger.error("查询单条实体错误", e);
			throw new CacheException(e);
		}
	}

	public List<E> getList() throws CacheException {
		try {
			return getCacheQuery().whole().getList();
		} catch (Exception e) {
			logger.error("查询实体列表错误", e);
			throw new CacheException(e);
		}
	}

	public E copy(E entity) throws CacheException {
		try {
			E newEntity = entityClass.newInstance();
			PropertyUtils.copyProperties(newEntity, entity);
			return newEntity;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	protected CacheQuery<E> getCacheQuery() throws Exception {
		return CacheConfig.getCacheMgr().createQuery(entityClass);
	}

	public void clearCache() throws CacheException {
		try {
			CacheConfig.getCacheMgr().clear(entityClass);
		} catch (Exception e) {
			logger.error("清空缓存错误", e);
			throw new CacheException(e);
		}
	}

	public void reloadCache() throws CacheException {
		clearCache();
		loadData();
	}

	public void loadData() throws CacheException {
		try {
			long time = System.currentTimeMillis();
			List<E> list = (List<E>)daoSupport.findForList(getMybatisQryFunc(), null);
			for (E entity : list) {
				if (entity != null) {
					CacheConfig.getCacheMgr().addEntity(StringUtil.getKeyValue(entity), entity);
				}
			}
			
			logger.info("缓存了" + list.size() + "条" + entityClass.getSimpleName() + "数据,用时" + (System.currentTimeMillis() - time) + "ms");
		} catch (Exception e) {
			logger.error("加载缓存错误", e);
			throw new CacheException(e);
		}
	}
	
	/**
	 * 获取mybatis方法
	 */
	public abstract String getMybatisQryFunc();
}