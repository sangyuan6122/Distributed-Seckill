package com.jecp.shop.order.dao;

import com.jecp.base.dao.BaseDao;
import com.jecp.shop.order.model.ShopOrderHis;

public interface ShopOrderHisDao extends BaseDao<ShopOrderHis>{
	/**
	 * 根据全局事务ID、状态查询
	 * @param gtid
	 * @param status
	 * @return
	 */
	public ShopOrderHis getByGtidAndStatus(Long gtid,String status);
	/**
	 * 根据全局事务ID删除
	 * @param gtid
	 * @return
	 */
	public int deleteByGtid(Long gtid);
	
}	
