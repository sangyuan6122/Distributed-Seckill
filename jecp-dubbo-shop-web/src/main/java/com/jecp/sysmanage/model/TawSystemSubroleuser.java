package com.jecp.sysmanage.model;

/**
 * TawSystemSubroleuser entity. @author MyEclipse Persistence Tools
 */

public class TawSystemSubroleuser implements java.io.Serializable {

	// Fields

	private String id;
	private String roleid;
	private String subroleid;
	private String userid;
	private String username;
	private String featurecode;

	// Constructors

	/** default constructor */
	public TawSystemSubroleuser() {
	}

	/** full constructor */
	public TawSystemSubroleuser(String roleid, String subroleid, String userid,
			String username, String featurecode) {
		this.roleid = roleid;
		this.subroleid = subroleid;
		this.userid = userid;
		this.username = username;
		this.featurecode = featurecode;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getSubroleid() {
		return this.subroleid;
	}

	public void setSubroleid(String subroleid) {
		this.subroleid = subroleid;
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

	public String getFeaturecode() {
		return this.featurecode;
	}

	public void setFeaturecode(String featurecode) {
		this.featurecode = featurecode;
	}

}