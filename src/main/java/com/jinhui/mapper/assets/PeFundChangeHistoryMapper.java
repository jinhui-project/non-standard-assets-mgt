package com.jinhui.mapper.assets;

import com.jinhui.controller.assets.vo.ChangeHistoryVo;
import com.jinhui.model.PeFundChangeHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PeFundChangeHistoryMapper {


    int insertSelective(PeFundChangeHistory record);

    PeFundChangeHistory selectByPrimaryKey(Integer id);

    List<PeFundChangeHistory> selectListByAid(ChangeHistoryVo changeHistoryVo);


    /**
     * 根据Aid找到同资产包下的最新的一条记录
    */
    PeFundChangeHistory selectNewsByAid(Integer aid);

    /**
     * 根据id更新记录的"有效"状态
     */
    int updateIsEnabledById(PeFundChangeHistory peFundChangeHistory);

}