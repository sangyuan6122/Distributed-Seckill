package com.jecp.shop.order.enums;

public enum ShopOrderSalesWayEnum {
	NORMAL_SALE(0,"正常销售"), GROUP_PURCHASE(2,"团购"), SECKILL(1,"秒杀");
	private String name;
	private Integer code;
	private ShopOrderSalesWayEnum(Integer code,String name) {
		this.code = code;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	public static String codeOfName(Integer code) {
		for(ShopOrderSalesWayEnum shopOrderSalesWayEnum:ShopOrderSalesWayEnum.values()) {
			if(shopOrderSalesWayEnum.getCode().equals(code)) {
				return shopOrderSalesWayEnum.name;
			}
		}
		return "";
	}
}
