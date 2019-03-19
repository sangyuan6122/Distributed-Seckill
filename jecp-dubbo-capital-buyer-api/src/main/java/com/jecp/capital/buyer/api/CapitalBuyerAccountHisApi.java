package com.jecp.capital.buyer.api;

import org.mengyun.tcctransaction.api.Compensable;

import com.jecp.capital.buyer.dto.BuyerAccountHisInputDTO;

public interface CapitalBuyerAccountHisApi {
	/**
	 * 买家账户资金转入
	 * @param capitalAccountHisInputDTO
	 */
	@Compensable
	public void credit(BuyerAccountHisInputDTO capitalAccountHisInputDTO);
	/**
	 * 买家账户资金转出
	 * @param capitalAccountHisInputDTO
	 */
	@Compensable
	public void debit(BuyerAccountHisInputDTO capitalAccountHisInputDTO);
}
