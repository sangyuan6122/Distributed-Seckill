package com.jecp.capital.seller.api;

import org.mengyun.tcctransaction.api.Compensable;

import com.jecp.capital.seller.dto.SellerAccountHisInputDTO;

public interface CapitalSellerAccountHisApi {
	/**
	 * 商家账户资金转入
	 * @param capitalAccountHisInputDTO
	 */
	@Compensable
	public void credit(SellerAccountHisInputDTO capitalAccountHisInputDTO);
	/**
	 * 商家账户资金转出
	 * @param capitalAccountHisInputDTO
	 */
	@Compensable
	public void debit(SellerAccountHisInputDTO capitalAccountHisInputDTO);
}
