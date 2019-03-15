package com.jecp.base.page;

import java.io.Serializable;

public class PageQuery implements Serializable{
	private static final long serialVersionUID = 1039567551886187965L;
	private Integer page;// 当前页
	private Integer rows;// 每页数量
	private String[] sort;// 排序字段
	private String[] order;// 排序类型
	private String key = "id";// 数据库表主键

	public PageQuery(Integer page, Integer rows, String[] sort, String[] order) {
		super();
		this.page = page;
		this.rows = rows;
		this.sort = sort;
		this.order = order;
	}
	public PageQuery(Integer page, Integer rows) {
		super();
		this.page = page;
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String[] getSort() {
		return sort;
	}

	public void setSort(String[] sort) {
		this.sort = sort;
	}

	public String[] getOrder() {
		return order;
	}

	public void setOrder(String[] order) {
		this.order = order;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
