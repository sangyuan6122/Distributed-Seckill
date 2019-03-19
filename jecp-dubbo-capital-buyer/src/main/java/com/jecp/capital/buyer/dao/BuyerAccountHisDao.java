package com.jecp.capital.buyer.dao;

import com.jecp.base.dao.BaseDao;
import com.jecp.capital.buyer.model.CapitalAccountHis;

public interface BuyerAccountHisDao extends BaseDao<CapitalAccountHis>{
	
	/**
	 * 根据全局ID查询
	 * @param gtid
	 * @return
	 */
	public CapitalAccountHis getByGtid(Long gtid);
}
