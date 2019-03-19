package com.jecp.shop.order.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.base.page.PageBean;
import com.jecp.base.result.BaseResult;
import com.jecp.base.result.CodeMsg;
import com.jecp.shop.order.api.ShopOrderApi;
import com.jecp.shop.order.dto.ShopOrderListDTO;
import com.jecp.trade.record.api.TradeRecordApi;

/**
 * @Title 商品管理 
 * @author WWT
 * @date 2018年10月25日
 */
@Controller
@RequestMapping("/shop/order")
public class ShopOrderController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private ShopOrderApi shopOrderApi;
	
	@Autowired
	private TradeRecordApi tradeRecordApi;
	/**
	 * 所有商品列表
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(){
		PageBean<ShopOrderListDTO> pageBean=shopOrderApi.list(super.getUserId(),super.getPageQuery());
		return pageBean.toDataGridJSON();
	}
	/**
	 * 订单支付
	 * @return
	 */
	@RequestMapping("/pay")
	@ResponseBody
	public BaseResult pay(Long gtid) {
		CodeMsg codeMsg=tradeRecordApi.balancePayMent(gtid);		
		return BaseResult.of(codeMsg);
	}
}
