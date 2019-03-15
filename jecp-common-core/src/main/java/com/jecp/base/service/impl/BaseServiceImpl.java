package com.jecp.base.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.model.ErrorMsgVO;
import com.jecp.base.page.PageQuery;
import com.jecp.base.service.BaseService;


public abstract class BaseServiceImpl<T> implements BaseService<T>{
	@Resource(name = "validator")
	private LocalValidatorFactoryBean localValidatorFactoryBean;
	@Autowired
	protected BaseDao<T> basedao;

	@Override
	public Serializable save(T entity) {		
		return basedao.save(entity);
	}

	@Override
	public void delete(Serializable id) {
		basedao.delete(id);
	}

	@Override
	public void deletes(Serializable[] ids) {
		basedao.deletes(ids);		
	}

	@Override
	public void update(T entity) {
		basedao.update(entity);
	}

	@Override
	public T getById(Serializable id) {		
		return (T) basedao.getById(id);
	}

	@Override
	public List<T> getByIds(Serializable[] ids) {		
		return basedao.getByIds(ids);
	}

	@Override
	public List<T> findAll() {		
		return basedao.findAll();
	}

	@Override
	public Long getCount(String hql, Object... objects) {		
		return basedao.getCount(hql, objects);
	}

	@Override
	public BigDecimal getCountSQL(String sql, Object... objects) {		
		return basedao.getCountSQL(sql, objects);
	}

	@Override
	public List<T> pageQueryHql(String hql, Integer beginIndex, Integer size,
			Object... objects) {		
		return basedao.pageQueryHql(hql, beginIndex, size, objects);
	}
	public long queryCountByHql(String hql,Object[] params) {
		return basedao.queryCountByHql(hql, params);
	}
	public List<T> queryByHql(String hql,PageQuery paginationQuery,Object[] params){
		return basedao.queryByHql(hql, paginationQuery, params);
	}
	@Override
	public List<T> pageQueryOrderHql(String hql,Integer beginIndex,Integer size,String sort[],String[] order,Object...objects){
		return basedao.pageQueryOrderHql(hql, beginIndex, size, sort, order, objects);
	}
	@Override
	public List<T> pageQuerySql(String hql, Integer beginIndex, Integer size,
			Object... objects) {		
		return basedao.pageQuerySql(hql, beginIndex, size, objects);
	}

	@Override
	public String getSheetID(String sheetType, String sheetKey) {
		return basedao.getSheetID(sheetType, sheetKey);
	}
	@Override
	public Timestamp getCurrentDBTime() {
		return basedao.getCurrentDBTime();
	}
	@Override
	public ErrorMsgVO validator(String key, T t) {
		Validator validator = localValidatorFactoryBean.getValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
		ErrorMsgVO errorMsgVO = new ErrorMsgVO();
		if (constraintViolations.size() == 0) {
			errorMsgVO.setResult(true);
		} else {
			errorMsgVO.setResult(false);
		}
		errorMsgVO.setId(key);
		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			if(errorMsgVO.getErrdesc()!=null&&!"".equals(errorMsgVO.getErrdesc())) {
				errorMsgVO.setErrdesc(errorMsgVO.getErrdesc()+","+constraintViolation.getPropertyPath().toString() + constraintViolation.getMessage());
			}else {
				errorMsgVO.setErrdesc(constraintViolation.getPropertyPath().toString() + constraintViolation.getMessage());
			}			
		}
		return errorMsgVO;
	}
	/*get、set*/
	public BaseDao<T> getBasedao() {
		return basedao;
	}

	public void setBasedao(BaseDao<T> basedao) {
		this.basedao = basedao;
	}

	

}
