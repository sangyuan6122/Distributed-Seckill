package com.jecp.base.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.jecp.base.page.PageQuery;

public abstract class BaseController {

	private static final ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<>();
	private static final ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<>();
	private static final ThreadLocal<PageQuery> pageQueryLocal = new ThreadLocal<>();

	@ModelAttribute
	private void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		requestLocal.set(request);
		responseLocal.set(response);
	}

	@ModelAttribute
	public void setPaginationParam(Integer page, Integer rows, String[] sort, String[] order) {
		pageQueryLocal.set(new PageQuery(page, rows, sort, order));
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	/**
	 * 获得Request
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return requestLocal.get();
	}
	/**
	 * 获得Response
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		return responseLocal.get();
	}
	/**
	 * 获得分页PageQuery
	 * @return
	 */
	protected PageQuery getPageQuery() {
		return pageQueryLocal.get();
	}
//	/**
//	 * 获得用户
//	 * @return
//	 */
//	protected TawSystemUser getUser() {
//		return (TawSystemUser) getRequest().getAttribute("tawSystemUser");
//	}
	protected String getUserId() {
		return (String) getRequest().getAttribute("userid");
	}
	/**
	 * 获取客户端的IP地址
	 * @return
	 */
	public String getIpAddr() {
		String ipAddress = null;
		ipAddress = getRequest().getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = getRequest().getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = getRequest().getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
}
