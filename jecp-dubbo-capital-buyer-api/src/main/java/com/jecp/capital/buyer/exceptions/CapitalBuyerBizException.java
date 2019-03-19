package com.jecp.capital.buyer.exceptions;

import com.jecp.exceptions.BizException;

public class CapitalBuyerBizException extends BizException{

	private static final long serialVersionUID = 3283838835160546593L;
	public static CapitalBuyerBizException ACCOUNT_NOT_FOUND=new CapitalBuyerBizException(200001,"账户不存在");
	
	
	public CapitalBuyerBizException(Integer errorCode,String errorMsg){
		super(errorCode,errorMsg);
	}
	public CapitalBuyerBizException(String errorMsg,Object... args) {
		super(errorMsg,args);
	}
	@Override
	public String toString() {
		return this.getClass().getName()+" [errorCode:" + errorCode + ", errorMsg:" + errorMsg + "]";
	}	
}
