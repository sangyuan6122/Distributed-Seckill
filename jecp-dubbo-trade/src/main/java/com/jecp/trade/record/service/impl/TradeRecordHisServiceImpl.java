package com.jecp.trade.record.service.impl;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.trade.record.dao.TradeRecordHisDao;
import com.jecp.trade.record.model.TradeRecordHis;

/**
 * @Title 交易历史服务
 * @author WWT
 * @date 2018年6月21日
 */
@Service
public class TradeRecordHisServiceImpl extends BaseServiceImpl<TradeRecordHis> {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private TradeRecordHisDao tradeRecordHisDao;
	
	@PostConstruct
    private void init() {
        super.setBasedao(tradeRecordHisDao);
    }
	/**
	 * 根据全局事务ID、交易状态查询
	 * @param gtid
	 * @return
	 */
	public TradeRecordHis getByGtidAndStatus(Long gtid,String status) {		
		return tradeRecordHisDao.getByGtidAndStatus(gtid, status);		
	}
	/**
	 * 根据全局事务ID删除
	 * @param gtid
	 * @return
	 */
	public int deleteByGtid(Long gtid) {
		return tradeRecordHisDao.deleteByGtid(gtid);
	}
}
