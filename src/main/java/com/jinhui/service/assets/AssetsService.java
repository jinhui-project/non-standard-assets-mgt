package com.jinhui.service.assets;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jinhui.controller.assets.vo.*;
import com.jinhui.model.*;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;


public interface AssetsService {
	
	public List<Assets> getMgAssetsForProduct(String assetName);
	
	public List<WorkflowStep> getWorkflowStep();
	
	public List<RecordOrganization> getOrganizationListByRecord();
	
	public List<Workflow> getWorkflowListByGid(Integer orgId);
	
	public int updateStatusByAssetsId(Integer assetsId);
	
	public Assets getAssetsById(int assetId);
	
	public PageInfo<Assets> getAllAssets(Integer pageNo,Integer pageSize);
	
	public PageInfo<Assets> getAssets(Integer pageNo, Integer pageSize,Assets assets);
	
	public PageInfo<Assets> getMgAssets(Integer pageNo, Integer pageSize,Assets assets);		

	public PageInfo<Assets> getMyAssets(Integer pageNo, Integer pageSize,Integer orgId,String roleType);

	AssetsDetialInfo getSubAssetInfoById(int assetId,int assetType);
	
	//查询资产的附件列表
	AssetsAttachmentInfo getSubAssetAttachmentsById(int assetId,int assetType);

	int assetToProject(Assets asset);

	int checkRecord(Assets asset);

	int putOnRecordStep(RecordLog recordLog);
	
	/**
     * 查询资产在某个步骤的协议文档
     * @param assetId  资产id
     * @param stepId  流程步骤id
     * @return
     */
    RecordLog queryRecordLog(String assetId,String stepId);
    
    /**
     * 将资产状态改为 6 完结
     * @param assetsId
     * @return
     */
   public int updateToOverStatusByAssetsId(Integer assetsId);

    /**
     * 将资产状态改为 7 失效
     * @param assetsId
     * @return
     */
    public int updateToInvalideStatusByAssetsId(Integer assetsId);
   
   /**
    * 查询应收账款列表
    * @param baseAssetsSearchVo
    * @return
    */
   PageInfo<PeAccountsReceivable> queryAccountReceivedList(BaseAssetsSearchVo baseAssetsSearchVo);
   
   /**
    * 新增ABS资产
    * @param assetsDetialInfo
    * @return
    */
   int addPeAbsAsset(AssetsDetialInfo assetsDetialInfo);
	
   /**
    * 编辑ABS资产
    * @param assetsDetialInfo
    * @return
    */
   int editPeAbsAsset(AssetsDetialInfo assetsDetialInfo); 
   
   /**
    * 资产包列表统计数据
    * @return
    */
   List<AssetsPackage> assetsPackageStaticData();
   
   /**
    * 查询可以用来替换的基础资产（多个应收债务人debtorName,逗号隔开）
    * @return
    */
   public List<PeAccountsReceivable> queryCouldReplaceByMul(BaseAssetsSearchVo basv);
   
   /**
    * 查询可以用来替换的基础资产（单个应收债务人debtorName）
    * @return
    */
   public PageInfo<PeAccountsReceivable> queryCouldReplaceBySimple(BaseAssetsSearchVo basv);
   
   /**
    * 查询资产包详情
    * @param id
    * @return
    */
   AssetPackageVo queryAssetPackageDetail(Integer id);
   
   /**
    * 查询资产包下的基础资产列表
    * @return
    */
   PageInfo<PeAccountsReceivable> queryBaseAssetsByAssetId(BaseAssetsSearchVo baseAssetsSearchVo);
   
   /**
    * 变更基础资产
    * @param baseAssetChangeVo
    * @return
    */
   int changeBaseAsset(BaseAssetChangeVo baseAssetChangeVo);
   
   /**
    * 通过多个id批量查询基础资产
    * @param ids
    * @return
    */
   List<PeAccountsReceivable> queryListByIds(String ids);
   
   /**
    * 查询资产包列表
    * @param assetPackageSearchVo
    * @return
    */
   PageInfo<AssetPackageVo> queryAssetPackageList(AssetPackageSearchVo assetPackageSearchVo);

    /**
     * 删除重选
     * @param baseAssetsSearchVo
     * @return
     */
    int deleteToChoiceAgain(BaseAssetsSearchVo baseAssetsSearchVo);

    /**
     * 兑付资产包
     * @param aid
     * @return
     */
    int cashAssetPackage(Integer aid);

    /**
     * 完结资产包之后解除关联的基础资产（待收和逾期状态）
     * @param baseAssetsSearchVo
     * @return
     */
    int removeBaseAssetAfterOver(BaseAssetsSearchVo baseAssetsSearchVo);

    /**
     * 资产包警告(资产包中匹配的基础资产规模小于预警规模)
     * @param aid
     * @return
     */
    int warnAssetPackage(Integer aid);


    /**
     * 批量保存资产包内原始基础资产
     * @param aid
     * @param baseAssetIds
     * @return
     */
    int saveAssetRelationBatch(String aid,String baseAssetIds);

    /**
     * 批量保存资产包基础资产更新记录
     * @param aid
     * @param baseAssetIds
     * @param changeType  变更动作类型：1-新增  2-手动被替换  3-手动替换  4-自动被替换  5-自动替换 6-自动补充 7-完结解除
     * @return
     */
    int saveAssetChangeHisBatch(String aid,String baseAssetIds,String createName,String changeType);


    /**
     * 批量保存资产包基础资产更新记录
     * @param aid
     * @param assetAmount
     * @param createName
     * @return
     */
    int saveAssetChangeHisSelective(String aid, BigDecimal assetAmount,String createName,String changeType);

    /**
     *批量保存资产包资金更新记录
     * @param aid
     * @param assetAmount
     * @param createName
     * @return
     */
    int saveFundChangeHisSelective(String aid, BigDecimal assetAmount,String createName,String changeType);


    /**
     * 根据资产id查询资产包中资产变动记录
     * @param changeHistoryVo
     * @return
     */
    PageInfo<PeAssetChangeHistory>  queryAssetChangeHisListByAid(ChangeHistoryVo changeHistoryVo);

    /**
     * 计算总资产量和时间范围内的资产量
     * @return
     */
    public Map calculateAssetAmount(ChangeHistoryVo changeHistoryVo);

    /**
     * 根据资产id查询资产包中资金变动记录
     * @param changeHistoryVo
     * @return
     */
    PageInfo<PeFundChangeHistory>  queryFundChangeHisListByAid(ChangeHistoryVo changeHistoryVo);

    /**
     * 计算总资产量和时间范围内的资产量
     * @return
     */
    public Map calculateFundAmount(ChangeHistoryVo changeHistoryVo);

}
