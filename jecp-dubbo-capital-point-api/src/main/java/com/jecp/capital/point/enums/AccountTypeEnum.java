package com.jecp.capital.point.enums;

public enum AccountTypeEnum {
	BUYER("买家","1"),SELLER("商户","2");
	
	// 成员变量  
    private String name;  
    private String code;
    // 构造方法  
    private AccountTypeEnum(String name,String code) {  
        this.name = name;  
        this.code=code;
    }  

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public static String getName(String code) {
		for(AccountTypeEnum accountTypeEnum:AccountTypeEnum.values()) {
			if(accountTypeEnum.code.equals(code)) {
				return accountTypeEnum.name;
			}
		}
		return "";
	}
	
}
