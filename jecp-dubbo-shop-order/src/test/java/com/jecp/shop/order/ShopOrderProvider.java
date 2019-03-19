package com.jecp.shop.order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ShopOrderProvider {
private static final Log log = LogFactory.getLog(ShopOrderProvider.class);
	
    public static void main(String[] args) {    	
        try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
		} catch (Exception e) {
			log.error("DubboProvider context start error:",e);
		}        
        synchronized (ShopOrderProvider.class) {
            while (true) {
                try {
                    ShopOrderProvider.class.wait();
                } catch (InterruptedException e) {
                	log.error("== synchronized error:",e);
                }
            }
        }
    }
}
