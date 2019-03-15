package com.jecp.trade.record.enums;

/**
 * @功能 支付方法枚举类 
 * @作者 WWT
 * @修改时间 2018年5月21日
 */
public enum PayMethodEnum {
	BALANCE_PAYMENT("余额支付");
	// 成员变量
	private String name;

	// 构造方法
	private PayMethodEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
