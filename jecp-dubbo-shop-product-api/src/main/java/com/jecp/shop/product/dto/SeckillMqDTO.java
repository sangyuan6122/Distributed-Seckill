package com.jecp.shop.product.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @Title 秒杀成功消息
 * @author WWT
 * @date 2018年6月7日
 */
public class SeckillMqDTO implements Serializable {
	private static final long serialVersionUID = -8266595067131682794L;
	/** 用户ID */
	private String userId;
	/** 秒杀商品ID */
	private String seckillId;


	public SeckillMqDTO(String userId, String seckillId) {
		super();
		this.userId = userId;
		this.seckillId = seckillId;

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
}
