package com.jinhui.service.featureName;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jinhui.model.UsFeatureName;
/**
 * 
 * @time 2017-4-18 下午2:31:01  
 * @author wsc
 * @desc  功能服务层
 *
 */
public interface UsFeatureNameService {

	/**
	 * 添加功能
	 * @param usFeatureName
	 * @return
	 */
	public int addFeature(UsFeatureName usFeatureName);
	
	/**
	 * 查询功能详情
	 * @param id
	 * @return
	 */
	public UsFeatureName queryFeature(int id);
	
	/**
	 * 根据角色id查询功能列表
	 * @param roleId
	 * @return
	 */
	public PageInfo<UsFeatureName> queryFeatureListByRoleId(Integer pageNum,Integer pageSize,int roleId);
	
	/**
	 * 查询功能列表
	 * @return
	 */
	public List<UsFeatureName> queryFeatureList();
	
	/**
	 * 编辑功能
	 * @param usFeatureName
	 * @return
	 */
	public int updateFeature(UsFeatureName usFeatureName);
	
	/**
	 * 根据功能id删除功能
	 * @param id
	 * @return
	 */
	public int deleteFeature(int id);
}
