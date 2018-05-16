package com.jinhui.mapper.assets;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.controller.assets.vo.AssetPackageSearchVo;
import com.jinhui.controller.assets.vo.AssetPackageVo;
import com.jinhui.model.Assets;
import com.jinhui.model.AssetsPackage;
import com.jinhui.model.StatisticalData;



public interface AssetsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Assets record);

    int insertSelective(Assets record);

    Assets selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Assets record);

    int updateByPrimaryKey(Assets record);
    
    int updateStatusByAssetsId(Integer assetsId);
    
    List<Assets> getAllAssets();
    
    List<Assets> getMgAssetsForProduct(@Param("name") String name);
    
    List<Assets> getAssets(Assets record);
    
    List<Assets> getMgAssets(Assets record);
    
    List<Assets> getMyAssets(Integer orgId);
    
    // 分页查询我的资产-资产列表(备案机构方_我备案的) 
    List<Assets> getMyRecordAssetsList(Integer orgId);
    
    // 分页查询我的资产-资产列表(备案机构方_我反馈的)
    List<Assets> getFeedBackRecordAssetsList(Integer orgId);
    
    // 分页查询我的资产-资产列表(资金方_我反馈的)
    List<Assets> getFeedBackFundsAssetsList(Integer orgId);
    
    // 分页查询我的资产-资产列表(资金方_我销售的)
    List<Assets> getSaleFundsAssetsList(Integer orgId);
    
    //  分页查询我的资产-资产列表(资产方) 
    List<Assets> getPropertyAssetsList(Integer orgId);
    
    /**
     * 火爆销售
     * @param id
     * @return
     */
    int toHot(@Param("id")Integer id);
    /**
     * 将资产状态改为 5 形成产品
     * @param assetsId
     * @return
     */
    int updateToProductStatusByAssetsId(Integer assetsId);
    /**
     * 将资产状态改为 6 完结
     * @param assetsId
     * @return
     */
    int updateToOverStatusByAssetsId(Integer assetsId);

    /**
     * 将资产状态改为 7 失效
     * @param assetsId
     * @return
     */
    int updateToInvalideStatusByAssetsId(Integer assetsId);
    
    /**
     * 资产包列表统计数据
     * @return
     */
    List<AssetsPackage> assetsPackageStaticData();
    
    /**
     * 查询资产包详情
     * @param id
     * @return
     */
    AssetPackageVo queryAssetPackageDetail(Integer id);
    
    /**
     * 查询资产包列表 
     * @return
     */
    List<AssetPackageVo> queryAssetPackageList(AssetPackageSearchVo assetPackageSearchVo);

    /**
     * 兑付资产包
     * @param aid
     * @return
     */
    int cashAssetPackage(Integer aid);

    /**
     * 资产包警告(资产包中匹配的基础资产规模小于预警规模)
     * @param aid
     * @return
     */
    int warnAssetPackage(Integer aid);

    /**
     * 资产包修改为正常(资产包中匹配的基础资产规模大于预警规模)
     * @param aid
     * @return
     */
    int normalAssetPackage(Integer aid);
    
}