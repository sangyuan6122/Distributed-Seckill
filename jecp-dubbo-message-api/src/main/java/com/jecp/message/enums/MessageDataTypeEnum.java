package com.jecp.message.enums;

public enum MessageDataTypeEnum {
	JSON;
	// 成员变量
	private String desc;

	

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static void main(String[] args) {
		System.out.println(MessageDataTypeEnum.JSON.name());
	}
}
