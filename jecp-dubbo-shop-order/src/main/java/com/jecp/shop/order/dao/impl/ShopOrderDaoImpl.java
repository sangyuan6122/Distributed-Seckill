package com.jecp.shop.order.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.base.page.PageQuery;
import com.jecp.shop.order.dao.ShopOrderDao;
import com.jecp.shop.order.model.ShopOrder;

@Repository
public class ShopOrderDaoImpl extends BaseDaoImpl<ShopOrder> implements ShopOrderDao {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Override
	public ShopOrder getByGtid(Long gtid) {
		Query query = getSession().createQuery("from ShopOrder where gtid=:gtid");
		query.setParameter("gtid", gtid);
		return (ShopOrder) query.uniqueResult();
	}
	@Override
	public int updateStatus(Long gtid, String status) {
		Query query = getSession().createQuery("update ShopOrder set status=:status where gtid=:gtid");
		query.setParameter("status", status);
		query.setParameter("gtid", gtid);
		return query.executeUpdate();
	}

	@Override
	public int deleteByGtid(Long gtid) {
		Query query = getSession().createQuery("delete from ShopOrder where gtid=:gtid");
		query.setParameter("gtid", gtid);
		return query.executeUpdate();
	}

	@Override
	public List<ShopOrder> list(String userId,PageQuery pageQuery) {
		pageQuery.setKey("orderId");	
		if("admin".equals(userId)) {
			return super.queryByHql("from ShopOrder",pageQuery,null);
		}else {
			Object[] params= {userId};
			return super.queryByHql("from ShopOrder where user_id=?", pageQuery, params);
		}		
	}

	@Override
	public long getCount(String userId,PageQuery pageQuery) {	
		if("admin".equals(userId)) {
			return super.queryCountByHql("select count(*) from ShopOrder", null);
		}else {
			Object[] params= {userId};
			return super.queryCountByHql("select count(*) from ShopOrder where user_id=?", params);
		}	
		
	}

	

}
