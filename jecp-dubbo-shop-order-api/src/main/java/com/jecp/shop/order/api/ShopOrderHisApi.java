package com.jecp.shop.order.api;

import org.mengyun.tcctransaction.api.Compensable;

import com.jecp.shop.order.dto.ShopOrderAddDTO;

public interface ShopOrderHisApi {
	/**
	 * 创建订单
	 * @param shopOrderAddDTO
	 */
	@Compensable
	public void createOrder(ShopOrderAddDTO shopOrderAddDTO);
	
	
}
