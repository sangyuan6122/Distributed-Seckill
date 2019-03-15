package com.jecp.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class ClassUtil {
	
	public static  Map<String, Map<String, String>> compile(Object db,
			Object new_) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();//存放修改前与修改后的属性值
		Class<Object> cDb = (Class<Object>) db.getClass();
		Field[] filesDb = cDb.getDeclaredFields();
		Class<Object> cNew_ = (Class<Object>) new_.getClass();
		Map<String, String> valDbMap = new HashMap<String, String>();//存放修改前的已修改的值
		Map<String, String> valNewMap = new HashMap<String, String>();//存放修改后的值
		for (Field field : filesDb) {
			String getMethodName = "get"
					+ field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);		
			if(getMethodName.equals("getRemark")){
				System.out.println();
			}
			try {
				Method mdb = (Method) cDb.getMethod(getMethodName,null);
				Method mNew_ = (Method) cNew_.getMethod(getMethodName,null);
				//自定义实现的注解类
				MyAnnotation meta = mdb.getAnnotation(MyAnnotation.class);  
				try {
					if(meta!= null){
						Object valDb = mdb.invoke(db);
						Object valNew = mNew_.invoke(new_);
						if(valDb == null&&"".equals(valNew)||
								valNew == null&&"".equals(valDb)){
							
						}else{
							if(valDb!=null&&!valDb.equals(valNew)||
								valNew != null&&!valNew.equals(valDb)){
								valDbMap.put(meta.fileCName(), valDb==null?null:String.valueOf(valDb));
								valNewMap.put(meta.fileCName(), String.valueOf(valNew));
							}
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} 
			} catch (NoSuchMethodException e) {
				//log.trace("没有这个方法可显示调用");
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		if(valDbMap.size()>0&&valNewMap.size()>0){
			map.put("更改前", valDbMap);
			map.put("更改后", valNewMap);
		}		
		return map;
	}
	/**
	 * Object转Map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> objectToMap(Object obj) throws Exception{
		if(obj==null){
			return null;
		}
		Map<String,String> map=new HashMap<String,String>();
		Field[] fields=obj.getClass().getDeclaredFields();
		for(Field field:fields){
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj).toString());
		}
		return map;
	}
	public static void main(String[] args)  {
//		Map<String, Map<String, String>> map=new HashMap<String, Map<String, String>>();
//		CrvEquipmentAccount c1=new CrvEquipmentAccount();
//		c1.setId("12");
//		c1.setSlotno("222");
//		c1.setB_height("aaa");
//		CrvEquipmentAccount c2=new CrvEquipmentAccount();
//		map=ClassUtil.compile(c1, c2);
//		System.out.println(map);
//		CrvEquipmentAccount c1=new CrvEquipmentAccount();
//		c1.setB_height("aaa");
//		Method m=c1.getClass().getMethod("getB_height", null);
//		System.out.println(m.invoke(c1));
	}
}
