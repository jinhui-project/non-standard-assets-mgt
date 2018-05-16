package com.jinhui.mapper.review;


import com.jinhui.mapper.review.criteria.ReviewAssetCriteria;
import com.jinhui.mapper.review.entity.ReviewAssetEntity;
import com.jinhui.model.review.Attachment;
import com.jinhui.model.review.Review;

import java.util.List;

/**
 * Created by jinhui on 2017/7/4.
 */
public interface ReviewMapper {
    Review find(Integer reviewId);

    List<Review> reviewHistory(Integer assetId);

    Review findLastUpdated(Integer assetId);
    void update(Review review);
    Long save(Review review);

    void addAttachment(Attachment attachment);
    void deleteAttachment(Long fileId);
    List<Attachment> findAttachment(Integer assetId);

    int countReviewAsset(ReviewAssetCriteria criteria);
    List<ReviewAssetEntity> findReviewAsset(ReviewAssetCriteria criteria);
}
