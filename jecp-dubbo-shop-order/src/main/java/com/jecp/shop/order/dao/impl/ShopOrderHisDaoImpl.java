package com.jecp.shop.order.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.shop.order.dao.ShopOrderHisDao;
import com.jecp.shop.order.model.ShopOrderHis;

@Repository
public class ShopOrderHisDaoImpl extends BaseDaoImpl<ShopOrderHis> implements ShopOrderHisDao {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Override
	public ShopOrderHis getByGtidAndStatus(Long gtid, String status) {
		Query query = getSession().createQuery("from ShopOrderHis where gtid=:gtid and status=:status");
		query.setParameter("gtid", gtid);
		query.setParameter("status", status);
		return (ShopOrderHis) query.uniqueResult();
	}

	@Override
	public int deleteByGtid(Long gtid) {
		Query query = getSession().createQuery("delete from ShopOrderHis where gtid=:gtid");
		query.setParameter("gtid", gtid);
		return query.executeUpdate();
	}

}
