package com.cmp.ehcache.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmp.ehcache.cache.indx.ICacheIndx;
import com.cmp.ehcache.cache.oper.CacheOperMgr;
import com.cmp.ehcache.cache.query.AndQuery;
import com.cmp.ehcache.cache.query.IdInQuery;
import com.cmp.ehcache.cache.query.LikeQuery;
import com.cmp.ehcache.cache.query.OrQuery;
import com.cmp.ehcache.util.Pager;

@SuppressWarnings("unchecked")
public class CacheQuery<E> {
	private static final Logger logger = LoggerFactory.getLogger(CacheQuery.class);
	private Class<E> entityClass;
	private Map<String, ICacheIndx> cacheIndxMap;
	private Map<ICacheSort, Boolean> cacheSorts = new HashMap<ICacheSort, Boolean>();
	private AndQuery andQuery = new AndQuery();
	private Set<Object> entityKeySet;
	private Set<Object> topKeys = new LinkedHashSet<Object>();
	private String cacheKey = "";
	private static Map<String, Map<String, Set<Object>>> cacheQueryMap = new HashMap<String, Map<String, Set<Object>>>();

	public CacheQuery(Map<String, ICacheIndx> cacheIndxMap, Class<E> entityClass) {
		this.entityClass = entityClass;
		this.cacheIndxMap = cacheIndxMap;
	}

	public List<E> getList() throws Exception {
		if (!queryFromCache("list")) {
			if(entityKeySet==null){
				entityKeySet=andQuery.execute();
			}
			
			if (entityKeySet == null) {
				return new ArrayList<E>();
			}
			
			indxSort();
			setQueryCache("list");
		}
		
		return getEntityList(entityKeySet);
	}

	public int count() throws Exception {
		if (!queryFromCache("count")) {
			if(entityKeySet==null){
				entityKeySet=andQuery.execute();
			}
			
			setQueryCache("count");
		}
		
		return entityKeySet == null ? 0 : entityKeySet.size();
	}

