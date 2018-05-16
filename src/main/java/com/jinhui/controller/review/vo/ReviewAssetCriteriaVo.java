package com.jinhui.controller.review.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by jinhui on 2017/7/14.
 */
public class ReviewAssetCriteriaVo {

    @ApiModelProperty(value="资产类型=[资产类型 1城投类  2上市公司资产类 3供应链资产类 4私募ABS类 5资产转让类  6 其他工商类]", dataType = "byte")
    private Byte assetType;
    @ApiModelProperty(value="资产简称")
    private String assetShortName;
    @ApiModelProperty(value="当前页码",  required = true , dataType = "int")
    private int currentPage;
    @ApiModelProperty( value="每页记录数", required = true, dataType = "int")
    private int pageSize;

    public Byte getAssetType() {
        return assetType;
    }

    public void setAssetType(Byte assetType) {
        this.assetType = assetType;
    }

    public String getAssetShortName() {
        return assetShortName;
    }

    public void setAssetShortName(String assetShortName) {
        this.assetShortName = assetShortName;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
