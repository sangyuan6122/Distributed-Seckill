package com.jecp.shop.product.enums;

/**
 * @Title 库存控制 
 * @author WWT
 * @date 2018年6月7日
 */
public enum ProductStockControlEnum {
	//增加
	INCREASE("增加"), 
	//减少
	REDUCE("减少");
	private String name;

	private ProductStockControlEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
