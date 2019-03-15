package com.jecp.common.util;

public interface BeanConvert<A,B> {
	/**
	 * 正向转换
	 * @param a
	 * @return
	 */
	public B convert(A a);
	/**
	 * 逆向转换
	 * @param b
	 * @return
	 
	public A doBackward(B b);
	*/
}
