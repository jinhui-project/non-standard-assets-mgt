package com.jinhui.mapper.featureName;

import java.util.List;

import com.jinhui.model.UsFeatureName;

public interface UsFeatureNameMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UsFeatureName record);

    UsFeatureName selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UsFeatureName record);
    
    List<UsFeatureName> queryFeatureListByRoleId(int roleId);
    
    List<UsFeatureName> queryFeatureList();
    
 
}