package com.jecp.shop.product.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.shop.product.dao.ShopProductDao;
import com.jecp.shop.product.model.ShopProduct;

@Repository
public class ShopProductDaoImpl extends BaseDaoImpl<ShopProduct> implements ShopProductDao {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Override
	public List<ShopProduct> listShopProduct() {
		
		return null;
	}

	@Override
	public int stockControl(String productId, int stock) {
		Query query=getSession().createQuery("update ShopProduct set stock=stock+:stock where productId=:productId");
		query.setParameter("stock", stock);
		query.setParameter("productId", productId);
		return query.executeUpdate();
	}
	
	
	
	
	
}
