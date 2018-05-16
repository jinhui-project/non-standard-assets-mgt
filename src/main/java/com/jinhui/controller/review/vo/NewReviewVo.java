package com.jinhui.controller.review.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Collections;
import java.util.List;

/**
 * Created by jinhui on 2017/7/10.
 */
public class NewReviewVo {
    @ApiModelProperty(value="用户ID", required = true)
    private Integer uid;
    @ApiModelProperty(value="资产ID", required = true)
    private Integer assetId;
    @ApiModelProperty(value="功能编号={" +
            "Publish-[\"LAUNCH_MEETING\",发起项目沟通会],\n" +
            "Meeting-[\"PASS_MEETING\",通过]-[\"NEARLY_MEETING\",有条件通过]-[\"REJECT_MEETING\",不通过]-[\"TERM_MEETING\",项目沟通会终止],\n" +
            "Business-[\"WAIT_BIZ_MANAGER\",等待业务经理审核]-[\"TERM_BIZ\",业务员审核终止],\n" +
            "BizManager-[\"PASS_BIZ_MANAGER\", 通过]-[\"NEARLY_BIZ_MANAGER\", 有条件通过]-[\"REJECT_BIZ_MANAGER\",不通过]-[\"TERM_BIZ_MANAGER\",业务经理终止审核],\n" +
            "Risk-[\"WAIT_RISK_MANAGER\",等待风控经理审核]-[\"TERM_RISK\",风控审核终止],\n" +
            "RiskManager-[\"PASS_RISK_MANAGER\", 通过]-[\"NEARLY_RISK_MANAGER\", 有条件通过]-[\"REJECT_RISK_MANAGER\",不通过]-[\"TERM_RISK_MANAGER\",风控经理终止审核],\n" +
            "Product-[\"WAIT_PROD_MANAGER\",等待产品经理审核]-[\"TERM_PROD\",产品审核终止],\n" +
            "ProdManager-[\"PASS_PROD_MANAGER\", 通过]-[\"NEARLY_PROD_MANAGER\", 有条件通过]-[\"REJECT_PROD_MANAGER\",不通过]-[\"TERM_PROD_MANAGER\",产品经理终止审核],\n" +
            "Review-[\"REVIEW_PASS\",通过],\n" +
            "Review-[\"REVIEW_REJECT\", 不通过],\n" +
            "Review-[\"REVIEW_NEARLY\", 有条件通过],\n" +
            "Review-[\"TERM_REVIEW\", 评审终止],\n" +
            "Review-[\"REVIEW_DISCUSSING\", 待议]}\n"
    , required = true)
    private String featureCode;
    @ApiModelProperty(value="批注")
    private String comment;
    @ApiModelProperty(value="附件", dataType = "com.jinhui.controller.review.vo.NewAttachmentVo")
    private List<NewAttachmentVo> attachments = Collections.EMPTY_LIST;

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

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public List<NewAttachmentVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<NewAttachmentVo> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "NewReviewVo{" +
                ", uid=" + uid +
                ", assetId=" + assetId +
                ", featureCode='" + featureCode + '\'' +
                ", comment='" + comment + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
