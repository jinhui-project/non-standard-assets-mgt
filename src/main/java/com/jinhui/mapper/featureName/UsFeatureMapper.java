package com.jinhui.mapper.featureName;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.UsFeature;

/**
 * 角色功能关联
 * @time 2017-4-19 下午4:28:36  
 * @author wsc
 * @desc
 *
 */
public interface UsFeatureMapper {
	
	/**
	 * 根据角色id删除该角色的所有功能
	 * @param roleId
	 * @return
	 */
    int deleteByRoleId(Integer roleId);

    /**
     * 为角色分配功能
     * @param usFeature
     * @param featureIds 多个功能ID集合
     * @return
     */
    int insertWithFeatureIDs(@Param("usFeature") UsFeature usFeature, @Param("list") List<Integer> featureIdsList);

}