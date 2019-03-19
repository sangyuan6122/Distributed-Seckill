package com.jecp.shop.product.enums;

/**
 * @Title 秒杀RabbitMq定义 
 * @author WWT
 * @date 2018年6月7日
 */
public class SeckillQueue {
	public static final String SECKILL_QUEUE="com.jecp.shop.product.seckill.queue";
	public static final String RECYCLE_STOCK_DELAY_QUEUE="com.jecp.shop.product.recycle.stock.delay.queue";
	public static final String RECYCLE_STOCK_QUEUE="com.jecp.shop.product.recycle.stock.queue";
	public static final String SECKILL_DEAD_EXCHANGE="seckillDeadExchange";
}
