package com.jinhui.controller.review.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by jinhui on 2017/7/10.
 */
public class NewAttachmentVo {

    @ApiModelProperty(value="评审ID", required = true)
    private Integer assetId;
    @ApiModelProperty(value="附件ID", required = true)
    private Long fileId;
    @ApiModelProperty(value="用户ID", required = true)
    private Integer userId;
    @ApiModelProperty(value="附件类型={[\"Proposal\",\"立项建议书\"],[\"Investigation\",\"尽调报告\"]," +
            "[\"MeetingSummary\",\"项目沟通会会议纪要\"],[\"RiskProposal\",\"风险审核意见书\"]," +
            "[\"ReviewSummary\",\"评审会会议纪要\"],[\"ReviewPoll\",\"评审会表决票\"]," +
            "[\"Clause\",\"落实条款\"],[\"Others\",\"其它\"]}", required = true)
    private String attachmentType;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
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

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    @Override
    public String toString() {
        return "NewAttachmentVo{" +
                "assetId=" + assetId +
                ", fileId=" + fileId +
                ", userId=" + userId +
                ", attachmentType='" + attachmentType + '\'' +
                '}';
    }
}
