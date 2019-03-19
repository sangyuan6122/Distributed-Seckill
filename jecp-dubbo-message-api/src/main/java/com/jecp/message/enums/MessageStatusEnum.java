package com.jecp.message.enums;

public enum MessageStatusEnum {
	UNCONFIRMED("待确认"), UNCONSUMED("待消费"), CONSUMED("已消费"),FAILED("失败");
	// 成员变量
	private String desc;

	// 构造方法
	private MessageStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	public static String getDesc(String name) {
		String desc="";
		try {
			desc=MessageStatusEnum.valueOf(name).desc;
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return desc;
	}
	
}
