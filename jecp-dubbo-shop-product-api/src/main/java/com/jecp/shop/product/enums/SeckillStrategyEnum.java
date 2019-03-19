package com.jecp.shop.product.enums;

/**
 * @Title 秒杀代码策略 
 * @author WWT
 * @date 2018年6月7日
 */
public enum SeckillStrategyEnum {
	//Redis乐观锁
	REDIS_OPTIMISTIC_LOCK("com.jecp.shop.product.strategy.RedisOptimisticLockStrategy", "Redis乐观锁"),
	//Redis分布式锁
	REDIS_DISTRIBUTED_LOCK("com.jecp.shop.product.strategy.RedisDistributedLockStrategy", "Redis悲观锁"),
	//Redis队列
	REDIS_QUEUE("com.jecp.shop.product.strategy.RedisQueueStrategy", "Redis队列");
	
	private String clazzName;
	private String desc;

	private SeckillStrategyEnum(String clazzName, String desc) {
		this.clazzName = clazzName;
		this.desc = desc;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
