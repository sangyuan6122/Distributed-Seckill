package com.jecp.trade.record.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.base.page.PageQuery;
import com.jecp.trade.record.dao.TradeRecordDao;
import com.jecp.trade.record.model.TradeRecord;

@Repository
public class TradeRecordDaoImpl extends BaseDaoImpl<TradeRecord> implements TradeRecordDao {
	private static final Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Override
	public TradeRecord getByGtid(Long gtid) {
		Query query = getSession().createQuery("from TradeRecord where gtid=:gtid");
		query.setParameter("gtid", gtid);
		TradeRecord tradeRecord = (TradeRecord) query.uniqueResult();
		return tradeRecord;
	}

	@Override
	public int updateStatus(String nowStatus,String beforeStatus,Long gtid) {
		Query query = getSession().createQuery("update TradeRecord set update_date=sysdate,status=:nowStatus where gtid=:gtid and status=:beforeStatus");
		query.setParameter("nowStatus", nowStatus);
		query.setParameter("gtid", gtid);
		query.setParameter("beforeStatus", beforeStatus);
		return query.executeUpdate();
	}

	@Override
	public int deleteByGtid(Long gtid) {
		Query query = getSession().createQuery("delete from TradeRecord where gtid=:gtid");
		query.setParameter("gtid", gtid);
		return query.executeUpdate();
	}

	@Override
	public List<TradeRecord> list(String buyerId, PageQuery pageQuery) {
		pageQuery.setKey("tradeId");	
		if("admin".equals(buyerId)) {
			return super.queryByHql("from TradeRecord",pageQuery,null);
		}else {
			Object[] params= {buyerId};
			return super.queryByHql("from TradeRecord where buyer_id=?", pageQuery, params);
		}			
	}

	@Override
	public long getCount(String buyerId, PageQuery pageQuery) {
		if("admin".equals(buyerId)) {
			return super.queryCountByHql("select count(*) from TradeRecord", null);
		}else {
			Object[] params= {buyerId};
			return super.queryCountByHql("select count(*) from TradeRecord where buyer_id=?", params);
		}
	}
}
