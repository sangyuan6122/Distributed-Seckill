package com.jecp.message.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.base.page.PageQuery;
import com.jecp.message.dao.ReliableMessageDao;
import com.jecp.message.dto.MessageStatusStatisticsDTO;
import com.jecp.message.model.ReliableMessage;

@Repository
public class ReliableMessageDaoImpl extends BaseDaoImpl<ReliableMessage> implements ReliableMessageDao {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Override
	public ReliableMessage getByMessageKey(String messageKey) {
		Query query = getSession().createQuery("from ReliableMessage where message_key=:messageKey");
		query.setParameter("messageKey", messageKey);
		return (ReliableMessage) query.uniqueResult();
	}

	@Override
	public void deleteByMessageKey(String messageKey) {
		Query query = getSession().createQuery("delete from ReliableMessage where message_key=:messageKey");
		query.setParameter("messageKey", messageKey);
		query.executeUpdate();		
	}

	@Override
	public void moveToHis(String messageKey) {
		Query query = getSession().createNativeQuery("insert into reliable_message_deleted select * from reliable_message where message_key=:messageKey");
		query.setParameter("messageKey", messageKey);
		query.executeUpdate();			
	}

	@Override
	public long queryCount(String status,Integer already_dead, String beforeTime) {
		StringBuilder sql=new StringBuilder();
		List<Serializable> params=new ArrayList<>();
		sql.append("select count(*) from ReliableMessage where 1=1");
		if(status!=null) {
			params.add(status);
			sql.append(" and status=?");
		}
		if(already_dead!=null) {
			params.add(already_dead);
			sql.append(" and already_dead=?");
		}
		if(beforeTime!=null) {
			params.add(beforeTime);
			sql.append(" and last_update_time<to_date(?,'yyyy-mm-dd HH24:mi:ss')");
		}
		long count = super.queryCountByHql(sql.toString(),params.toArray());
					
		return count;
	}

	@Override
	public List<ReliableMessage> list(String status,Integer already_dead, String beforeTime, PageQuery pageQuery) {
		StringBuilder sql=new StringBuilder();
		List<Serializable> params=new ArrayList<>();
		sql.append("from ReliableMessage where 1=1");
		if(status!=null) {
			params.add(status);
			sql.append(" and status=?");
		}
		if(already_dead!=null) {
			params.add(already_dead);
			sql.append(" and already_dead=?");
		}
		if(beforeTime!=null) {
			params.add(beforeTime);
			sql.append(" and last_update_time<to_date(?,'yyyy-mm-dd HH24:mi:ss')");
		}
		List<ReliableMessage> list= super.queryByHql(sql.toString(),pageQuery,params.toArray());
					
		return list;
	}

	@Override
	public MessageStatusStatisticsDTO getStatusStatistics() {
		Query query = getSession().createNativeQuery("select status as \"status\",count(*) as \"total\" from reliable_message where deleted=0 group by status union all select 'ALREADY_DEAD',count(*) from reliable_message where already_dead=1  ");
		List<Object[]> list=query.list();
		MessageStatusStatisticsDTO messageStatusStatisticsDTO=new MessageStatusStatisticsDTO();
		for(Object[] objs:list) {
			String status=(String) objs[0];
			int total=((BigDecimal) objs[1]).intValue();
			if("UNCONFIRMED".equals(status)) {
				messageStatusStatisticsDTO.setUnfirmed(total);
			}else if("UNCONSUMED".equals(status)) {
				messageStatusStatisticsDTO.setUnconsumed(total);
			}else if("ALREADY_DEAD".equals(status)) {
				messageStatusStatisticsDTO.setAlreadyDead(total);
			}
		}
		return messageStatusStatisticsDTO;
	}

}
