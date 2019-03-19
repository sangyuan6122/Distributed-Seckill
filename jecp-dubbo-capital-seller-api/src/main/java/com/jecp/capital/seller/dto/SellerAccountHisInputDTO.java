package com.jecp.capital.seller.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import com.jecp.capital.seller.model.CapitalAccountHis;
import com.jecp.common.util.BeanConvert;

/**
 * 商家账户交易流水DTO
 * @author wwt
 *
 */
public class SellerAccountHisInputDTO implements Serializable {

	private static final long serialVersionUID = -3103433021141287953L;
	/** 账户ID **/
	@NotNull
	private String userId;
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
	public CapitalAccountHis convertToSellerAccountHis() {
		SellerAccountHisInputDTOConvert sellerAccountHisInputDTOConvert = new SellerAccountHisInputDTOConvert();
		CapitalAccountHis capitalAccountHis = sellerAccountHisInputDTOConvert.convert(this);
		return capitalAccountHis;
	}

	private static class SellerAccountHisInputDTOConvert implements BeanConvert<SellerAccountHisInputDTO, CapitalAccountHis> {
		@Override
		public CapitalAccountHis convert(SellerAccountHisInputDTO CapitalAccountHisDTO) {
			CapitalAccountHis capitalAccountHis = new CapitalAccountHis();
			BeanUtils.copyProperties(CapitalAccountHisDTO, capitalAccountHis);
			
			return capitalAccountHis;
		}

	}


}
