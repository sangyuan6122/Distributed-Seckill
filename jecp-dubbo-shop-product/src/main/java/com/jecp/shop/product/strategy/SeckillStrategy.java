package com.jecp.shop.product.strategy;

/**
 * @Title 秒杀策略
 * @author WWT
 * @date 2018年7月18日
 */
public interface SeckillStrategy {
	/**
	 * 秒杀
	 * @param seckillId
	 * @param userId
	 * @return
	 */
	public boolean seckill(String seckillId,String userId);

	/**
	 * 缓存秒杀商品库存(Redis)
	 * @param seckillId
	 * @param seckillStock
	 * @param seconds
	 */
	public void cacheStock(String seckillId,long seckillStock, int seconds);

	/**
	 * 缓存结果
	 * @param seckillId
	 * @param seconds
	 */
	public void cacheStockStatus(String seckillId,int seconds);

}
