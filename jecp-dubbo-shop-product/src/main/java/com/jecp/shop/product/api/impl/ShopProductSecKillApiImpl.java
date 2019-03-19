package com.jecp.shop.product.api.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jecp.shop.order.api.ShopOrderHisApi;
import com.jecp.shop.product.api.ShopProductSecKillApi;
import com.jecp.shop.product.dto.SeckillAddDTO;
import com.jecp.shop.product.dto.SeckillDetailDTO;
import com.jecp.shop.product.model.ShopProductSeckill;
import com.jecp.shop.product.service.impl.ShopProductSeckillServiceImpl;

@Service
public class ShopProductSecKillApiImpl implements ShopProductSecKillApi {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private ShopProductSeckillServiceImpl shopProductSeckillService;
	@Autowired
	private ShopOrderHisApi shopOrderHisApi;
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createSecKill(SeckillAddDTO secKillAddInputDTO) {
		ShopProductSeckill shopProductSeckill = secKillAddInputDTO.convertToShopProductSeckill();
		shopProductSeckill.setCreateTime(new Date());
		String seckillId=shopProductSeckillService.createSeckill(shopProductSeckill);
		
	}

	@Override
	public List<SeckillDetailDTO> list() {
		return shopProductSeckillService.list();
	}

	@Override
	public boolean secKill(String userId, String seckillId) {
		return shopProductSeckillService.preStockReduction(userId, seckillId);		
	}

}
