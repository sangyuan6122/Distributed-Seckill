package com.jecp.shop.product.api;

import java.util.List;

import com.jecp.shop.product.dto.SeckillAddDTO;
import com.jecp.shop.product.dto.SeckillDetailDTO;

/**
 * @Title 秒杀服务 
 * @author WWT
 * @date 2018年12月7日
 */
public interface ShopProductSecKillApi {
	/**
	 * 创建秒杀活动
	 * @param secKillAddInputDTO
	 */
	public void createSecKill(SeckillAddDTO secKillAddInputDTO);

	/**
	 * 秒杀列表
	 * @return
	 */
	public List<SeckillDetailDTO> list();

	/**
	 * 商品秒杀
	 * @param userId
	 * @param seckillId
	 * @return
	 */
	public boolean secKill(String userId, String seckillId);
}
