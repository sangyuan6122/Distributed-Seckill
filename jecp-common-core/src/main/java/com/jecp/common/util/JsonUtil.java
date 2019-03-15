package com.jecp.common.util;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;
/**
 * JSON转换配置文件
 * @author WWT
 *
 */
public class JsonUtil {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	private String[] filterFiled;//需要过滤的字段
	private int type=-1;//0:不转换部分字段,1:只转换部分字段,-1:默认不处理
	static{
		JSONUtils.getMorpherRegistry().registerMorpher(new JsonMprpherUtil(new String[]{"yyyy-MM-dd HH:mm:ss",
				"yyyy/MM/dd HH:mm:ss","yyyy-MM-dd","yyyy/MM/dd"}));
	}
	public JsonConfig getJsonConfig(){
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){//如过滤Java对象可换成setJavaPropertyFilter

			@Override
			public boolean apply(Object arg0, String filed, Object arg2) {		
				if(type==0){
					if(filterFiled!=null&&find(filed)){
						return true;
					}else{
						return false;
					}					
				}else if(type==1){
					if(filterFiled!=null&&find(filed)){
						return false;
					}else{
						return true;
					}	
				}else{
					return false;
				}

			}			
		});
		jsonConfig.registerJsonValueProcessor(Object.class, new JsonValueProcessor(){
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {				
				return null;
			}

			@Override
			public Object processObjectValue(String key, Object value,
					JsonConfig jsonConfig) {				
				if(null==value)
					return "";
				return value;
			}			
		});
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessor(){
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {				
				return null;
			}

			@Override
			public Object processObjectValue(String key, Object value,
					JsonConfig jsonConfig) {				
				if(null==value)
					return "";
				if(value instanceof java.sql.Date){
					return TimeUtil.timeToStr(value);
				}
				return value;
			}			
		});
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessor(){
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {				
				return null;
			}

			@Override
			public Object processObjectValue(String key, Object value,
					JsonConfig jsonConfig) {				
				if(null==value)
					return "";
				if(value instanceof java.sql.Timestamp){
					return TimeUtil.timeToStr(value);
				}
				return value;
			}			
		});
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor(){
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {				
				return null;
			}

			@Override
			public Object processObjectValue(String key, Object value,
					JsonConfig jsonConfig) {				
				if(null==value)
					return "";
				if(value instanceof java.util.Date){
					return TimeUtil.timeToStr(value);
				}
				return value;
			}			
		});
		jsonConfig.registerJsonValueProcessor(java.sql.Clob.class, new JsonValueProcessor(){
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {				
				return null;
			}

			@Override
			public Object processObjectValue(String key, Object value,
					JsonConfig jsonConfig) {				
				if(null==value)
					return "";
				if(value instanceof java.sql.Clob){
					try {
						return ((java.sql.Clob) value).getSubString((long)1, (int) ((java.sql.Clob) value).length());
					} catch (SQLException e) {
						log.error("", e);
					}
				}
				return value;
			}			
		});
		return jsonConfig;
	}
	private Boolean find(String str){
		for(int i=0;filterFiled!=null&&i<filterFiled.length;i++){
			if(filterFiled[i].equals(str)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 需要过滤的字段
	 * @param filterFiled
	 */
	public void setFilterFiled(String[] filterFiled) {
		this.filterFiled = filterFiled;
	}
	/**
	 * 0:不转换部分字段,1:只转换部分字段,-1:默认不处理
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}
	

	
}
