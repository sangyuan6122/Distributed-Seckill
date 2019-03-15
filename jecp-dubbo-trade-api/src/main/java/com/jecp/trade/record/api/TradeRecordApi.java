package com.jecp.trade.record.api;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.mengyun.tcctransaction.api.Compensable;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.base.result.CodeMsg;
import com.jecp.trade.record.dto.TradeRecordInputDTO;
import com.jecp.trade.record.dto.TradeRecordListDTO;

/**
 * @功能 交易记录功能接口 
 * @作者 WWT
 * @修改时间 2018年5月21日
 */
public interface TradeRecordApi {
	
	/**
	 * 创建支付记录
	 * @param tradeRecordInputDTO
	 */
	@Compensable
	public void createTradeRecord(TradeRecordInputDTO tradeRecordInputDTO);
	/**
	 * 账户余额支付
	 */
	public CodeMsg balancePayMent(@NotNull Long gtid);
	/**
	 * 买家分页列表
	 * @param sellerId
	 * @param pageQuery
	 * @return
	 */
	public PageBean<TradeRecordListDTO> list(String sellerId,PageQuery pageQuery);
	/**
	 * 获得所有交易ID(压测)
	 * @return
	 */
	public Set<Long> getAllGtid();

}
