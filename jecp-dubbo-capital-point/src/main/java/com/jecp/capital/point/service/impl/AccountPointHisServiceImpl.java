package com.jecp.capital.point.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.capital.point.dao.AccountPointDao;
import com.jecp.capital.point.dao.AccountPointHisDao;
import com.jecp.capital.point.model.AccountPoint;
import com.jecp.capital.point.model.AccountPointHis;

/**
 * @功能 账户积分服务
 * @作者 WWT
 * @修改时间 2018年7月2日
 */
@Service
public class AccountPointHisServiceImpl extends BaseServiceImpl<AccountPointHis> {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private AccountPointHisDao accountPointHisDao;
	@Autowired
	private AccountPointDao accountPointDao;

	/**
	 * 会员积累积分
	 * @param accountPointHis
	 */
	@Transactional
	public void accumulatePoints(AccountPointHis accountPointHis) {
		/** 保存积分历史 **/
		accountPointHis.initcreateTime();
		accountPointHisDao.save(accountPointHis);

		/** 会员积分累加 **/
		AccountPoint foundAccountPoint = accountPointDao.getByUserid(accountPointHis.getUserId());
		log.info("会员增加积分===>{}", foundAccountPoint);
		Long point = accountPointHis.getTradeAmount().longValue();
		if (foundAccountPoint == null) {
			AccountPoint accountPoint=accountPointHis.convertToAccountPoint();
			accountPoint.initPoint();
			accountPoint.addPoints(point);
			accountPointDao.save(accountPoint);
		} else {
			accountPointDao.accumulatePointsByUserid(accountPointHis.getUserId(), point);
		}
	}

	/**
	 * 根据全局ID获得
	 * @param gtid
	 * @return
	 */
	public AccountPointHis getByGtid(Long gtid) {
		return accountPointHisDao.getByGtid(gtid);
	}

	/* set、get */
	@Override
	@Autowired
	public void setBasedao(BaseDao<AccountPointHis> basedao) {
		super.setBasedao(basedao);
	}

}
