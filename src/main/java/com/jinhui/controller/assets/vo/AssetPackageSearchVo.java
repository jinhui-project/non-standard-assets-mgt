package com.jinhui.controller.assets.vo;

import java.math.BigDecimal;

public class AssetPackageSearchVo {
	//应收债务人
    private String debtorNames;
    //资产包简称
  	private String shortName;
  	//起始资产包规模
  	private BigDecimal packageAmountBegin;
  	//结束资产包规模
  	private BigDecimal packageAmountEnd;
  	//起始封包时间
  	private String setupDateBegin;
  	//结束封包时间
  	private String setupDateEnd;
  	
  	private String pageNum;
  	
  	private String pageSize;

	public String getDebtorNames() {
		return debtorNames;
	}

	public void setDebtorNames(String debtorNames) {
		this.debtorNames = debtorNames;
	}

	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public BigDecimal getPackageAmountBegin() {
		return packageAmountBegin;
	}
	public void setPackageAmountBegin(BigDecimal packageAmountBegin) {
		this.packageAmountBegin = packageAmountBegin;
	}
	public BigDecimal getPackageAmountEnd() {
		return packageAmountEnd;
	}
	public void setPackageAmountEnd(BigDecimal packageAmountEnd) {
		this.packageAmountEnd = packageAmountEnd;
	}
	public String getSetupDateBegin() {
		return setupDateBegin;
	}
	public void setSetupDateBegin(String setupDateBegin) {
		this.setupDateBegin = setupDateBegin;
	}
	public String getSetupDateEnd() {
		return setupDateEnd;
	}
	public void setSetupDateEnd(String setupDateEnd) {
		this.setupDateEnd = setupDateEnd;
	}
	
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "AssetPackageSearchVo{" +
				"debtorNames='" + debtorNames + '\'' +
				", shortName='" + shortName + '\'' +
				", packageAmountBegin=" + packageAmountBegin +
				", packageAmountEnd=" + packageAmountEnd +
				", setupDateBegin='" + setupDateBegin + '\'' +
				", setupDateEnd='" + setupDateEnd + '\'' +
				", pageNum='" + pageNum + '\'' +
				", pageSize='" + pageSize + '\'' +
				'}';
	}
}
