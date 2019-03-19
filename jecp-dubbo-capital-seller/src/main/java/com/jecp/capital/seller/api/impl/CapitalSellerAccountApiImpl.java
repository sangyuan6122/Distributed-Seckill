package com.jecp.capital.seller.api.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.capital.seller.api.CapitalSellerAccountApi;
import com.jecp.capital.seller.dto.SellerAccountListDTO;
import com.jecp.capital.seller.service.impl.SellerAccountServiceImpl;

/**
 * @Title 买家资金账户接口服务
 * @author WWT
 * @date 2018年8月3日
 */
@Service
public class CapitalSellerAccountApiImpl implements CapitalSellerAccountApi {

	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private SellerAccountServiceImpl sellerAccountService;

	@Override
	public PageBean<SellerAccountListDTO> list(PageQuery pageQuery) {		
		return sellerAccountService.list(pageQuery);
	}

}
