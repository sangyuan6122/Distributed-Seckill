package com.jecp.capital.seller.model;

import java.math.BigDecimal;
import java.util.Date;

import com.jecp.capital.seller.enums.AccountHisStatusEnum;

/**
 * @Title 卖家资金账户历史
 * @author WWT
 * @date 2018年5月2日
 */
public class CapitalAccountHis implements java.io.Serializable {

	private String id;
	private String userId;
	private String accountType;
	private BigDecimal tradeAmount;
	private String tradeType;
	private BigDecimal balance;
	private String status;
	private Long gtid;
	private Date createTime;
	private Date updateDate;

	public CapitalAccountHis() {
	}

	public CapitalAccountHis(String id) {
		this.id = id;
	}

	public CapitalAccountHis(String id, String userId, String accountType, BigDecimal tradeAmount, String tradeType,
			BigDecimal balance, String status, Long gtid, Date createTime, Date updateDate) {
		this.id = id;
		this.userId = userId;
		this.accountType = accountType;
		this.tradeAmount = tradeAmount;
		this.tradeType = tradeType;
		this.balance = balance;
		this.status = status;
		this.gtid = gtid;
		this.createTime = createTime;
		this.updateDate = updateDate;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getTradeAmount() {
		return this.tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getGtid() {
		return this.gtid;
	}

	public void setGtid(Long gtid) {
		this.gtid = gtid;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public void trying(BigDecimal balance) {
		this.balance=balance;
		this.status=AccountHisStatusEnum.TRYING.name();
	}
	public void confirm() {
		this.status=AccountHisStatusEnum.CONFORM.name();
	}
	public void cancel() {
		this.status=AccountHisStatusEnum.CANCEL.name();
	}
}
