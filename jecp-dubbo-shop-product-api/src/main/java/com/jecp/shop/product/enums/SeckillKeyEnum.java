package com.jecp.shop.product.enums;

/**
 * @Title 秒杀缓存KEY 
 * @author WWT
 * @date 2018年6月7日
 */
public enum SeckillKeyEnum {
	//秒杀商品
	SHOP_PRODUCT_SECKILL("com.jecp.shop.product.model.shopProductSeckill","秒杀商品"),
	SECKILL_STOCK_STATUS("com.jecp.shop.product.model.shopProductSeckill:stockStatus","库存状态"),
	SECKILL_STOCK_COUNT("com.jecp.shop.product.model.shopProductSeckill:stockCount","库存数量"),
	SECKILL_DISTRIBUTED_LOCK("com.jecp.shop.product.model.shopProductSeckill:lock","秒杀分布式锁"),
	//秒杀商品详情DTO
	SECKILL_DETAIL_DTO("com.jecp.shop.product.model.secKillDetailDTO","秒杀商品详情DTO"),
	SECKILL_SUCCESS("com.jecp.shop.product.model.shopProductSeckill:success","秒杀成功列表");
	private String prefix;
	private String name;

	SeckillKeyEnum(String prefix, String name) {
		this.prefix = prefix;
		this.name = name;
	}

	public String join(Object... args) {
		StringBuilder sb = new StringBuilder(this.prefix);
		for (Object arg : args) {
			sb.append(":").append(arg);
		}
		return sb.toString();	
	}
	
	public String getPrefix() {
		return prefix;
	}

	public String getName() {
		return name;
	}


}
