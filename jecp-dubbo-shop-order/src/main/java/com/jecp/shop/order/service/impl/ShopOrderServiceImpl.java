package com.jecp.shop.order.service.impl;

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
import com.jecp.shop.order.dao.ShopOrderDao;
import com.jecp.shop.order.dto.ShopOrderListDTO;
import com.jecp.shop.order.enums.ShopOrderChannelEnum;
import com.jecp.shop.order.enums.ShopOrderSalesWayEnum;
import com.jecp.shop.order.enums.ShopOrderStatusEnum;
import com.jecp.shop.order.model.ShopOrder;

/**
 * @Title 订单服务
 * @author WWT
 * @date 2018年7月3日
 */
@Service
public class ShopOrderServiceImpl extends BaseServiceImpl<ShopOrder>{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private ShopOrderDao shopOrderDao;
	
	@PostConstruct
	private void init() {
		super.setBasedao(shopOrderDao);
	}
	/**
	 * 根据全局ID获得
	 * @param gtid
	 * @return
	 */
	public ShopOrder getByGtid(Long gtid) {
		return shopOrderDao.getByGtid(gtid);
	}
	/**
	 * 修改订单状态
	 * @param gtid
	 * @param status
	 * @return
	 */
	public int updateStatus(Long gtid, String status) {
		return shopOrderDao.updateStatus(gtid, status);
	}
	/**
	 * 根据全局ID删除
	 * @param gtid
	 * @return
	 */
	public int deleteByGtid(Long gtid) {
		return shopOrderDao.deleteByGtid(gtid);
	}
	/**
	 * 列表查询
	 * @param pageQuery
	 * @return
	 */
	public PageBean<ShopOrderListDTO> list(String userId,PageQuery pageQuery){
		List<ShopOrder> orderList=shopOrderDao.list(userId,pageQuery);
		List<ShopOrderListDTO> orderDtoList=new ArrayList<>();
		for(ShopOrder shopOrder:orderList) {
			ShopOrderListDTO shopOrderListDTO=shopOrder.convertTo();
			shopOrderListDTO.setOrderChannel(ShopOrderChannelEnum.codeOfName(shopOrder.getOrderChannel()));
			shopOrderListDTO.setSalesWay(ShopOrderSalesWayEnum.codeOfName(shopOrder.getSalesWay()));
			shopOrderListDTO.setStatus(ShopOrderStatusEnum.getDesc(shopOrder.getStatus()));
			orderDtoList.add(shopOrderListDTO);
		}
		long count=shopOrderDao.getCount(userId, pageQuery);
		return new PageBean<ShopOrderListDTO>(orderDtoList,count);
	}
}
