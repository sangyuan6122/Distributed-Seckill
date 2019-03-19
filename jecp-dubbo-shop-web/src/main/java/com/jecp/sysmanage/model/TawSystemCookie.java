package com.jecp.sysmanage.model;

import java.sql.Timestamp;

/**
 * TawSystemCookie entity. @author MyEclipse Persistence Tools
 */

public class TawSystemCookie implements java.io.Serializable {

	// Fields

	private String cookieid;
	private String uid;
	private String userid;
	private String username;
	private Long deptid;
	private String deptname;
	private String mobile;
	private String loginip;
	private Timestamp lastoperatetime;
	private Integer usercount;
	private String serverip;
	// Constructors

	/** default constructor */
	public TawSystemCookie() {
	}

	/** full constructor */
	public TawSystemCookie(String uid,String userid, String username, Long deptid,
			String deptname, String mobile, String loginip,
			Timestamp lastoperatetime, Integer usercount,String serverip) {
		this.uid=uid;
		this.userid = userid;
		this.username = username;
		this.deptid = deptid;
		this.deptname = deptname;
		this.mobile = mobile;
		this.loginip = loginip;
		this.lastoperatetime = lastoperatetime;
		this.usercount = usercount;
		this.serverip=serverip;
	}

	// Property accessors

	public String getCookieid() {
		return this.cookieid;
	}

	public void setCookieid(String cookieid) {
		this.cookieid = cookieid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getDeptid() {
		return this.deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return this.deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginip() {
		return this.loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public Timestamp getLastoperatetime() {
		return this.lastoperatetime;
	}

	public void setLastoperatetime(Timestamp lastoperatetime) {
		this.lastoperatetime = lastoperatetime;
	}

	public Integer getUsercount() {
		return this.usercount;
	}

	public void setUsercount(Integer usercount) {
		this.usercount = usercount;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getServerip() {
		return serverip;
	}

	public void setServerip(String serverip) {
		this.serverip = serverip;
	}
	
}