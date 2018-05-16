package com.jinhui.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class PeAccountsReceivable {
    private Integer id;

    private Integer aid;

    private String debtorName;

    private String customerName;

    private String custormerNumber;

    private String originalHolder;

    private BigDecimal invoicedAmount;

    private BigDecimal billAmount;

    private BigDecimal billBalance;

    private String invoiceNo;

    private String invoiceCode;

    private String invoicedDate;

    private String returnedDate;
    
    private int receivablePeriod;

    private String projectName;

    private String arrivalDate;

    private String status;

    private String shortName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustormerNumber() {
        return custormerNumber;
    }

    public void setCustormerNumber(String custormerNumber) {
        this.custormerNumber = custormerNumber;
    }

    public String getOriginalHolder() {
        return originalHolder;
    }

    public void setOriginalHolder(String originalHolder) {
        this.originalHolder = originalHolder;
    }

    public BigDecimal getInvoicedAmount() {
        return invoicedAmount;
    }

    public void setInvoicedAmount(BigDecimal invoicedAmount) {
        this.invoicedAmount = invoicedAmount;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public BigDecimal getBillBalance() {
        return billBalance;
    }

    public void setBillBalance(BigDecimal billBalance) {
        this.billBalance = billBalance;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoicedDate() {
        return invoicedDate;
    }

    public void setInvoicedDate(String invoicedDate) {
        this.invoicedDate = invoicedDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    public int getReceivablePeriod() {
        return receivablePeriod;
    }

    public void setReceivablePeriod(int receivablePeriod) {
        this.receivablePeriod = receivablePeriod;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}