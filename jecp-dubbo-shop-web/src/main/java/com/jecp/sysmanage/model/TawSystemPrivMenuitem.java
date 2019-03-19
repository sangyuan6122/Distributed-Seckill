package com.jecp.sysmanage.model;

public class TawSystemPrivMenuitem implements java.io.Serializable {

	// Fields

	private String id;
	private String menuid;
	private String privid;
	private String clienttype;

	// Constructors

	/** default constructor */
	public TawSystemPrivMenuitem() {
	}

	/** full constructor */
	public TawSystemPrivMenuitem(String menuid, String privid, String clienttype) {
		this.menuid = menuid;
		this.privid = privid;
		this.clienttype = clienttype;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuid() {
		return this.menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getPrivid() {
		return this.privid;
	}

	public void setPrivid(String privid) {
		this.privid = privid;
	}

	public String getClienttype() {
		return this.clienttype;
	}

	public void setClienttype(String clienttype) {
		this.clienttype = clienttype;
	}

}