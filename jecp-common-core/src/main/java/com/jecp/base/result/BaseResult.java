package com.jecp.base.result;

import java.io.Serializable;

/**
 * @Title 返回简单结果
 * @author WWT
 * @date 2018年5月1日
 */
public class BaseResult implements Serializable{

	private static final long serialVersionUID = 3055500232134711804L;
	private final Integer code;
	private final String message;
	// private final T data;

	protected BaseResult(CodeMsg codeMsg) {
		super();
		this.code = codeMsg.getCode();
		this.message = codeMsg.getMessage();
	}

	public static BaseResult of(CodeMsg codeMsg) {
		return new BaseResult(codeMsg);
	}
	public static <T> T of(T data) {
		return data;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
