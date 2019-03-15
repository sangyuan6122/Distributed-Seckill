package com.jecp.trade.record.enums;

/**
 * @功能 交易状态枚举类 
 * @作者 WWT
 * @修改时间 2018年5月21日
 */
public enum TradeStatusEnum {
	DRAFT("草稿"),WAITING_PAY("待支付"), PAYING("支付中"), PAY_SUCCESS("支付成功"), PAY_FAILED("支付失败"),CREATE_FAILED("创建失败");
	// 成员变量
	private String desc;
	
	// 构造方法
	private TradeStatusEnum(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static TradeStatusEnum getBydesc(String desc) {
		TradeStatusEnum[] tradeStatusEnums=TradeStatusEnum.values();
		for(TradeStatusEnum tradeStatusEnum:tradeStatusEnums) {
			if(tradeStatusEnum.desc.equals(desc)) {
				return tradeStatusEnum;
			}
		}
		return null;
	}
	public static String getDesc(String name) {
		String desc="";
		try {
			desc=TradeStatusEnum.valueOf(name).desc;
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return desc;
	}

}
