package com.jinhui.mapper.review.repository;


import com.jinhui.mapper.review.ReviewMapper;
import com.jinhui.model.review.Attachment;
import com.jinhui.model.review.Review;
import com.jinhui.model.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jinhui on 2017/7/3.
 */
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    @Autowired
    private ReviewMapper mapper;

    public Review find(Integer reviewId) {
        throw new UnsupportedOperationException("未实现");
    }

    public List<Review> reviewHistory(Integer assetId) {
        List<Review> reviews = mapper.reviewHistory(assetId);
        return reviews;
    }

    public Review findLastUpdated(Integer assetId) {
        Review review = mapper.findLastUpdated(assetId);
        if(review == null){
            review = Review.initial(assetId);
        }
        return review;
    }

    public void update(Review review) {
        mapper.update(review);
    }

    public Long save(Review review) {
        return mapper.save(review);
    }

    public void addAttachment(Attachment attachment) {
        mapper.addAttachment(attachment);
    }

    public void deleteAttachment(Long fileId) {
        mapper.deleteAttachment(fileId);
    }

    public List<Attachment> findAttachment(Integer assetId) {
        return mapper.findAttachment(assetId);
    }
}
