package com.jinhui.mapper.statisticsFile;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.StatisticsFile;
/**
 * 资产数据统计
 * @time 2017-4-10 下午2:04:23  
 * @author wsc
 * @desc
 *
 */
public interface StatisticsFileMapper {

	int insertSelective(StatisticsFile record);

    StatisticsFile selectByAssetId(@Param("assetId") Integer assetId);

}