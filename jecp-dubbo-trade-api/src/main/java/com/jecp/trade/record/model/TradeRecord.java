package com.jecp.trade.record.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jecp.capital.buyer.dto.BuyerAccountHisInputDTO;
import com.jecp.capital.buyer.enums.AccountTypeEnum;
import com.jecp.capital.point.dto.AccountPointHisInputDTO;
import com.jecp.capital.seller.dto.SellerAccountHisInputDTO;
import com.jecp.common.util.BeanConvert;
import com.jecp.trade.record.dto.TradeRecordListDTO;
import com.jecp.trade.record.enums.TradeStatusEnum;


public class TradeRecord implements java.io.Serializable {

	private String tradeId;
	private String buyerId;
	private String sellerId;
	private BigDecimal tradeAmount;
	private String payMethod;
	private String outOrderNo;
	private Long gtid;
	private String status;
	private Date createTime;
	private Date updateDate;

	public TradeRecord() {
	}

	public TradeRecord(String tradeId) {
		this.tradeId = tradeId;
	}

	public TradeRecord(String tradeId, String buyerId, String sellerId, BigDecimal tradeAmount, String payMethod,
			String outOrderNo, Long gtid, String status, Date createTime, Date updateDate) {
		this.tradeId = tradeId;
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.tradeAmount = tradeAmount;
		this.payMethod = payMethod;
		this.outOrderNo = outOrderNo;
		this.gtid = gtid;
		this.status = status;
		this.createTime = createTime;
		this.updateDate = updateDate;
	}

	public String getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getBuyerId() {
		return this.buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public BigDecimal getTradeAmount() {
		return this.tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getPayMethod() {
		return this.payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getOutOrderNo() {
		return this.outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
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

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
		this.updateDate=Calendar.getInstance().getTime();
	}
	/**
	 * 支付中
	 */
	public void paying() {
		this.status=TradeStatusEnum.PAYING.name();
		this.updateDate=Calendar.getInstance().getTime();
	}
	/**
	 * 支付成功
	 */
	public void paySuccess() {
		this.status=TradeStatusEnum.PAY_SUCCESS.name();
		this.updateDate=Calendar.getInstance().getTime();
	}
	/**
	 * 支付失败
	 */
	public void payFailed() {
		this.status=TradeStatusEnum.PAY_FAILED.name();
		this.updateDate=Calendar.getInstance().getTime();
	}
	/**
	 * 转换成买家资金账户历史DTO
	 * @return
	 */
	public BuyerAccountHisInputDTO convertToBuyerAccountHisInputDTO() {
		BuyerAccountHisInputDTOConverter convertToCapitalAccountHisInputDTO = new BuyerAccountHisInputDTOConverter();
		BuyerAccountHisInputDTO buyerAccountHisInputDTO = convertToCapitalAccountHisInputDTO.convert(this);
		return buyerAccountHisInputDTO;
	}
	/**
	 * 转换成卖家资金账户历史DTO
	 * @return
	 */
	public SellerAccountHisInputDTO convertToSellerAccountHisInputDTO() {
		SellerAccountHisInputDTOConverter convertToSellerAccountHisInputDTO = new SellerAccountHisInputDTOConverter();
		SellerAccountHisInputDTO sellerAccountHisInputDTO = convertToSellerAccountHisInputDTO.convert(this);
		return sellerAccountHisInputDTO;
	}
	/**
	 * 转换成积分DTO
	 * @return
	 */
	public AccountPointHisInputDTO convertToAccountPointHisInputDTO() {
		AccountPointHisInputDTOConverter accountPointHisInputDTOConverter = new AccountPointHisInputDTOConverter();
		AccountPointHisInputDTO accountPointHisInputDTO = accountPointHisInputDTOConverter.convert(this);
		accountPointHisInputDTO.setAccountType(AccountTypeEnum.BUYER.name());
		return accountPointHisInputDTO;
	}
	/**
	 * 订单记录列表转换
	 * @return
	 */
	public TradeRecordListDTO convertToTradeRecordListDTO() {
		TradeRecordListDTOConvert tradeRecordListDTOConvert=new TradeRecordListDTOConvert();
		TradeRecordListDTO tradeRecordListDTO=tradeRecordListDTOConvert.convert(this);
		return tradeRecordListDTO;
	}
	private static class BuyerAccountHisInputDTOConverter implements BeanConvert<TradeRecord, BuyerAccountHisInputDTO> {
		@Override
		public BuyerAccountHisInputDTO convert(TradeRecord tradeRecord) {
			BuyerAccountHisInputDTO buyerAccountHisInputDTO = new BuyerAccountHisInputDTO();
			BeanUtils.copyProperties(tradeRecord, buyerAccountHisInputDTO);
			buyerAccountHisInputDTO.setUserId(tradeRecord.buyerId);
			return buyerAccountHisInputDTO;
		}
	}

	private static class SellerAccountHisInputDTOConverter implements BeanConvert<TradeRecord, SellerAccountHisInputDTO> {
		@Override
		public SellerAccountHisInputDTO convert(TradeRecord tradeRecord) {
			SellerAccountHisInputDTO sellerAccountHisInputDTO = new SellerAccountHisInputDTO();
			BeanUtils.copyProperties(tradeRecord, sellerAccountHisInputDTO);
			sellerAccountHisInputDTO.setUserId(tradeRecord.sellerId);
			return sellerAccountHisInputDTO;
		}
	}
	private static class AccountPointHisInputDTOConverter implements BeanConvert<TradeRecord, AccountPointHisInputDTO> {
		@Override
		public AccountPointHisInputDTO convert(TradeRecord tradeRecord) {
			AccountPointHisInputDTO accountPointHisInputDTO = new AccountPointHisInputDTO();
			BeanUtils.copyProperties(tradeRecord, accountPointHisInputDTO);
			accountPointHisInputDTO.setUserId(tradeRecord.getBuyerId());
			accountPointHisInputDTO.setTradeType(tradeRecord.getPayMethod());
			return accountPointHisInputDTO;
		}		
	}	
	private static class TradeRecordListDTOConvert implements BeanConvert<TradeRecord, TradeRecordListDTO> {
		@Override
		public TradeRecordListDTO convert(TradeRecord tradeRecord) {
			TradeRecordListDTO tradeRecordListDTO = new TradeRecordListDTO();
			BeanUtils.copyProperties(tradeRecord, tradeRecordListDTO);	
			
			return tradeRecordListDTO;
		}
	}
}
