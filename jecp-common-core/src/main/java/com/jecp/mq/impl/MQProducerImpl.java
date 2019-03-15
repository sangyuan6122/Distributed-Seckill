package com.jecp.mq.impl;


import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jecp.mq.MQProducer;

@Service("mQProducer")
@Scope("prototype")
public class MQProducerImpl implements MQProducer {
    @Resource
    private RabbitTemplate  rabbitTemplate;

    private static final Logger log = LogManager.getLogger(MQProducerImpl.class);
    @Override
    public void sendDataToQueue(String queueKey, Object object) {
        try {        	
        	rabbitTemplate.convertAndSend(queueKey, object);
        } catch (Exception e) {
        	log.error(e);
        }

    }
}