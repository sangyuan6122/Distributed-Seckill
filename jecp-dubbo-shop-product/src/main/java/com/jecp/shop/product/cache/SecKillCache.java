package com.jecp.shop.product.cache;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jecp.common.cache.localcache.LocalCache;
import com.jecp.common.cache.rediscache.util.JedisUtil;
import com.jecp.shop.product.enums.SeckillKeyEnum;
import com.jecp.shop.product.model.ShopProductSeckill;

/**
 * @Title 秒杀业务缓存
 * @author WWT
 * @date 2018年6月3日
 */
public class SecKillCache {
	private static final Logger log = LoggerFactory.getLogger(JedisUtil.class);

	/**
	 * 检查是否重复下单
	 * @param seckillId
	 * @param userId
	 * @return
	 */
	public static boolean checkOrderRepeat(String seckillId, String userId) {
		final String redisKey = SeckillKeyEnum.SECKILL_SUCCESS.join(seckillId);
		final String localKey = SeckillKeyEnum.SECKILL_SUCCESS.join(seckillId, userId);
		Object cacheObj = LocalCache.get(localKey);
		if (cacheObj != null) {
			return true;
		} else {
			return JedisUtil.sismember(redisKey, userId);
		}
	}

	/**
	 * 缓存成功下单用户
	 * @param seckillId
	 * @param userId
	 * @param seconds
	 */
	public static void cacheSuccessUser(String seckillId, String userId, int seconds) {
		final String redisKey = SeckillKeyEnum.SECKILL_SUCCESS.join(seckillId);
		final String localKey = SeckillKeyEnum.SECKILL_SUCCESS.join(seckillId, userId);
		LocalCache.put(localKey, true, seconds, TimeUnit.SECONDS);
		JedisUtil.sadd(redisKey, userId);
		JedisUtil.expire(redisKey, seconds);
	}

	/**
	 * 缓存秒杀商品
	 * @param shopProductSeckill
	 */
	public static void cacheShopProductSeckill(ShopProductSeckill shopProductSeckill) {
		final String key = SeckillKeyEnum.SHOP_PRODUCT_SECKILL.join(shopProductSeckill.getSeckillId());
		LocalCache.put(key, shopProductSeckill, shopProductSeckill.getEndTime().getTime() - System.currentTimeMillis(),
				TimeUnit.MILLISECONDS);
	}
	/**
	 * 获得秒杀商品
	 * @param seckillId
	 * @return
	 */
	public static ShopProductSeckill getShopProductSeckill(String seckillId) {
		final String key = SeckillKeyEnum.SHOP_PRODUCT_SECKILL.join(seckillId);
		Object obj=LocalCache.get(key);
		if(obj!=null) {
			return (ShopProductSeckill) obj;
		}else {
			return null;
		}		
	}
}
