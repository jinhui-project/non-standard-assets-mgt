package com.jinhui.service.statisticsFile;

import com.jinhui.model.StatisticsFile;

/**
 * 资产数据统计
 * @time 2017-4-10 下午2:13:37  
 * @author wsc
 * @desc
 *
 */
public interface StatisticsFileService {
	
	public int insertSelective(StatisticsFile statisticsFile);

    public StatisticsFile selectByAssetId(Integer assetId);
}
