package com.jecp.sysmanage.util;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class AuthUtil {
	public enum ClientType{
		Computer,Mobile,Interface
	}
	private final static Set<String> COOKIE_PATH= new HashSet<>();
	private final static Set<String> NO_COOKIE_PATHS= new HashSet<>();
	static {
		COOKIE_PATH.add("/prvmenuTree");
		COOKIE_PATH.add("/dictgetToJson");
		COOKIE_PATH.add("/depttree");
		COOKIE_PATH.add("/usertree");
		COOKIE_PATH.add("/usubroletree");	
		NO_COOKIE_PATHS.add("/userBuildAllCookies");
		NO_COOKIE_PATHS.add("/trade/record/allTradeId");
	}
	/**
	 * 过滤请求权限
	 * @param path
	 * @return -1:无权限，1:登陆后所有权限，2:所有权限，包括未登录
	 */
	public static int filterPath(String path) {
		for(String p:COOKIE_PATH) {
			if(p.equals(path)) {
				return 1;
			}
		}
		for(String p:NO_COOKIE_PATHS) {
			if(p.equals(path)) {
				return 2;
			}
		}
		return -1;
	}
	public static ClientType getClientType(HttpServletRequest request) {
		String userAgent=request.getHeader("User-Agent");
		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
				"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
				"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
				"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
				"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (userAgent != null) {
			if("interface".equals(userAgent)) {
				return ClientType.Interface;
			}
			for (String mobileAgent : mobileAgents) {
				if (userAgent.toLowerCase().indexOf(mobileAgent) >= 0) {
					return ClientType.Mobile;
				}
			}			
		}
		return ClientType.Computer;
	}
}	
