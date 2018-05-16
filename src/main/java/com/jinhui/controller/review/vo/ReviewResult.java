package com.jinhui.controller.review.vo;

import com.jinhui.controller.base.WebResult;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by jinhui on 2017/7/10.
 */
public class ReviewResult extends WebResult {

    @ApiModelProperty(value="评审", dataType = "com.jinhui.controller.review.vo.ReviewVo")
    private ReviewVo review;

    public ReviewVo getReview() {
        return review;
    }

    public void setReview(ReviewVo review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "ReviewResult{" +
                "review=" + review +
                '}';
    }
}
