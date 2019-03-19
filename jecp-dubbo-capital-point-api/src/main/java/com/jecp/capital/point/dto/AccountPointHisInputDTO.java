package com.jecp.capital.point.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import com.jecp.capital.point.model.AccountPointHis;
import com.jecp.common.util.BeanConvert;

/**
 * @功能 账户积分历史输入
 * @作者 WWT
 * @修改时间 2018年6月28日
 */
public class AccountPointHisInputDTO implements Serializable {

	private static final long serialVersionUID = 8191839450423405486L;
	/** 账户ID **/
	private String userId;
	/** 账户类型 **/
	private String accountType;
	/** 交易金额 **/
	private BigDecimal tradeAmount;
	/** 交易类型 **/
	private String tradeType;
	/** 全局ID **/
	private Long gtid;

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

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Long getGtid() {
		return gtid;
	}

	public void setGtid(Long gtid) {
		this.gtid = gtid;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public AccountPointHis convertToAccountPointHis() {
		AccountPointHisInputDTOConvert accountPointHisInputDTOConvert = new AccountPointHisInputDTOConvert();
		return accountPointHisInputDTOConvert.convert(this);
	}

	private static class AccountPointHisInputDTOConvert implements BeanConvert<AccountPointHisInputDTO, AccountPointHis> {
		@Override
		public AccountPointHis convert(AccountPointHisInputDTO accountPointHisInputDTO) {
			AccountPointHis accountPointHis = new AccountPointHis();
			BeanUtils.copyProperties(accountPointHisInputDTO, accountPointHis);
			return accountPointHis;
		}
	}
}
