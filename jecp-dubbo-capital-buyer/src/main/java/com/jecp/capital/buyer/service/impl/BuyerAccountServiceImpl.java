package com.jecp.capital.buyer.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.base.page.PageBean;
import com.jecp.base.page.PageQuery;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.capital.buyer.dao.BuyerAccountDao;
import com.jecp.capital.buyer.dto.BuyerAccountListDTO;
import com.jecp.capital.buyer.model.CapitalAccount;

@Service
public class BuyerAccountServiceImpl extends BaseServiceImpl<CapitalAccount> {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private BuyerAccountDao buyerAccountDao;
	@PostConstruct
	private void init() {
		super.setBasedao(buyerAccountDao);
	}
	/**
	 * 根据用户ID获得账户资金信息
	 * 
	 * @param userid(用户ID)
	 * @param isPessimisticLocking(是否加悲观锁)
	 * @return
	 */
	public CapitalAccount getByUserid(String userid, Boolean isPessimisticLocking) {
		return buyerAccountDao.getByUserid(userid, isPessimisticLocking);
	}
	/**
	 * 尝试余额转出
	 * @param userid(用户ID)
	 * @param tradeAmount
	 * @return
	 */
	public int tryDebit(String userid,BigDecimal tradeAmount) {
		return buyerAccountDao.tryDebit(userid, tradeAmount);
	}
	/**
	 * 确认余额转出
	 * @param userid(用户ID)
	 * @param tradeAmount
	 * @return
	 */
	public int confirmDebit(String userid,BigDecimal tradeAmount){
		return buyerAccountDao.confirmDebit(userid, tradeAmount);
	}
	/**
	 * 取消余额转出
	 * @param userid(用户ID)
	 * @param tradeAmount
	 * @return
	 */
	public int cancelDebit(String userid,BigDecimal tradeAmount){
		return buyerAccountDao.cancelDebit(userid, tradeAmount);
	}
	/**
	 * 列表
	 * @param pageQuery
	 * @return
	 */
	public PageBean<BuyerAccountListDTO> list(PageQuery pageQuery) {
		Long count=buyerAccountDao.queryCount();
		List<CapitalAccount> buyerAccountList=buyerAccountDao.list(pageQuery);
		List<BuyerAccountListDTO> dtoList=new ArrayList<>();
		for(CapitalAccount capitalAccount:buyerAccountList) {
			dtoList.add(capitalAccount.convertToBuyerAccountListDTO());
		}
		PageBean<BuyerAccountListDTO> pageBean=new PageBean<BuyerAccountListDTO>(dtoList,count);
		return pageBean;
	}

}
