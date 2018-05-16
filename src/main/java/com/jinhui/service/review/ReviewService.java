package com.jinhui.service.review;


import com.jinhui.model.review.Attachment;
import com.jinhui.model.review.Review;

import java.util.List;

/**
 * Created by jinhui on 2017/7/3.
 */
public interface ReviewService {

    List<Review> reviewHistory(Integer assetId);

    Review findLastUpdated(Integer assetId);

    void review(Review review, List<Attachment> attachments);

    void addAttachments(List<Attachment> attachments);
    void deleteAttachment(Long fileId);
    List<Attachment> findAttachment(Integer assetId);
}
