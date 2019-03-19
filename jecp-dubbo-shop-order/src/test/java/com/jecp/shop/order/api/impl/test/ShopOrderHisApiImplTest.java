package com.jecp.shop.order.api.impl.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jecp.base.page.PageQuery;
import com.jecp.shop.order.model.ShopOrder;
import com.jecp.shop.order.service.impl.ShopOrderServiceImpl;

public class ShopOrderHisApiImplTest {
public static void main(String[] args) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/spring-context.xml");
		context.start();
		ShopOrderServiceImpl shopOrderService=(ShopOrderServiceImpl) context.getBean("shopOrderServiceImpl");
		String[] sort={"gtid"};
		String[] order={"desc"};
		PageQuery p=new PageQuery(2,3,sort ,order);
//		List<ShopOrder> list=shopOrderService.list(p);
//		System.out.println(list.size());
		synchronized (ShopOrderHisApiImplTest.class) {
            while (true) {
                try {
                	ShopOrderHisApiImplTest.class.wait();
                } catch (Throwable e) {
                	
                }
            }
		}
	}
}
