package com.jinhui.service.review.impl;


import com.jinhui.model.review.Attachment;
import com.jinhui.model.review.FeatureEvent;
import com.jinhui.model.review.Review;
import com.jinhui.model.review.ReviewRepository;
import com.jinhui.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by jinhui on 2017/7/3.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository repository;

    public List<Review> reviewHistory(Integer assetId) {
        return repository.reviewHistory(assetId);
    }

    public Review findLastUpdated(Integer assetId) {
        Review review = repository.findLastUpdated(assetId);
        return review;
    }

    @Transactional
    public void review(Review review, List<Attachment> attachments) {
        //// FIXME: 2017/7/10 检查资产是否存在
        Review lastReview = repository.findLastUpdated(review.asset().getId());
        boolean accept = review.state(lastReview.state()).fire();
        FeatureEvent event = review.featureEvent();
        if(!accept) {
            StringBuilder appender = new StringBuilder();
            appender.append("【").append(event.code()).append("】只能将资产从【")
                    .append(event.reviewState().getDesc()).append("】修改为【")
                    .append(event.nextState().getDesc()).append("】");
            throw new IllegalArgumentException(appender.toString());
        }
        if(lastReview.isInitial()){
            Long reviewId = repository.save(lastReview);
            review.reviewId(reviewId);
        }
        Assert.notNull(review.user().getId(), "操作人ID为空");
        //记录状态迁移信息
        lastReview.comment(review.comment());
        lastReview.user(review.user().getId());
        lastReview.featureCode(event.code());
        repository.update(lastReview);
        //进入新的状态
        repository.save(review);
        for(Attachment attachment:attachments)
            repository.addAttachment(attachment);
    }

    @Transactional
    public void addAttachments(List<Attachment> attachments) {
        for(Attachment attachment:attachments) {
            // FIXME: 2017/7/21 检查文件是否存在
            repository.addAttachment(attachment);
        }
    }

    public void deleteAttachment(Long fileId) {
        repository.deleteAttachment(fileId);
    }

    public List<Attachment> findAttachment(Integer assetId) {
        return repository.findAttachment(assetId);
    }
}
