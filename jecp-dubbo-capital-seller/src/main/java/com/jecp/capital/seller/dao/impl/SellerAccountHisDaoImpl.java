package com.jecp.capital.seller.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.capital.seller.dao.SellerAccountHisDao;
import com.jecp.capital.seller.model.CapitalAccountHis;

@Repository
public class SellerAccountHisDaoImpl extends BaseDaoImpl<CapitalAccountHis> implements SellerAccountHisDao{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Override
	public CapitalAccountHis getByGtid(Long gtid) {
		Query query=getSession().createQuery("from CapitalAccountHis where gtid=:gtid");
		query.setParameter("gtid", gtid);		
		return (CapitalAccountHis) query.uniqueResult();
	}
	
}
