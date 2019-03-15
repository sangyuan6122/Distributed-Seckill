package com.jecp.base.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jecp.common.model.Tree;
import com.jecp.common.model.TreeAttributes;

public class PageBean<T> implements Serializable {
	private static final long serialVersionUID = -7758346008441158370L;

	private List<T> pageData;
	private Integer currentPage = 1;
	private Integer pageSize = 20;
	private Long totalCount;

	public int getPageCount() {
		if (this.totalCount.intValue() % this.pageSize.intValue() == 0) {
			return this.totalCount.intValue() / this.pageSize.intValue();
		}
		return this.totalCount.intValue() / this.pageSize.intValue() + 1;
	}

	public PageBean(List<T> pageData, Long totalCount) {
		this.pageData = pageData;
		this.totalCount = totalCount;
	}

	public PageBean(List<T> pageData, Long totalCount, Integer pageSize) {
		this.pageData = pageData;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
	}

	public PageBean() {
	}

	public boolean isFirst() {
		return (this.currentPage.intValue() == 1) || (this.totalCount.intValue() == 0);
	}

	public boolean isLast() {
		return (this.totalCount.intValue() == 0) || (this.currentPage.intValue() >= getPageCount());
	}

	public boolean isHasNext() {
		return this.currentPage.intValue() < getPageCount();
	}

	public boolean isHasPrev() {
		return this.currentPage.intValue() > 1;
	}

	public Integer getNextPage() {
		if (this.currentPage.intValue() >= getPageCount()) {
			return Integer.valueOf(getPageCount());
		}
		return Integer.valueOf(this.currentPage.intValue() + 1);
	}

	public Integer getPrevPage() {
		if (this.currentPage.intValue() <= 1) {
			return Integer.valueOf(1);
		}
		return Integer.valueOf(this.currentPage.intValue() - 1);
	}

	public List<T> getPageData() {
		return this.pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

	public Integer getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public String toDataGridJSON() {
		JSONObject jSONObject = new JSONObject();
		jSONObject.put("total", this.totalCount);
		jSONObject.put("rows", this.pageData);
		return jSONObject.toJSONString();
	}

	public static void main(String[] args) {
		TreeAttributes treeAttributes = new TreeAttributes();
		treeAttributes.setA1("a1");
		Tree tree = new Tree();
		tree.setId("2");
		tree.setAttributes(treeAttributes);
		List<Tree> list = new ArrayList<>();
		list.add(tree);
		tree = new Tree();
		tree.setId("1");
		list.add(tree);
		PageBean pageBean = new PageBean(list, 10l);
		System.out.println(pageBean.toDataGridJSON());
	}
}