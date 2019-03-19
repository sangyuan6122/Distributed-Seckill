package com.jecp.capital.buyer.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import com.jecp.capital.buyer.dto.BuyerAccountListDTO;
import com.jecp.capital.buyer.enums.AccountTypeEnum;
import com.jecp.common.util.BeanConvert;


public class CapitalAccount implements java.io.Serializable {

	private String id;
	private String userId;
	private String accountType;
	private BigDecimal balance;
	private BigDecimal balanceFrozen;
	private Date createTime;
	private Date updateDate;

	public CapitalAccount() {
	}

	public CapitalAccount(String id) {
		this.id = id;
	}

	public CapitalAccount(String id, String userId, String accountType, BigDecimal balance, BigDecimal balanceFrozen,
			Date createTime, Date updateDate) {
		this.id = id;
		this.userId = userId;
		this.accountType = accountType;
		this.balance = balance;
		this.balanceFrozen = balanceFrozen;
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

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalanceFrozen() {
		return this.balanceFrozen;
	}

	public void setBalanceFrozen(BigDecimal balanceFrozen) {
		this.balanceFrozen = balanceFrozen;
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
	@Override
	public String toString() {		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	/**
	 * 资金转出Try阶段
	 * @param tradeAmount
	 */
	public void debitTry(BigDecimal tradeAmount) {
		this.balance=this.balance.subtract(tradeAmount);
		this.balanceFrozen=this.balanceFrozen.add(tradeAmount);		
	}
	/**
	 * 资金转出Confirm阶段
	 * @param tradeAmount
	 */
	public void debitConfirm(BigDecimal tradeAmount) {		
		this.balanceFrozen=this.balanceFrozen.subtract(tradeAmount);		
	}
	/**
	 * 资金转出Cancel阶段
	 * @param tradeAmount
	 */
	public void debitCancel(BigDecimal tradeAmount) {		
		this.balance=this.balance.add(tradeAmount);
		this.balanceFrozen=this.balanceFrozen.subtract(tradeAmount);		
	}
	/**
	 * 转换为列表DTO
	 * @return
	 */
	public BuyerAccountListDTO convertToBuyerAccountListDTO() {
		BuyerAccountListDTOConvert buyerAccountListDTOConvert=new BuyerAccountListDTOConvert();
		BuyerAccountListDTO buyerAccountListDTO=buyerAccountListDTOConvert.convert(this);
		return buyerAccountListDTO;
	}
	public static class BuyerAccountListDTOConvert implements BeanConvert<CapitalAccount,BuyerAccountListDTO > {

		@Override
		public BuyerAccountListDTO convert(CapitalAccount capitalAccount) {
			BuyerAccountListDTO buyerAccountListDTO=new BuyerAccountListDTO();
			BeanUtils.copyProperties(capitalAccount, buyerAccountListDTO);
			buyerAccountListDTO.setAccountType(AccountTypeEnum.getName(capitalAccount.getAccountType()));
			return buyerAccountListDTO;
		}
	}
}
