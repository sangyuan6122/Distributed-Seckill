package com.jecp.capital.point.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jecp.capital.point.dto.AccountPointHisInputDTO;
import com.jecp.capital.point.model.AccountPointHis;
import com.jecp.capital.point.service.impl.AccountPointHisServiceImpl;
import com.jecp.message.api.ReliableMessageApi;

@Service
public class AccountPointListener implements MessageListener {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private ReliableMessageApi reliableMessageApi;
	@Autowired
	private AccountPointHisServiceImpl accountPointHisService;
	@Override
	public void onMessage(Message message) {
		String messageJson = new String(message.getBody());
		log.info("收到积分消息:{}", messageJson);
		AccountPointHisInputDTO accountPointHisInputDTO = JSON.parseObject(messageJson,new TypeReference<AccountPointHisInputDTO>(){});
		/** 实现幂等 **/
		AccountPointHis foundAccountPonitHis = accountPointHisService.getByGtid(accountPointHisInputDTO.getGtid());
		if(foundAccountPonitHis==null) {
			AccountPointHis accountPonitHis = accountPointHisInputDTO.convertToAccountPointHis();
			accountPointHisService.accumulatePoints(accountPonitHis);			
		}
		reliableMessageApi.consumed(String.valueOf(accountPointHisInputDTO.getGtid()));
		
	}

}
