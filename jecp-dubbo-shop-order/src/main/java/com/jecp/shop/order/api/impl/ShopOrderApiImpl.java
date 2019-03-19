package com.jecp.shop.order.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.shop.order.api.ShopOrderApi;
import com.jecp.shop.order.dto.ShopOrderListDTO;
import com.jecp.shop.order.enums.ShopOrderStatusEnum;
import com.jecp.shop.order.model.ShopOrder;
import com.jecp.shop.order.service.impl.ShopOrderServiceImpl;

/**
 * @Title 订单接口
 * @author WWT
 * @date 2018年7月3日
 */
@Service
public class ShopOrderApiImpl implements ShopOrderApi {
	@Autowired
	private ShopOrderServiceImpl shopOrderService;

	@Override
	public PageBean<ShopOrderListDTO> list(String userid,PageQuery pageQuery) {

		return shopOrderService.list(userid,pageQuery);
	}

	@Override
	public void updateStatus(Long gtid, ShopOrderStatusEnum shopOrderStatusEnum) {
		ShopOrder shopOrder=shopOrderService.getByGtid(gtid);
		if(shopOrder!=null) {
			shopOrder.completed();
			shopOrderService.update(shopOrder);
		}		
	}
	
}
