package com.jecp.sysmanage.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	// 添加一个cookie
	public void addCookie(HttpServletResponse response,String key) {		
		Cookie cookie = new Cookie("k", key);
		cookie.setPath("/");
		//cookie.setMaxAge(60 * 60 * 24 * 7);// cookie保存一周
		cookie.setMaxAge(-1);//关闭浏览器失效
		response.addCookie(cookie);	
	}
	public void clearCookie(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		Cookie cookie;
		if (cookies != null) {
			for (int i=0;i<cookies.length;i++) {
				if ("HID".equals(cookies[i].getName())) {
					cookie = new Cookie("HID",null);
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			} 
		}	

	}
}