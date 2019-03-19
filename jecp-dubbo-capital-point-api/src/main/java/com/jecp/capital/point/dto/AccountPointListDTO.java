package com.jecp.capital.point.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Title 账户积分列表DTO
 * @author WWT
 * @date 2018年5月6日
 */
public class AccountPointListDTO implements Serializable {
	private static final long serialVersionUID = 4934271033038036328L;
	private String id;
	private String userId;
	private String accountType;
	private Long totalPoints;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public AccountPointListDTO() {
	}

	public AccountPointListDTO(String id) {
		this.id = id;
	}

	public AccountPointListDTO(String id, String userId, String accountType, Long totalPoints, Date createTime,
			Date updateTime) {
		this.id = id;
		this.userId = userId;
		this.accountType = accountType;
		this.totalPoints = totalPoints;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Long getTotalPoints() {
		return this.totalPoints;
	}

	public void setTotalPoints(Long totalPoints) {
		this.totalPoints = totalPoints;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
