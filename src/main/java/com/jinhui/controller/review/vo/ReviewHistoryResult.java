package com.jinhui.controller.review.vo;

import com.jinhui.controller.base.WebResult;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by jinhui on 2017/7/10.
 */
public class ReviewHistoryResult extends WebResult {

    @ApiModelProperty(value="附件ID", dataType = "com.jinhui.controller.review.vo.ReviewVo")
    private List<ReviewVo> reviews;

    public List<ReviewVo> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewVo> reviews) {
        this.reviews = reviews;
    }
}
