package com.jinhui.service.featureName.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jinhui.mapper.featureName.UsFeatureMapper;
import com.jinhui.model.UsFeature;
import com.jinhui.service.featureName.UsFeatureService;

/**
 * 角色功能关联服务层
 * @time 2017-4-19 下午5:04:31  
 * @author wsc
 * @desc
 *
 */
@Service("usFeatureService")
public class UsFeatureServiceImpl implements UsFeatureService {
	
	@Resource
	UsFeatureMapper usFeatureMapper;

	@Override
	public int grantFeaturesForRole(UsFeature usFeature,List<Integer> featureIds) {
		int flag = usFeatureMapper.deleteByRoleId(usFeature.getRoleId());
		int count = 0;
		if(flag >= 0 && featureIds.size() > 0){  
			count = usFeatureMapper.insertWithFeatureIDs(usFeature, featureIds);
		}
		return count;
	}

	@Override
	public int deleteUsFeatureByRoleId(Integer roleId) {
		return usFeatureMapper.deleteByRoleId(roleId);
	}

}
