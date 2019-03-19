package com.jecp.shop.product.api.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.shop.product.api.ShopProductApi;
import com.jecp.shop.product.model.ShopProduct;
import com.jecp.shop.product.service.impl.ShopProductServiceImpl;

@Service
public class ShopProductApiImpl implements ShopProductApi {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private ShopProductServiceImpl shopProductService;

	@Override
	public List<ShopProduct> listShopProduct(){
		List<ShopProduct> list = shopProductService.list();

		return list;
	}

}
