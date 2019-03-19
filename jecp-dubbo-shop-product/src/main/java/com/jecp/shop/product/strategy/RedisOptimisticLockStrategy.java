package com.jecp.shop.product.strategy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jecp.common.cache.localcache.LocalCache;
import com.jecp.common.cache.rediscache.util.JedisUtil;
import com.jecp.shop.product.enums.SeckillKeyEnum;
import com.jecp.shop.product.exceptions.ShopProductBizException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @Title Redis乐观锁实现 
 * @author WWT
 * @date 2018年7月27日
 */
public class RedisOptimisticLockStrategy implements SeckillStrategy {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Override
	public boolean seckill(String seckillId, String userId) {
		String statusKey = SeckillKeyEnum.SECKILL_STOCK_STATUS.join(seckillId);
		String stockCountKey = SeckillKeyEnum.SECKILL_STOCK_COUNT.join(seckillId);
		Object sellOut = LocalCache.get(statusKey);
		if(sellOut == null) {
			Jedis jedis = JedisUtil.getJedis();		
			try {
				while(true) {
					jedis.watch(stockCountKey);				
					int stock=Integer.valueOf(jedis.get(stockCountKey));
					if(stock>0) {
						//开启redis事务
						Transaction transaction = (Transaction) jedis.multi();
						transaction.decr(stockCountKey);
						List<Object> list=transaction.exec();
						if(list.size()>0) {
							//秒杀成功							
							return true;
						}
						TimeUnit.MILLISECONDS.sleep(100);
					}else {
						//库存不足
						return false;
					}
				}
			}catch(Exception e) {
				log.error("",e);
				throw ShopProductBizException.SECKILL_ERROR;
			}finally {
				jedis.unwatch();
				JedisUtil.returnResource(jedis);
			}
		}else {
			return false;
		}
							
	}

	@Override
	public void cacheStock(String seckillId, long seckillStock, int seconds) {
		String key = SeckillKeyEnum.SECKILL_STOCK_COUNT.join(seckillId);
		JedisUtil.set(key, String.valueOf(seckillStock));
		JedisUtil.expire(key, seconds);
	}

	@Override
	public void cacheStockStatus(String seckillId, int seconds) {
		String key = SeckillKeyEnum.SECKILL_STOCK_STATUS.join(seckillId);
		if (!LocalCache.containsKey(key)) {
			LocalCache.put(key, false, seconds, TimeUnit.SECONDS);
		}		
	}

}
