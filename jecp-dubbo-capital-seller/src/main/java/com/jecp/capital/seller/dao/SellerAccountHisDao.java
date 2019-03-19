package com.jecp.capital.seller.dao;

import com.jecp.base.dao.BaseDao;
import com.jecp.capital.seller.model.CapitalAccountHis;

public interface SellerAccountHisDao extends BaseDao<CapitalAccountHis>{
	
	/**
	 * 根据全局ID查询
	 * @param gtid
	 * @return
	 */
	public CapitalAccountHis getByGtid(Long gtid);
}
