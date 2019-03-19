package com.jecp.sysmanage.service;

import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemCookie;

public interface TawSystemCookieService extends BaseService<TawSystemCookie>{
	/**
	 * 验证Cookie
	 * @param cookieid
	 * @return
	 */	
	public TawSystemCookie getCookie(String cookieid);
	/**
	 * 删除cookie
	 * @param cookieid
	 */
	public String removeCookie(String cookieid);
	/**
	 * 添加cookie到缓存
	 * @param cookie
	 */
	public void addToCache(TawSystemCookie cookie);
	/**
	 * 获得当前在线用户
	 * @return
	 */
	public String online();
	/**
	 * 统计在线人数
	 * @return
	 */
	public int onlineTotal();
	/**
	 * 删除所有用户Cookie
	 */
	public void delAllUserCookie() ;
}
