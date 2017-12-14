package com.cmp.util;

import java.lang.reflect.Field;
import java.util.Map;

public class PageDataUtil {
	
	//Pd转化为对象
	public static  Object mapToObject(Map<String, Object> pd, Class<?> clazz) throws Exception{
		Object obj = clazz.newInstance();
		Field []declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
        	if (pd.get(field.getName()) == null) {
        		continue;
        	}
        	String setMethodName = "set"
                    + field.getName().substring(0, 1).toUpperCase()
                    + field.getName().substring(1);
        	 try{
                
        		 Class<?> fieldTypeClass = field.getType();
                 //根据字段类型进行值的转换
                 Object value = convertValType(pd.get(field.getName()), fieldTypeClass);
                 //调用对象对应的set方法
                 clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);
             }catch(NoSuchMethodException e){
                 e.printStackTrace();
             }
        }
        return obj;
	}
	
	
	/**
     * 将Object类型的值，转换成bean对象属性里对应的类型值
     *
     * @param value Object对象值
     * @param fieldTypeClass 属性的类型
     * @return 转换后的值
     */
    private static Object convertValType(Object value, Class<?> fieldTypeClass) {
        Object retVal = null;
        if(Long.class.getName().equals(fieldTypeClass.getName())
                || long.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Long.parseLong(value.toString());
        } else if(Integer.class.getName().equals(fieldTypeClass.getName())
                || int.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Integer.parseInt(value.toString());
        } else if(Float.class.getName().equals(fieldTypeClass.getName())
                || float.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Float.parseFloat(value.toString());
        } else if(Double.class.getName().equals(fieldTypeClass.getName())
                || double.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Double.parseDouble(value.toString());
        } else {
            retVal = value;
        }
        return retVal;
    }
	
//	public static void main(String[] args) {
//		Map a = new HashMap();
//		a.put("id", "78940a1319bb41c08777a47667b11cdc");
//		a.put("name", "终端管理项目");
//		
//		try {
//			Project p = (Project) PageDataUtil.mapToObject(a, Project.class);
//			System.out.println(p.getName());
//			System.out.println(p.getId());
//			System.out.println(p.getDisk_quota());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
