package com.jinhui.controller.assets.vo;

import java.math.BigDecimal;

public class BaseAssetsSearchVo {
	private int aid;
    //应收债务人
	private String debtorName;
	//状态
	private String status;
	//起始价税合计值
	private BigDecimal billAmountBegin;
	//结束价税合计值
	private BigDecimal billAmountEnd;
    //起始开票日期
  	private String invoicedDateBegin;
  	//结束开票日期
  	private String invoicedDateEnd;
  	//起始到期日
  	private String returnDateBegin;
  	//结束到期日
  	private String returnDateEnd;
  	//起始账期
  	private String receivablePeriodBegin;
  	//结束账期
  	private String receivablePeriodEnd;
  	//已选资产规模
  	private BigDecimal choiceAssetScale;
  	//总基础资产数
  	private int choiceAssetCount;
  	//原始权益人
  	private String originalHolder;
  	private String pageNum;
	private String pageSize;

	private String createName;

	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getDebtorName() {
		return debtorName;
	}
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}
	public String getInvoicedDateBegin() {
		return invoicedDateBegin;
	}
	public void setInvoicedDateBegin(String invoicedDateBegin) {
		this.invoicedDateBegin = invoicedDateBegin;
	}
	public String getInvoicedDateEnd() {
		return invoicedDateEnd;
	}
	public void setInvoicedDateEnd(String invoicedDateEnd) {
		this.invoicedDateEnd = invoicedDateEnd;
	}
	public String getReceivablePeriodBegin() {
		return receivablePeriodBegin;
	}
	public void setReceivablePeriodBegin(String receivablePeriodBegin) {
		this.receivablePeriodBegin = receivablePeriodBegin;
	}
	public String getReceivablePeriodEnd() {
		return receivablePeriodEnd;
	}
	public void setReceivablePeriodEnd(String receivablePeriodEnd) {
		this.receivablePeriodEnd = receivablePeriodEnd;
	}
	public BigDecimal getChoiceAssetScale() {
		return choiceAssetScale;
	}
	public void setChoiceAssetScale(BigDecimal choiceAssetScale) {
		this.choiceAssetScale = choiceAssetScale;
	}
	public int getChoiceAssetCount() {
		return choiceAssetCount;
	}
	public void setChoiceAssetCount(int choiceAssetCount) {
		this.choiceAssetCount = choiceAssetCount;
	}
	public String getReturnDateBegin() {
		return returnDateBegin;
	}
	public void setReturnDateBegin(String returnDateBegin) {
		this.returnDateBegin = returnDateBegin;
	}
	public String getReturnDateEnd() {
		return returnDateEnd;
	}
	public void setReturnDateEnd(String returnDateEnd) {
		this.returnDateEnd = returnDateEnd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getBillAmountBegin() {
		return billAmountBegin;
	}
	public void setBillAmountBegin(BigDecimal billAmountBegin) {
		this.billAmountBegin = billAmountBegin;
	}
	public BigDecimal getBillAmountEnd() {
		return billAmountEnd;
	}
	public void setBillAmountEnd(BigDecimal billAmountEnd) {
		this.billAmountEnd = billAmountEnd;
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

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getOriginalHolder() {
		return originalHolder;
	}

	public void setOriginalHolder(String originalHolder) {
		this.originalHolder = originalHolder;
	}

	@Override
	public String toString() {
		return "BaseAssetsSearchVo{" +
				"aid=" + aid +
				", debtorName='" + debtorName + '\'' +
				", status='" + status + '\'' +
				", billAmountBegin=" + billAmountBegin +
				", billAmountEnd=" + billAmountEnd +
				", invoicedDateBegin='" + invoicedDateBegin + '\'' +
				", invoicedDateEnd='" + invoicedDateEnd + '\'' +
				", returnDateBegin='" + returnDateBegin + '\'' +
				", returnDateEnd='" + returnDateEnd + '\'' +
				", receivablePeriodBegin='" + receivablePeriodBegin + '\'' +
				", receivablePeriodEnd='" + receivablePeriodEnd + '\'' +
				", choiceAssetScale=" + choiceAssetScale +
				", choiceAssetCount=" + choiceAssetCount +
				", pageNum='" + pageNum + '\'' +
				", pageSize='" + pageSize + '\'' +
				", createName='" + createName + '\'' +
				", originalHolder='" + originalHolder + '\'' +
				'}';
	}
}
