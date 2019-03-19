package com.jecp.shop.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import com.jecp.common.util.BeanConvert;
import com.jecp.shop.order.model.ShopOrder;
import com.jecp.shop.order.model.ShopOrderHis;

public class ShopOrderAddDTO implements Serializable {

	private static final long serialVersionUID = -3583926593671112091L;
	/* 全局事务ID */
	@NotNull
	private Long gtid;
	/* 商品ID */
	@NotNull
	private String productId;
	/* 商品名称 */
	@NotNull
	private String productTitle;
	/* 商品数量 */
	@NotNull
	private Long productCount;
	/* 商品价格 */
	@NotNull
	private BigDecimal productPrice;
	/* 用户ID */
	private String userId;
	/* 促销方式(0正常销售,1秒杀,2团购) */
	private Integer salesWay;
	/* 订单渠道(0PC,1ANDROID,2IOS) */
	private Integer orderChannel;
	/* 秒杀ID */
	private String seckillId;

	public Long getGtid() {
		return gtid;
	}

	public void setGtid(Long gtid) {
		this.gtid = gtid;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public Long getProductCount() {
		return productCount;
	}

	public void setProductCount(Long productCount) {
		this.productCount = productCount;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSalesWay() {
		return salesWay;
	}

	public void setSalesWay(Integer salesWay) {
		this.salesWay = salesWay;
	}

	public Integer getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(Integer orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(String seckillId) {
		this.seckillId = seckillId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public ShopOrder convertToShopOrder() {
		ShopOrderAddDTOConvert shopOrderAddDTOConvert = new ShopOrderAddDTOConvert();
		ShopOrder shopOrder = shopOrderAddDTOConvert.convert(this);
		return shopOrder;
	}

	public ShopOrderHis convertToShopOrderHis(String orderId) {
		ShopOrderAddDTOConvert shopOrderAddDTOConvert = new ShopOrderAddDTOConvert();
		ShopOrderHis shopOrderHis = shopOrderAddDTOConvert.convertToShopOrderHis(this);
		shopOrderHis.setOrderId(orderId);
		return shopOrderHis;
	}

	private static class ShopOrderAddDTOConvert implements BeanConvert<ShopOrderAddDTO, ShopOrder> {
		@Override
		public ShopOrder convert(ShopOrderAddDTO shopOrderAddDTO) {
			ShopOrder shopOrder = new ShopOrder();
			BeanUtils.copyProperties(shopOrderAddDTO, shopOrder);
			return shopOrder;
		}

		public ShopOrderHis convertToShopOrderHis(ShopOrderAddDTO shopOrderAddDTO) {
			ShopOrderHis shopOrderHis = new ShopOrderHis();
			BeanUtils.copyProperties(shopOrderAddDTO, shopOrderHis);
			return shopOrderHis;
		}
	}
}
