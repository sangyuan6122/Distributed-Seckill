package com.jecp.capital.point.dao;

import com.jecp.base.dao.BaseDao;
import com.jecp.capital.point.model.AccountPointHis;

public interface AccountPointHisDao extends BaseDao<AccountPointHis>{
	/**
	 * 根据全局ID查询
	 * @param getGtid
	 * @return
	 */
	public AccountPointHis getByGtid(Long getGtid);
	
	
}
