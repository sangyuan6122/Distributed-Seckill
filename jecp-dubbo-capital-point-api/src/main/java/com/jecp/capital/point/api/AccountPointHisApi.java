package com.jecp.capital.point.api;

import com.jecp.capital.point.dto.AccountPointHisInputDTO;

public interface AccountPointHisApi {
	
	/**
	 * 账户积分累加
	 * @param accountPointHisInputDTO
	 */
	public void accumulatePoints(AccountPointHisInputDTO accountPointHisInputDTO);
}
