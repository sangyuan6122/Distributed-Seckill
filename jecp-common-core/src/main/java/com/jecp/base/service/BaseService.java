package com.jecp.base.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.jecp.base.model.ErrorMsgVO;
import com.jecp.base.page.PageQuery;

public interface BaseService<T>{
	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	public Serializable save(T entity);

	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	public void delete(Serializable id);
	/**
	 * 根据数组删除
	 * @param ids
	 */
	public void deletes(Serializable[] ids);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 按id查询
	 * 
	 * @param id
	 * @return
	 */
	public T getById(Serializable id);

	/**
	 * 按id查询
	 * 
	 * @param ids
	 * @return
	 */
	public List<T> getByIds(Serializable[] ids);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public List<T> findAll();
	/**
	 * 获得Count(hql)
	 * @param hql
	 * @param objects
	 * @return
	 */
	@Deprecated
	public Long getCount(String hql,Object...objects);
	/**
	 * 获得Count(sql)
	 * @param sql
	 * @param objects
	 * @return
	 */
	@Deprecated
	public BigDecimal getCountSQL(String sql,Object...objects);
	/**
	 * 分页查询HQL
	 * @param beginIndex
	 * @param size
	 * @param objects
	 * @return
	 */
	@Deprecated
	public List<T> pageQueryHql(String hql,Integer beginIndex,Integer size,Object...objects);
	/**
	 * 查询总量
	 * @param hql
	 * @param params
	 * @return
	 */
	public long queryCountByHql(String hql,Object[] params);
	/**
	 * 分页查询Hql
	 * @param hql
	 * @param paginationQuery
	 * @param params
	 * @return
	 */
	public List<T> queryByHql(String hql,PageQuery paginationQuery,Object[] params);
	/**
	 * 分页查询HQL带排序功能
	 * @param hql
	 * @param beginIndex
	 * @param size
	 * @param sort
	 * @param order
	 * @param objects
	 * @return
	 */
	public List<T> pageQueryOrderHql(String hql,Integer beginIndex,Integer size,String sort[],String[] order,Object...objects);
	/**
	 * 分页查询SQL
	 * @param hql
	 * @param beginIndex
	 * @param size
	 * @param objects
	 * @return
	 */
	public List<T> pageQuerySql(String hql,Integer beginIndex,Integer size,Object...objects);
	/**
	 * 获得工单号
	 * @param sheetType
	 * @return
	 */
	public String getSheetID(String sheetType,String sheetKey);
	/**
	 * 获得当前数据库时间
	 * @return
	 */
	public Timestamp getCurrentDBTime();
	
	/**
	 * 校验
	 * @param key
	 * @param t
	 * @return
	 */
	public ErrorMsgVO validator(String key,T t);
}
