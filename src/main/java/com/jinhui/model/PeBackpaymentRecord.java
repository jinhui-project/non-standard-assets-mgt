package com.jinhui.model;

import java.math.BigDecimal;
import java.util.Date;

public class PeBackpaymentRecord {
    private Integer id;

    private String tradeId;

    private String receivableDebtor;

    private String tradeTimestamp;

    private Date tradeTime;

    private BigDecimal backpayAmount;

    private BigDecimal backpayBalance;

    private String payerAccount;

    private String payerName;

    private String tadeBankName;

    private String remark;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    public String getReceivableDebtor() {
        return receivableDebtor;
    }

    public void setReceivableDebtor(String receivableDebtor) {
        this.receivableDebtor = receivableDebtor == null ? null : receivableDebtor.trim();
    }

    public String getTradeTimestamp() {
        return tradeTimestamp;
    }

    public void setTradeTimestamp(String tradeTimestamp) {
        this.tradeTimestamp = tradeTimestamp == null ? null : tradeTimestamp.trim();
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public BigDecimal getBackpayAmount() {
        return backpayAmount;
    }

    public void setBackpayAmount(BigDecimal backpayAmount) {
        this.backpayAmount = backpayAmount;
    }

    public BigDecimal getBackpayBalance() {
        return backpayBalance;
    }

    public void setBackpayBalance(BigDecimal backpayBalance) {
        this.backpayBalance = backpayBalance;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount == null ? null : payerAccount.trim();
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName == null ? null : payerName.trim();
    }

    public String getTadeBankName() {
        return tadeBankName;
    }

    public void setTadeBankName(String tadeBankName) {
        this.tadeBankName = tadeBankName == null ? null : tadeBankName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}