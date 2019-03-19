package com.jecp.message.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.message.dao.ReliableMessageDao;
import com.jecp.message.dto.MessageStatusStatisticsDTO;
import com.jecp.message.dto.ReliableMessageListDTO;
import com.jecp.message.enums.MessageStatusEnum;
import com.jecp.message.model.ReliableMessage;

/**
 * @Title 可靠消息服务 
 * @author WWT
 * @date 2018年5月1日
 */
@Service
public class ReliableMessageServiceImpl extends BaseServiceImpl<ReliableMessage> {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Autowired
	private ReliableMessageDao reliableMessageDao;
	@Autowired
	private RabbitAdmin rabbitAdmin;

	@PostConstruct
	private void init() {
		super.setBasedao(reliableMessageDao);
	}
	/**
	 * 根据消息Key获得对象
	 * @param messageKey
	 * @return
	 */
	public ReliableMessage getByMessageKey(String messageKey) {
		return reliableMessageDao.getByMessageKey(messageKey);
	}
	/**
	 * 已消费
	 * @param messageKey
	 */
	@Transactional
	public void consumed(String messageKey) {
		ReliableMessage reliableMessage=reliableMessageDao.getByMessageKey(messageKey);
		if(reliableMessage!=null) {
			reliableMessage.consumed();
			reliableMessageDao.update(reliableMessage);
			reliableMessageDao.flushSession();
			reliableMessageDao.moveToHis(messageKey);
			reliableMessageDao.deleteByMessageKey(messageKey);
		}				
	}
	/**
	 * 分页获得异常消息
	 * @param messageStatusEnum
	 * @param beforeTime
	 * @param pageQuery
	 * @return
	 */
	public PageBean<ReliableMessage> getExceptionMsg(MessageStatusEnum messageStatusEnum,String beforeTime, PageQuery pageQuery) {		
		String status = messageStatusEnum.name();
		Long count = reliableMessageDao.queryCount(status, 0, beforeTime);
		List<ReliableMessage> list = reliableMessageDao.list(status, 0, beforeTime, pageQuery);
		PageBean<ReliableMessage> pageBean = new PageBean<ReliableMessage>(list, count, pageQuery.getRows());
		return pageBean;
	}
	/**
	 * 根据状态获得分页列表
	 * @param status
	 * @param pageQuery
	 * @return
	 */
	public PageBean<ReliableMessageListDTO> list(String status,PageQuery pageQuery) {
		Long count=reliableMessageDao.queryCount(status, null, null);
		List<ReliableMessage> list=reliableMessageDao.list(status, null, null, pageQuery);
		List<ReliableMessageListDTO> dtoList=new ArrayList<>();
		for(ReliableMessage reliableMessage:list) {
			dtoList.add(reliableMessage.convertToReliableMessageListDTO());
		}
		PageBean<ReliableMessageListDTO> pageBean=new PageBean<ReliableMessageListDTO>(dtoList,count);
		return pageBean;
	}
	/**
	 * 根据状态获得统计信息
	 * @return
	 */
	public MessageStatusStatisticsDTO getStatusStatistics() {
		return reliableMessageDao.getStatusStatistics();
	}
	
}
