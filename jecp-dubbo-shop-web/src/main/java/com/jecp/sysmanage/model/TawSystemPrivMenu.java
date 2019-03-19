package com.jecp.sysmanage.model;

/**
 * TawSystemPrivMenu entity. @author MyEclipse Persistence Tools
 */

public class TawSystemPrivMenu implements java.io.Serializable {

	// Fields

	private String id;
	private String menuname;
	private String remark;
	private String operateuserid;
	private String clienttype;

	// Constructors

	/** default constructor */
	public TawSystemPrivMenu() {
	}

	/** full constructor */
	public TawSystemPrivMenu(String menuname, String remark,
			String operateuserid, String clienttype) {
		this.menuname = menuname;
		this.remark = remark;
		this.operateuserid = operateuserid;
		this.clienttype = clienttype;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuname() {
		return this.menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperateuserid() {
		return this.operateuserid;
	}

	public void setOperateuserid(String operateuserid) {
		this.operateuserid = operateuserid;
	}

	public String getClienttype() {
		return this.clienttype;
	}

	public void setClienttype(String clienttype) {
		this.clienttype = clienttype;
	}

}