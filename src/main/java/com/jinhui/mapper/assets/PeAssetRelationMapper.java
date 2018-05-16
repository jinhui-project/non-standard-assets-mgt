package com.jinhui.mapper.assets;

import com.jinhui.model.PeAssetRelation;

import java.util.List;
import java.util.Map;

public interface PeAssetRelationMapper {

    int insertSelective(PeAssetRelation record);

    PeAssetRelation selectByPrimaryKey(Integer id);

    /**
     * 批量保存
     * @param list
     * @return
     */
    int insertBatchInit(List list);
}