package com.jecp.message.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.jecp.common.util.BeanConvert;
import com.jecp.message.model.ReliableMessage;

public class ReliableMessageInputDTO implements Serializable {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	private static final long serialVersionUID = 8987298063109097698L;

	/** 创建者 **/
	private String creater;
	/** 修改者 **/
	private String editor;
	/** 消息关键字 **/
	private String messageKey;
	/** 消息内容 **/
	private String messageBody;
	/** 数据类型 **/
	private String messageDataType;
	/** 确认超时队列 **/
	private String confirmTimeoutQueue;
	/** 消费者队列 **/
	private String consumerQueue;

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getMessageDataType() {
		return messageDataType;
	}

	public void setMessageDataType(String messageDataType) {
		this.messageDataType = messageDataType;
	}

	public String getConfirmTimeoutQueue() {
		return confirmTimeoutQueue;
	}

	public void setConfirmTimeoutQueue(String confirmTimeoutQueue) {
		this.confirmTimeoutQueue = confirmTimeoutQueue;
	}

	public String getConsumerQueue() {
		return consumerQueue;
	}

	public void setConsumerQueue(String consumerQueue) {
		this.consumerQueue = consumerQueue;
	}
	@Override
	public String toString() {		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	public ReliableMessage convertToReliableMessage() {
		ReliableMessageInputDTOConvert reliableMessageInputDTOConvert = new ReliableMessageInputDTOConvert();
		ReliableMessage reliableMessage = reliableMessageInputDTOConvert.convert(this);
//		try {
//			reliableMessage.setMessageBody(new javax.sql.rowset.serial.SerialClob(this.messageBody.toCharArray()));
//		} catch (Exception e) {
//			log.info("消息Body:{}",this.getMessageBody());
//			log.error("",e);
//			throw MessageBizException.MSG_CONVERT_ERROR;
//		}
		return reliableMessage;
	}

	private static class ReliableMessageInputDTOConvert
			implements BeanConvert<ReliableMessageInputDTO, ReliableMessage> {
		@Override
		public ReliableMessage convert(ReliableMessageInputDTO reliableMessageInputDTO) {
			ReliableMessage reliableMessage = new ReliableMessage();
			BeanUtils.copyProperties(reliableMessageInputDTO, reliableMessage);

			return reliableMessage;
		}
	}
}
