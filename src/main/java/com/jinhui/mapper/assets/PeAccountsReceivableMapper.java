package com.jinhui.mapper.assets;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jinhui.controller.assets.vo.BaseAssetsSearchVo;
import com.jinhui.model.PeAccountsReceivable;
import com.jinhui.model.StatisticalData;

public interface PeAccountsReceivableMapper {

    PeAccountsReceivable queryBaseAsset(int id);

    int updateByPrimaryKeySelective(PeAccountsReceivable record);
    
    /**
     * 基础资产池统计数据
     * @return
     */
    List<StatisticalData> queryStatisticalDataList();
    
    /**
     *  查询可以用来替换的基础资产（多个应收债务人debtorName,逗号隔开）
     * @return
     */
    List<PeAccountsReceivable> selectCouldReplaceByMul(Map map);
    
    /**
     * 查询可以用来替换的基础资产（单个应收债务人debtorName）
     * @return
     */
    List<PeAccountsReceivable> selectCouldReplaceBySingle(Map map);
    
    /**
     * 关联资产Id
     * @param record
     * @return
     */
    int updateAssetAid(PeAccountsReceivable record);
    
    /**
     * 查询基础资产列表
     * @param baseAssetsSearchVo
     * @return
     */
    List<PeAccountsReceivable> queryListBySelective(BaseAssetsSearchVo baseAssetsSearchVo);
    
    /**
     * 查询资产包下的基础资产列表
     * @param baseAssetsSearchVo
     * @return
     */
    List<PeAccountsReceivable> queryBaseAssetsByAssetId(BaseAssetsSearchVo baseAssetsSearchVo);
    
    /**
     * 解除基础资产
     * @param map
     * @return
     */
    int removeBaseAsset(Map map);
    
    /**
     * 绑定基础资产
     * @param map
     * @return
     */
    int bindBaseAsset(Map map);
    
    /**
     * 通过多个id批量查询基础资产
     * @param map
     * @return
     */
    List<PeAccountsReceivable> queryListByIds(Map map);

    /**
     * 删除重选
     * @param baseAssetsSearchVo
     * @return
     */
    int deleteToChoiceAgain(BaseAssetsSearchVo baseAssetsSearchVo);

    /**
     * 完结资产包之后解除关联的基础资产（待收和逾期状态）
     * @param baseAssetsSearchVo
     * @return
     */
    int removeBaseAssetAfterOver(BaseAssetsSearchVo baseAssetsSearchVo);

}