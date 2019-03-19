package com.jecp.sysmanage.model;

import java.sql.Timestamp;


/**
 * TawSystemAttachment entity. @author MyEclipse Persistence Tools
 */

public class TawSystemAttachment implements java.io.Serializable,Comparable<TawSystemAttachment> {

	// Fields

	private String id;
	private String fileenname;
	private String filecnname;
	private String attachsize;
	private String path;
	private Timestamp uploadtime;
	private String uploader;
	private String module;
	private String remark;

	// Constructors

	/** default constructor */
	public TawSystemAttachment() {
	}

	/** minimal constructor */
	public TawSystemAttachment(String id) {
		this.id = id;
	}

	/** full constructor */
	public TawSystemAttachment(String id, String fileenname, String filecnname,
			String attachsize, String path, Timestamp uploadtime,
			String uploader, String module, String remark) {
		this.id = id;
		this.fileenname = fileenname;
		this.filecnname = filecnname;
		this.attachsize = attachsize;
		this.path = path;
		this.uploadtime = uploadtime;
		this.uploader = uploader;
		this.module = module;
		this.remark = remark;
	}

	@Override
	public int compareTo(TawSystemAttachment o) {
		TawSystemAttachment attch=(TawSystemAttachment)o;
		Timestamp t =attch.getUploadtime();
		if(this.getUploadtime().getTime()>attch.getUploadtime().getTime()){
			return 1;
		}else{
			return -1;
		}

	}
	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileenname() {
		return this.fileenname;
	}

	public void setFileenname(String fileenname) {
		this.fileenname = fileenname;
	}

	public String getFilecnname() {
		return this.filecnname;
	}

	public void setFilecnname(String filecnname) {
		this.filecnname = filecnname;
	}

	public String getAttachsize() {
		return this.attachsize;
	}

	public void setAttachsize(String attachsize) {
		this.attachsize = attachsize;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Timestamp getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Timestamp uploadtime) {
		this.uploadtime = uploadtime;
	}

	public String getUploader() {
		return this.uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

}