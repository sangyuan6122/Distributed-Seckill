package com.jecp.sysmanage.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jecp.sysmanage.dao.TawSystemPrivMenuAssignDao;
import com.jecp.sysmanage.model.TawSystemCookie;
import com.jecp.sysmanage.service.TawSystemCookieService;
import com.jecp.sysmanage.service.TawSystemUserService;

public class LoginServlet extends HttpServlet implements Filter {
	private TawSystemCookieService tawSystemCookieService;
	private TawSystemPrivMenuAssignDao tawSystemPrivMenuAssignDao;
	private TawSystemUserService tawSystemUserService;
	@Override
	public void destroy() {		
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		boolean isMobile=JudgeIsMoblie(request);
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		String url= request.getServletPath();
		url=url.indexOf('?')==-1?url:url.substring(0, url.indexOf('?'));//完整URL
		uri=uri.substring(uri.lastIndexOf("/")+1);//文件名或请求名称
		if("mlogin.html".equals(uri)||"login.html".equals(uri)||"userlogin".equals(uri)
				||"userauth".equals(uri)
				||"weixinsso".equals(uri)||uri.endsWith("css")
				||uri.endsWith("gif")||uri.endsWith("jpg")||uri.endsWith("png")||uri.endsWith("js")
				||request.getRequestURI().indexOf("/crvms/services/")==0
				||url.indexOf("/upload/")>-1){
			chain.doFilter(request, response);
			return;
		}
		Cookie[] cookies =request.getCookies();
		TawSystemCookie tawSystemCookie;		
		for(int i=0;cookies!=null&&i<cookies.length;i++){
			if("k".equals(cookies[i].getName())){
				String k = cookies[i].getValue();
				tawSystemCookie=tawSystemCookieService.getCookie(k);				
				if( tawSystemCookie!=null ){
					if( uri.endsWith("html")||"/prvmenuTree".equals(url)||"/dictgetToJson".equals(url)
							||"/depttree".equals(url)||"/usertree".equals(url)						
							||"/usubroletree".equals(url)||url.indexOf("/index")==0
							||"admin".equals(tawSystemCookie.getUserid())
							||(tawSystemPrivMenuAssignDao.urlPermission(tawSystemCookie.getUserid(),tawSystemCookie.getDeptid(), url)) ){
						request.setAttribute("tawSystemCookie", tawSystemCookie);
						request.setAttribute("tawSystemUser", tawSystemUserService.getById(tawSystemCookie.getUid()));
						request.setAttribute("uid", tawSystemCookie.getUid());//传递userid
						request.setAttribute("userid", tawSystemCookie.getUserid());//传递userid
						request.setAttribute("deptid", tawSystemCookie.getDeptid());//传递deptid
						request.setAttribute("k", k);//传递cookie
						chain.doFilter(request, response);
						return;
					}else{
						response.getWriter().print("{\"status\":\"-1\"}");
						return;
					}					
				}else{
					
				}
			}			
		}
		if(isMobile){
			response.sendRedirect(contextPath+"/mlogin.html");
		}else{
			response.sendRedirect(contextPath+"/login.html");
		}		
		return;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}
	private boolean JudgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
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
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}
	/*set、get*/
	public void setTawSystemCookieService(
			TawSystemCookieService tawSystemCookieService) {
		this.tawSystemCookieService = tawSystemCookieService;
	}

	public TawSystemPrivMenuAssignDao getTawSystemPrivMenuAssignDao() {
		return tawSystemPrivMenuAssignDao;
	}
	public void setTawSystemPrivMenuAssignDao(
			TawSystemPrivMenuAssignDao tawSystemPrivMenuAssignDao) {
		this.tawSystemPrivMenuAssignDao = tawSystemPrivMenuAssignDao;
	}

	public void setTawSystemUserService(TawSystemUserService tawSystemUserService) {
		this.tawSystemUserService = tawSystemUserService;
	}
	
}
