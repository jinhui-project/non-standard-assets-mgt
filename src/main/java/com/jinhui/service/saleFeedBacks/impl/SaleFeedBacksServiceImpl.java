package com.jinhui.service.saleFeedBacks.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jinhui.mapper.saleFeedBacks.PlatFeedbacksMapper;
import com.jinhui.mapper.saleFeedBacks.SaleFeedbacksMapper;
import com.jinhui.model.PlatFeedbacks;
import com.jinhui.model.SaleFeedbacks;
import com.jinhui.service.saleFeedBacks.SaleFeedBacksService;

/**
 * 销售意向及反馈服务层实现 
 * @author wsc
 *
 */

@Service("saleFeedBacksService")
public class SaleFeedBacksServiceImpl implements SaleFeedBacksService {
	
	@Resource
	private SaleFeedbacksMapper saleFeedbacksMapper;
	
	@Resource
	private PlatFeedbacksMapper platFeedbacksMapper;
	
	public  List<PlatFeedbacks> getPlatbacksListByAssetId(Integer assetId) {
		return platFeedbacksMapper.getPlatbacksListByAssetId(assetId);
	}
	
	public int addPlatFeedbacks(PlatFeedbacks platFeedbacks) {
		return platFeedbacksMapper.insertSelective(platFeedbacks);
	}

	public List<SaleFeedbacks> getFeedbacksListByProductId(Integer productId) {
		return saleFeedbacksMapper.getFeedbacksListByProductId(productId);
	}
	
	
	
	public List<SaleFeedbacks> getFeedbacksListByAssetId(Integer assetId) {
		return saleFeedbacksMapper.getFeedbacksListByAssetId(assetId);
	}

	public int addSaleFeedbacks(SaleFeedbacks saleFeedbacks) {
		return saleFeedbacksMapper.insertSelective(saleFeedbacks);
	}

	public int updateFeedbacksBySelective(SaleFeedbacks saleFeedbacks) {
		return saleFeedbacksMapper.updateByPrimaryKeySelective(saleFeedbacks);
	}

	public int deleteSaleFeedbacks(Integer feedbacksId) {
		return saleFeedbacksMapper.deleteByPrimaryKey(feedbacksId);
	}

}
