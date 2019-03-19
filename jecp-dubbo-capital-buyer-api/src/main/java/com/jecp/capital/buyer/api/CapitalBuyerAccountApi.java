package com.jecp.capital.buyer.api;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.capital.buyer.dto.BuyerAccountListDTO;

public interface CapitalBuyerAccountApi {
	
	/**
	 * 获得买家资金账户列表
	 * @param pageQuery
	 * @return
	 */
	public PageBean<BuyerAccountListDTO> list(PageQuery pageQuery);
}
