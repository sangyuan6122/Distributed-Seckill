package com.jecp.capital.buyer.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuyerDubboTest {

	public static void main(String[] args) throws FileNotFoundException {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/spring-context.xml");
		context.start();
		synchronized (BuyerDubboTest.class) {
            while (true) {
                try {
                	BuyerDubboTest.class.wait();
                } catch (Throwable e) {
                	
                }
            }
		}

	}

}
