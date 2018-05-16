package com.jinhui.controller.assets;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jinhui.controller.assets.vo.*;
import com.jinhui.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.jinhui.mapper.assets.IndustryCommunityAssetMapper;
import com.jinhui.mapper.assets.IpoAssetMapper;
import com.jinhui.mapper.assets.PeAbsAssetMapper;
import com.jinhui.mapper.assets.PeAccountsReceivableMapper;
import com.jinhui.mapper.assets.StateInvestmentAssetMapper;
import com.jinhui.mapper.assets.SupplyChainAssetMapper;
import com.jinhui.mapper.assets.UsufructTransferAssetMapper;
import com.jinhui.mapper.user.UserMapper;
import com.jinhui.service.assets.AssetsService;
import com.jinhui.util.FormatReturn;
import com.jinhui.util.TokenUtil;




@Controller
@RequestMapping("/assets")
public class AssetsController {
	
	private static Logger logger = LoggerFactory.getLogger(AssetsController.class);

	@Autowired
	private AssetsService assetsService;
	@Resource
	private IpoAssetMapper ipoAssetMapper;
	
	@Resource
	private PeAbsAssetMapper peAbsAssetMapper;
	
	@Resource
	private SupplyChainAssetMapper supplyChainAssetMapper;
	
	@Resource
	private StateInvestmentAssetMapper stateInvestmentAssetMapper;
	
