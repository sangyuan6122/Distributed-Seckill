package com.jecp.trade.record.dao;

import com.jecp.base.dao.BaseDao;
import com.jecp.trade.record.model.TradeRecordHis;

public interface TradeRecordHisDao extends BaseDao<TradeRecordHis>{
	/**
	 * 根据全局事务ID、交易状态查询
	 * @param gtid
	 * @return
	 */
	public TradeRecordHis getByGtidAndStatus(Long gtid,String status);
	/**
	 * 根据全局事务ID删除
	 * @param gtid
	 * @return
	 */
	public int deleteByGtid(Long gtid);
}
