package com.jinhui.controller.abs.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/19 0019.
 */
public class BackPaymentFileVo {


    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件上传id
     */
    private String fileId;

    /**
     * 上传时间
     */
    @JsonFormat(pattern="yyyy/MM/dd", locale = "zh", timezone = "GMT+8")
    private Date uploadDate;

    /**
     * 上传账号
     */
    private String uploadAccount;

    /**
     * 状态
     */
    private String state;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadAccount() {
        return uploadAccount;
    }

    public void setUploadAccount(String uploadAccount) {
        this.uploadAccount = uploadAccount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BackPaymentFileVo{" +
                "fileName='" + fileName + '\'' +
                ", fileId='" + fileId + '\'' +
                ", uploadDate=" + uploadDate +
                ", uploadAccount='" + uploadAccount + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
