package com.jecp.sysmanage.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * TawSystemDicttype entity. @author MyEclipse Persistence Tools
 */

public class TawSystemDicttype implements java.io.Serializable {

	// Fields
	@Id
    @SequenceGenerator(name="TAW_SYSTEM_DICTTYPE_SEQ",sequenceName="TAW_SYSTEM_DICTTYPE_SEQ" ,allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "DICTID", unique = true, nullable = false)
	private Integer dictid;
	private String dictname;
	private String dictalias;
	private String parentdictid;
	private String dictcode;
	private String remark;
	private String sort;

	// Constructors

	/** default constructor */
	public TawSystemDicttype() {
	}

	/** full constructor */
	public TawSystemDicttype(String dictname, String dictalias,
			String parentdictid, String dictcode, String remark, String sort) {
		this.dictname = dictname;
		this.dictalias = dictalias;
		this.parentdictid = parentdictid;
		this.dictcode = dictcode;
		this.remark = remark;
		this.sort = sort;
	}

	// Property accessors

	public Integer getDictid() {
		return this.dictid;
	}

	public void setDictid(Integer dictid) {
		this.dictid = dictid;
	}

	public String getDictname() {
		return this.dictname;
	}

	public void setDictname(String dictname) {
		this.dictname = dictname;
	}

	public String getDictalias() {
		return this.dictalias;
	}

	public void setDictalias(String dictalias) {
		this.dictalias = dictalias;
	}

	public String getParentdictid() {
		return this.parentdictid;
	}

	public void setParentdictid(String parentdictid) {
		this.parentdictid = parentdictid;
	}

	public String getDictcode() {
		return this.dictcode;
	}

	public void setDictcode(String dictcode) {
		this.dictcode = dictcode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}