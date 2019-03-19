package com.jecp.shop.product.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.shop.product.dao.ShopProductDao;
import com.jecp.shop.product.enums.ProductStatusEnum;
import com.jecp.shop.product.enums.ProductStockControlEnum;
import com.jecp.shop.product.model.ShopProduct;

@Service
public class ShopProductServiceImpl extends BaseServiceImpl<ShopProduct> {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private ShopProductDao shopProductDao;

	@PostConstruct
	private void init() {
		super.setBasedao(shopProductDao);
	}

	/**
	 * 列表
	 * 
	 * @return
	 */
	public List<ShopProduct> list() {
		List<ShopProduct> list = shopProductDao.findAll();
		for (ShopProduct shopProduct : list) {			
			if(shopProduct.getStatus()!=null) {
				ProductStatusEnum productStatusEnum = ProductStatusEnum.valueOf(shopProduct.getStatus());
				shopProduct.setStatus(productStatusEnum.getName());
			}			
		}
		return list;
	}

	/**
	 * 库存控制
	 * @param productId
	 * @param stock
	 * @param productStockControlEnum
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int stockControl(String productId, int stock, ProductStockControlEnum productStockControlEnum) {
		int result = 0;
		if (productStockControlEnum.name().equals(ProductStockControlEnum.INCREASE.name())) {
			result = shopProductDao.stockControl(productId, stock);
		} else {
			result = shopProductDao.stockControl(productId, -stock);
		}
		return result;
	}
}
