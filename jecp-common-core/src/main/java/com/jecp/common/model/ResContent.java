package com.jecp.common.model;
/**
 * http请求返回对象
 * @author wwt
 *
 */
public class ResContent {
	private String cookie;
	private StringBuffer content;
	private String location;
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public StringBuffer getContent() {
		return content;
	}
	public void setContent(StringBuffer content) {
		this.content = content;
	}
	
}
