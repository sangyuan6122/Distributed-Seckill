package com.jecp.message.test;

import java.io.FileNotFoundException;
import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.message.api.ReliableMessageApi;
import com.jecp.message.dto.ReliableMessageInputDTO;

public class ReliableMessageApiImplTest {

	public static void main(String[] args) throws FileNotFoundException {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/spring-context.xml");
		context.start();

		ReliableMessageApi reliableMessageApi=(ReliableMessageApi)context.getBean("reliableMessageApiImpl");
		/*ReliableMessageInputDTO reliableMessageInputDTO=new ReliableMessageInputDTO();
		reliableMessageInputDTO.setMessageKey("111");
		reliableMessageInputDTO.setConsumerQueue("consumer");
		reliableMessageInputDTO.setMessageBody("ttttt");
		reliableMessageApi.prestore(reliableMessageInputDTO);
		reliableMessageApi.messageConfirm("111");
		
		PageQuery pageQuery=new PageQuery(3,30);
		PageBean pageBean=reliableMessageApi.getConfirmTimeOut(new Date(), pageQuery);
		System.out.println(pageBean.getPageCount());
		System.out.println(pageBean.toDataGridJSON());*/
		synchronized (ReliableMessageApiImplTest.class) {
            while (true) {
                try {
                	ReliableMessageApiImplTest.class.wait();
                } catch (Throwable e) {
                	
                }
            }
		}
	
	}

}
