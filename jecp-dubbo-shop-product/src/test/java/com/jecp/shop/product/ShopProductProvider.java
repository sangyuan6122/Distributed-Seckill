package com.jecp.shop.product;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jecp.shop.product.model.ShopProductSeckill;
import com.jecp.shop.product.service.impl.ShopProductSeckillServiceImpl;


public class ShopProductProvider {
	private static final Log log = LogFactory.getLog(ShopProductProvider.class);
	
    public static void main(String[] args) {    	
        try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
			ShopProductSeckillServiceImpl shopProductSeckillService=(ShopProductSeckillServiceImpl) context.getBean("shopProductSeckillServiceImpl");
			
			
//			RabbitTemplate rabbitTemplate=(RabbitTemplate) context.getBean("rabbitTemplate");
//			ConnectionFactory connectionFactory=(ConnectionFactory) context.getBean("connectionFactory");
//			for(int i=0;i<1000;i++) {
//				rabbitTemplate.convertAndSend(SeckillQueue.RECYCLE_STOCK_QUEUE, "1A");
//			}
//	
//			AmqpAdmin amqpAdmin = SpringContextHolder.getBean("amqpAdmin");
//			rabbitTemplate.setChannelTransacted(true);
//			String incoming = (String) rabbitTemplate.receiveAndConvert("delay");
//			System.out.println("incoming:"+incoming);
//		    rabbitTemplate.convertAndSend("delay","888");
//			
//			Test test=(Test) context.getBean("test");
//			test.init();
//			test.C();
//			test.testSendAndReceiveInTransaction();
//			Map<String, Object> arguments = new HashMap<String, Object>();  
//	        arguments.put("x-dead-letter-exchange",SeckillQueue.SECKILL_DEAD_EXCHANGE);  
//	        arguments.put("x-dead-letter-routing-key",SeckillQueue.RECYCLE_STOCK_QUEUE);
//			Queue delayQueue=new Queue(SeckillQueue.RECYCLE_STOCK_DELAY_QUEUE,true,false,false,arguments);
//			Queue recyleQueue=new Queue(SeckillQueue.RECYCLE_STOCK_QUEUE,true);
//			amqpAdmin.declareQueue(delayQueue);
//			amqpAdmin.declareQueue(recyleQueue);
//			DirectExchange exchange=new DirectExchange(SeckillQueue.SECKILL_DEAD_EXCHANGE);
//			amqpAdmin.declareExchange(exchange);
//			Binding binding=BindingBuilder.bind(recyleQueue).to(exchange).with(SeckillQueue.RECYCLE_STOCK_QUEUE);
//			amqpAdmin.declareBinding(binding);
//			Object obj=rabbitTemplate.convertSendAndReceive(SeckillQueue.RECYCLE_STOCK_DELAY_QUEUE, (Object) "123", new MessagePostProcessor() {
//				@Override
//				public Message postProcessMessage(Message message) throws AmqpException {
//					message.getMessageProperties().setExpiration("10000");
//					return message;
//				}
//			});
			
		} catch (Exception e) {
			log.error("DubboProvider context start error:",e);
		}        
        synchronized (ShopProductProvider.class) {
            while (true) {
                try {
                    ShopProductProvider.class.wait();
                } catch (InterruptedException e) {
                	log.error("== synchronized error:",e);
                }
            }
        }
    }
}
