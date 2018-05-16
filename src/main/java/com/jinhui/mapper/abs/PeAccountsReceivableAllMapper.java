package com.jinhui.mapper.abs;


import com.jinhui.controller.abs.vo.AccountsReceivableFileVo;
import com.jinhui.model.PeAccountsReceivableAll;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PeAccountsReceivableAllMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PeAccountsReceivableAll record);

    int insertSelective(PeAccountsReceivableAll record);

    PeAccountsReceivableAll selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PeAccountsReceivableAll record);

    int updateByPrimaryKey(PeAccountsReceivableAll record);


    /**
     * 查询不同的文件名及信息
     * @return
     */
    List<AccountsReceivableFileVo> selectDistinctByFileName();



    /**
     * 根据文件名查询應收賬款信息
     */
    List<PeAccountsReceivableAll> selectByFileId(@Param("fileId") String fileId);



}