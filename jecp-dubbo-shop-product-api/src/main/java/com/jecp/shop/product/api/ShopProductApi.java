package com.jecp.shop.product.api;

import java.util.List;

import com.jecp.shop.product.model.ShopProduct;

/**
 * 商品服务
 * @Title 
 * @author WWT
 * @date 2018年12月7日
 */
public interface ShopProductApi {
	/**
	 * 获得商品列表
	 * @return
	 */
	public List<ShopProduct> listShopProduct();
}
