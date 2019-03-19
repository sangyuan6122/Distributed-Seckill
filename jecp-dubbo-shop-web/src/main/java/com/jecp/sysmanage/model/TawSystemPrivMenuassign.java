package com.jecp.sysmanage.model;

/**
 * TawSystemPrivMenuassign entity. @author MyEclipse Persistence Tools
 */

public class TawSystemPrivMenuassign implements java.io.Serializable {

	// Fields

	private String id;
	private String menuid;
	private String menulevel;
	private String privid;
	private String url;
	private String type;
	private String owner;
	private String clienttype;

	// Constructors

	/** default constructor */
	public TawSystemPrivMenuassign() {
	}

	/** full constructor */
	public TawSystemPrivMenuassign(String menuid, String menulevel,
			String privid, String url, String type, String owner,
			String clienttype) {
		this.menuid = menuid;
		this.menulevel = menulevel;
		this.privid = privid;
		this.url = url;
		this.type = type;
		this.owner = owner;
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

	public String getMenulevel() {
		return this.menulevel;
	}

	public void setMenulevel(String menulevel) {
		this.menulevel = menulevel;
	}

	public String getPrivid() {
		return this.privid;
	}

	public void setPrivid(String privid) {
		this.privid = privid;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getClienttype() {
		return this.clienttype;
	}

	public void setClienttype(String clienttype) {
		this.clienttype = clienttype;
	}

}