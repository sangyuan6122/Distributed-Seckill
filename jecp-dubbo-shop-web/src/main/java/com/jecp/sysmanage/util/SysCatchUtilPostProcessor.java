package com.jecp.sysmanage.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class SysCatchUtilPostProcessor implements BeanPostProcessor{

	@Override
	public Object postProcessAfterInitialization(Object obj, String arg1)
			throws BeansException {
		if(obj instanceof SysCatchUtil){
			((SysCatchUtil) obj).loadCache();
		}
		return obj;
	}

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		
		return arg0;
	}

}
