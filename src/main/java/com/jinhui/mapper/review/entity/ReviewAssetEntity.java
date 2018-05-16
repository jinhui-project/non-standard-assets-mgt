package com.jinhui.mapper.review.entity;

/**
 * Created by jinhui on 2017/7/14.
 */
public class ReviewAssetEntity {
    private Integer assetId;
    private Integer assetStatus;
    private String assetName;
    private String reviewState;
    private String assetShortName;
    private Byte assetType;
    private String reviewUserName;
    private String lastFeatureCode;
    private String updatedTime;
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

    public String getReviewState() {
        return reviewState;
    }

    public void setReviewState(String reviewState) {
        this.reviewState = reviewState;
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
}
