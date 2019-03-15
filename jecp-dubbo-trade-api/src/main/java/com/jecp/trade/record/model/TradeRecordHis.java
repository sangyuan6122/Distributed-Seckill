package com.jecp.trade.record.model;

import java.util.Calendar;
import java.util.Date;

import com.jecp.trade.record.enums.TradeStatusEnum;


public class TradeRecordHis implements java.io.Serializable {

	private String tradeHisId;
	private Long gtid;
	private String status;
	private Date createTime;

	public TradeRecordHis() {
	}

	public TradeRecordHis(String tradeHisId) {
		this.tradeHisId = tradeHisId;
	}

	public TradeRecordHis(String tradeHisId, Long gtid, String status, Date createTime) {
		this.tradeHisId = tradeHisId;
		this.gtid = gtid;
		this.status = status;
		this.createTime = createTime;
	}

	public String getTradeHisId() {
		return this.tradeHisId;
	}

	public void setTradeHisId(String tradeHisId) {
		this.tradeHisId = tradeHisId;
	}

	public Long getGtid() {
		return this.gtid;
	}

	public void setGtid(Long gtid) {
		this.gtid = gtid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 保存草稿
	 */
	public void draft() {
		this.status=TradeStatusEnum.DRAFT.name();
		this.createTime=Calendar.getInstance().getTime();
	}
	/**
	 * 待支付
	 */
	public void waitingPay() {
		this.status=TradeStatusEnum.WAITING_PAY.name();
		this.createTime=Calendar.getInstance().getTime();
	}
	/**
	 * 支付中
	 */
	public void paying() {
		this.status=TradeStatusEnum.PAYING.name();
		this.createTime=Calendar.getInstance().getTime();
	}
	/**
	 * 支付成功
	 */
	public void paySuccess() {
		this.status=TradeStatusEnum.PAY_SUCCESS.name();
		this.createTime=Calendar.getInstance().getTime();
	}
	/**
	 * 支付失败
	 */
	public void payFailed() {
		this.status=TradeStatusEnum.PAY_FAILED.name();
		this.createTime=Calendar.getInstance().getTime();
	}
}
