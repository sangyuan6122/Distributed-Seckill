package com.jecp.trade.record.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jecp.capital.point.dto.AccountPointHisInputDTO;
import com.jecp.message.api.ReliableMessageApi;
import com.jecp.trade.record.enums.TradeStatusEnum;
import com.jecp.trade.record.model.TradeRecord;
import com.jecp.trade.record.service.impl.TradeRecordServiceImpl;
import com.rabbitmq.client.Channel;

/**
 * @Title 监听积分消息回查队列
 * @author WWT
 * @date 2018年6月21日
 */
@Component
@Lazy(false)
public class TradeRecordListener {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Autowired
	private TradeRecordServiceImpl tradeRecordServiceImpl;
	@Autowired
	private ReliableMessageApi reliableMessageApi;
	@Autowired
	@Qualifier("pointConfirmQueue")
	private Queue pointConfirmQueue;
	@Autowired
	@Qualifier("connectionFactory")
	private ConnectionFactory connectionFactory;
	@Value("${rabbitmq.maxConcurrentConsumers}")
	private int maxConcurrentConsumers;
	@Value("${rabbitmq.concurrentConsumers}")
	private int concurrentConsumers;
	@Value("${rabbitmq.prefetchCount}")
	private int prefetchCount;

	@Bean
	public SimpleMessageListenerContainer messageContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(pointConfirmQueue);
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(maxConcurrentConsumers);
		container.setConcurrentConsumers(concurrentConsumers);
		container.setAcknowledgeMode(AcknowledgeMode.NONE);
		container.setPrefetchCount(prefetchCount);
		container.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				AccountPointHisInputDTO accountPointHisInputDTO = JSON.parseObject(new String(message.getBody()),new TypeReference<AccountPointHisInputDTO>() {});
				TradeRecord tradeRecord = tradeRecordServiceImpl.getByGtid(accountPointHisInputDTO.getGtid());
				String status = tradeRecord.getStatus();
				String messageKey=String.valueOf(tradeRecord.getGtid());
				if (TradeStatusEnum.PAY_SUCCESS.name().equals(status)) {
					reliableMessageApi.messageConfirm(messageKey);
				}else if(TradeStatusEnum.PAY_FAILED.name().equals(status)) {
					reliableMessageApi.failed(messageKey);
				}
			}
		});
		return container;
	}
}
