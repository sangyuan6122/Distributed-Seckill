package com.jecp.capital.point.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import com.jecp.capital.point.dto.AccountPointListDTO;
import com.jecp.capital.point.enums.AccountTypeEnum;
import com.jecp.common.util.BeanConvert;

/**
 * @Title 账户积分
 * @author WWT
 * @date 2018年5月6日
 */
public class AccountPoint implements java.io.Serializable {

	private String id;
	private String userId;
	private String accountType;
	private Long totalPoints;
	private Date createTime;
	private Date updateTime;

	public AccountPoint() {
	}

	public AccountPoint(String id) {
		this.id = id;
	}

	public AccountPoint(String id, String userId, String accountType, Long totalPoints, Date createTime,
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
	/**
	 * 初始化积分
	 */
	public void initPoint() {
		this.totalPoints=0L;
	}
	/**
	 * 增加积分
	 * @param point
	 */
	public void addPoints(Long point) {
		this.totalPoints=this.totalPoints+point;
	}
	/**
	 * 转换为列表DTO
	 * @return
	 */
	public AccountPointListDTO convertToAccountPointListDTO() {
		AccountPointListDTOConvert accountPointListDTOConvert=new AccountPointListDTOConvert();
		AccountPointListDTO accountPointListDTO=accountPointListDTOConvert.convert(this);
		return accountPointListDTO;
	}
	public static class AccountPointListDTOConvert implements BeanConvert<AccountPoint,AccountPointListDTO > {

		@Override
		public AccountPointListDTO convert(AccountPoint accountPoint) {
			AccountPointListDTO accountPointListDTO=new AccountPointListDTO();
			BeanUtils.copyProperties(accountPoint, accountPointListDTO);
			accountPointListDTO.setAccountType(AccountTypeEnum.getName(accountPoint.getAccountType()));
			return accountPointListDTO;
		}
	}
}
