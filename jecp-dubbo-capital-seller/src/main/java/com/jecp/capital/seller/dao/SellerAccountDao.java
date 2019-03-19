package com.jecp.capital.seller.dao;

import java.math.BigDecimal;
import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.page.PageQuery;
import com.jecp.capital.seller.model.CapitalAccount;

public interface SellerAccountDao extends BaseDao<CapitalAccount>{
	/**
	 * 根据用户ID获得账户资金信息
	 * @param userid(用户ID)
	 * @param isPessimisticLocking(是否加悲观锁)
	 * @return
	 */
	public CapitalAccount getByUserid(String userid,Boolean isPessimisticLocking);
	
	/**
	 * 资金转入
	 * @param userid(用户ID)
	 * @param tradeAmount
	 * @return
	 */
	public int credit(String userid,BigDecimal tradeAmount);
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
