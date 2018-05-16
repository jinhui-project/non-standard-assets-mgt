package com.jinhui.controller.review.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by jinhui on 2017/7/14.
 */
public class ReviewAssetEntityVo {
    @ApiModelProperty(value="资产ID")
    private Integer assetId;
    @ApiModelProperty(value="资产状态[0草稿 1提交 2发布 3备案 5形成产品 6完结]")
    private Integer assetStatus;
    @ApiModelProperty(value="资产名")
    private String assetName;
    @ApiModelProperty(value="资产类型")
    private Byte assetType;
    @ApiModelProperty(value="资产评审状态")
    private String reviewState;
    @ApiModelProperty(value="资产简称")
    private String assetShortName;
    @ApiModelProperty(value="最后评审动作code")
    private String lastFeatureCode;
    @ApiModelProperty(value="资产评审用户名")
    private String reviewUserName;
    @ApiModelProperty(value="最新更新事件")
    private String updatedTime;
    @ApiModelProperty(value="立项时间")
    private String startupTime;

    public Byte getAssetType() {
        return assetType;
    }

    public void setAssetType(Byte assetType) {
        this.assetType = assetType;
    }

    public String getLastFeatureCode() {
        return lastFeatureCode;
    }

    public void setLastFeatureCode(String lastFeatureCode) {
        this.lastFeatureCode = lastFeatureCode;
    }

    public Integer getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Integer assetStatus) {
        this.assetStatus = assetStatus;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getReviewState() {
        return reviewState;
    }

    public void setReviewState(String reviewState) {
        this.reviewState = reviewState;
    }

    public String getAssetShortName() {
        return assetShortName;
    }

    public void setAssetShortName(String assetShortName) {
        this.assetShortName = assetShortName;
    }

    public String getReviewUserName() {
        return reviewUserName;
    }

    public void setReviewUserName(String reviewUserName) {
        this.reviewUserName = reviewUserName;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getStartupTime() {
        return startupTime;
    }

    public void setStartupTime(String startupTime) {
        this.startupTime = startupTime;
    }

    @Override
    public String toString() {
        return "ReviewAssetEntityVo{" +
                "assetId=" + assetId +
                ", assetStatus=" + assetStatus +
                ", assetName='" + assetName + '\'' +
                ", assetType=" + assetType +
                ", reviewState='" + reviewState + '\'' +
                ", assetShortName='" + assetShortName + '\'' +
                ", lastFeatureCode='" + lastFeatureCode + '\'' +
                ", reviewUserName='" + reviewUserName + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                ", startupTime='" + startupTime + '\'' +
                '}';
    }
}
