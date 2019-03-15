package com.jecp.common.model;

public class TreeAttributes {
	private String a1;
	private String a2;
	private String a3;//树节点下是否有人员(true:部门下有人员，false:虚拟部门或部门人员为空)
	public String getA1() {
		return a1;
	}
	public void setA1(String a1) {
		this.a1 = a1;
	}
	public String getA2() {
		return a2;
	}
	public void setA2(String a2) {
		this.a2 = a2;
	}
	public String getA3() {
		return a3;
	}
	public void setA3(String a3) {
		this.a3 = a3;
	}
	
}
