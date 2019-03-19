package com.jecp.sysmanage.vo;


public class TawSystemLogQueryVO {
	
	private String begintime;
	private String endtime;
	private String module;
	private String businessid;
	private String userid;
	private String type;
	
	public TawSystemLogQueryVO() {
		super();
	}
	public TawSystemLogQueryVO(String begintime, String endtime, String module,
			String businessid, String userid, String type) {
		super();
		this.begintime = begintime;
		this.endtime = endtime;
		this.module = module;
		this.businessid = businessid;
		this.userid = userid;
		this.type = type;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getBusinessid() {
		return businessid;
	}
	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
