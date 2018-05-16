package com.jinhui.service.saleFeedBacks;

import java.util.List;

import com.jinhui.model.PlatFeedbacks;
import com.jinhui.model.SaleFeedbacks;

/**
 * 销售意向及反馈服务层接口
 * @author wsc
 *
 */
public interface SaleFeedBacksService {
	
	public List<SaleFeedbacks> getFeedbacksListByAssetId(Integer assetId);
	
	/**
	 * 添加资产的平台反馈
	 * @return
	 */
	int addPlatFeedbacks(PlatFeedbacks platFeedbacks);
	
	/**
	 * 查询资产的平台反馈
	 * @return
	 */
	public  List<PlatFeedbacks> getPlatbacksListByAssetId(Integer assetId);
	/**
	 * 查询单个产品的销售意向及反馈
	 * @return
	 */
	public List<SaleFeedbacks> getFeedbacksListByProductId(Integer productId);
	
	/**
	 * 添加销售意向及反馈
	 * @param saleFeedbacks
	 */
	public int addSaleFeedbacks(SaleFeedbacks saleFeedbacks);
	
	/**
	 * 修改销售意向及反馈
	 * @param saleFeedbacks
	 * @return
	 */
	public int updateFeedbacksBySelective(SaleFeedbacks saleFeedbacks);
	/**
	 * 删除销售意向及反馈
	 * @param feedbacksId
	 */
	public int deleteSaleFeedbacks(Integer feedbacksId);
}
