package com.jecp.capital.seller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SellerDubboTest {

	public static void main(String[] args) throws FileNotFoundException {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/spring-context.xml");
		context.start();
		synchronized (SellerDubboTest.class) {
            while (true) {
                try {
                	SellerDubboTest.class.wait();
                } catch (Throwable e) {
                	
                }
            }
		}

	}

}
