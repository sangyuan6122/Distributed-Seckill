package com.jecp.sysmanage.model;

/**
 * TawSystemDept entity. @author MyEclipse Persistence Tools
 */

public class TawSystemDept implements java.io.Serializable {

	// Fields

	private Long deptid;
	private String deptname;
	private Long parentdeptid;
	private String deptfullname;
	private Long rtxdeptid;
	private String rtxparentdeptid;
	private Boolean deleted;
	private Boolean external;
	private Integer seq;
	private String remark;

	// Constructors

	/** default constructor */
	public TawSystemDept() {
	}

	/** minimal constructor */
	public TawSystemDept(Long deptid) {
		this.deptid = deptid;
	}

	/** full constructor */
	public TawSystemDept(Long deptid, String deptname, Long parentdeptid,
			String deptfullname, Long rtxdeptid, String rtxparentdeptid,
			Boolean deleted, Boolean external, Integer seq, String remark) {
		this.deptid = deptid;
		this.deptname = deptname;
		this.parentdeptid = parentdeptid;
		this.deptfullname = deptfullname;
		this.rtxdeptid = rtxdeptid;
		this.rtxparentdeptid = rtxparentdeptid;
		this.deleted = deleted;
		this.external = external;
		this.seq = seq;
		this.remark = remark;
	}

	// Property accessors

	public Long getDeptid() {
		return this.deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return this.deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public Long getParentdeptid() {
		return this.parentdeptid;
	}

	public void setParentdeptid(Long parentdeptid) {
		this.parentdeptid = parentdeptid;
	}

	public String getDeptfullname() {
		return this.deptfullname;
	}

	public void setDeptfullname(String deptfullname) {
		this.deptfullname = deptfullname;
	}

	public Long getRtxdeptid() {
		return this.rtxdeptid;
	}

	public void setRtxdeptid(Long rtxdeptid) {
		this.rtxdeptid = rtxdeptid;
	}

	public String getRtxparentdeptid() {
		return this.rtxparentdeptid;
	}

	public void setRtxparentdeptid(String rtxparentdeptid) {
		this.rtxparentdeptid = rtxparentdeptid;
	}

	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getExternal() {
		return this.external;
	}

	public void setExternal(Boolean external) {
		this.external = external;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}