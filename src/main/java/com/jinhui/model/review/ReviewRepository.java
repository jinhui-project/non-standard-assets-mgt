package com.jinhui.model.review;

import java.util.List;

/**
 * Created by jinhui on 2017/6/30.
 */
public interface ReviewRepository {

    Review find(Integer reviewId);

    List<Review> reviewHistory(Integer assetId);

    Review findLastUpdated(Integer assetId);
    void update(Review review);
    Long save(Review review);

    void addAttachment(Attachment attachment);
    void deleteAttachment(Long fileId);
    List<Attachment> findAttachment(Integer assetId);
}
