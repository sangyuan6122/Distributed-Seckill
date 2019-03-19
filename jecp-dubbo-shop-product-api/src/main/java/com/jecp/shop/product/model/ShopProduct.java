package com.jecp.shop.product.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Title 商品映射类 
 * @author WWT
 * @date 2018年6月7日
 */
public class ShopProduct implements java.io.Serializable {

	private String productId;
	private Integer categoryId;
	private String title;
	private String subtitle;
	private String pictureUrl;
	private BigDecimal price;
	private Integer stock;
	private String status;
	private String sellerId;
	private Date createTime;
	private Date updateTime;

	public ShopProduct() {
	}

	public ShopProduct(String productId) {
		this.productId = productId;
	}

	public ShopProduct(String productId, Integer categoryId, String title, String subtitle, String pictureUrl,
			BigDecimal price, Integer stock, String status, String sellerId, Date createTime, Date updateTime) {
		this.productId = productId;
		this.categoryId = categoryId;
		this.title = title;
		this.subtitle = subtitle;
		this.pictureUrl = pictureUrl;
		this.price = price;
		this.stock = stock;
		this.status = status;
		this.sellerId = sellerId;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getPictureUrl() {
		return this.pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
