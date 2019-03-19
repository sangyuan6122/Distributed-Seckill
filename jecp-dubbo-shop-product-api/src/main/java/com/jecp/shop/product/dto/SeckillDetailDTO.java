package com.jecp.shop.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import com.jecp.common.util.BeanConvert;
import com.jecp.shop.order.dto.ShopOrderAddDTO;
import com.jecp.trade.record.dto.TradeRecordInputDTO;

/**
 * @Title 秒杀商品详情DTO
 * @author WWT
 * @date 2018年7月25日
 */
public class SeckillDetailDTO implements Serializable {
	private static final long serialVersionUID = -6129932594291420328L;
	/** 商品分类ID */
	private Integer categoryId;
	/** 商品标题 */
	private String title;
	/** 商品副标题 */
	private String subtitle;
	/** 商品图片 */
	private String pictureUrl;
	/** 卖家ID */
	private String sellerId;
	/** 秒杀商品ID */
	private String seckillId;
	/** 商品ID */
	private String productId;
	/** 秒杀价格 */
	private BigDecimal seckillPrice;
	/** 秒杀库存 */
	private Integer seckillStock;
	/** 秒杀开始时间 */
	private Date startTime;
	/** 秒杀结束时间 */
	private Date endTime;
	/** 创建秒杀时间 */
	private Date createTime;
	/** 当前时间 */
	private Date currentTime;
	/** 代码策略 */
	private String codeStrategy;

	public SeckillDetailDTO() {
		super();
		currentTime = new Date();
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Object categoryId) {
		if (categoryId instanceof BigDecimal) {
			this.categoryId = ((BigDecimal) categoryId).intValue();
		} else {
			this.categoryId = (Integer) categoryId;
		}		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(String seckillId) {
		this.seckillId = seckillId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getSeckillPrice() {
		return seckillPrice;
	}

	public void setSeckillPrice(BigDecimal seckillPrice) {
		this.seckillPrice = seckillPrice;
	}

	public Integer getSeckillStock() {
		return seckillStock;
	}

	public void setSeckillStock(Object seckillStock) {
		if (seckillStock instanceof BigDecimal) {
			this.seckillStock = ((BigDecimal) seckillStock).intValue();
		} else {
			this.seckillStock = (Integer) seckillStock;
		}
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
	
	public String getCodeStrategy() {
		return codeStrategy;
	}

	public void setCodeStrategy(String codeStrategy) {
		this.codeStrategy = codeStrategy;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * 计算总金额
	 */
	public BigDecimal totalAmountCalculate() {
		return this.seckillPrice.multiply(new BigDecimal(this.seckillStock));
	}

	/**
	 * 转换新增订单DTO
	 * 
	 * @param userId
	 * @return
	 */
	public ShopOrderAddDTO convertToShopOrderAddDTO(Long gtid, String userId,Long productCount) {
		ShopOrderAddDTOConvert shopOrderAddDTOConvert = new ShopOrderAddDTOConvert();
		ShopOrderAddDTO shopOrderAddDTO = shopOrderAddDTOConvert.convert(this);
		shopOrderAddDTO.setUserId(userId);
		shopOrderAddDTO.setGtid(gtid);
		shopOrderAddDTO.setProductCount(productCount);
		shopOrderAddDTO.setProductPrice(this.getSeckillPrice());
		return shopOrderAddDTO;
	}

	/**
	 * 转换交易记录
	 * 
	 * @param buyerId
	 * @return
	 */
	public TradeRecordInputDTO convertToTradeRecordInputDTO(long gtid, String buyerId) {
		TradeRecordInputDTOConvert tradeRecordInputDTOConvert = new TradeRecordInputDTOConvert();
		TradeRecordInputDTO tradeRecordInputDTO = tradeRecordInputDTOConvert.convert(this);
		tradeRecordInputDTO.setBuyerId(buyerId);
		tradeRecordInputDTO.setGtid(gtid);
		tradeRecordInputDTO.setTradeAmount(this.getSeckillPrice().doubleValue());		
		return tradeRecordInputDTO;
	}

	private static class ShopOrderAddDTOConvert implements BeanConvert<SeckillDetailDTO, ShopOrderAddDTO> {
		@Override
		public ShopOrderAddDTO convert(SeckillDetailDTO secKillDetailDTO) {
			ShopOrderAddDTO shopOrderAddDTO = new ShopOrderAddDTO();
			BeanUtils.copyProperties(secKillDetailDTO, shopOrderAddDTO);
			shopOrderAddDTO.setProductTitle(secKillDetailDTO.title);
			shopOrderAddDTO.setProductPrice(secKillDetailDTO.getSeckillPrice());
			return shopOrderAddDTO;
		}
	}

	private static class TradeRecordInputDTOConvert implements BeanConvert<SeckillDetailDTO, TradeRecordInputDTO> {

		@Override
		public TradeRecordInputDTO convert(SeckillDetailDTO secKillDetailDTO) {
			TradeRecordInputDTO tradeRecordInputDTO = new TradeRecordInputDTO();
			BeanUtils.copyProperties(secKillDetailDTO, tradeRecordInputDTO);
			tradeRecordInputDTO.setTradeAmount(secKillDetailDTO.totalAmountCalculate().doubleValue());
			return tradeRecordInputDTO;
		}

	}
}
