package com.jecp.trade.record.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import com.jecp.capital.buyer.enums.AccountTypeEnum;
import com.jecp.capital.point.dto.AccountPointHisInputDTO;
import com.jecp.common.util.BeanConvert;
import com.jecp.trade.record.model.TradeRecord;
import com.jecp.trade.record.model.TradeRecordHis;

/**
 * @功能 交易记录输入DTO
 * @作者 WWT
 * @修改时间 2018年5月21日
 */
public class TradeRecordInputDTO implements Serializable {

	private static final long serialVersionUID = 4682377225022869819L;

	/** 买家ID **/
	private String buyerId;

	/** 卖家ID **/
	private String sellerId;

	/** 交易金额 **/
	private Double tradeAmount;

	/** 支付方法 **/
	private String payMethod;

	/** 第三方支付订单号 **/
	private String outOrderNo;

	/** 全局事务ID **/
	private Long gtid;
	
	public TradeRecordInputDTO() {
		super();
	}
	public TradeRecordInputDTO(String buyerId, String sellerId, Double tradeAmount, String payMethod, String outOrderNo,
			Long gtid) {
		super();
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.tradeAmount = tradeAmount;
		this.payMethod = payMethod;
		this.outOrderNo = outOrderNo;
		this.gtid = gtid;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public Double getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
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
	/**
	 * 转换成交易记录
	 * @return
	 */
	public TradeRecord convertToTradeRecord() {
		TradeRecordInputDTOConverter tradeRecordInputDTOConvert = new TradeRecordInputDTOConverter();
		TradeRecord tradeRecord = tradeRecordInputDTOConvert.convert(this);		
		return tradeRecord;
	}

	/**
	 * 转换成积分DTO
	 * 
	 * @return
	 */
	public AccountPointHisInputDTO convertToAccountPointHisInputDTO() {
		AccountPointHisInputDTOConverter accountPointHisInputDTOConverter = new AccountPointHisInputDTOConverter();
		AccountPointHisInputDTO accountPointHisInputDTO = accountPointHisInputDTOConverter.convert(this);
		accountPointHisInputDTO.setAccountType(AccountTypeEnum.BUYER.name());
		return accountPointHisInputDTO;
	}
	/**
	 * 转换交易历史记录
	 * @return
	 */
	public TradeRecordHis convertToTradeRecordHis() {
		TradeRecordHisConverter tradeRecordHisConverter = new TradeRecordHisConverter();
		TradeRecordHis tradeRecordHis = tradeRecordHisConverter.convert(this);	
		return tradeRecordHis;
	}
	private static class TradeRecordInputDTOConverter implements BeanConvert<TradeRecordInputDTO, TradeRecord> {
		@Override
		public TradeRecord convert(TradeRecordInputDTO tradeRecordDTO) {
			TradeRecord tradeRecord = new TradeRecord();
			BeanUtils.copyProperties(tradeRecordDTO, tradeRecord);
			tradeRecord.setTradeAmount(new BigDecimal(tradeRecordDTO.getTradeAmount()));
			return tradeRecord;
		}
	}
	private static class AccountPointHisInputDTOConverter implements BeanConvert<TradeRecordInputDTO, AccountPointHisInputDTO> {
		@Override
		public AccountPointHisInputDTO convert(TradeRecordInputDTO tradeRecordDTO) {
			AccountPointHisInputDTO accountPointHisInputDTO = new AccountPointHisInputDTO();
			BeanUtils.copyProperties(tradeRecordDTO, accountPointHisInputDTO);
			accountPointHisInputDTO.setUserId(tradeRecordDTO.getBuyerId());
			accountPointHisInputDTO.setTradeType(tradeRecordDTO.getPayMethod());
			return accountPointHisInputDTO;
		}		
	}
	private static class TradeRecordHisConverter implements BeanConvert<TradeRecordInputDTO, TradeRecordHis> {

		@Override
		public TradeRecordHis convert(TradeRecordInputDTO tradeRecordInputDTO) {
			TradeRecordHis tradeRecordHis=new TradeRecordHis();
			BeanUtils.copyProperties(tradeRecordInputDTO, tradeRecordHis);			
			return tradeRecordHis;
		}
			
	}

}
