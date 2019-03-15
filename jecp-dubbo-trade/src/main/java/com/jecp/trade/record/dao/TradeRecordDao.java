package com.jecp.trade.record.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.page.PageQuery;
import com.jecp.trade.record.model.TradeRecord;

public interface TradeRecordDao extends BaseDao<TradeRecord>{
	/**
	 * 根据全局事务ID查询
	 * @param gtid
	 * @return
	 */
	public TradeRecord getByGtid(Long gtid);
	/**
	 * 根据全局事务ID、状态更新
	 * @param nowStatus
	 * @param beforeStatus
	 * @param gtid
	 * @return
	 */
	public int updateStatus(String nowStatus,String beforeStatus,Long gtid);
	/**
	 * 根据全局事务ID删除
	 * @param gtid
	 * @return
	 */
	public int deleteByGtid(Long gtid);
	/**
	 * 买家分页列表
	 * @param buyerId
	 * @param pageQuery
	 * @return
	 */
	public List<TradeRecord> list(String buyerId,PageQuery pageQuery);
	/**
	 * 买家总条数
	 * @param buyerId
	 * @param pageQuery
	 * @return
	 */
	public long getCount(String buyerId,PageQuery pageQuery);
}
