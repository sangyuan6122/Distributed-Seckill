package com.jecp.shop.order.enums;

public enum ShopOrderChannelEnum {
	PC(1, "电脑"), ANDROID(2, "安卓"), IOS(4, "苹果");
	private String name;
	private Integer code;

	private ShopOrderChannelEnum(Integer code, String name) {
		this.name = name;
		this.code = code;
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
		for (ShopOrderChannelEnum shopOrderChannelEnum : ShopOrderChannelEnum.values()) {
			if (shopOrderChannelEnum.getCode().equals(code)) {
				return shopOrderChannelEnum.name;
			}
		}
		return "";
	}
}
