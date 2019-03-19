package com.jecp.capital.buyer.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import com.jecp.capital.buyer.model.CapitalAccountHis;
import com.jecp.common.util.BeanConvert;

/**
 * 买家账户交易历史DTO
 * @功能 
 * @作者 WWT
 * @修改时间 2018年6月21日
 */
public class BuyerAccountHisInputDTO implements Serializable {

	private static final long serialVersionUID = -3103433021141287953L;
	/** 账户ID **/
	private String userId;
	/** 账户类型 
	private String accountType;**/
	/** 交易金额 **/
	private BigDecimal tradeAmount;
	/** 交易类型 **/
	private String tradeType;
	/** 全局订单号 **/
	private Long gtid;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public CapitalAccountHis convertToBuyerAccountHis() {
		BuyerAccountHisInputDTOConvert buyerAccountHisInputDTOConvert = new BuyerAccountHisInputDTOConvert();
		CapitalAccountHis capitalAccountHis = buyerAccountHisInputDTOConvert.convert(this);
		return capitalAccountHis;
	}

	private static class BuyerAccountHisInputDTOConvert implements BeanConvert<BuyerAccountHisInputDTO, CapitalAccountHis> {
		@Override
		public CapitalAccountHis convert(BuyerAccountHisInputDTO capitalAccountHisDTO) {
			CapitalAccountHis capitalAccountHis = new CapitalAccountHis();
			BeanUtils.copyProperties(capitalAccountHisDTO, capitalAccountHis);
			return capitalAccountHis;
		}

	}
}
