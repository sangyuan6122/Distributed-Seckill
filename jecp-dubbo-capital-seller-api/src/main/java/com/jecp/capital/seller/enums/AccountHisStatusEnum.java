package com.jecp.capital.seller.enums;

public enum AccountHisStatusEnum {
	TRYING("处理中"), CONFORM("已确认"), CANCEL("取消");
	// 成员变量
	private String name;

	// 构造方法
	private AccountHisStatusEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
