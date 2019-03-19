package com.jecp.capital.point.api;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.capital.point.dto.AccountPointListDTO;

public interface AccountPointApi {
	/**
	 * 获得账户积分列表
	 * @param pageQuery
	 * @return
	 */
	public PageBean<AccountPointListDTO> list(PageQuery pageQuery);
}
