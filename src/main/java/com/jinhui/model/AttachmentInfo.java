package com.jinhui.model;

import java.io.Serializable;

public class AttachmentInfo implements Serializable {

	private Long id;
	private int index;
	private String meturialKey;
	private String meturialName;
	private String fileName;	private String filePath;	//private  String fileType;	//private  String editTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getMeturialKey() {
		return meturialKey;
	}
	public void setMeturialKey(String meturialKey) {
		this.meturialKey = meturialKey;
	}
	public String getMeturialName() {
		return meturialName;
	}
	public void setMeturialName(String meturialName) {
		this.meturialName = meturialName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}