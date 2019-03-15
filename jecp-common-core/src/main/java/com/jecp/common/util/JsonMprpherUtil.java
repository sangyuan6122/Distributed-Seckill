package com.jecp.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.object.AbstractObjectMorpher;

public class JsonMprpherUtil extends AbstractObjectMorpher{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	private String[] formats;
	
	public JsonMprpherUtil(String[] formats) {
		this.formats = formats;
	}

	@Override
	public Object morph(Object value) {
		if(value==null){
			return null;
		}
		if(Timestamp.class.isAssignableFrom(value.getClass())){
			return (Timestamp)value;
		}
		if(!supports(value.getClass())){
			throw new MorphException(value.getClass()+" 是不支持的类型");
		}
		String strValue=(String)value;
		if("".equals(strValue)){
			return null;
		}
		SimpleDateFormat sdf = null;
		Date date=null;
		for(int i=0;i<formats.length;i++){
			if(null==sdf){
				sdf=new SimpleDateFormat(formats[i]);
			}else{				
				sdf.applyPattern(formats[i]);
				try {
					date=sdf.parse(strValue.toLowerCase());
					break;
				} catch (ParseException e) {										
				}
			}
		}
		if(date==null){
			System.out.println("日期解析异常:"+strValue);
			log.error("日期解析异常:"+strValue);
		}
		Timestamp t=new Timestamp(date.getTime());
		return t;		
	}

	@Override
	public Class morphsTo() {
		
		return Timestamp.class;
	}
	public boolean supports(Class clazz){
		return String.class.isAssignableFrom(clazz);
	}

}
