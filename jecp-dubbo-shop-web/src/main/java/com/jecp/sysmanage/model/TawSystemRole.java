package com.jecp.sysmanage.model;

/**
 * TawSystemRole entity. @author MyEclipse Persistence Tools
 */

public class TawSystemRole implements java.io.Serializable {

	// Fields

	private String roleid;
	private String parentid;
	private String deleted;
	private String name;
	private String type;
	private String sort;
	private String remark;

	// Constructors

	/** default constructor */
	public TawSystemRole() {
	}

	/** full constructor */
	public TawSystemRole(String parentid, String deleted, String name,
			String type, String sort, String remark) {
		this.parentid = parentid;
		this.deleted = deleted;
		this.name = name;
		this.type = type;
		this.sort = sort;
		this.remark = remark;
	}

	// Property accessors

	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}