package com.jecp.exceptions;

import java.text.MessageFormat;

public class BizException extends RuntimeException{

	private static final long serialVersionUID = 8597574242485218852L;
	public static final BizException TEST_A_B=new  BizException(100001,"测试");
	
	protected Integer errorCode;
	protected String errorMsg;
	
	public BizException(Integer errorCode,Throwable e,String errorMsgFormat,Object... args) {
		super(MessageFormat.format(errorMsgFormat, args),e);			
		this.errorCode = errorCode;
		this.errorMsg = MessageFormat.format(errorMsgFormat, args);
	}
	public BizException(Integer errorCode,String errorMsg) {
		this(errorCode,null,errorMsg,null);
	}
	public BizException(Throwable e,String errorMsg) {
		this(null,e,errorMsg,null);
	}
	public BizException(Throwable e) {
		this(null,e,null,null);
	}
	public BizException(String errorMsg,Object... args) {
		this(null,null,errorMsg,args);
	}
	public BizException() {
		
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	
}
