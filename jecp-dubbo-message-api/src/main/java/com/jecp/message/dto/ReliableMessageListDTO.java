package com.jecp.message.dto;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @Title 消息列表DTO
 * @author WWT
 * @date 2018年3月1日
 */
public class ReliableMessageListDTO implements Serializable {
	private static final long serialVersionUID = -4625571871636433815L;
	/* 主键 */
	private String id;
	/* 创建者 */
	private String creater;
	/* 修改者 */
	private String editor;
	/* 创建时间 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/* 最后更新时间 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;
	/* 消息ID */
	private String messageKey;
	/* 消息内容 */
	private String messageBody;
	/* 数据类型 */
	private String messageDataType;
	/* 确认超时队列 */
	private String confirmTimeoutQueue;
	/* 消费者队列 */
	private String consumerQueue;
	/* 回查次数 */
	private Integer checkProducerTimes;
	/* 重发次数 */
	private Integer resendTimes;
	/* 消息是否死亡 */
	private Integer alreadyDead;
	/* 消息状态 */
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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

	public Integer getCheckProducerTimes() {
		return checkProducerTimes;
	}

	public void setCheckProducerTimes(Integer checkProducerTimes) {
		this.checkProducerTimes = checkProducerTimes;
	}

	public Integer getResendTimes() {
		return resendTimes;
	}

	public void setResendTimes(Integer resendTimes) {
		this.resendTimes = resendTimes;
	}

	public Integer getAlreadyDead() {
		return alreadyDead;
	}

	public void setAlreadyDead(Integer alreadyDead) {
		this.alreadyDead = alreadyDead;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
