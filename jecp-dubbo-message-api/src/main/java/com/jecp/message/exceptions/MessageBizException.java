package com.jecp.message.exceptions;

import com.jecp.exceptions.BizException;

public class MessageBizException extends BizException{

	private static final long serialVersionUID = 3283838835160546593L;
	public static MessageBizException MSG_CONVERT_ERROR=new MessageBizException(500001,"消息转换异常");
	public static MessageBizException MSG_REPEAT=new MessageBizException(500001,"消息重复");
	
	public MessageBizException(Integer errorCode,String errorMsg){
		super(errorCode,errorMsg);
	}
	public MessageBizException(String errorMsg,Object... args) {
		super(errorMsg,args);
	}
	
	public MessageBizException() {
		super();		
	}
	@Override
	public String toString() {
		return this.getClass().getName()+" [errorCode:" + errorCode + ", errorMsg:" + errorMsg + "]";
	}	
}
