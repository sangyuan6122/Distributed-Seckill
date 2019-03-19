package com.jecp.capital.seller.service.impl;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.capital.seller.dao.SellerAccountHisDao;
import com.jecp.capital.seller.model.CapitalAccountHis;

@Service
public class SellerAccountHisServiceImpl extends BaseServiceImpl<CapitalAccountHis>{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	
	@Autowired
	private SellerAccountHisDao capitalAccountHisDao;
	@PostConstruct
	private void init() {
		super.setBasedao(capitalAccountHisDao);
	}
	/**
	 * 根据全局ID查询
	 * @param gtid
	 */
	public CapitalAccountHis getByGtid(Long gtid) {
		return capitalAccountHisDao.getByGtid(gtid);
	}

}
