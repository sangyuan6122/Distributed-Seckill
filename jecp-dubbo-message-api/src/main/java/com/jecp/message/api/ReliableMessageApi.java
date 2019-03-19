package com.jecp.message.api;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.message.dto.MessageStatusStatisticsDTO;
import com.jecp.message.dto.ReliableMessageInputDTO;
import com.jecp.message.dto.ReliableMessageListDTO;
import com.jecp.message.enums.MessageStatusEnum;
import com.jecp.message.model.ReliableMessage;

/**
 * @功能 可靠消息服务接口
 * @作者 WWT
 * @修改时间 2018年6月17日
 */
public interface ReliableMessageApi {
	/**
	 * 消息预存储
	 * @param reliableMessageInputDTO
	 */
	public void prestore(ReliableMessageInputDTO reliableMessageInputDTO);
	/**
	 * 消息确认
	 * @param messageKey
	 */
	public void messageConfirm(String messageKey);
	/**
	 * 消息死亡
	 * @param messageKey
	 */
	public void messageDead(String messageKey);
	/**
	 * 消息状态回查
	 * @param messageKey
	 */
	public void checkProducerTransactionState(String messageKey);
	/**
	 * 消息重发
	 * @param messageKey
	 */
	public void reSendMessage(String messageKey);
	/**
	 * 已消费
	 * @param messageKey
	 */
	public void consumed(String messageKey);
	/**
	 * 消息失败
	 * @param messageKey
	 */
	public void failed(String messageKey);
	/**
	 * 获得超时异常数据数据
	 * @param messageStatusEnum
	 * @param beforeTime
	 * @param pageQuery
	 * @return
	 */
	public PageBean<ReliableMessage> getExceptionMsg(MessageStatusEnum messageStatusEnum,String beforeTime, PageQuery pageQuery);
	/**
	 * 获得列表
	 * @param status
	 * @param pageQuery
	 * @return
	 */
	public PageBean<ReliableMessageListDTO> list(String status,PageQuery pageQuery); 
	
	/**
	 * 获得消息状态统计
	 * @return
	 */
	public MessageStatusStatisticsDTO getStatusStatistics() ;
	/**
	 * 消息异常处理
	 * @param ids
	 */
	public void exceptionHandling(String[] ids);
}
