package com.jinhui.mapper.assets;

import com.jinhui.model.StateInvestmentAsset;



public interface StateInvestmentAssetMapper {
    int deleteByPrimaryKey(Integer id);

    StateInvestmentAsset selectByPrimaryKey(Integer id);
    
    StateInvestmentAsset selectByAssetId(Integer assetId);

    int updateByPrimaryKeySelective(StateInvestmentAsset record);
    
    int updateByAssetsidSelective(StateInvestmentAsset record);

    int updateByPrimaryKey(StateInvestmentAsset record);
}