	public E getUniqueResult() throws Exception {
		List<E> list = getList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

	public Page<E> page(int first, int max) throws Exception {
		Page<E> page = new Page<E>();
		String operate = "page_" + first + "_" + max;
		if (!queryFromCache(operate)) {
			if(entityKeySet==null){
				entityKeySet=andQuery.execute();
			}
			
			if (entityKeySet == null) {
				return page;
			}
			
			indxSort();
			setQueryCache(operate);
		}
		
		page.setCount(entityKeySet.size());
		List<Object> pageKeys = CacheIndxPage.page(entityKeySet, first, max);
		List<E> entities = getEntityList(pageKeys);
		page.setList(entities);
		return page;
	}

	public Pager<E> page(Pager<E> pager) throws Exception {
		Page<E> page = page(pager.getFirst(), pager.getPageSize());
		if (page != null) {
			pager.setList(page.getList());
			pager.setTotalcnt(page.getCount());
		}
		return pager;
	}

	private void indxSort() throws CacheException {
		for (ICacheSort sort : cacheSorts.keySet()) {
			entityKeySet = sort.sort(entityKeySet, ((Boolean) cacheSorts.get(sort)).booleanValue());
		}
		
		if (topKeys.size() > 0) {
			topKeys.addAll(entityKeySet);
			entityKeySet = topKeys;
		}
	}

	private List<E> getEntityList(Collection<Object> keys) throws CacheException {
		if (keys == null) {
			return new ArrayList<E>();
		}
		
		CacheOperMgr cacheOperMgr=CacheConfig.getCacheMgr().getCacheOperMgr();
		List<E> list = new ArrayList<E>();
		for (Object key : keys) {
			Object value = cacheOperMgr.getEntityZip(entityClass, key);
			if (value != null) {
				list.add((E) value);
			}
		}
		
		return list;
	}

	public CacheQuery<E> addCacheKeyQuery(ICacheKeyQuery keyQuery, String indxName, Object value) throws CacheException {
		String valueKey = "";
		if (value != null) {
			valueKey = (value instanceof Object[]) ? StringUtils.join((Object[]) value, "_") : value.toString();
		}
		
		cacheKey += "[" + indxName + "&" + keyQuery.getClass().getSimpleName() + "&" + valueKey + "]";
		andQuery.add(keyQuery);
		return this;
	}

	public CacheQuery<E> or(List<ICacheKeyQuery> keyQuerys, Object value) throws CacheException {
		return addCacheKeyQuery(new OrQuery().add(keyQuerys), null, value);
	}

	public CacheQuery<E> whole() throws CacheException {
		ICacheIndx cacheIndx = getCacheIndx(entityClass.getSimpleName());
		return addCacheKeyQuery(cacheIndx.createEqQuery(entityClass.getSimpleName()), null, "value");
	}

	public CacheQuery<E> eq(String indxName, Object value) throws CacheException {
		return addCacheKeyQuery(getCacheIndx(indxName).createEqQuery(value), indxName, value);
	}

	public CacheQuery<E> gt(String indxName, Object value) throws CacheException {
		return addCacheKeyQuery(getCacheIndx(indxName).createGreatQuery(value, false), indxName, value);
	}

	public CacheQuery<E> ge(String indxName, Object value) throws CacheException {
		return addCacheKeyQuery(getCacheIndx(indxName).createGreatQuery(value, true), indxName, value);
	}

	public CacheQuery<E> lt(String indxName, Object value) throws CacheException {
		return addCacheKeyQuery(getCacheIndx(indxName).createLessQuery(value, false), indxName, value);
	}

	public CacheQuery<E> le(String indxName, Object value) throws CacheException {
		return addCacheKeyQuery(getCacheIndx(indxName).createLessQuery(value, true), indxName, value);
	}

	public CacheQuery<E> like(String indxName, Object value) throws CacheException {
		return addCacheKeyQuery(new LikeQuery(getCacheIndx(indxName).getIndxMap(), value), null, value);
	}

	public CacheQuery<E> ne(String indxName, Object value) throws CacheException {
		List<ICacheKeyQuery> keyQuerys = new ArrayList<ICacheKeyQuery>();
		keyQuerys.add(getCacheIndx(indxName).createLessQuery(value, false));
		keyQuerys.add(getCacheIndx(indxName).createGreatQuery(value, false));
		return or(keyQuerys, value);
	}

	public CacheQuery<E> in(String indxName, Object[] values) throws CacheException {
		List<ICacheKeyQuery> keyQuerys = new ArrayList<ICacheKeyQuery>();
		for (Object value : values) {
			keyQuerys.add(getCacheIndx(indxName).createEqQuery(value));
		}
		return or(keyQuerys, values);
	}

	public CacheQuery<E> between(String indxName, Object lowValue, Object hignValue) throws CacheException {
		return ge(indxName, lowValue).le(indxName, hignValue);
	}

	public CacheQuery<E> isNull(String indxName) throws CacheException {
		return eq(indxName, null);
	}

	public CacheQuery<E> notNull(String indxName) throws CacheException {
		return ne(indxName, null);
	}

	public CacheQuery<E> join(List<?> joinValues, String idProperty) throws Exception {
		return join(joinValues, idProperty, false);
	}

	public CacheQuery<E> join(List<?> joinValues, String idProperty, boolean sort) throws Exception {
		IdInQuery idIn = new IdInQuery(joinValues, idProperty);
		if (sort) {
			ICacheSort cacheSort = new JoinCacheSort(idIn.execute());
			cacheSorts.put(cacheSort, Boolean.valueOf(false));
		}
		
		return addCacheKeyQuery(idIn, null, joinValues.toArray());
	}

	public CacheQuery<E> asc(String indxName) throws CacheException {
		return order(indxName, true);
	}

	public CacheQuery<E> desc(String indxName) throws CacheException {
		return order(indxName, false);
	}

	public CacheQuery<E> order(String indxName, boolean asc) throws CacheException {
		ICacheIndx cacheIndx = getCacheIndx(indxName);
		cacheSorts.put(cacheIndx, Boolean.valueOf(asc));
		return this;
	}

	public CacheQuery<E> topKeys(Set<Object> topKeys) {
		this.topKeys.addAll(topKeys);
		return this;
	}

	private ICacheIndx getCacheIndx(String indxName) throws CacheException {
		ICacheIndx cacheIndx = (ICacheIndx) cacheIndxMap.get(indxName);
		CacheException.asserts(cacheIndx != null, "无entity=" + entityClass.getSimpleName() + ",indxName=" + indxName + "的索引");
		return cacheIndx;
	}

	private boolean queryFromCache(String operate) {
		Map<String, Set<Object>> cacheMap = (Map<String, Set<Object>>) cacheQueryMap.get(entityClass.getSimpleName());
		if (cacheMap != null) {
			String key = cacheKey + "|" + operate;
			entityKeySet = ((Set<Object>) cacheMap.get(key));
			if (entityKeySet != null) {
				logger.info("缓存查询" + entityClass.getSimpleName() + ",key=" + key);
				return true;
			}
		}
		
		return false;
	}

	private void setQueryCache(String operate) {
		Map<String, Set<Object>> cacheMap = (Map<String, Set<Object>>) cacheQueryMap.get(entityClass.getSimpleName());
		if (cacheMap != null) {
			String key = cacheKey + "|" + operate;
			logger.info("更新缓存" + entityClass.getSimpleName() + ",key=" + key);
			synchronized (cacheMap) {
				cacheMap.put(key, entityKeySet);
			}
		}
	}

	public static void clearQueryCache(Class<?> entityClass) {
		Map<String, Set<Object>> cacheMap = (Map<String, Set<Object>>) cacheQueryMap.get(entityClass.getSimpleName());
		if (cacheMap != null){
			synchronized (cacheMap) {
				cacheMap.clear();
			}
		}
	}

	public static void addCache(Class<?> entityClass, int size) {
		cacheQueryMap.put(entityClass.getSimpleName(), new LinkedHashMap<String, Set<Object>>() {
			private static final long serialVersionUID = 1L;
			protected boolean removeEldestEntry(Map.Entry<String, Set<Object>> eldest) {
				CacheQuery.logger.debug("缓存大小=" + size() + "/" + values().size());
				return size() > values().size();
			}
		});
	}

	private static class JoinCacheSort implements ICacheSort {
		private Set<Object> indxKeys;

		public JoinCacheSort(Set<Object> indxKeys) {
			this.indxKeys = indxKeys;
		}

		public Set<Object> sort(Set<Object> entityKeySet, boolean asc) throws CacheException {
			Set<Object> removeKeys = new HashSet<Object>();
			removeKeys.addAll(indxKeys);
			removeKeys.removeAll(entityKeySet);
			indxKeys.removeAll(removeKeys);
			return indxKeys;
		}
	}
}