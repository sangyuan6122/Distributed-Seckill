package com.jecp.trade.record.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.trade.record.dao.TradeRecordHisDao;
import com.jecp.trade.record.model.TradeRecordHis;

@Repository
public class TradeRecordHisDaoImpl extends BaseDaoImpl<TradeRecordHis> implements TradeRecordHisDao {
	private static final Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME); 
	@Override
	public TradeRecordHis getByGtidAndStatus(Long gtid,String status) {
		Query query = getSession().createQuery("from TradeRecordHis where gtid=:gtid and status=:status");
		query.setParameter("gtid", gtid);
		query.setParameter("status", status);
		TradeRecordHis tradeRecordHis = (TradeRecordHis) query.uniqueResult();
		return tradeRecordHis;
	}
	@Override
	public int deleteByGtid(Long gtid) {
		Query query = getSession().createQuery("delete from TradeRecordHis where gtid=:gtid");
		query.setParameter("gtid", gtid);
		return query.executeUpdate();
	}
}
