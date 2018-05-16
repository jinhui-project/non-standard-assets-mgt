package com.jinhui.service.statisticsFile.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jinhui.mapper.statisticsFile.StatisticsFileMapper;
import com.jinhui.model.StatisticsFile;
import com.jinhui.service.statisticsFile.StatisticsFileService;

/**
 * 资产数据统计
 * @time 2017-4-10 下午2:14:22  
 * @author wsc
 * @desc
 *
 */
@Service("statisticsFileService")
public class StatisticsFileServiceImpl implements StatisticsFileService {

	@Resource
	StatisticsFileMapper statisticsFileMapper;
	
	@Override
	public int insertSelective(StatisticsFile statisticsFile) {
		return statisticsFileMapper.insertSelective(statisticsFile);
	}

	@Override
	public StatisticsFile selectByAssetId(Integer assetId) {
		return statisticsFileMapper.selectByAssetId(assetId);
	}

}
