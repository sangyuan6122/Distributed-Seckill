package com.jecp.capital.point.api.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.capital.point.api.AccountPointApi;
import com.jecp.capital.point.dto.AccountPointListDTO;
import com.jecp.capital.point.service.impl.AccountPointServiceImpl;

@Service
public class AccountPointApiImpl implements AccountPointApi {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private AccountPointServiceImpl accountPointService;

	@Override
	public PageBean<AccountPointListDTO> list(PageQuery pageQuery) {

		return accountPointService.list(pageQuery);
	}

}
