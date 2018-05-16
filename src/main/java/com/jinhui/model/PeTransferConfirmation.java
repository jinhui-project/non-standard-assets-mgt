package com.jinhui.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class PeTransferConfirmation {
    /**
     * id
     */
    private Integer id;

    /**
     * 资产包id
     */
    private Integer aid;

    /**
     * 对应pe_fund_change_history表的id
     */
    private Integer fundId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 上传文件id
     */
    private String uploadFileId;

    /**
     * 付款人账号
     */
    private String payerAccount;

    /**
     * 付款人名字
     */
    private String payerName;

    /**
     * 付款人开户行
     */
    private String payerBank;

    /**
     * 收款人账号
     */
    private String payeeAccount;

    /**
     * 收款人名字
     */
    private String payeeName;

    /**
     * 收款人开户行
     */
    private String payeeBank;

    /**
     * 大额系统支付号
     */
    private String bigPayNo;

    /**
     * 转账金额
     */
    private BigDecimal transferAmount;

    /**
     * 资金用途
     */
    private String useOfFunds;

    /**
     * 用款依据
     */
    private String useOfProve;

    /**
     * 文件状态：0-未确认,1-已确认,2-失效
     */
    private String fileState;

    /**
     * 文件创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date createDate;

    /**
     * 状态变更时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date updateDate;

    /**
     * 文件创建人
     */
    private String creator;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取资产包id
     *
     * @return aid - 资产包id
     */
    public Integer getAid() {
        return aid;
    }

    /**
     * 设置资产包id
     *
     * @param aid 资产包id
     */
    public void setAid(Integer aid) {
        this.aid = aid;
    }

    /**
     * 获取对应pe_fund_change_history表的id
     *
     * @return fund_id - 对应pe_fund_change_history表的id
     */
    public Integer getFundId() {
        return fundId;
    }

    /**
     * 设置对应pe_fund_change_history表的id
     *
     * @param fundId 对应pe_fund_change_history表的id
     */
    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    /**
     * 获取文件名称
     *
     * @return file_name - 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名称
     *
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 获取上传文件id
     *
     * @return upload_file_id - 上传文件id
     */
    public String getUploadFileId() {
        return uploadFileId;
    }

    /**
     * 设置上传文件id
     *
     * @param uploadFileId 上传文件id
     */
    public void setUploadFileId(String uploadFileId) {
        this.uploadFileId = uploadFileId == null ? null : uploadFileId.trim();
    }

    /**
     * 获取付款人账号
     *
     * @return payer_account - 付款人账号
     */
    public String getPayerAccount() {
        return payerAccount;
    }

    /**
     * 设置付款人账号
     *
     * @param payerAccount 付款人账号
     */
    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount == null ? null : payerAccount.trim();
    }

    /**
     * 获取付款人名字
     *
     * @return payer_name - 付款人名字
     */
    public String getPayerName() {
        return payerName;
    }

    /**
     * 设置付款人名字
     *
     * @param payerName 付款人名字
     */
    public void setPayerName(String payerName) {
        this.payerName = payerName == null ? null : payerName.trim();
    }

    /**
     * 获取付款人开户行
     *
     * @return payer_bank - 付款人开户行
     */
    public String getPayerBank() {
        return payerBank;
    }

    /**
     * 设置付款人开户行
     *
     * @param payerBank 付款人开户行
     */
    public void setPayerBank(String payerBank) {
        this.payerBank = payerBank == null ? null : payerBank.trim();
    }

    /**
     * 获取收款人账号
     *
     * @return payee_account - 收款人账号
     */
    public String getPayeeAccount() {
        return payeeAccount;
    }

    /**
     * 设置收款人账号
     *
     * @param payeeAccount 收款人账号
     */
    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount == null ? null : payeeAccount.trim();
    }

    /**
     * 获取收款人名字
     *
     * @return payee_name - 收款人名字
     */
    public String getPayeeName() {
        return payeeName;
    }

    /**
     * 设置收款人名字
     *
     * @param payeeName 收款人名字
     */
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName == null ? null : payeeName.trim();
    }

    /**
     * 获取收款人开户行
     *
     * @return payee_bank - 收款人开户行
     */
    public String getPayeeBank() {
        return payeeBank;
    }

    /**
     * 设置收款人开户行
     *
     * @param payeeBank 收款人开户行
     */
    public void setPayeeBank(String payeeBank) {
        this.payeeBank = payeeBank == null ? null : payeeBank.trim();
    }

    /**
     * 获取大额系统支付号
     *
     * @return big_pay_no - 大额系统支付号
     */
    public String getBigPayNo() {
        return bigPayNo;
    }

    /**
     * 设置大额系统支付号
     *
     * @param bigPayNo 大额系统支付号
     */
    public void setBigPayNo(String bigPayNo) {
        this.bigPayNo = bigPayNo == null ? null : bigPayNo.trim();
    }

    /**
     * 获取转账金额
     *
     * @return transfer_amount - 转账金额
     */
    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    /**
     * 设置转账金额
     *
     * @param transferAmount 转账金额
     */
    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    /**
     * 获取资金用途
     *
     * @return use_of_Funds - 资金用途
     */
    public String getUseOfFunds() {
        return useOfFunds;
    }

    /**
     * 设置资金用途
     *
     * @param useOfFunds 资金用途
     */
    public void setUseOfFunds(String useOfFunds) {
        this.useOfFunds = useOfFunds == null ? null : useOfFunds.trim();
    }

    /**
     * 获取用款依据
     *
     * @return use_of_prove - 用款依据
     */
    public String getUseOfProve() {
        return useOfProve;
    }

    /**
     * 设置用款依据
     *
     * @param useOfProve 用款依据
     */
    public void setUseOfProve(String useOfProve) {
        this.useOfProve = useOfProve == null ? null : useOfProve.trim();
    }

    /**
     * 获取文件状态：0-未确认,1-已确认,2-失效
     *
     * @return file_state - 文件状态：0-未确认,1-已确认,2-失效
     */
    public String getFileState() {
        return fileState;
    }

    /**
     * 设置文件状态：0-未确认,1-已确认,2-失效
     *
     * @param fileState 文件状态：0-未确认,1-已确认,2-失效
     */
    public void setFileState(String fileState) {
        this.fileState = fileState == null ? null : fileState.trim();
    }

    /**
     * 获取文件创建时间
     *
     * @return create_date - 文件创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置文件创建时间
     *
     * @param createDate 文件创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取状态变更时间
     *
     * @return update_date - 状态变更时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置状态变更时间
     *
     * @param updateDate 状态变更时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取文件创建人
     *
     * @return creator - 文件创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置文件创建人
     *
     * @param creator 文件创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }
}