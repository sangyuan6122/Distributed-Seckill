package com.jecp.capital.point.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PointDubboTest {

	public static void main(String[] args) throws FileNotFoundException {
	
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/spring-context.xml");
		context.start();
		synchronized (PointDubboTest.class) {
            while (true) {
                try {
                	PointDubboTest.class.wait();
                } catch (Throwable e) {
                	
                }
            }
		}

	}

}
