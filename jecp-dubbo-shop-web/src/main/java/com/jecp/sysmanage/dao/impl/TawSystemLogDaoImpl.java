package com.jecp.sysmanage.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.sysmanage.dao.TawSystemLogDao;
import com.jecp.sysmanage.model.TawSystemLog;
import com.jecp.sysmanage.vo.TawSystemLogQueryVO;
@Repository(value="tawSystemLogDao")
public class TawSystemLogDaoImpl  extends BaseDaoImpl<TawSystemLog> implements TawSystemLogDao{

	@Override
	public List<TawSystemLog> query(TawSystemLogQueryVO vo,
			Integer beginIndex, Integer size, String[] sort, String[] order) {
		//多字段排序
		String sortSql=" order by ";
		if(sort!=null&&order!=null){
			for(int i=0;i<sort.length;i++){
				sortSql=sortSql+sort[i]+" "+order[i]+",";
			}
		}else{
			sortSql=sortSql+" l.time desc,";
		}
		sortSql=sortSql+"l.id";
		//查询条件
		String whereSql="where 1=1 ";
		if(vo.getModule()!=null &&!"".equals(vo.getModule()) ){
			whereSql=whereSql+" and l.module ='"+vo.getModule()+"'";
		}
		if(vo.getBusinessid()!=null &&!"".equals(vo.getBusinessid()) ){
			whereSql=whereSql+" and l.businessid ='"+vo.getBusinessid()+"'";
		}
		if(vo.getType()!=null &&!"".equals(vo.getType()) ){
			whereSql=whereSql+" and l.type ='"+vo.getType()+"'";
		}
		if(vo.getBegintime()!=null&&vo.getEndtime()!=null
				&&!"".equals(vo.getBegintime())&&!"".equals(vo.getEndtime())){
			whereSql=whereSql+" and l.time>=to_date('"+vo.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') "
					+ "and l.time<to_date('"+vo.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')";
		}
		Query query = getSession().createQuery("from TawSystemLog l "+whereSql+sortSql);
		if(beginIndex!=null&&size!=null){
			query.setFirstResult((beginIndex-1)*size).setMaxResults(size);
		}
		
		return query.list();
	}

	@Override
	public Long queryCount(TawSystemLogQueryVO vo) {
		//查询条件
		String whereSql="where 1=1 ";
		if(vo.getModule()!=null &&!"".equals(vo.getModule()) ){
			whereSql=whereSql+" and l.module ='"+vo.getModule()+"'";
		}
		if(vo.getBusinessid()!=null &&!"".equals(vo.getBusinessid()) ){
			whereSql=whereSql+" and l.businessid ='"+vo.getBusinessid()+"'";
		}
		if(vo.getType()!=null &&!"".equals(vo.getType()) ){
			whereSql=whereSql+" and l.type ='"+vo.getType()+"'";
		}
		if(vo.getBegintime()!=null&&vo.getEndtime()!=null
				&&!"".equals(vo.getBegintime())&&!"".equals(vo.getEndtime())){
			whereSql=whereSql+" and l.time>=to_date('"+vo.getBegintime()+"','yyyy-mm-dd hh24:mi:ss') "
					+ "and l.time<to_date('"+vo.getEndtime()+"','yyyy-mm-dd hh24:mi:ss')";
		}
		Query query = getSession().createQuery("select count(*) from TawSystemLog l "+whereSql);		
		return (Long) query.uniqueResult();
	}

}
