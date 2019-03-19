package com.jecp.shop.product.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.shop.product.api.ShopProductApi;
import com.jecp.shop.product.model.ShopProduct;

/**
 * @Title 商品管理 
 * @author WWT
 * @date 2018年6月25日
 */
@Controller
@RequestMapping("/shop/product")
public class ShopProductController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private ShopProductApi shopProductApi;
	
	/**
	 * 所有商品列表
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<ShopProduct> listAll(){
		return shopProductApi.listShopProduct();
	}
}
