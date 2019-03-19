package com.jecp.shop.product.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Title 秒杀商品映射类 
 * @author WWT
 * @date 2018年6月7日
 */
public class ShopProductSeckill implements java.io.Serializable {

	private String seckillId;
	private String productId;
	private BigDecimal seckillPrice;
	private Integer seckillStock;
	private String sellerId;
	private Date startTime;
	private Date endTime;
	private Date createTime;
	private String codeStrategy;

	public ShopProductSeckill() {
	}

	public ShopProductSeckill(String seckillId, String productId) {
		this.seckillId = seckillId;
		this.productId = productId;
	}

	public ShopProductSeckill(String seckillId, String productId, BigDecimal seckillPrice, Integer seckillStock,
			String sellerId, Date startTime, Date endTime, Date createTime, String codeStrategy) {
		this.seckillId = seckillId;
		this.productId = productId;
		this.seckillPrice = seckillPrice;
		this.seckillStock = seckillStock;
		this.sellerId = sellerId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.codeStrategy = codeStrategy;
	}

	public String getSeckillId() {
		return this.seckillId;
	}

	public void setSeckillId(String seckillId) {
		this.seckillId = seckillId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getSeckillPrice() {
		return this.seckillPrice;
	}

	public void setSeckillPrice(BigDecimal seckillPrice) {
		this.seckillPrice = seckillPrice;
	}

	public Integer getSeckillStock() {
		return this.seckillStock;
	}

	public void setSeckillStock(Integer seckillStock) {
		this.seckillStock = seckillStock;
	}

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCodeStrategy() {
		return this.codeStrategy;
	}

	public void setCodeStrategy(String codeStrategy) {
		this.codeStrategy = codeStrategy;
	}

}
