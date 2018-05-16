package com.jinhui.service.featureName.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.mapper.featureName.UsFeatureNameMapper;
import com.jinhui.model.Products;
import com.jinhui.model.UsFeatureName;
import com.jinhui.service.featureName.UsFeatureNameService;

/**
 * 
 * @time 2017-4-18 下午2:31:01  
 * @author wsc
 * @desc  功能服务层实现
 *
 */
@Service("usFeatureNameService")
public class UsFeatureNameServiceImpl implements UsFeatureNameService {
	@Resource
	UsFeatureNameMapper usFeatureNameMapper;

	@Override
	public int addFeature(UsFeatureName usFeatureName) {
		return usFeatureNameMapper.insertSelective(usFeatureName);
	}

	@Override
	public UsFeatureName queryFeature(int id) {
		return usFeatureNameMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<UsFeatureName> queryFeatureListByRoleId(Integer pageNum,Integer pageSize,int roleId) {
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		List<UsFeatureName> list = usFeatureNameMapper.queryFeatureListByRoleId(roleId);
		
		return new PageInfo<UsFeatureName>(list);
	}

	@Override
	public int updateFeature(UsFeatureName usFeatureName) {
		return usFeatureNameMapper.updateByPrimaryKeySelective(usFeatureName);
	}

	@Override
	public int deleteFeature(int id) {
		return usFeatureNameMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<UsFeatureName> queryFeatureList() {
		return usFeatureNameMapper.queryFeatureList();
	}

}
