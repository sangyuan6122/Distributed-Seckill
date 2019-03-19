package com.jecp.capital.seller.service.impl;

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
import com.jecp.capital.seller.dao.SellerAccountDao;
import com.jecp.capital.seller.dto.SellerAccountListDTO;
import com.jecp.capital.seller.model.CapitalAccount;

@Service
public class SellerAccountServiceImpl extends BaseServiceImpl<CapitalAccount> {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private SellerAccountDao sellerAccountDao;
	@PostConstruct
	private void init() {
		super.setBasedao(sellerAccountDao);
	}
	/**
	 * 根据用户ID获得账户资金信息
	 * 
	 * @param userid(用户ID)
	 * @param isPessimisticLocking(是否加悲观锁)
	 * @return
	 */
	public CapitalAccount getByUserid(String userid, Boolean isPessimisticLocking) {
		return sellerAccountDao.getByUserid(userid, isPessimisticLocking);
	}
	/**
	 * 资金转入
	 * @param userid
	 * @param tradeAmount
	 * @return
	 */
	public int credit(String userid, BigDecimal tradeAmount) {
		return sellerAccountDao.credit(userid, tradeAmount);
	}
	/**
	 * 列表
	 * @param pageQuery
	 * @return
	 */
	public PageBean<SellerAccountListDTO> list(PageQuery pageQuery) {
		Long count=sellerAccountDao.queryCount();
		List<CapitalAccount> buyerAccountList=sellerAccountDao.list(pageQuery);
		List<SellerAccountListDTO> dtoList=new ArrayList<>();
		for(CapitalAccount capitalAccount:buyerAccountList) {
			dtoList.add(capitalAccount.convertToSellerAccountListDTO());
		}
		PageBean<SellerAccountListDTO> pageBean=new PageBean<SellerAccountListDTO>(dtoList,count);
		return pageBean;
	}
}
