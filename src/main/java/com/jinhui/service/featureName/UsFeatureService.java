package com.jinhui.service.featureName;

import java.util.List;

import com.jinhui.model.UsFeature;

/**
 * 角色功能关联服务层
 * @time 2017-4-19 下午5:04:31  
 * @author wsc
 * @desc
 *
 */
public interface UsFeatureService {
	
	/**
	 * 根据角色id删除该角色的所有功能
	 * @param roleId
	 * @return
	 */
    public int deleteUsFeatureByRoleId(Integer roleId);

	/**
	 * 给角色分配功能
	 * @param usFeature
	 * @param featureIds
	 */
	public int grantFeaturesForRole(UsFeature usFeature, List<Integer> featureIds);
	
	
}
