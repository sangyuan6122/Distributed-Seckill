package com.jecp.shop.order.api.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.shop.order.api.ShopOrderHisApi;
import com.jecp.shop.order.dto.ShopOrderAddDTO;
import com.jecp.shop.order.enums.ShopOrderStatusEnum;
import com.jecp.shop.order.model.ShopOrder;
import com.jecp.shop.order.service.impl.ShopOrderHisServiceImpl;
import com.jecp.shop.order.service.impl.ShopOrderServiceImpl;

/**
 * @Title 订单历史接口
 * @author WWT
 * @date 2018年7月3日
 */
@Service
public class ShopOrderHisApiImpl implements ShopOrderHisApi {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private ShopOrderServiceImpl shopOrderService;
	@Autowired
	private ShopOrderHisServiceImpl shopOrderHisService;

	@Override
	@Compensable(confirmMethod = "confirmCreate", cancelMethod = "cancelCreate", transactionContextEditor = DubboTransactionContextEditor.class, asyncConfirm = true, asyncCancel = true)	
	public void createOrder(ShopOrderAddDTO shopOrderAddDTO) {
		log.debug("创建订单Try阶段,ShopOrderAddDTO:{}",shopOrderAddDTO.toString());
		ShopOrder shopOrder = shopOrderAddDTO.convertToShopOrder();
		shopOrder.draft();
		shopOrder.totalAmountCalculate();
		shopOrderService.save(shopOrder);
	}
	
	public void confirmCreate(ShopOrderAddDTO shopOrderAddDTO) {
		log.debug("创建订单Confirm阶段,ShopOrderAddDTO:{}",shopOrderAddDTO.toString());
		Long gtid = shopOrderAddDTO.getGtid();
		ShopOrder shopOrderFound = shopOrderService.getByGtid(gtid);
		/* 幂等判断 */
		if (shopOrderFound != null&&ShopOrderStatusEnum.DRAFT.name().equals(shopOrderFound.getStatus())) {
			shopOrderService.updateStatus(gtid, ShopOrderStatusEnum.UN_PAID.name());			
		}
	}
	
	public void cancelCreate(ShopOrderAddDTO shopOrderAddDTO) {
		log.debug("创建订单Cancel阶段,ShopOrderAddDTO:{}",shopOrderAddDTO.toString());
		Long gtid = shopOrderAddDTO.getGtid();
		ShopOrder shopOrderFound = shopOrderService.getByGtid(gtid);
		/* 幂等判断 */
		if (shopOrderFound != null&&ShopOrderStatusEnum.DRAFT.name().equals(shopOrderFound.getStatus())) {
			shopOrderFound.failed();;
			shopOrderService.update(shopOrderFound);;			
		}
	}
}
