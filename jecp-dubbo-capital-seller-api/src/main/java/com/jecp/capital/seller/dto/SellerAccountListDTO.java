package com.jecp.capital.seller.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @功能 商家账户列表DTO
 * @作者 WWT
 * @修改时间 2018年6月21日
 */
public class SellerAccountListDTO implements Serializable {
	private static final long serialVersionUID = 2015062045908336110L;
	
	private String id;
	private String userId;
	private String accountType;
	private BigDecimal balance;
	private BigDecimal balanceFrozen;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getBalanceFrozen() {
		return balanceFrozen;
	}
	public void setBalanceFrozen(BigDecimal balanceFrozen) {
		this.balanceFrozen = balanceFrozen;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
