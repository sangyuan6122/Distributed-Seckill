package com.jecp.capital.point.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.page.PageQuery;
import com.jecp.capital.point.model.AccountPoint;

public interface AccountPointDao extends BaseDao<AccountPoint>{
	/**
	 * 根据用户ID进行查询
	 * @param userid
	 * @return
	 */
	public AccountPoint getByUserid(String userid);
	/**
	 * 根据用户ID累计积分
	 * @param userid
	 * @param point
	 */
	public void accumulatePointsByUserid(String userid,Long point);
	/**
	 * 列表总数
	 * @return
	 */
	public Long queryCount();
	/**
	 * 列表
	 * @param pageQuery
	 * @return
	 */
	public List<AccountPoint> list(PageQuery pageQuery);
}
