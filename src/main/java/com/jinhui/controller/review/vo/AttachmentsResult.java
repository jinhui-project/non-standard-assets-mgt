package com.jinhui.controller.review.vo;

import com.jinhui.controller.base.WebResult;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by jinhui on 2017/7/10.
 */
public class AttachmentsResult extends WebResult {

    @ApiModelProperty(value="附件", dataType = "com.jinhui.controller.review.vo.AttachmentVo")
    private Map<String, List<AttachmentVo>> attachments;

    public Map<String, List<AttachmentVo>> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, List<AttachmentVo>> attachments) {
        this.attachments = attachments;
    }
}
