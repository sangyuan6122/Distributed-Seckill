package com.jecp.sysmanage.vo;
/**
 * 根据菜单方案查询有哪些所有者及类型
 * @author Administrator
 *
 */
public class MenuAssignOwnerVO {
	private String menuid;
	private String owner;
	private String type;
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
