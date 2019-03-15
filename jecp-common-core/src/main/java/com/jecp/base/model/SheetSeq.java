package com.jecp.base.model;

import java.sql.Timestamp;

/**
 * SheetSeq entity. @author MyEclipse Persistence Tools
 */

public class SheetSeq implements java.io.Serializable {

	// Fields

	private String sheetkey;
	private String sheettype;
	private Integer seq;
	private Timestamp createtime;
	private String sheetid;

	// Constructors

	/** default constructor */
	public SheetSeq() {
	}

	/** minimal constructor */
	public SheetSeq(String sheetkey) {
		this.sheetkey = sheetkey;
	}

	/** full constructor */
	public SheetSeq(String sheetkey, String sheettype, Integer seq,
			Timestamp createtime, String sheetid) {
		this.sheetkey = sheetkey;
		this.sheettype = sheettype;
		this.seq = seq;
		this.createtime = createtime;
		this.sheetid = sheetid;
	}

	// Property accessors

	public String getSheetkey() {
		return this.sheetkey;
	}

	public void setSheetkey(String sheetkey) {
		this.sheetkey = sheetkey;
	}

	public String getSheettype() {
		return this.sheettype;
	}

	public void setSheettype(String sheettype) {
		this.sheettype = sheettype;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getSheetid() {
		return this.sheetid;
	}

	public void setSheetid(String sheetid) {
		this.sheetid = sheetid;
	}

}