package com.jinhui.model;

import java.math.BigDecimal;

/**
 * 基础资产池统计数据
 * @time 2017-10-12 下午2:30:19  
 * @author wsc
 * @desc
 *
 */
public class StatisticalData {
   //总应收债款数量	
   private int totalDebtCount;
   //总导入成功比率
   private BigDecimal importSuccRate;
   //总应收债款金额
   private BigDecimal totalDebtAmount;
   //总应收债款余额
   private BigDecimal totalDebtBalance;
   //总逾期债款数量
   private int totalOverdueDebtCount;
   //总逾期债率
   private BigDecimal totalOverdueDebtRate;
   //待收数量
   private int dueInCount;
   //已收数量
   private int receivedCount;
   //逾期数量
   private int overdueCount;
   //冲销数量
   private int writeoffCount;
   //逾期后回收数量
   private int overdueReceivedCount;
   //坏账数量
   private int badDebtCount;
   //资产包内基础资产数
   private int inPackageCount;
   //包外可用资产数
   private int canUseCount;
   //即将逾期基础资产数
   private int  toExpiredCount;
   //资产包外基础资产数
   private int outPackageCount;
   //包外可用基础资产金额
   private BigDecimal canUseAmount;
   //即将逾期基础资产金额
   private BigDecimal  toExpiredAmount;
   //待收金额
   private BigDecimal  dueInAmount;
   //已收金额
   private BigDecimal receivedAmount;
   //逾期金额
   private BigDecimal overdueAmount;
   //冲销金额
   private BigDecimal writeoffAmount;
   //逾期后回收金额
   private BigDecimal overdueReceivedAmount;
   //坏账金额
   private BigDecimal badDebtAmount;

	public int getTotalDebtCount() {
		return totalDebtCount;
	}
	public void setTotalDebtCount(int totalDebtCount) {
		this.totalDebtCount = totalDebtCount;
	}
	public BigDecimal getImportSuccRate() {
		return importSuccRate;
	}
	public void setImportSuccRate(BigDecimal importSuccRate) {
		this.importSuccRate = importSuccRate;
	}
	public BigDecimal getTotalDebtAmount() {
		return totalDebtAmount;
	}
	public void setTotalDebtAmount(BigDecimal totalDebtAmount) {
		this.totalDebtAmount = totalDebtAmount;
	}
	public BigDecimal getTotalDebtBalance() {
		return totalDebtBalance;
	}
	public void setTotalDebtBalance(BigDecimal totalDebtBalance) {
		this.totalDebtBalance = totalDebtBalance;
	}
	public int getTotalOverdueDebtCount() {
		return totalOverdueDebtCount;
	}
	public void setTotalOverdueDebtCount(int totalOverdueDebtCount) {
		this.totalOverdueDebtCount = totalOverdueDebtCount;
	}
	public BigDecimal getTotalOverdueDebtRate() {
		return totalOverdueDebtRate;
	}
	public void setTotalOverdueDebtRate(BigDecimal totalOverdueDebtRate) {
		this.totalOverdueDebtRate = totalOverdueDebtRate;
	}
	public int getDueInCount() {
		return dueInCount;
	}
	public void setDueInCount(int dueInCount) {
		this.dueInCount = dueInCount;
	}
	public int getReceivedCount() {
		return receivedCount;
	}
	public void setReceivedCount(int receivedCount) {
		this.receivedCount = receivedCount;
	}
	public int getOverdueCount() {
		return overdueCount;
	}
	public void setOverdueCount(int overdueCount) {
		this.overdueCount = overdueCount;
	}
	public int getWriteoffCount() {
		return writeoffCount;
	}
	public void setWriteoffCount(int writeoffCount) {
		this.writeoffCount = writeoffCount;
	}
	public int getOverdueReceivedCount() {
		return overdueReceivedCount;
	}
	public void setOverdueReceivedCount(int overdueReceivedCount) {
		this.overdueReceivedCount = overdueReceivedCount;
	}
	public int getBadDebtCount() {
		return badDebtCount;
	}
	public void setBadDebtCount(int badDebtCount) {
		this.badDebtCount = badDebtCount;
	}

	public int getInPackageCount() {
		return inPackageCount;
	}

	public void setInPackageCount(int inPackageCount) {
		this.inPackageCount = inPackageCount;
	}

	public int getCanUseCount() {
		return canUseCount;
	}

	public void setCanUseCount(int canUseCount) {
		this.canUseCount = canUseCount;
	}

	public int getToExpiredCount() {
		return toExpiredCount;
	}

	public void setToExpiredCount(int toExpiredCount) {
		this.toExpiredCount = toExpiredCount;
	}

	public int getOutPackageCount() {
		return outPackageCount;
	}

	public void setOutPackageCount(int outPackageCount) {
		this.outPackageCount = outPackageCount;
	}

	public BigDecimal getCanUseAmount() {
		return canUseAmount;
	}

	public void setCanUseAmount(BigDecimal canUseAmount) {
		this.canUseAmount = canUseAmount;
	}

	public BigDecimal getToExpiredAmount() {
		return toExpiredAmount;
	}

	public void setToExpiredAmount(BigDecimal toExpiredAmount) {
		this.toExpiredAmount = toExpiredAmount;
	}

	public BigDecimal getDueInAmount() {
		return dueInAmount;
	}

	public void setDueInAmount(BigDecimal dueInAmount) {
		this.dueInAmount = dueInAmount;
	}

	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public BigDecimal getWriteoffAmount() {
		return writeoffAmount;
	}

	public void setWriteoffAmount(BigDecimal writeoffAmount) {
		this.writeoffAmount = writeoffAmount;
	}

	public BigDecimal getOverdueReceivedAmount() {
		return overdueReceivedAmount;
	}

	public void setOverdueReceivedAmount(BigDecimal overdueReceivedAmount) {
		this.overdueReceivedAmount = overdueReceivedAmount;
	}

	public BigDecimal getBadDebtAmount() {
		return badDebtAmount;
	}

	public void setBadDebtAmount(BigDecimal badDebtAmount) {
		this.badDebtAmount = badDebtAmount;
	}
}
