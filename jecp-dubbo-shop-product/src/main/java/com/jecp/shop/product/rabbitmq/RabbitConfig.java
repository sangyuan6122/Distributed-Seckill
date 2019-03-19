package com.jecp.shop.product.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.jecp.shop.product.enums.SeckillQueue;

@Configuration
public class RabbitConfig {
	@Value("${rabbitmq.host}")
	private String host;

	@Value("${rabbitmq.port}")
	private int port;

	@Value("${rabbitmq.username}")
	private String username;

	@Value("${rabbitmq.password}")
	private String password;

	@Value("${rabbitmq.publisherConfirms}")
	private Boolean publisherConfirms;
	
	@Value("${rabbitmq.publisherReturns}")
	private Boolean publisherReturns;
	
	@Value("${rabbitmq.virtualHost}")
	private String virtualHost;

	@Value("${rabbitmq.cacheMode}")
	private String cacheMode;

	@Value("${rabbitmq.channelCacheSize}")
	private int channelCacheSize;

	@Value("${rabbitmq.channelCheckoutTimeout}")
	private int channelCheckoutTimeout;

	@Bean
	public CachingConnectionFactory connectionFactory() {
		/** 如果非默认端口需在构造时定义 */
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
		connectionFactory.setUsername(this.username);
		connectionFactory.setPassword(this.password);
		connectionFactory.setVirtualHost(this.virtualHost);
		connectionFactory.setPublisherConfirms(this.publisherConfirms);
		connectionFactory.setPublisherReturns(this.publisherReturns);
		connectionFactory.setCacheMode(CacheMode.valueOf(this.cacheMode));
		connectionFactory.setChannelCacheSize(this.channelCacheSize);
		connectionFactory.setChannelCheckoutTimeout(this.channelCheckoutTimeout);
		return connectionFactory;
	}
	@Bean
	public CachingConnectionFactory connectionFactory2() {
		/** 如果非默认端口需在构造时定义 */
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
		connectionFactory.setUsername(this.username);
		connectionFactory.setPassword(this.password);
		connectionFactory.setVirtualHost(this.virtualHost);		
		connectionFactory.setCacheMode(CacheMode.valueOf(this.cacheMode));
		connectionFactory.setChannelCacheSize(this.channelCacheSize);
		connectionFactory.setChannelCheckoutTimeout(this.channelCheckoutTimeout);
		return connectionFactory;
	}
	/**
	 * rabbitmq的模板配置,建议是prototype类型
	 * channel为事务方式
	 * @return
	 */
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplateTransact() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory2());
		rabbitTemplate.setChannelTransacted(true);
		rabbitTemplate.setMessageConverter(simpleMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate() {		
		RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory());
		rabbitTemplate.setMessageConverter(simpleMessageConverter());
		rabbitTemplate.setChannelTransacted(false);
		// template.setConfirmCallback(); 设置消息确认
		// template.setReturnCallback();
		return rabbitTemplate;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(this.connectionFactory());
	}

	@Bean
	public MessageConverter simpleMessageConverter() {
		return new SimpleMessageConverter();
	}
	/**
	 * 死信交换机
	 * @return
	 */
	@Bean
	public DirectExchange deadExchange() {
		DirectExchange deadExchange = new DirectExchange(SeckillQueue.SECKILL_DEAD_EXCHANGE);
		amqpAdmin().declareExchange(deadExchange);
		return deadExchange;
	}
	/**
	 * 库存延时队列
	 * @return
	 */
	@Bean
	public Queue stockDelayQueue() {
		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("x-dead-letter-exchange", SeckillQueue.SECKILL_DEAD_EXCHANGE);
		arguments.put("x-dead-letter-routing-key", SeckillQueue.RECYCLE_STOCK_QUEUE);
		Queue stockDelayQueue = new Queue(SeckillQueue.RECYCLE_STOCK_DELAY_QUEUE, true, false, false, arguments);
		amqpAdmin().declareQueue(stockDelayQueue);
		return stockDelayQueue;
	}
	/**
	 * 秒杀结束库存回收队列
	 * @return
	 */
	@Bean
	public Queue recycleStockQueue() {
		Queue recycleStockQueue = new Queue(SeckillQueue.RECYCLE_STOCK_QUEUE, true);
		amqpAdmin().declareQueue(recycleStockQueue);
		return recycleStockQueue;
	}
	/**
	 * 秒杀队列
	 * @return
	 */
	@Bean
	public Queue seckillQueue() {
		Queue seckillQueue=new Queue(SeckillQueue.SECKILL_QUEUE);
		amqpAdmin().declareQueue(seckillQueue);
		return seckillQueue;
	}
	/**
	 * 队列交换绑定
	 * @return
	 */
	@Bean
	public Binding bindRecyleQueue() {
		Binding binding = BindingBuilder.bind(recycleStockQueue()).to(deadExchange())
				.with(SeckillQueue.RECYCLE_STOCK_QUEUE);
		amqpAdmin().declareBinding(binding);
		return binding;
	}

}
