package com.jecp.message.model;

import java.sql.Clob;
import java.util.Date;

public class ReliableMessageDeleted implements java.io.Serializable {

	private String id;
	private String creater;
	private String editor;
	private Date createTime;
	private Date lastUpdateTime;
	private String messageKey;
	private Clob messageBody;
	private String messageDataType;
	private String confirmTimeoutQueue;
	private String consumerQueue;
	private Integer checkProducerTimes;
	private Integer resendTimes;
	private Integer alreadyDead;
	private String status;
	private Integer deleted;

	public ReliableMessageDeleted() {
	}

	public ReliableMessageDeleted(String id) {
		this.id = id;
	}

	public ReliableMessageDeleted(String id, String creater, String editor, Date createTime, Date lastUpdateTime,
			String messageKey, Clob messageBody, String messageDataType, String confirmTimeoutQueue,
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

	public Clob getMessageBody() {
		return this.messageBody;
	}

	public void setMessageBody(Clob messageBody) {
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

}
