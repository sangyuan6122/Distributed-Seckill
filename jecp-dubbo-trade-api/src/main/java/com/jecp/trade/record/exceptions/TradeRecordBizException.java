package com.jecp.trade.record.exceptions;

import com.jecp.exceptions.BizException;

/**
 * @Title 交易记录异常类 
 * @author WWT
 * @date 2018年6月7日
 */
public class TradeRecordBizException extends BizException{

	private static final long serialVersionUID = -5254734362119580713L;
	
	public static final TradeRecordBizException TEST=new TradeRecordBizException(100001,"重复支付");
	
	
	public TradeRecordBizException(Integer errorCode,Throwable e,String errorMsgFormat,Object... args){
		super(errorCode,e,errorMsgFormat,args);
	}
	
	public TradeRecordBizException(Integer errorCode,String errorMsg){
		super(errorCode,errorMsg);
	}
	public TradeRecordBizException(String errorMsg,Object... args) {
		super(errorMsg,args);
	}
	@Override
	public String toString() {
		return this.getClass().getName()+" [errorCode:" + errorCode + ", errorMsg:" + errorMsg + "]";
	}	
	
}
