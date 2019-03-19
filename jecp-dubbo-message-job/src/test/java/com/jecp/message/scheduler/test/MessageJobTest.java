package com.jecp.message.scheduler.test;

import java.io.FileNotFoundException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.message.api.ReliableMessageApi;
import com.jecp.message.model.ReliableMessage;

public class MessageJobTest {
	public static void main(String[] args) throws FileNotFoundException {
//		PrintStream printStream=new PrintStream(new FileOutputStream("output.txt"));
//		System.setOut(printStream);
//		System.setErr(printStream);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/spring-context.xml");
		context.start();
		synchronized (MessageJobTest.class) {
            while (true) {
                try {
                	MessageJobTest.class.wait();
                } catch (Throwable e) {
                	
                }
            }
		}

	}
}
