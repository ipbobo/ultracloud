package com.cmp.ehcache.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.cmp.ehcache.annotation.CacheKey;
import com.cmp.ehcache.cache.CacheException;
import com.cmp.ehcache.cache.NullObject;

public class StringUtil {
	private static Logger logger = Logger.getLogger(StringUtil.class);
	
	public static Object getEntityValue(Object entity, String indxName) throws CacheException {
		try {
			return NullObject.getValue(PropertyUtils.getProperty(entity, indxName));
		} catch (Exception e) {
			throw new CacheException("访问" + entity.getClass().getName() + "." + indxName + "出错", e);
		}
	}
	
	public static Object getKeyValue(Object entity) throws Exception {
		if (entity == null) {
			return null;
		}
		
		String keyName = getKeyName(entity.getClass());
		return PropertyUtils.getProperty(entity, keyName!=null?keyName:"id");
	}

	public static String getKeyName(Class<?> entityClass) throws Exception {
		for (Field field : entityClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(CacheKey.class)) {//CacheKey标注id
				return field.getName();
			}
		}
		
		return null;
	}

	public static Set<Object> getPropertyValueList(String property, List<?> entityList) throws Exception {
		Set<Object> set = new LinkedHashSet<Object>();
		for (Iterator<?> it = entityList.iterator(); it.hasNext();) {
			try {
				set.add(PropertyUtils.getProperty(it.next(), property));
			} catch (Exception e) {
				throw new Exception("取得实体属性值错误", e);
			}
		}
		
		return set;
	}
	
	public static Class<?> getEntityClass(Class<?> clzz) {
		Type genType = clzz.getGenericSuperclass();
		if(genType instanceof ParameterizedType) {
			return (Class<?>)((ParameterizedType)genType).getActualTypeArguments()[0];
		}
		
		return null;
	}
	
	//获取本地ip
	public static String getLocalIp(){
		String defaultIp="127.0.0.1";
		try {
			Enumeration<NetworkInterface> eis = NetworkInterface.getNetworkInterfaces();//定义网络接口枚举类，获得网络接口
			while (eis.hasMoreElements()){//遍历所有的网络接口
				NetworkInterface ei = eis.nextElement();
				Enumeration<InetAddress> ias = ei.getInetAddresses();//同样再定义网络地址枚举类
				while (ias.hasMoreElements()) {
					InetAddress ia = ias.nextElement();//声明一个InetAddress类型ip地址
					if (ia != null && ia instanceof Inet4Address){//InetAddress类包括Inet4Address和Inet6Address
						if(!defaultIp.equals(ia.getHostAddress())){
							return ia.getHostAddress();
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取本地ip错误： " + e);
		}
		
		return defaultIp;
	}
}