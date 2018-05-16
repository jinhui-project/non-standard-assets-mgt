package com.jinhui.controller.review.api;

import com.jinhui.controller.review.assembler.ReviewAssembler;
import com.jinhui.controller.base.WebConstants;
import com.jinhui.controller.base.WebResult;
import com.jinhui.controller.review.vo.*;
import com.jinhui.mapper.review.criteria.ReviewAssetCriteria;
import com.jinhui.mapper.review.entity.ReviewAssetEntity;
import com.jinhui.mapper.review.query.ReviewQueryManagement;
import com.jinhui.model.review.Attachment;
import com.jinhui.model.review.Review;
import com.jinhui.service.authorizing.shiro.PermissionUtils;
import com.jinhui.service.review.ReviewService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/review")
@Api(value = "review", description = "评审相关接口")
public class ReviewApiController {
    private final static Logger logger = LoggerFactory.getLogger(ReviewApiController.class);

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewQueryManagement queryManagement;

    @ApiOperation(value = "评审", response = WebResult.class, httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/review", method = POST)
    public WebResult review(@ApiParam(value = "评审", required = true) @RequestBody NewReviewVo reviewVo){
        PermissionUtils.checkPermission("/review/review", reviewVo.getFeatureCode());
        if(logger.isInfoEnabled()) {
            logger.info("评审: {}",reviewVo.toString());
        }
        WebResult result = WebResult.successResult();
        try {
            Review review = ReviewAssembler.voToReview(reviewVo);
            List<Attachment> attachments = ReviewAssembler.vosToAttachments(reviewVo.getAttachments());
            reviewService.review(review, attachments);

            if (logger.isInfoEnabled()) {
                logger.info("评审更新返回");
            }
        }catch (Exception e){
            logger.warn("评审异常: {}",e);
            result = WebResult.failureResult(e.getMessage());
        }

        return result;
    }

    @ApiOperation(value = "添加附件", response = WebResult.class, httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/addAttachments", method = POST)
    public WebResult addAttachments(@ApiParam(value = "附件对象", required = true) @RequestBody List<NewAttachmentVo> attachmentVos){
        if(logger.isInfoEnabled()) {
            logger.info("添加附件: {}",attachmentVos.toString());
        }
        WebResult result = WebResult.successResult();
        try {
            List<Attachment> attachments = ReviewAssembler.vosToAttachments(attachmentVos);
            reviewService.addAttachments(attachments);

            if (logger.isInfoEnabled()) {
                logger.info("添加返回");
            }
        }catch (Exception e){
            logger.warn("添加附件异常: {}",e);
            result = WebResult.failureResult(e.getMessage());
        }

        return result;
    }

    @ApiOperation(value = "删除附件", response = WebResult.class, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fileId", dataType = "long", required = true, value = "文件ID"),
    })
    @ResponseBody
    @RequestMapping(value = "/deleteAttachments", method = GET)
    public WebResult deleteAttachments(Long fileId){
        if(logger.isInfoEnabled()) {
            logger.info("删除附件: 文件ID = {}",fileId);
        }
        WebResult result = WebResult.successResult();
        try {

            reviewService.deleteAttachment(fileId);

            if (logger.isInfoEnabled()) {
                logger.info("删除附件返回");
            }
        }catch (Exception e){
            logger.warn("删除附件异常: {}",e);
            result = WebResult.failureResult(e.getMessage());
        }

        return result;
    }


    @ApiOperation(value = "查询评审历史", response = ReviewHistoryResult.class, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "assetId", dataType = "int", required = true, value = "资产ID"),
    })
    @ResponseBody
    @RequestMapping(value = "/reviewHistory", method = GET)
    public ReviewHistoryResult reviewHistory(int assetId){
        if(logger.isInfoEnabled()) {
            logger.info("查询评审历史: assetId={}",assetId);
        }
        ReviewHistoryResult result = new ReviewHistoryResult();
        try {
            result.setResultCode(WebConstants.RESULT_SUCCESS_CODE);
            List<Review> reviewList = reviewService.reviewHistory(assetId);
            List<ReviewVo> vos = ReviewAssembler.reviewsToVos(reviewList);
            result.setReviews(vos);
            if (logger.isInfoEnabled()) {
                logger.info("查询评审历史返回");
            }
        }catch (Exception e){
            logger.warn("查询评审历史异常: {}",e);
            result.setResultCode(WebConstants.RESULT_FAIL_CODE);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @ApiOperation(value = "查询附件", response = AttachmentsResult.class, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "assetId", dataType = "int", required = true, value = "资产ID"),
    })
    @ResponseBody
    @RequestMapping(value = "/attachments", method = GET)
    public AttachmentsResult attachments(int assetId){
        if(logger.isInfoEnabled()) {
            logger.info("查询附件: assetId={}",assetId);
        }
        AttachmentsResult result = new AttachmentsResult();
        try {
            result.setResultCode(WebConstants.RESULT_SUCCESS_CODE);
            List<Attachment> attachments = reviewService.findAttachment(assetId);
            List<AttachmentVo> vos = ReviewAssembler.attachmentsToVos(attachments);
            Map<String, List<AttachmentVo>> vosMap = ReviewAssembler.attachments2Map(vos);
            result.setAttachments(vosMap);
            if (logger.isInfoEnabled()) {
                logger.info("查询附件返回");
            }
        }catch (Exception e){
            logger.warn("查询附件异常: {}",e);
            result.setResultCode(WebConstants.RESULT_FAIL_CODE);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @ApiOperation(value = "查询最新评审", response = ReviewResult.class, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "assetId", dataType = "int", required = true, value = "资产ID"),
    })
    @ResponseBody
    @RequestMapping(value = "/findLastUpdated", method = GET)
    public ReviewResult findLastUpdated(int assetId){
        if(logger.isInfoEnabled()) {
            logger.info("查询评审历史: assetId={}",assetId);
        }
        ReviewResult result = new ReviewResult();
        try {
            result.setResultCode(WebConstants.RESULT_SUCCESS_CODE);
            Review review = reviewService.findLastUpdated(assetId);
            ReviewVo vo = ReviewAssembler.reviewToVo(review);
            result.setReview(vo);
            if (logger.isInfoEnabled()) {
                logger.info("查询评审历史返回");
            }
        }catch (Exception e){
            logger.warn("查询评审历史异常: {}",e);
            result.setResultCode(WebConstants.RESULT_FAIL_CODE);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @ApiOperation(value = "查询评审资产", response = ReviewAssetResult.class, httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/findReviewAsset", method = POST)
    public ReviewAssetResult findReviewAsset(@ApiParam(value = "查询条件", required = true)
                                         @RequestBody ReviewAssetCriteriaVo criteriaVo){
        if(logger.isInfoEnabled()) {
            logger.info("查询条件: {}",criteriaVo.toString());
        }
        ReviewAssetResult result = new ReviewAssetResult();
        try {
            result.setCurrentPage(criteriaVo.getCurrentPage());
            result.setPageSize(criteriaVo.getPageSize());
            result.setResultCode(WebConstants.RESULT_SUCCESS_CODE);
            ReviewAssetCriteria criteria = new ReviewAssetCriteria();
            BeanUtils.copyProperties(criteriaVo, criteria);
            criteria.setSize(criteriaVo.getPageSize());
            criteria.setOffset(criteriaVo.getPageSize() * criteriaVo.getCurrentPage());
            int total = queryManagement.countReviewAsset(criteria);
            result.setTotalPage(total);
            if(total > 0) {
                List<ReviewAssetEntity> entities = queryManagement.findReviewAsset(criteria);
                List<ReviewAssetEntityVo> vos = new ArrayList<ReviewAssetEntityVo>(entities.size());
                for(ReviewAssetEntity entity:entities){
                    ReviewAssetEntityVo vo = new ReviewAssetEntityVo();
                    BeanUtils.copyProperties(entity, vo);
                    vos.add(vo);
                }
                result.setVoList(vos);
            }
            if (logger.isInfoEnabled()) {
                logger.info("查询评审资产返回");
            }
        } catch (Exception e) {
            logger.warn("添加附件异常: {}",e);
            result.setResultCode(WebConstants.RESULT_FAIL_CODE);
            result.setMessage(e.getMessage());
        }

        return result;
    }
}
