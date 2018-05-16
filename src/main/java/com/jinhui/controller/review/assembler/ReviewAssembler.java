package com.jinhui.controller.review.assembler;

import com.jinhui.controller.review.vo.*;
import com.jinhui.model.review.Attachment;
import com.jinhui.model.review.FeatureEvent;
import com.jinhui.model.review.Review;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinhui on 2017/7/10.
 */
public class ReviewAssembler {

    private final static String[] attachmentTypes = {
            "Proposal","Investigation","MeetingSummary",
            "RiskProposal","ReviewSummary","ReviewPoll",
            "Clause","Others"};
    public static Map<String , List<AttachmentVo>> attachments2Map(List<AttachmentVo> vos){
        Map<String, List<AttachmentVo>> vosMap = new HashMap<String, List<AttachmentVo>>();
        for(AttachmentVo vo:vos){
            List<AttachmentVo> voList = vosMap.get(vo.getAttachmentType());
            if(voList == null){
                voList = new ArrayList<AttachmentVo>();
                vosMap.put(vo.getAttachmentType(), voList);
            }
            voList.add(vo);
        }
        insertDefault(vosMap);
        return vosMap;
    }
    private static void insertDefault(Map<String , List<AttachmentVo>> vosMap){
        for(String key:attachmentTypes){
            if(vosMap.get(key) == null){
                List<AttachmentVo> vos = new ArrayList<AttachmentVo>(1);
                vos.add(new AttachmentVo());
                vosMap.put(key, vos);
            }
        }
    }

    public static Review voToReview(NewReviewVo vo){
        Review review = new Review(vo.getUid(), vo.getAssetId(), vo.getFeatureCode());
        review.comment(vo.getComment());
        return review;
    }

    public static List<Attachment> vosToAttachments(List<NewAttachmentVo> vos){
        List<Attachment> attachments = new ArrayList<Attachment>(vos.size());
        for(NewAttachmentVo vo:vos){
            attachments.add(voToAttachment(vo));
        }
        return attachments;
    }

    public static Attachment voToAttachment(NewAttachmentVo vo){
        Attachment attachment = new Attachment(vo.getAssetId(), vo.getFileId(),
                vo.getUserId(), vo.getAttachmentType());
        return attachment;
    }

    public static List<ReviewVo> reviewsToVos(List<Review> reviews){
        List<ReviewVo> vos = new ArrayList<ReviewVo>(reviews.size());
        for(Review review:reviews){
            vos.add(reviewToVo(review));
        }
        return vos;
    }

    public static ReviewVo reviewToVo(Review review){
        ReviewVo vo = new ReviewVo();
        vo.setReviewId(review.reviewId());
        vo.setAssetId(review.asset().getId());
        if(review.user() != null) {
            vo.setUid(review.user().getId());
            vo.setUsername(review.user().getName());
        }
        FeatureEvent fe = review.featureEvent();
        if(fe != null) {
            vo.setFeatureCode(fe.code());
            vo.setFeatureName(fe.featureName());
        }
        vo.setComment(review.comment());
        vo.setState(review.state().toString());
        vo.setStateName(review.state().getDesc());
        vo.setLastUpdatedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(review.lastUpdatedTime()));
        return vo;
    }

    public static List<AttachmentVo> attachmentsToVos(List<Attachment> attachments){
        List<AttachmentVo> vos = new ArrayList<AttachmentVo>(attachments.size());
        for(Attachment attachment:attachments){
            vos.add(toAttachmentVo(attachment));
        }
        return vos;
    }

    public static AttachmentVo toAttachmentVo(Attachment attachment){
        AttachmentVo vo = new AttachmentVo();
        BeanUtils.copyProperties(attachment, vo);
        vo.setFileId(attachment.fileinfo().getId());
        vo.setFileName(attachment.fileinfo().getFileName());
        vo.setFilePath(attachment.fileinfo().getFilePath());
        vo.setUserId(attachment.user().getId());
        vo.setUsername(attachment.user().getName());
        vo.setGmtCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(attachment.getGmtCreated()));
        return vo;
    }
}
