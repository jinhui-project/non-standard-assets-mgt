package com.jinhui.controller.review.vo;

import com.jinhui.controller.base.WebPageResult;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by jinhui on 2017/7/14.
 */
public class ReviewAssetResult extends WebPageResult {

    @ApiModelProperty(value="附件", dataType = "com.jinhui.controller.review.vo.ReviewAssetEntityVo")
    private List<ReviewAssetEntityVo> voList;

    public List<ReviewAssetEntityVo> getVoList() {
        return voList;
    }

    public void setVoList(List<ReviewAssetEntityVo> voList) {
        this.voList = voList;
    }
}
