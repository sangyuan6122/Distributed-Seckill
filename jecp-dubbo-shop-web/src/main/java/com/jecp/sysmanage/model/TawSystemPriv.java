package com.jecp.sysmanage.model;

/**
 * TawSystemPriv entity. @author MyEclipse Persistence Tools
 */

public class TawSystemPriv implements java.io.Serializable {

	// Fields

	private String privid;
	private String parentprivid;
	private String name;
	private String url;
	private String menulevel;
	private String hide;
	private String sort;
	private String clienttype;
	private String ico;
	private String remark;

	// Constructors

	/** default constructor */
	public TawSystemPriv() {
	}

	/** full constructor */
	public TawSystemPriv(String parentprivid, String name, String url,
			String menulevel, String hide, String sort, String clienttype,
			String ico, String remark) {
		this.parentprivid = parentprivid;
		this.name = name;
		this.url = url;
		this.menulevel = menulevel;
		this.hide = hide;
		this.sort = sort;
		this.clienttype = clienttype;
		this.ico = ico;
		this.remark = remark;
	}

	// Property accessors

	public String getPrivid() {
		return this.privid;
	}

	public void setPrivid(String privid) {
		this.privid = privid;
	}

	public String getParentprivid() {
		return this.parentprivid;
	}

	public void setParentprivid(String parentprivid) {
		this.parentprivid = parentprivid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenulevel() {
		return this.menulevel;
	}

	public void setMenulevel(String menulevel) {
		this.menulevel = menulevel;
	}

	public String getHide() {
		return this.hide;
	}

	public void setHide(String hide) {
		this.hide = hide;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getClienttype() {
		return this.clienttype;
	}

	public void setClienttype(String clienttype) {
		this.clienttype = clienttype;
	}

	public String getIco() {
		return this.ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}