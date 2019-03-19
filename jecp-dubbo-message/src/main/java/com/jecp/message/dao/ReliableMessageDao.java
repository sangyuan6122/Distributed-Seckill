package com.jecp.message.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.page.PageQuery;
import com.jecp.message.dto.MessageStatusStatisticsDTO;
import com.jecp.message.model.ReliableMessage;

public interface ReliableMessageDao extends BaseDao<ReliableMessage>{
	/**
	 * 根据消息关键字获得
	 * @param messageKey
	 * @return
	 */
	public ReliableMessage getByMessageKey(String messageKey);
	/**
	 * 根据消息关键字删除
	 * @param messageKey
	 */
	public void deleteByMessageKey(String messageKey);
	/**
	 * 移动到历史表
	 * @param messageKey
	 */
	public void moveToHis(String messageKey);
	/**
	 * 获得列表总数
	 * @param status
	 * @param already_dead
	 * @param beforeTime
	 * @return
	 */
	public long queryCount(String status,Integer already_dead,String beforeTime);
	/**
	 * 获得列表
	 * @param status
	 * @param already_dead
	 * @param beforeTime
	 * @param pageQuery
	 * @return
	 */
	public List<ReliableMessage> list(String status,Integer already_dead,String beforeTime, PageQuery pageQuery);
	/**
	 * 获得状态统计
	 * @return
	 */
	public MessageStatusStatisticsDTO getStatusStatistics();
}
