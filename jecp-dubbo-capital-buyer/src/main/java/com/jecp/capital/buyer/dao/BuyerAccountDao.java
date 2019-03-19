package com.jecp.capital.buyer.dao;

import java.math.BigDecimal;
import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.page.PageQuery;
import com.jecp.capital.buyer.model.CapitalAccount;

public interface BuyerAccountDao extends BaseDao<CapitalAccount>{
	/**
	 * 根据用户ID获得账户资金信息
	 * @param userid(用户ID)
	 * @param isPessimisticLocking(是否加悲观锁)
	 * @return
	 */
	public CapitalAccount getByUserid(String userid,Boolean isPessimisticLocking);
	/**
	 * 尝试余额转出
	 * @param userid(用户ID)
	 * @param tradeAmount
	 * @return
	 */
	public int tryDebit(String userid,BigDecimal tradeAmount);
	/**
	 * 确认余额转出
	 * @param userid(用户ID)
	 * @param tradeAmount
	 * @return
	 */
	public int confirmDebit(String userid,BigDecimal tradeAmount);
	/**
	 * 取消余额转出
	 * @param userid(用户ID)
	 * @param tradeAmount
	 * @return
	 */
	public int cancelDebit(String userid,BigDecimal tradeAmount);
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
	public List<CapitalAccount> list(PageQuery pageQuery);
}
