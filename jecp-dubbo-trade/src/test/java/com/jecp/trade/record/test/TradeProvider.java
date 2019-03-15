package com.jecp.trade.record.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TradeProvider {
private static final Log log = LogFactory.getLog(TradeProvider.class);
	
    public static void main(String[] args) {    	
        try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
		} catch (Exception e) {
			log.error("DubboProvider context start error:",e);
		}        
        synchronized (TradeProvider.class) {
            while (true) {
                try {
                    TradeProvider.class.wait();
                } catch (InterruptedException e) {
                	log.error("== synchronized error:",e);
                }
            }
        }
    }
}
