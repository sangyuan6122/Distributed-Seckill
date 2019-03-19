package com.jecp.capital.seller.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.base.page.PageBean;
import com.jecp.capital.seller.api.CapitalSellerAccountApi;
import com.jecp.capital.seller.dto.SellerAccountListDTO;

/**
 * @Title 商家资金账户
 * @author WWT
 * @date 2018年10月25日
 */
@Controller
@RequestMapping("/capital/seller")
public class CapitalSellerAccountController extends BaseController {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	public CapitalSellerAccountApi capitalSellerAccountApi;
	
	/**
	 * 列表
	 * @param status
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(String status) {
		PageBean<SellerAccountListDTO> pageBean = capitalSellerAccountApi.list(super.getPageQuery());
		return pageBean.toDataGridJSON();
	}
}
