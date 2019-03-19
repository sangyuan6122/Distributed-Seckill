package com.jecp.capital.point.api.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jecp.capital.point.api.AccountPointHisApi;
import com.jecp.capital.point.dto.AccountPointHisInputDTO;
import com.jecp.capital.point.model.AccountPointHis;


public class AccountPointHisApiImpl implements AccountPointHisApi {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Override
	public void accumulatePoints(AccountPointHisInputDTO accountPointHisInputDTO) {
		
		AccountPointHis accountPonitHis = accountPointHisInputDTO.convertToAccountPointHis();
		

	}
}
