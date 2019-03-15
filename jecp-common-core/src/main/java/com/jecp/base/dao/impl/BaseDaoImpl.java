package com.jecp.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.model.SheetSeq;
import com.jecp.base.page.PageQuery;

@SuppressWarnings("unchecked")
@Transactional
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	@Resource
	private SessionFactory sessionFactory;
	protected Session session;
	private Class<T> clazz;

	public BaseDaoImpl() {
		// 使用反射技术得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass(); // 获取当前new的对象的 泛型的父类 类型
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型
	}
	

	/**
	 * 获取当前可用的Session
	 * 
	 * @return
	 */
	@Transactional
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}	
	/**
	 * 获取新的Session
	 * 
	 * @return
	 */
	@Transactional
	protected Session openSession() {
		return sessionFactory.openSession();
	}
	/**
	 * 混合事务框架sql执行顺序问题
	 */
	@Transactional
	public void flushSession() {
		getSession().flush();
	}
	/**
	 * 增加对象
	 */
	public Serializable save(T entity) {
		return getSession().save(entity);
	}
	/**
	 * 更新对象
	 */
	public void update(T entity) {
		getSession().update(entity);
	}
	/**
	 * 根据主键删除对象
	 */
	public void delete(Serializable id) {
		Object obj = getById(id);
		if (obj != null) {
			getSession().delete(obj);
		}
	}
	/**
	 * 根据主键集合删除对象
	 */
	public void deletes(Serializable[] ids) {
		getSession().createQuery("delete from "+clazz.getSimpleName()+" WHERE id IN (:ids)")
		.setParameterList("ids", ids).executeUpdate();
		
	}
	/**
	 * 根据主键查找对象
	 */
	public T getById(Serializable id) {
		return (T) getSession().get(clazz, id);
	}
	/**
	 * 根据主键集合查找对象
	 */
	public List<T> getByIds(Serializable[] ids) {
		return getSession().createQuery(//
				"FROM "+clazz.getSimpleName()+" WHERE id IN (:ids)")//
				.setParameterList("ids", ids)//
				.list();	
	}
	/**
	 * 查询对象表所有记录
	 */
	public List<T> findAll() {
		return getSession().createQuery(//
				"FROM " + clazz.getSimpleName())//
				.list();
	}
	/**
	 * 分页查询
	 */
	@Override
	public List<T> pageQueryHql(String hql,Integer beginIndex,Integer size,Object...objects){
		Query query =getSession().createQuery(hql);
		if(objects!=null){
			for(int i=0;i<objects.length;i++){
				query.setParameter(i, objects[i]);
			}
		}
		if(beginIndex!=null&&size!=null){
			query.setFirstResult((beginIndex-1)*size).setMaxResults(size);
		}
		List<T> list = query.list();
		return list;		
	}
	public long queryCountByHql(String hql,Object[] params){
		Query query =getSession().createQuery(hql);
		if(params!=null) {
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		return (long) query.uniqueResult();
	}
	@Override
	public List<T> queryByHql(String hql,PageQuery pageQuery,Object[] params){
		String sort[]=pageQuery.getSort();
		String[] order=pageQuery.getOrder();
		String sortSql=" order by ";
		if(sort!=null&&order!=null){			
			for(int i=0;i<sort.length;i++){
				sortSql=sortSql+sort[i]+" "+order[i]+",";						
			}			
		}
		hql=hql+sortSql+pageQuery.getKey();
		Query query =getSession().createQuery(hql);
		if(params!=null) {
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		if(pageQuery.getPage()!=null&&pageQuery.getRows()!=null){
			query.setFirstResult((pageQuery.getPage()-1)*pageQuery.getRows())
				.setMaxResults(pageQuery.getRows());
		}
		List<T> list = query.list();
		return list;		
	}
	public List<T> pageQueryOrderHql(String hql,Integer beginIndex,Integer size,String sort[],String[] order,Object...objects){
		
		String sortSql="";
		if(sort!=null&&order!=null){
			sortSql="order by ";
			for(int i=0;i<sort.length;i++){
				if(i==sort.length-1){
					sortSql=sortSql+sort[i]+" "+order[i];
				}else{
					sortSql=sortSql+sort[i]+" "+order[i]+",";
				}			
			}
			sortSql=sortSql+",id";
		}
		hql=hql+sortSql;
		Query query =getSession().createQuery(hql);
		if(objects!=null){
			for(int i=0;i<objects.length;i++){
				query.setParameter(i, objects[i]);
			}
		}
		if(beginIndex!=null&&size!=null){
			query.setFirstResult((beginIndex-1)*size).setMaxResults(size);
		}
		List<T> list = query.list();
		return list;
	}
	@Override
	public List<T> pageQuerySql(String sql,Integer beginIndex,Integer size,Object...objects){
		Query query =getSession().createNativeQuery(sql).addEntity(clazz);
		if(objects!=null){
			for(int i=0;i<objects.length;i++){
				query.setParameter(i, objects[i]);
			}
		}
		if(beginIndex!=null&&size!=null){
			query.setFirstResult((beginIndex-1)*size).setMaxResults(size);
		}
		List<T> list = query.list();
		return list;		
	}
	public Long getCount(String hql,Object...objects){
		Query query =getSession().createQuery(hql);
		if(objects!=null){
			for(int i=0;i<objects.length;i++){
				query.setParameter(i, objects[i]);
			}
		}
		return (Long) query.uniqueResult();
	}
	public BigDecimal getCountSQL(String sql,Object...objects){
		Query query =getSession().createNativeQuery(sql);
		if(objects!=null){
			for(int i=0;i<objects.length;i++){
				query.setParameter(i, objects[i]);
			}
		}
		return (BigDecimal) query.uniqueResult();
	}
	public String getSheetID(String sheetType,String sheetKey){
		StringBuffer sb= new StringBuffer();
		sb.append("update sheet_seq ");
		sb.append("set seq=(case when trunc(createtime,'dd')= trunc(sysdate, 'dd') then seq + 1 else 1 end) ");
		sb.append(",createtime=sysdate,sheetkey=?,sheetid=? || to_char(sysdate, 'yyyymmdd') ||'-'||");
		sb.append("lpad(case when trunc(createtime,'dd')= trunc(sysdate, 'dd') then seq + 1 else 1 end,'5','0')");		
		sb.append("where sheettype=?");
		Query query=getSession().createNativeQuery(sb.toString());
		query.setParameter(0, sheetKey);
		query.setParameter(1, "GSXT-"+sheetType+"-");
		query.setParameter(2, sheetType);
		query.executeUpdate();
		SheetSeq sheetSeq=(SheetSeq) getSession().get(SheetSeq.class, sheetKey);
		if(sheetSeq!=null){
			return sheetSeq.getSheetid();
		}
		return null;
	}
	public Timestamp getCurrentDBTime(){
		Query query=getSession().createNativeQuery("select to_char(systimestamp,'yyyy-mm-dd hh24:mi:ssxff3') from dual");
		String t=(String) query.uniqueResult();
		
		return Timestamp.valueOf(t);
	}
}
