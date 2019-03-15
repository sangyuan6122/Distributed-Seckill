package com.jecp.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigParser {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	private FileInputStream is =null;
	private Properties p=null;
	public ConfigParser(String file){
		try {
			String url=this.getClass().getResource("").getPath();
			url=url.substring(0, url.indexOf("WEB-INF"))+"WEB-INF/config/ogg.properties";			
			is=new FileInputStream(url);
			
			if(is!=null){
				p=new Properties();		
				p.load(is);
			}
		} catch (IOException e) {
			log.error("",e);			
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {					
					log.error("",e);
				}
			}
		}
	}
	public String getProperties(String name) {
		if(p!=null){
			return p.getProperty(name);
		}
		return "";
	}
	
}
