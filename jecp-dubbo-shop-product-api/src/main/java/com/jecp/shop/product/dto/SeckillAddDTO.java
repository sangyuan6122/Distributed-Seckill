package com.jecp.shop.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import com.jecp.common.util.BeanConvert;
import com.jecp.shop.product.enums.SeckillStrategyEnum;
import com.jecp.shop.product.model.ShopProductSeckill;

/**
 * @Title 创建秒杀商品DTO
 * @author WWT
 * @date 2018年7月25日
 */
public class SeckillAddDTO implements Serializable {
	private static final long serialVersionUID = -5018126648961549486L;
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
	/** 代码策略 */
	private String codeStrategy;

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

	public void setSeckillStock(Integer seckillStock) {
		this.seckillStock = seckillStock;
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
	
	public String getCodeStrategy() {
		return codeStrategy;
	}

	public void setCodeStrategy(String codeStrategy) {
		SeckillStrategyEnum seckillStrategyEnum=SeckillStrategyEnum.valueOf(codeStrategy);
		this.codeStrategy = seckillStrategyEnum.name();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public ShopProductSeckill convertToShopProductSeckill() {
		SecKillAddInputDTOConvert secKillAddInputDTOConvert = new SecKillAddInputDTOConvert();
		return secKillAddInputDTOConvert.convert(this);
	}

	private static class SecKillAddInputDTOConvert implements BeanConvert<SeckillAddDTO, ShopProductSeckill> {
		@Override
		public ShopProductSeckill convert(SeckillAddDTO secKillAddInputDTO) {
			ShopProductSeckill shopProductSeckill = new ShopProductSeckill();
			BeanUtils.copyProperties(secKillAddInputDTO, shopProductSeckill);
			return shopProductSeckill;
		}
	}
	public static void main(String[] args) {
		SeckillStrategyEnum.valueOf("123");
	}
}
