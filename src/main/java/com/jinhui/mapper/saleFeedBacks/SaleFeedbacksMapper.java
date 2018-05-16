package com.jinhui.mapper.saleFeedBacks;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.SaleFeedbacks;;

public interface SaleFeedbacksMapper {
	
	List<SaleFeedbacks> getFeedbacksListByAssetId(Integer assetId);
    int deleteByPrimaryKey(Integer id);

    int insert(SaleFeedbacks record);

    int insertSelective(SaleFeedbacks record);

    SaleFeedbacks selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SaleFeedbacks record);

    int updateByPrimaryKey(SaleFeedbacks record);
    
    List<SaleFeedbacks> getFeedbacksListByProductId(@Param("productId") Integer productId);
}