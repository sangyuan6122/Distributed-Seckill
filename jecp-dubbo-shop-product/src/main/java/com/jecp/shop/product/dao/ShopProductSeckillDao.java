package com.jecp.shop.product.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.shop.product.dto.SeckillDetailDTO;
import com.jecp.shop.product.model.ShopProductSeckill;

public interface ShopProductSeckillDao extends BaseDao<ShopProductSeckill>{
	/**
	 * 秒杀列表
	 * @return
	 */
	public List<SeckillDetailDTO> list();
	/**
	 * 获得秒杀商品详情
	 * @param seckillId
	 * @return
	 */
	public SeckillDetailDTO getSecKillDetailDTO(String seckillId);
	/**
	 * 减库存
	 * @param seckillId
	 * @param productCount
	 * @return
	 */
	public int reduceStock(String seckillId,Integer productCount);
}
