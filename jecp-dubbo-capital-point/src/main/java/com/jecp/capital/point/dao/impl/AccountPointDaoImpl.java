package com.jecp.capital.point.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.base.page.PageQuery;
import com.jecp.capital.point.dao.AccountPointDao;
import com.jecp.capital.point.model.AccountPoint;

@Repository
public class AccountPointDaoImpl extends BaseDaoImpl<AccountPoint> implements AccountPointDao {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Override
	public AccountPoint getByUserid(String userid) {
		Query query = getSession().createQuery("from AccountPoint where user_id=:userid");
		query.setParameter("userid", userid);
		return (AccountPoint) query.uniqueResult();
	}

	@Override
	public void accumulatePointsByUserid(String userid, Long point) {	
		Query query = getSession().createQuery("update AccountPoint set total_points=total_points+:point where user_id=:userid");
		query.setParameter("point", point);
		query.setParameter("userid", userid);
		query.executeUpdate();
	}
	@Override
	public Long queryCount() {
		List<Serializable> params = new ArrayList<>();
		Long count = super.queryCountByHql("select count(*) from AccountPoint", params.toArray());
		return count;
	}

	@Override
	public List<AccountPoint> list(PageQuery pageQuery) {
		List<Serializable> params = new ArrayList<>();
		List<AccountPoint> list=super.queryByHql("from AccountPoint", pageQuery, params.toArray());
		return list;
	}
}
