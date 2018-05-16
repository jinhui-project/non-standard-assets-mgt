package com.jinhui.mapper.assets;

import com.jinhui.model.UsufructTransferAsset;


public interface UsufructTransferAssetMapper {
    int deleteByPrimaryKey(Integer aid);

    UsufructTransferAsset selectByPrimaryKey(Integer aid);
    
    UsufructTransferAsset selectByAssetId(Integer assetId);

    int updateByPrimaryKeySelective(UsufructTransferAsset record);
    
    int updateByAssetsidSelective(UsufructTransferAsset record);

    int updateByPrimaryKey(UsufructTransferAsset record);
}