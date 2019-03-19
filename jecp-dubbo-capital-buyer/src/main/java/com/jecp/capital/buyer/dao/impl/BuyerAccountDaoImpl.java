package com.jecp.capital.buyer.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LockOptions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.base.page.PageQuery;
import com.jecp.capital.buyer.dao.BuyerAccountDao;
import com.jecp.capital.buyer.model.CapitalAccount;

@Repository
public class BuyerAccountDaoImpl extends BaseDaoImpl<CapitalAccount> implements BuyerAccountDao{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Override
	public CapitalAccount getByUserid(String userid, Boolean isPessimisticLocking) {
		log.info("根据用户{}查询,是否加锁{}",userid,isPessimisticLocking);
		Query query=getSession().createQuery("from CapitalAccount where user_id=:userid");
		query.setParameter("userid", userid);
		if(isPessimisticLocking) {
			query.setLockOptions(LockOptions.UPGRADE);
		}		
		return (CapitalAccount) query.uniqueResult();
	}

	@Override
	public int tryDebit(String userid,BigDecimal tradeAmount) {
		Query query=getSession().createQuery("update CapitalAccount set balance=balance-:tradeAmount,balance_frozen=balance_frozen+:tradeAmount where user_id=:userid and balance>=:tradeAmount");
		query.setParameter("userid", userid);
		query.setParameter("tradeAmount", tradeAmount);
		return query.executeUpdate();
	}

	@Override
	public int confirmDebit(String userid,BigDecimal tradeAmount) {
		Query query=getSession().createQuery("update CapitalAccount set balance_frozen=balance_frozen-:tradeAmount where user_id=:userid");
		query.setParameter("userid", userid);
		query.setParameter("tradeAmount", tradeAmount);
		return query.executeUpdate();
	}

	@Override
	public int cancelDebit(String userid,BigDecimal tradeAmount) {
		Query query=getSession().createQuery("update CapitalAccount set balance=balance+:tradeAmount,balance_frozen=balance_frozen-:tradeAmount where user_id=:userid");
		query.setParameter("userid", userid);
		query.setParameter("tradeAmount", tradeAmount);
		return query.executeUpdate();
	}

	@Override
	public Long queryCount() {
		List<Serializable> params = new ArrayList<>();
		Long count = super.queryCountByHql("select count(*) from CapitalAccount", params.toArray());
		return count;
	}

	@Override
	public List<CapitalAccount> list(PageQuery pageQuery) {
		List<Serializable> params = new ArrayList<>();
		List<CapitalAccount> list=super.queryByHql("from CapitalAccount", pageQuery, params.toArray());
		return list;
	}
	
}
