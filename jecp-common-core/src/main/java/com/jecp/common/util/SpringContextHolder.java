package com.jecp.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Lazy(false) 
public class SpringContextHolder implements ApplicationContextAware{
	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextHolder.applicationContext=applicationContext;		
	}
	
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}
	public static <T>T getBean(String beanName){
		checkApplicationContext();
		return (T) applicationContext.getBean(beanName);
	}
	public static void checkApplicationContext(){
		if(applicationContext==null){
			throw new IllegalStateException("SpringContextHolder未注入,请检查applicationContext.xml文件。");
		}	
	}
}
