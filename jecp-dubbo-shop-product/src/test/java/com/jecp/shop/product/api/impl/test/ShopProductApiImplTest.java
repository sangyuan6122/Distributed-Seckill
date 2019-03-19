package com.jecp.shop.product.api.impl.test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jecp.common.cache.localcache.LocalCache;
import com.jecp.common.cache.rediscache.util.JedisUtil;
import com.jecp.shop.product.enums.SeckillKeyEnum;
import com.jecp.shop.product.enums.SeckillStrategyEnum;
import com.jecp.shop.product.service.impl.ShopProductSeckillServiceImpl;
import com.jecp.shop.product.strategy.SeckillFactory;
import com.jecp.shop.product.strategy.SeckillStrategy;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@EnableRabbit
public class ShopProductApiImplTest {
	final static String USER_ID="zhangsan";
	final static String SECKILL_ID="ff808081690a5a6f01690a5b4d7a0000";
	public static void main(String[] args) throws Exception {				
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/spring-context.xml");
		context.start();
		RabbitTemplate rabbitTemplate= (RabbitTemplate) context.getBean("rabbitTemplate");
		ShopProductSeckillServiceImpl shopProductSeckillService = (ShopProductSeckillServiceImpl) context.getBean("shopProductSeckillServiceImpl");

		SeckillStrategy seckillStrategy=SeckillFactory.getStrategy(SeckillStrategyEnum.REDIS_QUEUE);
//		seckillStrategy.cacheStock(SECKILL_ID,10000,(int) TimeUnit.HOURS.toSeconds(1L));
//		
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
		long start=System.currentTimeMillis();
	
		
		 for(int i=0;i<50000;i++) {
	        	fixedThreadPool.execute(new Runnable() {
					@Override
					public void run() {						
//						shopProductSeckillService.placeOrder("zs","297e4314677d94b101677d9536b40000");
//						RedisUtil1.get("aaa");
//						rabbitTemplate.convertAndSend("com.jecp.shop.product.seckill.queue", "{\"seckillId\":\"297e4314677d94b101677d9536b40000\",\"userId\":\"zhangsan\"}");
//						String s=JedisUtil.get("aaa");
//						boolean result=shopProductSeckillService.preStockReduction(USER_ID, SECKILL_ID);
						
//						if(result) {
							shopProductSeckillService.placeOrder(USER_ID, SECKILL_ID);
//						}						
					}        		
	        	});	
	        }
        fixedThreadPool.shutdown();
        while(true) {
        	if(fixedThreadPool.isTerminated()) {
        		LocalCache.statistics();
        		long time=System.currentTimeMillis()-start;
        		System.out.println(time/1000+" 秒");        		
        		break;
        	}
        }
		synchronized (ShopProductApiImplTest.class) {
			while (true) {
				try {
					ShopProductApiImplTest.class.wait();
				} catch (Throwable e) {

				}
			}
		}

	}

}
