package com.jecp.shop.order.service.impl;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.shop.order.dao.ShopOrderHisDao;
import com.jecp.shop.order.model.ShopOrderHis;

/**
 * @Title 订单历史服务
 * @author WWT
 * @date 2018年7月3日
 */
@Service
public class ShopOrderHisServiceImpl extends BaseServiceImpl<ShopOrderHis> {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private ShopOrderHisDao shopOrderHisDao;

	@PostConstruct
	private void init() {
		super.setBasedao(shopOrderHisDao);
	}

	/**
	 * 根据全局事务ID、状态查询
	 * 
	 * @param gtid
	 * @param status
	 * @return
	 */
	public ShopOrderHis getByGtidAndStatus(Long gtid, String status) {
		return shopOrderHisDao.getByGtidAndStatus(gtid, status);
	}
	/**
	 * 根据全局事务ID删除
	 * @param gtid
	 * @return
	 */
	public int deleteByGtid(Long gtid) {
		return shopOrderHisDao.deleteByGtid(gtid);
	}
}
