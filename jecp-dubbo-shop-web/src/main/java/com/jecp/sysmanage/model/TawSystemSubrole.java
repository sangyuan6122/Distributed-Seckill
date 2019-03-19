package com.jecp.sysmanage.model;

/**
 * TawSystemSubrole entity. @author MyEclipse Persistence Tools
 */

public class TawSystemSubrole implements java.io.Serializable {

	// Fields

	private String id;
	private String roleid;
	private String deptid;
	private String deptname;
	private String subrolename;
	private String featurecode;
	private String remark;

	// Constructors

	/** default constructor */
	public TawSystemSubrole() {
	}

	/** full constructor */
	public TawSystemSubrole(String roleid, String deptid, String deptname,
			String subrolename, String featurecode, String remark) {
		this.roleid = roleid;
		this.deptid = deptid;
		this.deptname = deptname;
		this.subrolename = subrolename;
		this.featurecode = featurecode;
		this.remark = remark;
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

	public String getDeptid() {
		return this.deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return this.deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getSubrolename() {
		return this.subrolename;
	}

	public void setSubrolename(String subrolename) {
		this.subrolename = subrolename;
	}

	public String getFeaturecode() {
		return this.featurecode;
	}

	public void setFeaturecode(String featurecode) {
		this.featurecode = featurecode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}