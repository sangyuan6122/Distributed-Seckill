package com.jecp.shop.order.model;


import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jecp.common.util.BeanConvert;
import com.jecp.shop.order.dto.ShopOrderListDTO;
import com.jecp.shop.order.enums.ShopOrderStatusEnum;


public class ShopOrder implements java.io.Serializable {

	private String orderId;
	private Long gtid;
	private String status;
	private String productId;
	private String productTitle;
	private Long productCount;
	private BigDecimal productPrice;
	private BigDecimal totalAmount;
	private String userId;
	private Integer salesWay;
	private Integer orderChannel;
	private Date createTime;
	private Date lastUpdateTime;
	private Date payTime;
	private String seckillId;
	
	public ShopOrder() {
	}

	public ShopOrder(String orderId) {
		this.orderId = orderId;
	}

	public ShopOrder(String orderId, Long gtid, String status, String productId, String productTitle, Long productCount,
			BigDecimal productPrice, BigDecimal totalAmount, String userId, Integer salesWay, Integer orderChannel,
			Date createTime, Date lastUpdateTime, Date payTime, String seckillId) {
		this.orderId = orderId;
		this.gtid = gtid;
		this.status = status;
		this.productId = productId;
		this.productTitle = productTitle;
		this.productCount = productCount;
		this.productPrice = productPrice;
		this.totalAmount = totalAmount;
		this.userId = userId;
		this.salesWay = salesWay;
		this.orderChannel = orderChannel;
		this.createTime = createTime;
		this.lastUpdateTime = lastUpdateTime;
		this.payTime = payTime;
		this.seckillId = seckillId;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getGtid() {
		return this.gtid;
	}

	public void setGtid(Long gtid) {
		this.gtid = gtid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductTitle() {
		return this.productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public Long getProductCount() {
		return this.productCount;
	}

	public void setProductCount(Long productCount) {
		this.productCount = productCount;
	}

	public BigDecimal getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSalesWay() {
		return this.salesWay;
	}

	public void setSalesWay(Integer salesWay) {
		this.salesWay = salesWay;
	}

	public Integer getOrderChannel() {
		return this.orderChannel;
	}

	public void setOrderChannel(Integer orderChannel) {
		this.orderChannel = orderChannel;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getSeckillId() {
		return this.seckillId;
	}

	public void setSeckillId(String seckillId) {
		this.seckillId = seckillId;
	}
	/**
	 * 计算总金额
	 */
	public void totalAmountCalculate() {
		this.totalAmount=this.productPrice.multiply(new BigDecimal(this.productCount));				
	}
	/**
	 * 创建失败
	 */
	public void failed() {		
		this.status=ShopOrderStatusEnum.CREATE_FAILED.name();
		this.lastUpdateTime=new Date();
	}
	/**
	 * 草稿
	 */
	public void draft() {		
		this.status=ShopOrderStatusEnum.DRAFT.name();
		this.createTime=new Date();
	}
	/**
	 * 待付款
	 */
	public void unPaid() {
		this.status=ShopOrderStatusEnum.UN_PAID.name();
		this.lastUpdateTime=new Date();
	}
	/**
	 * 已完成
	 */
	public void completed() {
		this.status=ShopOrderStatusEnum.COMPLETED.name();
		this.payTime=new Date();
		this.lastUpdateTime=new Date();
	}
	/**
	 * 订单记录列表转换
	 * @return
	 */
	public ShopOrderListDTO convertTo() {
		ShopOrderConvert shopOrderConvert=new ShopOrderConvert();
		ShopOrderListDTO shopOrderListDTO=shopOrderConvert.convert(this);
		return shopOrderListDTO;
	}
	private static class ShopOrderConvert implements BeanConvert<ShopOrder, ShopOrderListDTO> {
		@Override
		public ShopOrderListDTO convert(ShopOrder shopOrder) {
			ShopOrderListDTO shopOrderListDTO = new ShopOrderListDTO();
			BeanUtils.copyProperties(shopOrder, shopOrderListDTO);	
			shopOrderListDTO.setGtid(String.valueOf(shopOrder.getGtid()));
			return shopOrderListDTO;
		}
	}
}
