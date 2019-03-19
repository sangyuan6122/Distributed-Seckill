package com.jecp.shop.product.exceptions;

import com.jecp.exceptions.BizException;

/**
 * @Title 商品业务异常类 
 * @author WWT
 * @date 2018年6月7日
 */
public class ShopProductBizException extends BizException{

	private static final long serialVersionUID = -5254734362119580713L;
	
	public static final ShopProductBizException BULD_SECKILL_INSTANCE_ERROR=new ShopProductBizException(600001,"构建秒杀策略实例异常");
	public static final ShopProductBizException LOW_STOCK=new ShopProductBizException(600002,"库存不足或秒杀商品数据不存在");
	public static final ShopProductBizException SECKILL_ERROR=new ShopProductBizException(600003,"秒杀异常");
	public static final ShopProductBizException SECKILL_LOCK_EXCEPTION=new ShopProductBizException(600004,"秒杀锁异常");
	
	public ShopProductBizException(Integer errorCode,Throwable e,String errorMsgFormat,Object... args){
		super(errorCode,e,errorMsgFormat,args);
	}
	
	public ShopProductBizException(Integer errorCode,String errorMsg){
		super(errorCode,errorMsg);
	}
	public ShopProductBizException(String errorMsg,Object... args) {
		super(errorMsg,args);
	}
	
	public ShopProductBizException() {
		super();		
	}

	@Override
	public String toString() {
		return this.getClass().getName()+" [errorCode:" + errorCode + ", errorMsg:" + errorMsg + "]";
	}	
	
}
