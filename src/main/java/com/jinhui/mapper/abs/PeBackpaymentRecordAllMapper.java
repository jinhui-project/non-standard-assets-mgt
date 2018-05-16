package com.jinhui.mapper.abs;

import com.jinhui.controller.abs.vo.BackPaymentFileVo;
import com.jinhui.model.PeBackpaymentRecordAll;

import java.util.List;

public interface PeBackpaymentRecordAllMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PeBackpaymentRecordAll record);

    int insertSelective(PeBackpaymentRecordAll record);

    PeBackpaymentRecordAll selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PeBackpaymentRecordAll record);

    int updateByPrimaryKey(PeBackpaymentRecordAll record);


    List<BackPaymentFileVo> selectDistinctByFileName();

    List<PeBackpaymentRecordAll> selectByFileId(String fileId);
}