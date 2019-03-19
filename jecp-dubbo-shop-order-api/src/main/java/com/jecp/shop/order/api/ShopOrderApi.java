package com.jecp.shop.order.api;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.shop.order.dto.ShopOrderListDTO;
import com.jecp.shop.order.enums.ShopOrderStatusEnum;

public interface ShopOrderApi {
	/**
	 * 获得订单列表
	 * @param userid
	 * @param pageQuery
	 * @return
	 */
	public PageBean<ShopOrderListDTO> list(String userid,PageQuery pageQuery);
	/**
	 * 更新订单状态
	 * @param gtid
	 * @param shopOrderStatusEnum
	 * @return
	 */
	public void updateStatus(Long gtid, ShopOrderStatusEnum shopOrderStatusEnum);
}
