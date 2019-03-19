package com.jecp.shop.product.strategy;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jecp.common.cache.localcache.LocalCache;
import com.jecp.common.cache.rediscache.util.JedisUtil;
import com.jecp.common.util.SnowflakeIdWorker;
import com.jecp.common.util.SpringContextHolder;
import com.jecp.shop.product.enums.SeckillKeyEnum;
import com.jecp.shop.product.exceptions.ShopProductBizException;

/**
 * @Title Redis分布式锁
 * @author WWT
 * @date 2018年7月28日
 */
public class RedisDistributedLockStrategy implements SeckillStrategy {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	/** 锁等待超时时间(毫秒) */
	private static final int LOCK_WAIT_TIME = 45000;
	/** 锁过期时间(毫秒) */
	private static final int LOCK_EXPRIE_TIME = 150000;
	
	@Override
	public boolean seckill(String seckillId, String userId) {		
		/* 检查本地缓存 */
		String statusKey = SeckillKeyEnum.SECKILL_STOCK_STATUS.join(seckillId);
		Object sellOut = LocalCache.get(statusKey);
		if (sellOut != null) {
			return false;
		}
		/*以分布式锁方式执行秒杀*/
		RedisDistributedLock redisDistributedLock=SpringContextHolder.getBean("redisDistributedLock");
		String stockCountKey = SeckillKeyEnum.SECKILL_STOCK_COUNT.join(seckillId);
		String lockKey = SeckillKeyEnum.SECKILL_DISTRIBUTED_LOCK.join(seckillId);
		String id = String.valueOf(new SnowflakeIdWorker(1, 1).nextId());

		long stockCount = JedisUtil.get(stockCountKey)==null?0L:Long.valueOf(JedisUtil.get(stockCountKey));
		boolean success=false;
		if (stockCount > 0) {
			long beginTime=System.currentTimeMillis();
			try {
				
				boolean lock = redisDistributedLock.tryLock(lockKey, id, LOCK_WAIT_TIME, LOCK_EXPRIE_TIME);
				log.trace("ID[{}]获得锁用时[{}]",id,System.currentTimeMillis()-beginTime);
				if (lock) {
					stockCount = Long.valueOf(JedisUtil.get(stockCountKey));
					if (stockCount > 0) {
						JedisUtil.decr(stockCountKey);
					}
				}else {
					throw ShopProductBizException.SECKILL_LOCK_EXCEPTION;
				}
			} catch (Exception e) {
				log.error("", e);
				throw ShopProductBizException.SECKILL_ERROR;
			} finally {
				success = redisDistributedLock.unLock(lockKey, id);		
				log.trace("ID[{}]获得锁到释放用时[{}],锁释放成功标志[{}]",id,System.currentTimeMillis()-beginTime,success);
			}
		}
		return success;	
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
