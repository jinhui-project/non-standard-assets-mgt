package com.jinhui.mapper.organization;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.Organization;
import com.jinhui.model.RecordOrganization;

public interface OrganizationMapper {
	
	
	/**
	 * 获取备案机构
	 * @return
	 */
	List<RecordOrganization> getOrganizationListByRecord();
	
	
	/**
	 * 机构信息分页
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<Organization> queryOrganizationPage(Organization organizationint);
	
	
	/**
	 * 查询总数量
	 */
	Integer queryOrganizationCount(Organization organizationint);
	
	/**
	 * 机构信息详情
	 * @param id
	 * @return
	 */
	Organization queryOrganizationDetail(@Param("id")Integer id);
	
	/**
	 * 机构信息--添加信息
	 * @param organization
	 */
	void insert(@Param("record")Organization organization);
	
	
	/**
	 * 机构信息list
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<Organization> queryOrganizationAll(Organization organizationint);
	
	/**
	 * 机构 信息大于0的
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<Organization> queryOrganizationEqFundType(Organization organizationint);
	
	void updateNotNullOrganization(Organization organizationint);

}
