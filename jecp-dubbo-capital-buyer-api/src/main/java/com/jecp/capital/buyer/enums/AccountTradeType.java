package com.jecp.capital.buyer.enums;

public enum AccountTradeType {
	ADD("加款"),SUB("扣款");
	// 成员变量  
    private String name;  
 
    // 构造方法  
    private AccountTradeType(String name) {  
        this.name = name;   
    }  

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
