package com.jinhui.mapper.assets;

import com.jinhui.controller.assets.vo.ChangeHistoryVo;
import com.jinhui.model.PeAssetChangeHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PeAssetChangeHistoryMapper {

    int insertSelective(PeAssetChangeHistory record);

    PeAssetChangeHistory selectByPrimaryKey(Integer id);

    List<PeAssetChangeHistory>  selectListByAid(ChangeHistoryVo changeHistoryVo);


    /**
     * 批量保存
     * @param list
     * @return
     */
    int insertBatchInit(List list);
}