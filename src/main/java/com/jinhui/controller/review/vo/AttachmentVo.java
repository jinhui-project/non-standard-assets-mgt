package com.jinhui.controller.review.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by jinhui on 2017/7/10.
 */
public class AttachmentVo {

    @ApiModelProperty(value="资产ID")
    private Integer assetId;
    @ApiModelProperty(value="附件类型")
    private String attachmentType;
    @ApiModelProperty(value="附件ID")
    private Long fileId;
    @ApiModelProperty(value="上传用户ID")
    private Integer userId;
    @ApiModelProperty(value="上传用户名")
    private String username;
    @ApiModelProperty(value="文件名")
    private String fileName;
    @ApiModelProperty(value="文件路径")
    private String filePath;
    @ApiModelProperty(value="附件添加时间")
    private String gmtCreated;


    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

}
