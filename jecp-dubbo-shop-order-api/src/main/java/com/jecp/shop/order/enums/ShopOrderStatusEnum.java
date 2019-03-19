package com.jecp.shop.order.enums;

public enum ShopOrderStatusEnum {
	DRAFT("草稿"), UN_PAID("未付款"), TO_SHIP("待发货"), SHIPPING("运输中"), COMPLETED("已完成"), CANCELLATION("取消交易"), RETURN("退货"),CLOSED("已关闭"),CREATE_FAILED("创建失败");
	private String desc;

	private ShopOrderStatusEnum(String desc) {
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
			desc=ShopOrderStatusEnum.valueOf(name).desc;
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return desc;
	}
	
	
}
