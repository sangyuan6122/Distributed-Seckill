package com.jecp.capital.point.enums;

public enum AccountPointQueueEnum {
	POINTS_CONSUMER_QUEUE("积分消费队列"), POINTS_CONFIRM_TIME_OUT_QUEUE("积分确认超时队列");
	// 成员变量
	private String desc;

	// 构造方法
	private AccountPointQueueEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
