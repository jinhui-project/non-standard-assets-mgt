package com.jinhui.mapper.review.query;

import com.jinhui.mapper.review.ReviewMapper;
import com.jinhui.mapper.review.criteria.ReviewAssetCriteria;
import com.jinhui.mapper.review.entity.ReviewAssetEntity;
import com.jinhui.model.review.FeatureEvent;
import com.jinhui.model.review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinhui on 2017/7/14.
 */
@Service
public class ReviewQueryManagement {

    @Autowired
    private ReviewMapper reviewMapper;

    public int countReviewAsset(ReviewAssetCriteria criteria){
        return reviewMapper.countReviewAsset(criteria);
    }
    public List<ReviewAssetEntity> findReviewAsset(ReviewAssetCriteria criteria){
        List<ReviewAssetEntity> entities = reviewMapper.findReviewAsset(criteria);
        for(ReviewAssetEntity entity:entities){
            FeatureEvent fe = FeatureEvent.codeOf(entity.getLastFeatureCode());
            entity.setReviewState(fe.nextState().name());
        }
        return entities;
    }
}
