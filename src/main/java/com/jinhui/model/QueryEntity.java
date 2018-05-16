package com.jinhui.model;

import java.io.Serializable;

public class QueryEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9159894715312659820L;
	/**
	 * 创建开始时间
	 */
	private Long creatStartTime;
	/**
	 * 创建截至时间
	 */
	private Long creatEndTime;
	
	/**
	 * 修改开始时间
	 */
	private Long modifStartTime;
	/**
	 * 修改截至时间
	 */
	private Long modifEndTime;
	
	private int offset;
	
	private int limit;

	public Long getCreatStartTime() {
		return creatStartTime;
	}

	public void setCreatStartTime(Long creatStartTime) {
		this.creatStartTime = creatStartTime;
	}

	public Long getCreatEndTime() {
		return creatEndTime;
	}

	public void setCreatEndTime(Long creatEndTime) {
		this.creatEndTime = creatEndTime;
	}

	public Long getModifStartTime() {
		return modifStartTime;
	}

	public void setModifStartTime(Long modifStartTime) {
		this.modifStartTime = modifStartTime;
	}

	public Long getModifEndTime() {
		return modifEndTime;
	}

	public void setModifEndTime(Long modifEndTime) {
		this.modifEndTime = modifEndTime;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
