package com.cmp.ehcache.cache.oper.impl;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.cmp.ehcache.cache.CacheException;
import com.cmp.ehcache.cache.CacheConfig;
import com.cmp.ehcache.cache.oper.CacheOper;

public class CacheOperEh extends CacheOper {
	private CacheManager cacheMgr;

	public void init() throws CacheException {
		cacheMgr = CacheManager.create(CacheOperEh.class.getResource("/").getPath() + "conf/ehcache-cache.xml");
	}

	private Cache getCache(Class<?> clazz) throws CacheException {
		Cache cache = cacheMgr.getCache(clazz.getName());
		CacheException.asserts(cache != null, "没有在eh中找到名为" + clazz.getName() + "的缓存：请在配置文件ehcache-cache.xml中配置");
		return cache;
	}

	public Object getEntity(Class<?> clazz, Object key) throws CacheException {
		Element elt = getCache(clazz).get(key);
		if (elt != null) {
			return elt.getObjectValue();
		}

		return null;
	}

	public void putEntity(Object key, Object entity) throws CacheException {
		getCache(entity.getClass()).put(new Element(key, CacheConfig.isZip(entity.getClass())?zip(entity):entity));
	}

	public boolean removeEntity(Class<?> clazz, Object key) throws CacheException {
		return getCache(clazz).remove(key);
	}

	public long size(Class<?> clazz) throws CacheException {
		return getCache(clazz).calculateInMemorySize();
	}

	public long count(Class<?> clazz) throws CacheException {
		return getCache(clazz).getSize();
	}

	public long hits(Class<?> clazz) throws CacheException {
		return getCache(clazz).getStatistics().getCacheHits();
	}

	@SuppressWarnings("rawtypes")
	public long clear(Class<?> clazz) throws CacheException {
		long clearCount = 0L;
		Cache cache = getCache(clazz);
		for (Iterator it = cache.getKeys().iterator(); it.hasNext();) {
			clearCount = cache.remove(it.next()) ? clearCount + 1L : clearCount;
		}

		return clearCount;
	}

	public List<?> getList(Class<?> clazz) throws CacheException {
		Cache cache = getCache(clazz);
		List<Object> list = new ArrayList<Object>();
		for (Iterator<?> it = getCache(clazz).getKeys().iterator(); it.hasNext();) {
			list.add(cache.get(it.next()).getObjectValue());
		}

		return list;
	}
	
	public Object zip(Object entity) throws CacheException {
		if (entity == null){
			return null;
		}
		
		try {
			ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(byteArrayOutput);
			ObjectOutputStream output = new ObjectOutputStream(gzip);
			output.writeObject(entity);
			output.flush();
			output.close();
			gzip.close();
			return byteArrayOutput.toByteArray();
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}
}
