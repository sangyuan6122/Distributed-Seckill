package com.jecp.sysmanage.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jecp.common.util.StatusEnum;
import com.jecp.sysmanage.dao.TawSystemPrivMenuAssignDao;
import com.jecp.sysmanage.model.TawSystemCookie;
import com.jecp.sysmanage.service.TawSystemCookieService;
import com.jecp.sysmanage.service.TawSystemUserService;
import com.jecp.sysmanage.util.AuthUtil;
import com.jecp.sysmanage.util.AuthUtil.ClientType;
import com.jecp.sysmanage.util.SysCatchUtil;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	@Resource
	private TawSystemCookieService tawSystemCookieService;
	@Resource
	private TawSystemPrivMenuAssignDao tawSystemPrivMenuAssignDao;
	@Resource
	private TawSystemUserService tawSystemUserService;
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ClientType userAgent=AuthUtil.getClientType(request);		
		
		String contextPath = request.getContextPath();		
		String servletPath= request.getServletPath();
		servletPath=servletPath.indexOf('?')==-1?servletPath:servletPath.substring(0, servletPath.indexOf('?'));//完整URL		
		Cookie[] cookies =request.getCookies();
		int filterPath=AuthUtil.filterPath(servletPath);
		if(filterPath==2) {
			return true;
		}
		TawSystemCookie tawSystemCookie;	 
		for(int i=0;cookies!=null&&i<cookies.length;i++){
			if("k".equals(cookies[i].getName())){
				String k = cookies[i].getValue();
				tawSystemCookie=tawSystemCookieService.getCookie(k);				
				if( tawSystemCookie!=null ){
					String userId=tawSystemCookie.getUserid();
					Long deptId=tawSystemCookie.getDeptid();
					if( filterPath==1||SysCatchUtil.superAdmin(userId)||(tawSystemPrivMenuAssignDao.urlPermission(userId,deptId, servletPath)) ){						
						request.setAttribute("tawSystemCookie", tawSystemCookie);
						request.setAttribute("uid", tawSystemCookie.getUid());
						request.setAttribute("userid", userId);
						request.setAttribute("deptid", deptId);
						request.setAttribute("k", k);				
						return true;
					}else{
						response.getWriter().print(StatusEnum.STATUS_UNAUTHORIZED.toString());
						return false;
					}					
				}
			}			
		}
		if(ClientType.Interface==userAgent){
			response.getWriter().print(StatusEnum.STATUS_UNAUTHORIZED.toString());
		}else if(ClientType.Mobile==userAgent){
			response.sendRedirect(contextPath+"/mlogin.html");
		}else if(ClientType.Computer==userAgent){
			response.sendRedirect(contextPath+"/login.html");
		}
		return false; 
	}
	
	public static void main(String[] args) {
		System.out.println(false||true||false);
	}
}
