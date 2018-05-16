package com.jinhui.mapper.assets;

import com.jinhui.model.PeBackpaymentRecord;

public interface PeBackpaymentRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PeBackpaymentRecord record);

    int insertSelective(PeBackpaymentRecord record);

    PeBackpaymentRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PeBackpaymentRecord record);

    int updateByPrimaryKey(PeBackpaymentRecord record);
}