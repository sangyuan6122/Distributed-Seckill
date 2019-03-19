package com.jecp.capital.point.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jecp.common.util.BeanConvert;

/**
 * @Title 账户积分历史
 * @author WWT
 * @date 2018年5月6日
 */
public class AccountPointHis implements java.io.Serializable {

	private String id;
	private String userId;
	private String accountType;
	private BigDecimal tradeAmount;
	private String tradeType;
	private Long gtid;
	private Date createTime;
	private Date updateTime;

	public AccountPointHis() {
	}

	public AccountPointHis(String id) {
		this.id = id;
	}

	public AccountPointHis(String id, String userId, String accountType, BigDecimal tradeAmount, String tradeType,
			Long gtid, Date createTime, Date updateTime) {
		this.id = id;
		this.userId = userId;
		this.accountType = accountType;
		this.tradeAmount = tradeAmount;
		this.tradeType = tradeType;
		this.gtid = gtid;
		this.createTime = createTime;
		this.updateTime = updateTime;
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

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 初始化更新时间
	 */
	public void initUpdateTime() {
		this.updateTime = Calendar.getInstance().getTime();
	}
	/**
	 * 初始化创建时间
	 */
	public void initcreateTime() {
		this.createTime = Calendar.getInstance().getTime();
	}
	/**
	 * 转换为积分类
	 * @return
	 */
	public AccountPoint convertToAccountPoint() {
		AccountPointHisConvert accountPointHisConvert = new AccountPointHisConvert();
		AccountPoint accountPoint = accountPointHisConvert.convert(this);
		accountPoint.setCreateTime(Calendar.getInstance().getTime());
		accountPoint.setUpdateTime(null);
		return accountPoint;
	}

	private static class AccountPointHisConvert implements BeanConvert<AccountPointHis, AccountPoint> {
		@Override
		public AccountPoint convert(AccountPointHis accountPointHis) {
			AccountPoint accountPoint = new AccountPoint();
			BeanUtils.copyProperties(accountPointHis, accountPoint);
			return accountPoint;
		}

	}
}
