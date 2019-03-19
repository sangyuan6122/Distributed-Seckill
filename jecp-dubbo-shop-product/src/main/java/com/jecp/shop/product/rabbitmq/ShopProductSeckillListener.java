package com.jecp.shop.product.rabbitmq;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mengyun.tcctransaction.ConfirmingException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.jecp.shop.product.dto.SeckillMqDTO;
import com.jecp.shop.product.enums.SeckillQueue;
import com.jecp.shop.product.service.impl.ShopProductSeckillServiceImpl;
import com.rabbitmq.client.Channel;

@Component
@Lazy(false)
public class ShopProductSeckillListener {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Value("${rabbitmq.maxConcurrentConsumers}")
	private int maxConcurrentConsumers;
	@Value("${rabbitmq.concurrentConsumers}")
	private int concurrentConsumers;
	@Value("${rabbitmq.prefetchCount}")
	private int prefetchCount;
	@Autowired
	private ShopProductSeckillServiceImpl shopProductSeckillService;

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	@Qualifier("seckillQueue")
	private Queue seckillQueue;

	@Autowired
	@Qualifier("recycleStockQueue")
	private Queue recycleStockQueue;

	public void handleSeckillMessage(Message message, Channel channel) {
		SeckillMqDTO seckillMqDTO = null;
		boolean result = true;
		try {
			seckillMqDTO = JSON.parseObject(new String(message.getBody()), new TypeReference<SeckillMqDTO>() {});
			shopProductSeckillService.placeOrder(seckillMqDTO.getUserId(), seckillMqDTO.getSeckillId());
		} catch (JSONException jSONException) {
			/* 发生JSON转换异常，消息无需返回队列 */
			log.error("收到消息转换失败:{}", message.getBody());
		} catch(DataIntegrityViolationException e) {
			log.error("数据库完整性校验异常:{}", e);
		} catch (ConfirmingException confirmingException) {
			log.error("", confirmingException);
		} catch (Throwable e) {
			result = false;
			log.error("", e);
		}
		if (result) {
			try {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			} catch (IOException e) {
				log.error("", e);
			}
		} else {
			exceptionDeal(message, channel);
		}

	}

	@Bean
	public SimpleMessageListenerContainer messageContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(seckillQueue, recycleStockQueue);
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(maxConcurrentConsumers);
		container.setConcurrentConsumers(concurrentConsumers);	
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//		container.setTaskExecutor(Executors.newCachedThreadPool());
		container.setPrefetchCount(prefetchCount);		
		container.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				String consumerQueue = message.getMessageProperties().getConsumerQueue();
				if (SeckillQueue.SECKILL_QUEUE.equals(consumerQueue)) {
					handleSeckillMessage(message, channel);
				} else if (SeckillQueue.RECYCLE_STOCK_QUEUE.equals(consumerQueue)) {
					handleRecycleStockMessage(message, channel);
				}
			}
		});
		return container;
	}
	/**
	 * 秒杀库存回收
	 * @param message
	 * @param channel
	 */
	public void handleRecycleStockMessage(Message message, Channel channel) {
		try {
			shopProductSeckillService.recyleStock(new String(message.getBody()));
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}catch(Exception e) {
			log.error("秒杀库存回收失败",e);
			exceptionDeal(message, channel);
		}
	}

	public void exceptionDeal(Message message, Channel channel) {
		if (message.getMessageProperties().getRedelivered()) {
			/** 放弃消息 */
			try {
				channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
			} catch (IOException e) {
				log.error("", e);
			}
			log.info("已处理失败，不在处理!消息内容:{}",new String(message.getBody()));
		} else {			
			try {
				channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
			} catch (IOException e) {
				log.error("", e);
			}
		}
	}

}
