package com.jecp.shop.order.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.page.PageQuery;
import com.jecp.shop.order.model.ShopOrder;

public interface ShopOrderDao extends BaseDao<ShopOrder>{
	/**
	 * 根据全局ID获得
	 * @param gtid
	 * @return
	 */
	public ShopOrder getByGtid(Long gtid);
	/**
	 * 修改订单状态
	 * @param gtid
	 * @param status
	 * @return
	 */
	public int updateStatus(Long gtid,String status);
	/**
	 * 根据全局事务ID删除
	 * @param gtid
	 * @return
	 */
	public int deleteByGtid(Long gtid);
	/**
	 * 列表查询
	 * @param userId
	 * @param pageQuery
	 * @return
	 */
	public List<ShopOrder> list(String userId,PageQuery pageQuery);
	/**
	 * 列表总量
	 * @param userId
	 * @param pageQuery
	 * @return
	 */
	public long getCount(String userId,PageQuery pageQuery);
}
