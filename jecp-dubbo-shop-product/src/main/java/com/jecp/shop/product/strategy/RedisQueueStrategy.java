package com.jecp.shop.product.strategy;

import java.util.concurrent.TimeUnit;

import com.jecp.common.cache.localcache.LocalCache;
import com.jecp.common.cache.rediscache.util.JedisUtil;
import com.jecp.shop.product.enums.SeckillKeyEnum;

/**
 * @Title Redis队列策略
 * @author WWT
 * @date 2018年7月18日
 */
public class RedisQueueStrategy implements SeckillStrategy {

	@Override
	public void cacheStock(String seckillId,long seckillStock, int seconds) {
		String key=SeckillKeyEnum.SECKILL_STOCK_COUNT.join(seckillId);
		for (int i = 0; i < seckillStock; i++) {
			JedisUtil.lpush(key, String.valueOf(i));
		}
		JedisUtil.expire(key,seconds);
	}

	@Override
	public boolean seckill(String seckillId,String userId) {
		String key=SeckillKeyEnum.SECKILL_STOCK_COUNT.join(seckillId);
		Object sellOut = LocalCache.get(key);
		if (sellOut == null) {
			Object result = JedisUtil.rpop(key);
			if (result != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void cacheStockStatus(String seckillId,int seconds) {
		String key=SeckillKeyEnum.SECKILL_STOCK_STATUS.join(seckillId);
		if (!LocalCache.containsKey(key)) {
			LocalCache.put(key, false, seconds, TimeUnit.SECONDS);
		}
	}
	
}
