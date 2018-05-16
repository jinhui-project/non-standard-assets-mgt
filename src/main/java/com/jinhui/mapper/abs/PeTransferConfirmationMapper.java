package com.jinhui.mapper.abs;

import com.jinhui.model.PeTransferConfirmation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PeTransferConfirmationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PeTransferConfirmation record);

    int insertSelective(PeTransferConfirmation record);

    PeTransferConfirmation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PeTransferConfirmation record);

    int updateByPrimaryKey(PeTransferConfirmation record);


    List<PeTransferConfirmation> selectByFileNameAndCreateDate(@Param("aid") Integer aid,@Param("fileName") String fileName, @Param("createDateBegin") String createDateBegin, @Param("createDateEnd") String createDateEnd);


}