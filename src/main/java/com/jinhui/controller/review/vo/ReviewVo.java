package com.jinhui.controller.review.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by jinhui on 2017/7/10.
 */
public class ReviewVo {
    @ApiModelProperty(value="评审ID")
    private Long reviewId;
    @ApiModelProperty(value="用户ID")
    private Integer uid;
    @ApiModelProperty(value="用户名")
    private String username;
    @ApiModelProperty(value="资产ID")
    private Integer assetId;
    @ApiModelProperty(value="功能编号={" +
            "[LAUNCH_MEETING,发起项目沟通会],[PASS_MEETING,通过],[NEARLY_MEETING, 有条件通过]," +
            "[WAIT_BIZ_MANAGER,等待业务经理审核],[PASS_BIZ_MANAGER, 通过],[NEARLY_BIZ_MANAGER, 有条件通过]," +
            "[WAIT_RISK_MANAGER,等待风控经理审核],[PASS_RISK_MANAGER,通过],[NEARLY_RISK_MANAGER, 有条件通过]," +
            "[WAIT_PROD_MANAGER,等待产品经理审核],[PASS_PROD_MANAGER,通过],[NEARLY_PROD_MANAGER, 有条件通过]," +
            "[REVIEW_PASS,通过],[REVIEW_NEARLY, 有条件通过],[REVIEW_DISCUSSING, 待议]," +
            "[REVIEW_REJECT, 不通过],[TERM_*, 终止评审]}"
    )
    private String featureCode;
    @ApiModelProperty(value="操作名称")
    private String featureName;
    @ApiModelProperty(value="资产状态名")
    private String stateName;
    @ApiModelProperty(value="资产状态=[Publish(\"发起评审\"), Meeting(\"项目沟通会\"),\n" +
            "Business(\"预审-业务人员处理\"), BizManager(\"预审-业务负责人审批\"),\n" +
            "Risk(\"预审-风控经理处理\"), RiskManager(\"预审-风控负责人审批\"),\n" +
            "Product(\"预审-产品经理处理\"), ProdManager(\"预审-产品负责人审批\"),\n" +
            "Review(\"评审\"), Termination(\"终止\")")
    private String state;
    @ApiModelProperty(value="批注")
    private String comment;
    @ApiModelProperty(value="评审时间")
    private String lastUpdatedTime;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
