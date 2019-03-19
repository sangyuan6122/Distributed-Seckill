package com.jecp.trade.record.api.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mengyun.tcctransaction.ConfirmingException;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.base.result.BaseCodeMsgEnum;
import com.jecp.base.result.CodeMsg;
import com.jecp.message.api.ReliableMessageApi;
import com.jecp.message.dto.ReliableMessageInputDTO;
import com.jecp.shop.order.api.ShopOrderApi;
import com.jecp.shop.order.enums.ShopOrderStatusEnum;
import com.jecp.trade.record.api.TradeRecordApi;
import com.jecp.trade.record.dto.TradeRecordInputDTO;
import com.jecp.trade.record.dto.TradeRecordListDTO;
import com.jecp.trade.record.enums.TradeStatusEnum;
import com.jecp.trade.record.exceptions.TradeRecordBizException;
import com.jecp.trade.record.model.TradeRecord;
import com.jecp.trade.record.service.impl.TradeRecordHisServiceImpl;
import com.jecp.trade.record.service.impl.TradeRecordServiceImpl;

/**
 * @Title 支付记录历史API
 * @author WWT
 * @date 2018年6月21日
 */
@Service
public class TradeRecordApiImpl implements TradeRecordApi {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Autowired
	private TradeRecordServiceImpl tradeRecordService;
	@Autowired
	private TradeRecordHisServiceImpl tradeRecordHisService;
	@Autowired
	private ReliableMessageApi reliableMessageApi;
	@Autowired
	private ShopOrderApi shopOrderApi;
	@Override
	@Compensable(confirmMethod = "confirmCreate", cancelMethod = "cancelCreate", transactionContextEditor = DubboTransactionContextEditor.class,asyncConfirm=true,asyncCancel=true)	
	public void createTradeRecord(TradeRecordInputDTO tradeRecordInputDTO) {		
		TradeRecord tradeRecord = tradeRecordInputDTO.convertToTradeRecord();
//		TradeRecordHis tradeRecordHis = tradeRecordInputDTO.convertToTradeRecordHis();
		tradeRecord.draft();
//		tradeRecordHis.draft();
		tradeRecordService.save(tradeRecord);
//		tradeRecordHisService.save(tradeRecordHis);		
	}
	
	public void confirmCreate(TradeRecordInputDTO tradeRecordInputDTO) {		
		Long gtid = tradeRecordInputDTO.getGtid();
		TradeRecord tradeRecordFound = tradeRecordService.getByGtid(gtid);
		/* 幂等判断 */
		if (tradeRecordFound != null&&TradeStatusEnum.DRAFT.name().endsWith(tradeRecordFound.getStatus())) {
			tradeRecordService.updateStatus(TradeStatusEnum.WAITING_PAY.name(),TradeStatusEnum.DRAFT.name(),gtid);						
		}
	}	
	public void cancelCreate(TradeRecordInputDTO tradeRecordInputDTO) {
		Long gtid = tradeRecordInputDTO.getGtid();
//		tradeRecordHisService.deleteByGtid(gtid);
		tradeRecordService.deleteByGtid(gtid);
	}

	@Override
	public CodeMsg balancePayMent(Long gtid) {		
		TradeRecord tradeRecord=tradeRecordService.getByGtid(gtid);
		if(!TradeStatusEnum.WAITING_PAY.name().equals(tradeRecord.getStatus())) {
			return BaseCodeMsgEnum.DATA_REPEAT;
		}
		/* 消息预存储 */
		ReliableMessageInputDTO pointMessageInputDTO = tradeRecordService.buildPointMessageDTO(tradeRecord);
		reliableMessageApi.prestore(pointMessageInputDTO);

		/* 执行Tcc转账交易 */
		try {
			tradeRecordService.buyerTransferPayment(tradeRecord);			
		} catch (ConfirmingException confirmingException) {			
			log.error("", confirmingException);
		} catch (Throwable e) {
			log.error("TCC服务异常:", e);			
			throw TradeRecordBizException.PAY_ERROR;
		}
		reliableMessageApi.messageConfirm(pointMessageInputDTO.getMessageKey());
		/*后续改为最大努力通知*/
		shopOrderApi.updateStatus(gtid, ShopOrderStatusEnum.COMPLETED);
		return BaseCodeMsgEnum.SUCCESS;
	}

	@Override
	public PageBean<TradeRecordListDTO> list(String sellerId, PageQuery pageQuery) {
		return tradeRecordService.list(sellerId, pageQuery);
	}

	@Override
	public Set<Long> getAllGtid() {		
		List<TradeRecord> list = tradeRecordService.findAll();
		Set<Long> gtidSet=new HashSet<>();
		for (TradeRecord tradeRecord : list) {
			gtidSet.add(tradeRecord.getGtid());
		}
		return gtidSet;
	}

}
