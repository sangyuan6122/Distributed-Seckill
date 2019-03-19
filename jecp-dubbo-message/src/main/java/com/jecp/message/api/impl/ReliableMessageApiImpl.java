package com.jecp.message.api.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.message.api.ReliableMessageApi;
import com.jecp.message.dto.MessageStatusStatisticsDTO;
import com.jecp.message.dto.ReliableMessageInputDTO;
import com.jecp.message.dto.ReliableMessageListDTO;
import com.jecp.message.enums.MessageStatusEnum;
import com.jecp.message.exceptions.MessageBizException;
import com.jecp.message.model.ReliableMessage;
import com.jecp.message.service.impl.ReliableMessageServiceImpl;

@Service
public class ReliableMessageApiImpl implements ReliableMessageApi {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	
	@Autowired
	private ReliableMessageServiceImpl reliableMessageService;
	@Autowired
	private RabbitAdmin rabbitAdmin;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void prestore(ReliableMessageInputDTO reliableMessageInputDTO) {
		log.trace("预存储消息:{}",reliableMessageInputDTO);
		ReliableMessage reliableMessage = reliableMessageInputDTO.convertToReliableMessage();
		reliableMessage.unConfirmed();
		try {
			reliableMessageService.save(reliableMessage);
		}catch(DataIntegrityViolationException e) {
			log.error(e);			
		}		
	}

	@Override
	public void messageConfirm(String messageKey) {
		log.info("消息确认,messageKey:{}",messageKey);
		
		ReliableMessage reliableMessage = reliableMessageService.getByMessageKey(messageKey);
		if (reliableMessage == null) {
			log.info("消息不存在,messageKey:{}", messageKey);
			return;
		}
		reliableMessage.confirmed();
		reliableMessageService.update(reliableMessage);
		/* 发送消息，无需持久化 */
		Queue queue = new Queue(reliableMessage.getConsumerQueue(), false);
		rabbitAdmin.declareQueue(queue);
		rabbitTemplate.convertAndSend(reliableMessage.getConsumerQueue(), reliableMessage.getMessageBody(), m -> {
			m.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
			return m;
		});
		
		
		// rabbitTemplate.convertAndSend(reliableMessage.getConsumerQueue(),reliableMessage.getMessageBody());
	}
	@Override
	public void messageDead(String messageKey) {
		log.info("消息死亡,messageKey:{}",messageKey);
		ReliableMessage reliableMessage = reliableMessageService.getByMessageKey(messageKey);
		if (reliableMessage == null) {
			throw new MessageBizException("消息不存在,messageKey:{0}", messageKey);
		}
		reliableMessage.measageDead();
		reliableMessageService.update(reliableMessage);
	}
	@Override
	public void checkProducerTransactionState(String messageKey) {
		log.info("消息回查,messageKey:{}",messageKey);
		ReliableMessage reliableMessage = reliableMessageService.getByMessageKey(messageKey);
		if (reliableMessage == null) {
			throw new MessageBizException("消息不存在,messageKey:{0}", messageKey);
		}
		//判断是否待确认状态
		if(MessageStatusEnum.UNCONFIRMED.name().equals(reliableMessage.getStatus())) {
			reliableMessage.checkProducer();
			reliableMessageService.update(reliableMessage);
			/* 发送消息，无需持久化 */
			Queue queue = new Queue(reliableMessage.getConfirmTimeoutQueue(), false);
			rabbitAdmin.declareQueue(queue);
			rabbitTemplate.convertAndSend(reliableMessage.getConfirmTimeoutQueue(), reliableMessage.getMessageBody(), m -> {
				m.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
				return m;
			});
		}		
	}

	@Override
	public void reSendMessage(String messageKey) {
		log.info("重新发送消息,messageKey:{}",messageKey);
		ReliableMessage reliableMessage = reliableMessageService.getByMessageKey(messageKey);
		if (reliableMessage == null) {
			throw new MessageBizException("消息不存在,messageKey:{0}", messageKey);
		}
		//判断是否待消费状态
		if(MessageStatusEnum.UNCONSUMED.name().equals(reliableMessage.getStatus())) {
			reliableMessage.reSend();
			reliableMessageService.update(reliableMessage);
			/* 发送消息，无需持久化 */
			Queue queue = new Queue(reliableMessage.getConsumerQueue(), false);
			rabbitAdmin.declareQueue(queue);
			rabbitTemplate.convertAndSend(reliableMessage.getConsumerQueue(), reliableMessage.getMessageBody(), m -> {
				m.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
				return m;
			});
		}
		
	}

	@Override
	public void consumed(String messageKey) {
		log.info("消息已消费,messageKey:{}",messageKey);		
		reliableMessageService.consumed(messageKey);		
	}
	@Override
	public void failed(String messageKey) {
		ReliableMessage reliableMessage = reliableMessageService.getByMessageKey(messageKey);
		if (reliableMessage == null) {
			throw new MessageBizException("消息不存在,messageKey:{0}", messageKey);
		}
		reliableMessage.measageFailed();
		reliableMessageService.update(reliableMessage);
	}
	@Override
	public PageBean<ReliableMessage> getExceptionMsg(MessageStatusEnum messageStatusEnum,String beforeTime, PageQuery pageQuery) {		
		return reliableMessageService.getExceptionMsg(messageStatusEnum,beforeTime, pageQuery);
	}

	@Override
	public PageBean<ReliableMessageListDTO> list(String status,PageQuery pageQuery) {		
		return reliableMessageService.list(status,pageQuery);
	}

	@Override
	public MessageStatusStatisticsDTO getStatusStatistics() {		
		return reliableMessageService.getStatusStatistics();
	}

	@Override
	public void exceptionHandling(String[] ids) {
		List<ReliableMessage> list=reliableMessageService.getByIds(ids);
		for(ReliableMessage reliableMessage:list) {
			if(MessageStatusEnum.UNCONFIRMED.name().equals(reliableMessage.getStatus())) {
				checkProducerTransactionState(reliableMessage.getMessageKey());
			}else if(MessageStatusEnum.UNCONSUMED.name().equals(reliableMessage.getStatus())) {
				reSendMessage(reliableMessage.getMessageKey());
			}
		}
	}

	
}
