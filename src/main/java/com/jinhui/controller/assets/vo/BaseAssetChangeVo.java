package com.jinhui.controller.assets.vo;

public class BaseAssetChangeVo {

	private int aid;
	
	private String changeBeforeIds;
	
	private String changeAfterIds;

	private String createName;

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getChangeBeforeIds() {
		return changeBeforeIds;
	}

	public void setChangeBeforeIds(String changeBeforeIds) {
		this.changeBeforeIds = changeBeforeIds;
	}

	public String getChangeAfterIds() {
		return changeAfterIds;
	}

	public void setChangeAfterIds(String changeAfterIds) {
		this.changeAfterIds = changeAfterIds;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Override
	public String toString() {
		return "BaseAssetChangeVo{" +
				"aid=" + aid +
				", changeBeforeIds='" + changeBeforeIds + '\'' +
				", changeAfterIds='" + changeAfterIds + '\'' +
				", createName='" + createName + '\'' +
				'}';
	}
}
