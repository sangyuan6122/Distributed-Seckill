package com.jecp.capital.buyer.api.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.capital.buyer.api.CapitalBuyerAccountApi;
import com.jecp.capital.buyer.dto.BuyerAccountListDTO;
import com.jecp.capital.buyer.service.impl.BuyerAccountServiceImpl;

/**
 * @Title 买家资金账户接口服务
 * @author WWT
 * @date 2018年8月3日
 */
@Service
public class CapitalBuyerAccountApiImpl implements CapitalBuyerAccountApi {

	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private BuyerAccountServiceImpl buyerAccountServiceImpl;

	@Override
	public PageBean<BuyerAccountListDTO> list(PageQuery pageQuery) {		
		return buyerAccountServiceImpl.list(pageQuery);
	}

}
