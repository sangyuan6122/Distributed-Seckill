package com.jecp.shop.product.enums;

/**
 * @Title 商品状态
 * @author WWT
 * @date 2018年6月7日
 */
public enum ProductStatusEnum {
	//在售
	IN_SALE("在售"),
	//下架
	OFF_SHELVES("下架"), 
	//已删除
	DELETED("已删除");

	private String name;

	private ProductStatusEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
