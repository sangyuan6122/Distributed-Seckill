package com.jecp.trade.record.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.capital.buyer.api.CapitalBuyerAccountHisApi;
import com.jecp.capital.point.dto.AccountPointHisInputDTO;
import com.jecp.capital.point.enums.AccountPointQueueEnum;
import com.jecp.capital.seller.api.CapitalSellerAccountHisApi;
import com.jecp.message.dto.ReliableMessageInputDTO;
import com.jecp.message.enums.MessageDataTypeEnum;
import com.jecp.trade.record.dao.TradeRecordDao;
import com.jecp.trade.record.dto.TradeRecordListDTO;
import com.jecp.trade.record.enums.TradeStatusEnum;
import com.jecp.trade.record.exceptions.TradeRecordBizException;
import com.jecp.trade.record.model.TradeRecord;

/**
 * @Title 交易服务
 * @author WWT
 * @date 2018年6月21日
 */
@Service
public class TradeRecordServiceImpl extends BaseServiceImpl<TradeRecord> {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private TradeRecordDao tradeRecordDao;
	@Autowired
	private TradeRecordHisServiceImpl tradeRecordHisService;
	@Autowired
	private CapitalBuyerAccountHisApi capitalBuyerAccountHisApi;
	@Autowired
	private CapitalSellerAccountHisApi capitalSellerAccountHisApi;

	@PostConstruct
	private void init() {
		super.setBasedao(tradeRecordDao);
	}

	/**
	 * 买家转账支付
	 * 
	 * @param tradeRecord
	 */
	@Compensable(confirmMethod = "confirmBuyerTransferPayment", cancelMethod = "cancelBuyerTransferPayment", asyncConfirm = true)
	public void buyerTransferPayment(TradeRecord tradeRecord) {
		long gtid = tradeRecord.getGtid();
		TradeRecord foundTradeRecord = tradeRecordDao.getByGtid(gtid);
		if (foundTradeRecord != null && TradeStatusEnum.WAITING_PAY.name().equals(foundTradeRecord.getStatus())) {
			int sqlResult = tradeRecordDao.updateStatus(TradeStatusEnum.PAYING.name(),TradeStatusEnum.WAITING_PAY.name(), gtid);
			// 防止重复提交
			if (sqlResult == 1) {
				// 商家账户资金转入
				capitalSellerAccountHisApi.credit(tradeRecord.convertToSellerAccountHisInputDTO());
				Future<Void> sellerFutyre = RpcContext.getContext().getFuture();
				// 买家账户资金转出
				capitalBuyerAccountHisApi.debit(tradeRecord.convertToBuyerAccountHisInputDTO());	
				try {
					sellerFutyre.get();
				} catch (Exception e) {
					log.error("",e);
					throw TradeRecordBizException.PAY_ERROR;
				}
			}
		}
	}

	public void confirmBuyerTransferPayment(TradeRecord tradeRecord) {
		TradeRecord foundTradeRecord = tradeRecordDao.getByGtid(tradeRecord.getGtid());
		if (foundTradeRecord != null && TradeStatusEnum.PAYING.name().equals(foundTradeRecord.getStatus())) {
			// 更新交易记录状态
			foundTradeRecord.paySuccess();
			tradeRecordDao.update(foundTradeRecord);
		}

	}

	public void cancelBuyerTransferPayment(TradeRecord tradeRecord) {
		// 更新交易记录状态
		TradeRecord foundTradeRecord = tradeRecordDao.getByGtid(tradeRecord.getGtid());
		if (foundTradeRecord != null && TradeStatusEnum.PAYING.name().equals(foundTradeRecord.getStatus())) {
			foundTradeRecord.payFailed();
			tradeRecordDao.update(foundTradeRecord);
		}

	}

	/**
	 * 根据全局事务ID、状态更新
	 * 
	 * @param nowStatus
	 * @param beforeStatus
	 * @param gtid
	 * @return
	 */
	public int updateStatus(String nowStatus, String beforeStatus, Long gtid) {
		return tradeRecordDao.updateStatus(nowStatus, beforeStatus, gtid);
	}

	/**
	 * 根据GTID查询
	 * 
	 * @param gtid
	 * @return
	 */
	public TradeRecord getByGtid(Long gtid) {
		return tradeRecordDao.getByGtid(gtid);
	}

	/**
	 * 根据全局事务ID删除
	 * 
	 * @param gtid
	 * @return
	 */
	public int deleteByGtid(Long gtid) {
		return tradeRecordDao.deleteByGtid(gtid);
	}

	/**
	 * 构建可靠消息DTO
	 * 
	 * @param messageQueueEnum
	 * @param tradeRecordInputDTO
	 * @return
	 */
	public ReliableMessageInputDTO buildPointMessageDTO(TradeRecord tradeRecord) {
		AccountPointHisInputDTO accountPointHisInputDTO = tradeRecord.convertToAccountPointHisInputDTO();
		ReliableMessageInputDTO reliableMessageInputDTO = new ReliableMessageInputDTO();
		reliableMessageInputDTO.setConfirmTimeoutQueue(AccountPointQueueEnum.POINTS_CONFIRM_TIME_OUT_QUEUE.name());
		reliableMessageInputDTO.setConsumerQueue(AccountPointQueueEnum.POINTS_CONSUMER_QUEUE.name());
		reliableMessageInputDTO
				.setMessageBody(JSON.toJSONString(accountPointHisInputDTO, SerializerFeature.WriteMapNullValue,
						SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty));
		reliableMessageInputDTO.setMessageDataType(MessageDataTypeEnum.JSON.name());
		reliableMessageInputDTO.setMessageKey(String.valueOf(tradeRecord.getGtid()));
		return reliableMessageInputDTO;
	}

	/**
	 * 买家分页列表
	 * 
	 * @param sellerId
	 * @param pageQuery
	 * @return
	 */
	public PageBean<TradeRecordListDTO> list(String buyerId, PageQuery pageQuery) {
		long count = tradeRecordDao.getCount(buyerId, pageQuery);
		List<TradeRecord> list = tradeRecordDao.list(buyerId, pageQuery);
		List<TradeRecordListDTO> recordDtoList = new ArrayList<>();
		for (TradeRecord tradeRecord : list) {
			TradeRecordListDTO tradeRecordListDTO = tradeRecord.convertToTradeRecordListDTO();
			tradeRecordListDTO.setStatus(TradeStatusEnum.getDesc(tradeRecord.getStatus()));
			tradeRecordListDTO.setGtid(String.valueOf(tradeRecord.getGtid()));
			recordDtoList.add(tradeRecordListDTO);
		}
		return new PageBean<TradeRecordListDTO>(recordDtoList, count);
	}
}
