package com.jinhui.model;

import java.math.BigDecimal;

/**
 * 资产包列表统计数据
 * @time 2017-10-12 下午2:30:19  
 * @author wsc
 * @desc
 *
 */
public class AssetsPackage {
	//总资产包数量
    private int assetsCount;
    //问题资产包数量
    private int exceptionAssetsCount;
    //发行规模总金额
    private BigDecimal packageAmount;
    //实际金额
    private BigDecimal actualAmount;
    //结束状态数量
    private int overAssetsCount;
    //警告状态数量
    private int warnAssetsCount;
    //兑付状态数量
    private int cashAssetsCount;
    //正常状态数量
    private int normalAssetsCount;
	//结束资产包金额
    private BigDecimal overAssetsAmount;
    //警告资产包金额
    private BigDecimal warnAssetsAmount;
    //兑付资产包金额
    private BigDecimal cashAssetsAmount;
    //正常资产包金额
    private BigDecimal normalAssetsAmount;

	public int getAssetsCount() {
		return assetsCount;
	}
	public void setAssetsCount(int assetsCount) {
		this.assetsCount = assetsCount;
	}
	public int getExceptionAssetsCount() {
		return exceptionAssetsCount;
	}
	public void setExceptionAssetsCount(int exceptionAssetsCount) {
		this.exceptionAssetsCount = exceptionAssetsCount;
	}
	public BigDecimal getPackageAmount() {
		return packageAmount;
	}
	public void setPackageAmount(BigDecimal packageAmount) {
		this.packageAmount = packageAmount;
	}
	public BigDecimal getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
	public int getOverAssetsCount() {
		return overAssetsCount;
	}
	public void setOverAssetsCount(int overAssetsCount) {
		this.overAssetsCount = overAssetsCount;
	}
	public int getWarnAssetsCount() {
		return warnAssetsCount;
	}
	public void setWarnAssetsCount(int warnAssetsCount) {
		this.warnAssetsCount = warnAssetsCount;
	}
	public int getCashAssetsCount() {
		return cashAssetsCount;
	}
	public void setCashAssetsCount(int cashAssetsCount) {
		this.cashAssetsCount = cashAssetsCount;
	}
	public int getNormalAssetsCount() {
		return normalAssetsCount;
	}
	public void setNormalAssetsCount(int normalAssetsCount) {
		this.normalAssetsCount = normalAssetsCount;
	}

	public BigDecimal getOverAssetsAmount() {
		return overAssetsAmount;
	}

	public void setOverAssetsAmount(BigDecimal overAssetsAmount) {
		this.overAssetsAmount = overAssetsAmount;
	}

	public BigDecimal getWarnAssetsAmount() {
		return warnAssetsAmount;
	}

	public void setWarnAssetsAmount(BigDecimal warnAssetsAmount) {
		this.warnAssetsAmount = warnAssetsAmount;
	}

	public BigDecimal getCashAssetsAmount() {
		return cashAssetsAmount;
	}

	public void setCashAssetsAmount(BigDecimal cashAssetsAmount) {
		this.cashAssetsAmount = cashAssetsAmount;
	}

	public BigDecimal getNormalAssetsAmount() {
		return normalAssetsAmount;
	}

	public void setNormalAssetsAmount(BigDecimal normalAssetsAmount) {
		this.normalAssetsAmount = normalAssetsAmount;
	}

	@Override
	public String toString() {
		return "AssetsPackage [assetsCount=" + assetsCount
				+ ", exceptionAssetsCount=" + exceptionAssetsCount
				+ ", packageAmount=" + packageAmount + ", actualAmount="
				+ actualAmount + ", overAssetsCount=" + overAssetsCount
				+ ", warnAssetsCount=" + warnAssetsCount + ", cashAssetsCount="
				+ cashAssetsCount + ", normalAssetsCount=" + normalAssetsCount
				+ "]";
	}
	
}