	@Resource
	private UsufructTransferAssetMapper usufructTransferAssetMapper;
	@Resource
	private IndustryCommunityAssetMapper industryCommunityAssetMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PeAccountsReceivableMapper peAccountsReceivableMapper;
	/**
	 * 获取资产状态是产品的状态
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/queryMgAssetsForProduct",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryMgAssetsForProduct(@RequestParam("assetName") String assetName){
		logger.info("输入：  "+ "assetName="+assetName);
		List<Assets> assetsLists = assetsService.getMgAssetsForProduct(assetName);
		logger.info("输出：  "+ JSON.toJSONString(assetsLists,SerializerFeature.WriteMapNullValue));
		return JSON.toJSONString(assetsLists,SerializerFeature.WriteMapNullValue);
	}
	
	
	/**
	 * 获取流程表的相关信息
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/queryWorkflowStepInfo",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryWorkflowStepInfo(){
		List<WorkflowStep> workflowStepLists = assetsService.getWorkflowStep();
		logger.info("输出：  "+ JSON.toJSONString(workflowStepLists));
		return JSON.toJSONString(workflowStepLists);
	}
	
	
	
	/**
	 * 获取备案机构
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/queryRecordOrganInfo",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryRecordOrganInfo(){
		List<RecordOrganization> organizationLists = assetsService.getOrganizationListByRecord();
		logger.info("输出：  "+ JSON.toJSONString(organizationLists));
		return JSON.toJSONString(organizationLists);
	}
	
	
	/**
	 * 获取备案机构的备案流程名
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/queryWorkflowListByGid",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryWorkflowListByGid(Integer orgId){
		logger.info("输入：  "+ "orgId="+orgId);
		List<Workflow> workflowLists = assetsService.getWorkflowListByGid(orgId);
		logger.info("输出：  "+ JSON.toJSONString(workflowLists));
		return JSON.toJSONString(workflowLists);
	}
	
	/**
	 * 管理系统资产发布
	 * @param assetsId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/editStatusByAssetsId",method=RequestMethod.POST)
	@ResponseBody
	public String editStatusByAssetsId(HttpServletRequest request,Integer assetsId) throws Exception{
		logger.info("输入：  "+ "assetsId="+assetsId);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = assetsService.updateStatusByAssetsId(assetsId);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));

	}
	
	/**
	 * 管理系统条件查询所有资产
	 * @param pageSize  每页记录数
	 * @return
	 */
	@RequestMapping(value="/getMgAssets",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String getMgAssets(int pageNum, int pageSize, String shortName,  Integer assetType, String borrower, BigDecimal scale, BigDecimal financingCost, Integer status,String editTimeStart,String editTimeEnd){
		Assets assetsInfo = new Assets();
		if(shortName == null || "".equals(shortName)){
			assetsInfo.setShortName(null);
		}else{
			assetsInfo.setShortName(shortName);
		}		
		assetsInfo.setAssetType(assetType);
		if(borrower == null || "".equals(borrower)){
			assetsInfo.setBorrower(null);
		}else{
			assetsInfo.setBorrower(borrower);  
		}						 
		assetsInfo.setScale(scale);
		assetsInfo.setFinancingCost(financingCost);
		assetsInfo.setStatus(status);
		if(editTimeStart == null || "".equals(editTimeStart)){
			assetsInfo.setEditTimeStart(null); 
		}else{
			assetsInfo.setEditTimeStart(editTimeStart); 
		}
		if(editTimeEnd == null || "".equals(editTimeEnd)){
			assetsInfo.setEditTimeEnd(null);
		}else{
			assetsInfo.setEditTimeEnd(editTimeEnd);
		}			
		PageInfo<Assets> assetsLis = assetsService.getMgAssets(pageNum,pageSize,assetsInfo);
		logger.info("输出：   "+JSON.toJSONString(assetsLis,SerializerFeature.WriteMapNullValue));
		return JSON.toJSONString(assetsLis,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 *  管理系统获取单个子资产信息
	 * @param assetId assetType
	 * @return
	 */
	@RequestMapping(value="/queryMgSubAssetInfo",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryMgSubAssetInfo(int assetId,int assetType){
		logger.info("输入：  "+  "assetId="+assetId+"assetType="+assetType);
		AssetsDetialInfo subAssetsInfo = assetsService.getSubAssetInfoById(assetId,assetType);	
		logger.info("输出：   "+JSON.toJSONString(subAssetsInfo,SerializerFeature.WriteMapNullValue));
		return JSON.toJSONString(subAssetsInfo,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 *  单个子资产的项目材料列表
	 * @param assetId assetType
	 * @return
	 */
	@RequestMapping(value="/queryMgSubAttachments",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryMgSubAttachments(int assetId,int assetType){
		logger.info("输入：  "+  "assetId="+assetId+"assetType="+assetType);
		AssetsAttachmentInfo assetsAttachmentInfo = assetsService.getSubAssetAttachmentsById(assetId, assetType);
		logger.info("输出：   "+JSON.toJSONString(assetsAttachmentInfo,SerializerFeature.WriteMapNullValue));
		return JSON.toJSONString(assetsAttachmentInfo,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 *  更新子资产的项目材料列表
	 * @return
	 */
	@RequestMapping(value="/updateMgSubAttachments",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String updateMgSubAttachments(ProjectMeturialInfo projectMeturialInfo){
		logger.info("输入： "+" projectMeturialInfo="+projectMeturialInfo.toString());
		int flag = 0;
		//城投类资产
		if(projectMeturialInfo.getAssetType() == 1){
			StateInvestmentAsset stateInvestmentAsset = new StateInvestmentAsset();
			BeanUtils.copyProperties(projectMeturialInfo, stateInvestmentAsset);
			stateInvestmentAsset.setAid(projectMeturialInfo.getAssetId());
			flag = stateInvestmentAssetMapper.updateByAssetsidSelective(stateInvestmentAsset);
		}else if(projectMeturialInfo.getAssetType() == 2){  //上市公司资产
			IpoAsset ipoAsset = new IpoAsset();
			BeanUtils.copyProperties(projectMeturialInfo, ipoAsset);
			ipoAsset.setAid(projectMeturialInfo.getAssetId());
			flag = ipoAssetMapper.updateByAssetsidSelective(ipoAsset);
		}else if(projectMeturialInfo.getAssetType() == 3){  //供应链资产
			SupplyChainAsset supplyChainAsset = new SupplyChainAsset();
			BeanUtils.copyProperties(projectMeturialInfo, supplyChainAsset);
			supplyChainAsset.setAid(projectMeturialInfo.getAssetId());
			flag = supplyChainAssetMapper.updateByAssetsidSelective(supplyChainAsset);
		}else if(projectMeturialInfo.getAssetType() == 4){  //私募ABS
			PeAbsAsset peAbsAsset = new PeAbsAsset();
			BeanUtils.copyProperties(projectMeturialInfo, peAbsAsset);
			peAbsAsset.setAid(projectMeturialInfo.getAssetId());
			flag = peAbsAssetMapper.updateByAssetsidSelective(peAbsAsset);
		}else if(projectMeturialInfo.getAssetType() == 5){  //资产转让
			UsufructTransferAsset usufructTransferAsset = new UsufructTransferAsset();
			BeanUtils.copyProperties(projectMeturialInfo, usufructTransferAsset);
			usufructTransferAsset.setAid(projectMeturialInfo.getAssetId());
			flag = usufructTransferAssetMapper.updateByAssetsidSelective(usufructTransferAsset);
		}else if(projectMeturialInfo.getAssetType() == 6){  //其他工商类资产
			IndustryCommunityAsset industryCommunityAsset = new IndustryCommunityAsset();
			BeanUtils.copyProperties(projectMeturialInfo, industryCommunityAsset);
			industryCommunityAsset.setAid(projectMeturialInfo.getAssetId());
			flag = industryCommunityAssetMapper.updateByAssetsidSelective(industryCommunityAsset);
		}
		
		logger.info("输出：   "+JSON.toJSONString(flag));
		return JSON.toJSONString(flag);
	}
	
	/**
	 * 获取单个主资产信息
	 * @param assetId
	 * @return
	 */
	@RequestMapping(value="/queryAssetInfo",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryAssetsDetailInfo(int assetId){
		logger.info("输入：  "+ "assetId="+assetId);
		Assets asset = assetsService.getAssetsById(assetId);
		logger.info("输出：  "+ JSON.toJSONString(asset,SerializerFeature.WriteMapNullValue));
		return JSON.toJSONString(asset,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 分页查询所有资产
	 * @param pageSize  每页记录数
	 * @return
	 */
	@RequestMapping(value="/queryAllAssets",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryAllAssets(int pageNum,int pageSize){
		logger.info("输入：  "+ "pageNum="+pageNum+ "  pageSize="+pageSize);
		PageInfo<Assets> assetsLis = assetsService.getAllAssets(pageNum,pageSize);
		logger.info("输出：   "+JSON.toJSONString(assetsLis,SerializerFeature.WriteMapNullValue));
		return JSON.toJSONString(assetsLis,SerializerFeature.WriteMapNullValue);
	}
	
	
	/**
	 * 条件查询所有资产
	 * @param pageSize  每页记录数
	 * @return
	 */
	@RequestMapping(value="/getAssets",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String getAssets(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
			@RequestParam("assetType") Integer assetType,@RequestParam("tenorRoleType") Integer tenorRoleType,@RequestParam("scaleType") Integer scaleType,@RequestParam("financingCostStart") BigDecimal financingCostStart,@RequestParam("financingCostEnd") BigDecimal financingCostEnd){
		logger.info("输入：  "+ "pageNum="+pageNum+ "  pageSize="+pageSize+ "assetType="+assetType+ "tenorRoleType="+tenorRoleType+ "scaleType="+scaleType+ "financingCostStart="+financingCostStart+ "financingCostEnd="+financingCostEnd);
		Assets assetsInfo = new Assets();
		assetsInfo.setAssetType(assetType);
		assetsInfo.setScaleType(scaleType);
		assetsInfo.setTenorRoleType(tenorRoleType);
		assetsInfo.setFinancingCostStart(financingCostStart);
		assetsInfo.setFinancingCostEnd(financingCostEnd);
		if(tenorRoleType == null || tenorRoleType == 0){
			assetsInfo.setTenorType(null);
		}
		if(tenorRoleType != null && tenorRoleType == 1){
			assetsInfo.setTenorDayStart(0);
			assetsInfo.setTenorDayEnd(180);
		}
        if(tenorRoleType != null && tenorRoleType == 2){
        	assetsInfo.setTenorDayStart(181);
			assetsInfo.setTenorDayEnd(365);
		}
        if(tenorRoleType != null && tenorRoleType == 3){
        	assetsInfo.setTenorDayStart(366);
        }        
        if(scaleType == null || scaleType == 0){
			assetsInfo.setScaleType(null);
		}
        if(scaleType != null && scaleType == 1){
			assetsInfo.setScaleStart(new BigDecimal(0.00));
			assetsInfo.setScaleEnd(new BigDecimal(9999999));			
		}
        if(scaleType != null && scaleType == 2){
			assetsInfo.setScaleStart(new BigDecimal(10000000));
			assetsInfo.setScaleEnd(new BigDecimal(49999999));
		}
        if(scaleType != null && scaleType == 3){
			assetsInfo.setScaleStart(new BigDecimal(50000000));
			assetsInfo.setScaleEnd(new BigDecimal(99999999));
		}
        if(scaleType != null && scaleType == 4){
			assetsInfo.setScaleStart(new BigDecimal(100000000));
		}	
		PageInfo<Assets> assetsLis = assetsService.getAssets(pageNum,pageSize,assetsInfo);
		logger.info("输出：   "+JSON.toJSONString(assetsLis,SerializerFeature.WriteMapNullValue));
		return JSON.toJSONString(assetsLis,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 查询我的所有资产
	 * @param pageSize  每页记录数
	 * @return
	 */
	@RequestMapping(value="/getMyAssets",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String getMyAssets(int pageNum,int pageSize, int orgId, String roleType){
		logger.info("输入：  "+ "pageNum="+pageNum+ "  pageSize="+pageSize+ "orgId="+orgId+"roleType="+roleType);
		PageInfo<Assets> assetsLis = assetsService.getMyAssets(pageNum,pageSize,orgId,roleType);
		logger.info("输出：   "+JSON.toJSONString(assetsLis,SerializerFeature.WriteMapNullValue));
		return JSON.toJSONString(assetsLis,SerializerFeature.WriteMapNullValue);
	}
	
	
	
	/**
	 * 获取单个子资产信息
	 * @param assetId assetType
	 * @return
	 */
	@RequestMapping(value="/querySubAssetInfo",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String querySubAssetInfo(int assetId,int assetType){
		logger.info("输入：  "+  "assetId="+assetId+"assetType="+assetType);
		AssetsDetialInfo subAssetsInfo = assetsService.getSubAssetInfoById(assetId,assetType);	
		logger.info("输出：   "+JSON.toJSONString(subAssetsInfo,SerializerFeature.WriteMapNullValue));
		return JSON.toJSONString(subAssetsInfo,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 资产立项
	 * @return
	 */
	@RequestMapping(value="/assetToProject",method=RequestMethod.POST)
	@ResponseBody
	public String  assetToProject(Assets assetProjectInfo){
		logger.info("输入：  "+  "assetProjectInfo="+JSON.toJSONString(assetProjectInfo));
		assetProjectInfo.setWfStatus(1);
		assetProjectInfo.setStatus(3);
		int flag = assetsService.assetToProject(assetProjectInfo);
		return JSON.toJSONString(flag+"");
	}
	
	
	/**
	 * 备案过程
	 * @return
	 */
	@RequestMapping(value="/putOnRecord",method=RequestMethod.POST)
	@ResponseBody
	public String  putOnRecord(RecordLog recordLogInfo){
		logger.info("输入：  "+  "recordLogInfo="+JSON.toJSONString(recordLogInfo));
		int flag = assetsService.putOnRecordStep(recordLogInfo);		
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	
	/**
	 * 平台审核备案（该功能已移除）
	 * @param checkRecordInfo
	 * @return
	 */
/*	@RequestMapping(value="/checkRecord",method=RequestMethod.POST)
	@ResponseBody
	public String  checkRecord(Assets checkRecordInfo){
		logger.info("输入：  "+  "checkRecordInfo="+JSON.toJSONString(checkRecordInfo));
		int flag = assetsService.checkRecord(checkRecordInfo);
		return JSON.toJSONString(flag+"");
	}*/
	
	
	/**
     * 查询资产在某个步骤的协议文档
     * @param assetId  资产id
     * @param stepId  流程步骤id
     * @return
     */
	@RequestMapping(value="/queryRecordLogs",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryRecordLogs(@RequestParam("assetId") String assetId,@RequestParam("stepId") String stepId){
		logger.info("输入：  "+ "assetId="+assetId+ "  stepId="+stepId);
		RecordLog recordLogsList = assetsService.queryRecordLog(assetId, stepId);
		logger.info("输出：   "+JSON.toJSONString(recordLogsList));
		return JSON.toJSONString(recordLogsList,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 将资产状态改为 6 完结
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/changeToOverStatus",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String changeToOverStatus(@RequestParam("assetId") int assetId){
		logger.info("输入：  "+ "assetId="+assetId);
		int flag = assetsService.updateToOverStatusByAssetsId(assetId);
		logger.info("输出：  "+ JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag),SerializerFeature.WriteMapNullValue);
	}
	
	/**
     * 查询基础资产列表
     * @return
     */
	@RequestMapping(value="/queryAccountReceivedList",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryAccountReceivedList(BaseAssetsSearchVo baseAssetsSearchVo){
		logger.info("输入：  "+ baseAssetsSearchVo.toString());
		PageInfo<PeAccountsReceivable> baseAssetList = assetsService.queryAccountReceivedList(baseAssetsSearchVo); 
		logger.info("输出：   "+JSON.toJSONString(baseAssetList));
		return JSON.toJSONString(baseAssetList,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 资产包的录入
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addAssetsPakage",method=RequestMethod.POST)
	@ResponseBody
	public String addPeAbsAsset(HttpServletRequest request,AssetsDetialInfo assetsDetialInfo) throws Exception{	
		logger.info("输入：  "+  "assetsDetialInfo="+JSON.toJSONString(assetsDetialInfo));
		//TokenUtil.tokenOperate(request,userMapper);
		int flag;
		int falseflag;
		
		if(assetsDetialInfo.getIssueDate() == null || "".equals(assetsDetialInfo.getIssueDate())){
			assetsDetialInfo.setIssueDate(null);
		}
		if(assetsDetialInfo.getAid() != null && !"".equals(assetsDetialInfo.getAid())){//修改
			flag = assetsService.editPeAbsAsset(assetsDetialInfo);
			if(flag == 1){
				return JSON.toJSONString(assetsDetialInfo.getAid());
			}else{
				falseflag = 0;
				return JSON.toJSONString(falseflag);
			}
		}else{//添加
			assetsDetialInfo.setStatus(3);   //资产包一旦建立即到“备案”状态
			assetsDetialInfo.setWfType(1);  //备案流程类型
			assetsDetialInfo.setWfId(8);    //当前备案流程代码
			assetsDetialInfo.setWfStatus(0);  //当前备案流程状态
			flag = assetsService.addPeAbsAsset(assetsDetialInfo);
			if(flag == 1){
				//保存资产包中基础资产新增记录
				assetsService.saveAssetChangeHisSelective(assetsDetialInfo.getAid().toString(),BigDecimal.ZERO,assetsDetialInfo.getCreateName(),"0");

				//保存资产包中资金的新增记录
				assetsService.saveFundChangeHisSelective(assetsDetialInfo.getAid().toString(),assetsDetialInfo.getScale(),assetsDetialInfo.getCreateName(),"0");

				return JSON.toJSONString(assetsDetialInfo.getAid());
			}else{
				falseflag = 0;
				return JSON.toJSONString(falseflag);
			}
		}
	}
	
	/**
	 * 关联基础资产
	 * @return
	 */
	@RequestMapping(value="/associateBaseAsset",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String associateBaseAsset(BaseAssetsSearchVo baseAssetsSearchVo){
		logger.info("输入：  "+ baseAssetsSearchVo.toString());
		//查询可以用来替换的基础资产（多个应收债务人debtorName,逗号隔开）(状态为“待收”)
		List<PeAccountsReceivable> couldUseList = assetsService.queryCouldReplaceByMul(baseAssetsSearchVo);
		PeAbsAsset assets = peAbsAssetMapper.selectByPrimaryKey(baseAssetsSearchVo.getAid());
		BigDecimal choiceAssetScale = new BigDecimal(0);
		int choiceAssetCount = 0;
		StringBuffer debtorNames = new StringBuffer();
		StringBuffer baseAssetIds = new StringBuffer();
		BigDecimal assetAmount = new BigDecimal(0);
		if(assets != null){
		     for(PeAccountsReceivable par : couldUseList){

				if(choiceAssetScale.subtract(assets.getWarnScale()).doubleValue() < 0){
					PeAccountsReceivable record = new PeAccountsReceivable();
					record.setAid(assets.getAid());
					record.setId(par.getId());
					peAccountsReceivableMapper.updateAssetAid(record);
					choiceAssetScale = choiceAssetScale.add(par.getBillBalance());
					choiceAssetCount++;
					baseAssetIds.append(","+par.getId());
					assetAmount = assetAmount.add(par.getBillBalance());
					if(debtorNames.indexOf(par.getDebtorName()) == -1){
						debtorNames.append(","+par.getDebtorName());
					}

				}else{
					break;
				}
			}
			//如果基础资产总金额小于预警金额，将资产预警状态更新为“预警”
			if(choiceAssetScale.subtract(assets.getWarnScale()).doubleValue() < 0){
				assetsService.warnAssetPackage(assets.getAid());
			}

			//保存资产包中基础资产关联记录
			assetsService.saveAssetChangeHisSelective(assets.getAid().toString(),assetAmount,baseAssetsSearchVo.getCreateName(),"1");


			if(!"".equals(baseAssetIds.toString())){
				//保存资产包中原始基础资产
				assetsService.saveAssetRelationBatch(assets.getAid().toString(),baseAssetIds.toString().substring(1));
			}

			//返回信息
			baseAssetsSearchVo.setChoiceAssetScale(choiceAssetScale);
			baseAssetsSearchVo.setChoiceAssetCount(choiceAssetCount);
			if(!"".equals(debtorNames.toString())){
				baseAssetsSearchVo.setDebtorName(debtorNames.toString().substring(1));
			}
		}
		return JSON.toJSONString(baseAssetsSearchVo);
	}
	
	/**
	 * 基础资产池统计数据
	 * @return
	 */
	@RequestMapping(value="/baseAssetStatisticalData",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String baseAssetStatisticalData(){
		logger.info("输入：  "+ "");
		 List<StatisticalData> sdList = peAccountsReceivableMapper.queryStatisticalDataList();
		logger.info("输出：   "+JSON.toJSONString(sdList));
		return JSON.toJSONString(sdList,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 资产包列表统计数据
	 * @return
	 */
	@RequestMapping(value="/assetsPackageStatisticalData",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String assetsPackageStatisticalData(){
		logger.info("输入：  "+ "");
		 List<AssetsPackage> apList = assetsService.assetsPackageStaticData();
		logger.info("输出：   "+JSON.toJSONString(apList));
		return JSON.toJSONString(apList,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 资产包详情
	 * @return
	 */
	@RequestMapping(value="/queryAssetsPackageDetail",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryAssetsPackageDetail(int assetId){
		logger.info("输入： assetId= "+ assetId);
		AssetPackageVo assetPackage = assetsService.queryAssetPackageDetail(assetId);
		logger.info("输出：   "+JSON.toJSONString(assetPackage));
		return JSON.toJSONString(assetPackage,SerializerFeature.WriteMapNullValue);
	}
	
	/**
     * 查询资产包下的基础资产列表
     * @return
     */
	@RequestMapping(value="/queryBaseAssetsByAssetId",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryBaseAssetsByAssetId(BaseAssetsSearchVo baseAssetsSearchVo){
		logger.info("输入：  "+ baseAssetsSearchVo.toString());
		PageInfo<PeAccountsReceivable> accountReceivedList = assetsService.queryBaseAssetsByAssetId(baseAssetsSearchVo);
		logger.info("输出：   "+JSON.toJSONString(accountReceivedList));
		return JSON.toJSONString(accountReceivedList,SerializerFeature.WriteMapNullValue);
	}
	
	/**
     * 查询可替换的基础资产列表
     * @return
     */
	@RequestMapping(value="/queryCouldReplaceAssetList",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryCouldReplaceAssetList(BaseAssetsSearchVo baseAssetsSearchVo){
	    logger.info("输入：  "+ baseAssetsSearchVo.toString());
	    //可以用的基础资产(状态为“待收”)
	    PageInfo<PeAccountsReceivable> couldUseList = assetsService.queryCouldReplaceBySimple(baseAssetsSearchVo);
		logger.info("输出：   "+JSON.toJSONString(couldUseList));
		return JSON.toJSONString(couldUseList,SerializerFeature.WriteMapNullValue);
	}
	
	/**
     * 基础资产变更
     * @return
     */
	@RequestMapping(value="/changeBaseAsset",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String changeBaseAsset(BaseAssetChangeVo baseAssetChangeVo){
	    logger.info("输入：  "+ baseAssetChangeVo.toString());
	    int count = assetsService.changeBaseAsset(baseAssetChangeVo);
		logger.info("输出：   "+JSON.toJSONString(count));
		return JSON.toJSONString(count,SerializerFeature.WriteMapNullValue);
	}
	
	/**
     * 查询已选择的需要被替换以及用来替换的资产列表
     * @return
     */
	@RequestMapping(value="/queryChoiceAssetList",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryChoiceAssetList(BaseAssetChangeVo baseAssetChangeVo){
	    logger.info("输入：  "+ baseAssetChangeVo.toString());
	    Map<String, Object> map = new HashMap<String, Object>();
		List<PeAccountsReceivable> changeBeforeList = new ArrayList<PeAccountsReceivable>();
		List<PeAccountsReceivable> changeAfterList = new ArrayList<PeAccountsReceivable>();
		if(!"".equals(baseAssetChangeVo.getChangeBeforeIds()) && baseAssetChangeVo.getChangeBeforeIds() != null){
			changeBeforeList = assetsService.queryListByIds(baseAssetChangeVo.getChangeBeforeIds());
			map.put("changeBeforeList", changeBeforeList);
		}else{
			map.put("changeBeforeList", null);
		}

		if(!"".equals(baseAssetChangeVo.getChangeAfterIds()) && baseAssetChangeVo.getChangeAfterIds() != null){
			changeAfterList= assetsService.queryListByIds(baseAssetChangeVo.getChangeAfterIds());
			map.put("changeAfterList", changeAfterList);
		}else{
			map.put("changeAfterList", null);
		}

		logger.info("输出：   "+JSON.toJSONString(map));
		return JSON.toJSONString(map,SerializerFeature.WriteMapNullValue);
	}
	
	/**
     * 资产包列表筛选
     * @return
     */
	@RequestMapping(value="/queryAssetPackageList",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryAssetPackageList(AssetPackageSearchVo assetPackageSearchVo){
	    logger.info("输入：  "+ assetPackageSearchVo.toString());
	    PageInfo<AssetPackageVo> list = assetsService.queryAssetPackageList(assetPackageSearchVo);
		logger.info("输出：   "+JSON.toJSONString(list));
		return JSON.toJSONString(list,SerializerFeature.WriteMapNullValue);
	}

	/**
	 * 删除重选
	 * @return
	 */
	@RequestMapping(value="/deleteToChoiceAgain",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String deleteToChoiceAgain(BaseAssetsSearchVo baseAssetsSearchVo){
		logger.info("输入：  "+ baseAssetsSearchVo.toString());
		int count = assetsService.deleteToChoiceAgain(baseAssetsSearchVo);
		logger.info("输出：   "+JSON.toJSONString(count));
		return JSON.toJSONString(count,SerializerFeature.WriteMapNullValue);
	}

	/**
	 * 资产包兑付
	 * @return
	 */
	@RequestMapping(value="/cashAssetPackage",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String cashAssetPackage(int aid){
		logger.info("输入：  "+ aid);
		int count = assetsService.cashAssetPackage(aid);
		logger.info("输出：   "+JSON.toJSONString(count));
		return JSON.toJSONString(count,SerializerFeature.WriteMapNullValue);
	}

	/**
	 * 完结资产包
	 * @return
	 */
	@RequestMapping(value="/overAssetPackage",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String overAssetPackage(BaseAssetsSearchVo baseAssetsSearchVo){
		logger.info("输入：  "+ baseAssetsSearchVo.toString());
		//资产状态修改为“完结”
		int count = assetsService.updateToOverStatusByAssetsId(baseAssetsSearchVo.getAid());
		//解除关联的基础资产（待收和逾期状态）
		assetsService.removeBaseAssetAfterOver(baseAssetsSearchVo);
		logger.info("输出：   "+JSON.toJSONString(count));
		return JSON.toJSONString(count,SerializerFeature.WriteMapNullValue);
	}

	/**
	 * 资产包失效
	 * @return
	 */
	@RequestMapping(value="/loseEffAssetPackage",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String loseEffAssetPackage(BaseAssetsSearchVo baseAssetsSearchVo){
		logger.info("输入：  "+ baseAssetsSearchVo.toString());
		//资产状态修改为“失效”
		int count = assetsService.updateToInvalideStatusByAssetsId(baseAssetsSearchVo.getAid());
		//解除关联的基础资产（待收和逾期状态）
		assetsService.removeBaseAssetAfterOver(baseAssetsSearchVo);
		logger.info("输出：   "+JSON.toJSONString(count));
		return JSON.toJSONString(count,SerializerFeature.WriteMapNullValue);
	}

	/**
	 * 资产变动列表
	 * @return
	 */
	@RequestMapping(value="/queryAssetChangeHisList",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryAssetChangeHisList(ChangeHistoryVo changeHistoryVo){
		logger.info("输入：  "+ changeHistoryVo.toString());
		PageInfo<PeAssetChangeHistory> assetChangeHisList = assetsService.queryAssetChangeHisListByAid(changeHistoryVo);
		logger.info("输出：   "+JSON.toJSONString(assetChangeHisList));
		return JSON.toJSONString(assetChangeHisList,SerializerFeature.WriteMapNullValue);
	}

	/**
	 * 查询总资产量和时间范围内的资产量
	 * @param changeHistoryVo
	 * @return
	 */
	@RequestMapping(value="/queryAssetAmount",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryAssetAmount(ChangeHistoryVo changeHistoryVo){
		logger.info("输入：  "+ changeHistoryVo.toString());
		Map map = assetsService.calculateAssetAmount(changeHistoryVo);
		logger.info("输出：   "+JSON.toJSONString(map));
		return JSON.toJSONString(map,SerializerFeature.WriteMapNullValue);
	}

	/**
	 * 资金变动列表
	 * @return
	 */
	@RequestMapping(value="/queryFundChangeHisList",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryFundChangeHisList(ChangeHistoryVo changeHistoryVo){
		logger.info("输入：  "+ changeHistoryVo.toString());
		PageInfo<PeFundChangeHistory> fundChangeHisList = assetsService.queryFundChangeHisListByAid(changeHistoryVo);
		logger.info("输出：   "+JSON.toJSONString(fundChangeHisList));
		return JSON.toJSONString(fundChangeHisList,SerializerFeature.WriteMapNullValue);
	}

	/**
	 * 查询总资金量和时间范围内的资金量
	 * @return
	 */
	@RequestMapping(value="/queryFundAmount",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryFundAmount(ChangeHistoryVo changeHistoryVo){
		logger.info("输入：  "+ changeHistoryVo.toString());
		Map map = assetsService.calculateFundAmount(changeHistoryVo);
		logger.info("输出：   "+JSON.toJSONString(map));
		return JSON.toJSONString(map,SerializerFeature.WriteMapNullValue);
	}




}
