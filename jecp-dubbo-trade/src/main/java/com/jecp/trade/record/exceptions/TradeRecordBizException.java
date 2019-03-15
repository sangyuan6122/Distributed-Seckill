package com.jecp.trade.record.exceptions;

import com.jecp.exceptions.BizException;

/**
 * @Title 支付记录异常类
 * @author WWT
 * @date 2018年6月21日
 */
public class TradeRecordBizException extends BizException{

	private static final long serialVersionUID = 3283838835160546593L;
	public static TradeRecordBizException PAY_ERROR=new TradeRecordBizException(100001,"交易系统异常，支付失败");
	
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
