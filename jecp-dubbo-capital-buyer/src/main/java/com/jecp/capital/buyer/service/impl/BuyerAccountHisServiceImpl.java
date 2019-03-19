package com.jecp.capital.buyer.service.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.capital.buyer.dao.BuyerAccountHisDao;
import com.jecp.capital.buyer.model.CapitalAccountHis;

@Service
public class BuyerAccountHisServiceImpl extends BaseServiceImpl<CapitalAccountHis>{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	
	@Autowired
	private BuyerAccountHisDao capitalAccountHisDao;
	/**
	 * 根据全局ID查询
	 * @param getByGtid
	 */
	public CapitalAccountHis getByGtid(Long gtid) {
		return capitalAccountHisDao.getByGtid(gtid);
	}
	
	/*set、get*/
	@Override
	@Autowired
	public void setBasedao(BaseDao<CapitalAccountHis> basedao) {		
		super.setBasedao(basedao);
	}
}
