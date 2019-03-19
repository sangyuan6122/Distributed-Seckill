package com.jecp.capital.seller.api;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.capital.seller.dto.SellerAccountListDTO;

public interface CapitalSellerAccountApi {
	
	/**
	 * 获得买家资金账户列表
	 * @param pageQuery
	 * @return
	 */
	public PageBean<SellerAccountListDTO> list(PageQuery pageQuery);
}
