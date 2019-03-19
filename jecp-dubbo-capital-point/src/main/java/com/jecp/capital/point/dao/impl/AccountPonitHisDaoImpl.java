package com.jecp.capital.point.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.capital.point.dao.AccountPointHisDao;
import com.jecp.capital.point.model.AccountPointHis;

@Repository
public class AccountPonitHisDaoImpl extends BaseDaoImpl<AccountPointHis> implements AccountPointHisDao{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Override
	public AccountPointHis getByGtid(Long gtid) {
		Query query=getSession().createQuery("from AccountPointHis where gtid=:gtid");
		query.setParameter("gtid", gtid);
		return (AccountPointHis) query.uniqueResult();
	}

	
	
}
