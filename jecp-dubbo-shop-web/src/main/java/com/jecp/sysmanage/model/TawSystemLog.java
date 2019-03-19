package com.jecp.sysmanage.model;

import java.sql.Timestamp;

/**
 * TawSystemLog entity. @author MyEclipse Persistence Tools
 */

public class TawSystemLog implements java.io.Serializable {

	// Fields

	private String id;
	private Timestamp time;
	private String module;
	private String businessid;
	private String userid;
	private String username;
	private String loginip;
	private String type;
	private String logtext;
	private String remark;

	// Constructors

	/** default constructor */
	public TawSystemLog() {
	}

	/** full constructor */
	public TawSystemLog(Timestamp time, String module, String businessid,
			String userid, String username, String loginip, String type,
			String logtext, String remark) {
		this.time = time;
		this.module = module;
		this.businessid = businessid;
		this.userid = userid;
		this.username = username;
		this.loginip = loginip;
		this.type = type;
		this.logtext = logtext;
		this.remark = remark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getBusinessid() {
		return this.businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
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

	public String getLoginip() {
		return this.loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogtext() {
		return this.logtext;
	}

	public void setLogtext(String logtext) {
		this.logtext = logtext;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}