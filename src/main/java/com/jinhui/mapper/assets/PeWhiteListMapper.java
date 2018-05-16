package com.jinhui.mapper.assets;

import com.jinhui.model.PeWhiteList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PeWhiteListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PeWhiteList record);

    int insertSelective(PeWhiteList record);

    PeWhiteList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PeWhiteList record);

    int updateByPrimaryKey(PeWhiteList record);


    List<PeWhiteList> selectAll();


    /**
     * 查询白名单个数
     */
    int selectCount();

    /**
     * 条件查询白名单
     */
    List<PeWhiteList> selectByQueryParam(@Param("debtor") String debtor, @Param("status") String status,@Param("period")  String period,@Param("creator")  String creator);

}