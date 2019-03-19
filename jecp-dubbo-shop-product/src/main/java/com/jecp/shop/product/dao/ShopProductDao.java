package com.jecp.shop.product.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.shop.product.model.ShopProduct;

public interface ShopProductDao extends BaseDao<ShopProduct>{
	/**
	 * 获得全部商品列表
	 * @return
	 */
	public List<ShopProduct> listShopProduct();
	/**
	 * 库存控制
	 * @param productId
	 * @param stock
	 * @return
	 */
	public int stockControl(String productId, int stock);
}
