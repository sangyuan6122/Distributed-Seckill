package com.jecp.message.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jecp.common.util.BeanConvert;
import com.jecp.message.dto.ReliableMessageListDTO;
import com.jecp.message.enums.MessageStatusEnum;

public class ReliableMessage implements java.io.Serializable {
	private static final long serialVersionUID = 881475918136959060L;

	private String id;
	private String creater;
	private String editor;
	private Date createTime;
	private Date lastUpdateTime;
	private String messageKey;
	private String messageBody;
	private String messageDataType;
	private String confirmTimeoutQueue;
	private String consumerQueue;
	private Integer checkProducerTimes;
	private Integer resendTimes;
	private Integer alreadyDead;
	private String status;
	private Integer deleted;

	public ReliableMessage() {
	}

	public ReliableMessage(String id) {
		this.id = id;
	}

	public ReliableMessage(String id, String creater, String editor, Date createTime, Date lastUpdateTime,
			String messageKey, String messageBody, String messageDataType, String confirmTimeoutQueue,
			String consumerQueue, Integer checkProducerTimes, Integer resendTimes, Integer alreadyDead, String status,
			Integer deleted) {
		this.id = id;
		this.creater = creater;
		this.editor = editor;
		this.createTime = createTime;
		this.lastUpdateTime = lastUpdateTime;
		this.messageKey = messageKey;
		this.messageBody = messageBody;
		this.messageDataType = messageDataType;
		this.confirmTimeoutQueue = confirmTimeoutQueue;
		this.consumerQueue = consumerQueue;
		this.checkProducerTimes = checkProducerTimes;
		this.resendTimes = resendTimes;
		this.alreadyDead = alreadyDead;
		this.status = status;
		this.deleted = deleted;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getMessageKey() {
		return this.messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageBody() {
		return this.messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getMessageDataType() {
		return this.messageDataType;
	}

	public void setMessageDataType(String messageDataType) {
		this.messageDataType = messageDataType;
	}

	public String getConfirmTimeoutQueue() {
		return this.confirmTimeoutQueue;
	}

	public void setConfirmTimeoutQueue(String confirmTimeoutQueue) {
		this.confirmTimeoutQueue = confirmTimeoutQueue;
	}

	public String getConsumerQueue() {
		return this.consumerQueue;
	}

	public void setConsumerQueue(String consumerQueue) {
		this.consumerQueue = consumerQueue;
	}

	public Integer getCheckProducerTimes() {
		return this.checkProducerTimes;
	}

	public void setCheckProducerTimes(Integer checkProducerTimes) {
		this.checkProducerTimes = checkProducerTimes;
	}

	public Integer getResendTimes() {
		return this.resendTimes;
	}

	public void setResendTimes(Integer resendTimes) {
		this.resendTimes = resendTimes;
	}

	public Integer getAlreadyDead() {
		return this.alreadyDead;
	}

	public void setAlreadyDead(Integer alreadyDead) {
		this.alreadyDead = alreadyDead;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	/**
	 * 待确认
	 */
	public void unConfirmed() {
		this.status = MessageStatusEnum.UNCONFIRMED.name();
		this.alreadyDead = 0;
		this.deleted = 0;
		this.checkProducerTimes = 0;
		this.resendTimes = 0;
		this.createTime = Calendar.getInstance().getTime();
		this.lastUpdateTime = Calendar.getInstance().getTime();
	}

	/**
	 * 已确认
	 */
	public void confirmed() {
		this.alreadyDead = 0;
		this.status = MessageStatusEnum.UNCONSUMED.name();
		this.lastUpdateTime = Calendar.getInstance().getTime();
	}
	/**
	 * 已消费
	 */
	public void consumed() {
		this.status =MessageStatusEnum.CONSUMED.name();
		this.lastUpdateTime = Calendar.getInstance().getTime();
	}

	/**
	 * 回查
	 */
	public void checkProducer() {
		this.checkProducerTimes++;
		this.lastUpdateTime = Calendar.getInstance().getTime();
	}

	/**
	 * 重发
	 */
	public void reSend() {
		this.resendTimes++;
		this.lastUpdateTime = Calendar.getInstance().getTime();
	}

	/**
	 * 消息死亡
	 */
	public void measageDead() {
		this.alreadyDead = 1;
		this.lastUpdateTime = Calendar.getInstance().getTime();
	}
	/**
	 * 消息失败
	 */
	public void measageFailed() {
		this.status=MessageStatusEnum.FAILED.name();
		this.lastUpdateTime = Calendar.getInstance().getTime();
	}
	/**
	 * 转换为DTO
	 * @return
	 */
	public ReliableMessageListDTO convertToReliableMessageListDTO() {
		ReliableMessageListDTOConvert reliableMessageListDTOConvert=new ReliableMessageListDTOConvert();
		return reliableMessageListDTOConvert.convert(this);		
	}
	private static class ReliableMessageListDTOConvert implements BeanConvert<ReliableMessage, ReliableMessageListDTO> {
		@Override
		public ReliableMessageListDTO convert(ReliableMessage reliableMessage) {
			ReliableMessageListDTO reliableMessageListDTO = new ReliableMessageListDTO();
			BeanUtils.copyProperties(reliableMessage, reliableMessageListDTO);
			reliableMessageListDTO.setStatus(MessageStatusEnum.getDesc(reliableMessageListDTO.getStatus()));
			return reliableMessageListDTO;
		}
	}
}
