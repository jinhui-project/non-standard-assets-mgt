package com.jinhui.service.assets.impl;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import java.util.List;

import javax.annotation.Resource;

import com.jinhui.constant.DictConstant;
import com.jinhui.controller.assets.vo.*;
import com.jinhui.mapper.assets.*;
import com.jinhui.mapper.dict.DataDictionaryMapper;
import com.jinhui.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.mapper.organization.OrganizationMapper;
import com.jinhui.mapper.workflow.WorkflowMapper;
import com.jinhui.mapper.workflowstep.WorkflowStepMapper;
import com.jinhui.service.assets.AssetsService;
import com.jinhui.service.upLoad.FileinfosService;
import com.jinhui.util.ReqActionContains;
import com.jinhui.util.UtilTool;

@Service("assetsService")
public class AssetsServiceImpl implements AssetsService {

	@Resource
	private AssetsMapper assetsMapper;
	
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
	@Autowired
	private FileinfosService fileinfosService;

	@Resource
	private RecordLogMapper recordLogMapper;
	
	@Resource
	private OrganizationMapper organizationMapper;
	
	@Resource
	private WorkflowMapper workflowMapper;
	
	@Resource
	private WorkflowStepMapper workflowStepMapper;
	
	@Resource
	private IndustryCommunityAssetMapper industryCommunityAssetMapper;
	
	@Resource
	private PeAccountsReceivableMapper peAccountsReceivableMapper;

	@Resource
	private PeAssetRelationMapper peAssetRelationMapper;

	@Resource
	private PeAssetChangeHistoryMapper peAssetChangeHistoryMapper;

	@Resource
	private PeFundChangeHistoryMapper peFundChangeHistoryMapper;

	@Resource
	private DataDictionaryMapper dataDictionaryMapper;

	/**
	 * 字符串转日期
	 * @param sdate
	 * @return
	 */
	public Date stringTranToDate(String sdate){
		if(sdate == null || "".equals(sdate)){
			return null;
		}else{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Date date = null;
			try {
				date = sdf.parse(sdate);  
  
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}		
	}
	
	/**
	 * 日期转字符串
	 * @param date
	 * @return
	 */
	public String dateTranToString(Date date){
		if(date == null || "".equals(date)){
			return null;
		}else{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			String str=sdf.format(date); 		
			return str;	
		}			
	}
	 
	/**
	 * 
	 * @param tenor
	 * @param tenorType
	 * @return
	 */
	public int getTenorDay(int tenor,int tenorType){
		int tenorDay = 0;
		if(tenorType==0){
			return 0;
		}
		if(tenorType==1){
			tenorDay = tenor * 1;
		}
        if(tenorType==2){
        	tenorDay = tenor * 30;
		}
        if(tenorType==3){
        	tenorDay = tenor * 365;
        }		
		return tenorDay;		
	}
	
	public List<Assets> getMgAssetsForProduct(String assetName){
		return assetsMapper.getMgAssetsForProduct(assetName);
	}
	/**
	 * 管理系统发布
	 */
	public int updateStatusByAssetsId(Integer assetsId){				
		return assetsMapper.updateStatusByAssetsId(assetsId);		
	}
	
	public List<RecordOrganization> getOrganizationListByRecord(){
		return organizationMapper.getOrganizationListByRecord();
	}
	
	
	public List<Workflow> getWorkflowListByGid(Integer orgId){
		return workflowMapper.getWorkflowListByGid(orgId);
	}
	
	public List<WorkflowStep> getWorkflowStep(){
		return workflowStepMapper.getWorkflowStepList();
	}
	
	
	/**
	 * 资产立项
	 */
	@Override
	public int assetToProject(Assets asset){
		return assetsMapper.updateByPrimaryKeySelective(asset);				
	}
	
	/**
	 * 备案过程
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int putOnRecordStep(RecordLog recordLog){
		RecordLog record = recordLogMapper.queryRecordLog(recordLog.getAid()+"", recordLog.getStepId()+"");
		int flag = 0;
		if(record == null){  //如果不存在记录则新增
			flag = recordLogMapper.insert(recordLog);
		}else{   //如果存在记录则更新改记录
			record.setFileStr(recordLog.getFileStr());
			flag = recordLogMapper.updateByPrimaryKey(record);
		}
		/**
		 * 插入文件fileinfos表成功
		 * 更新备案状态
		 * 
		 */
		if(flag == 1 && "0".equals(recordLog.getFlag())){
			Assets asset = new Assets();
			asset.setId(recordLog.getAid());
			asset.setWfId(recordLog.getStepId());
			asset.setWfStatus(recordLog.getWfStatus());
			asset.setMaterials(recordLog.getMaterials());
			asset.setDueReports(recordLog.getDueReports());
			asset.setPassResult(recordLog.getPassResult());
			asset.setPassContent(recordLog.getPassContent());
			flag = assetsMapper.updateByPrimaryKeySelective(asset);
		}		
		return flag;
	}
	
	
	
	/**
	 * 平台审核备案
	 */
	@Override
	public int checkRecord(Assets asset){
		return assetsMapper.updateByPrimaryKeySelective(asset);				
	}
	
	@Override
	public Assets getAssetsById(int assetId) {
		return assetsMapper.selectByPrimaryKey(assetId);
	}
	
	@Override
	public AssetsDetialInfo getSubAssetInfoById(int assetId,int assetType) {
		AssetsDetialInfo assetsDetialInfo = new AssetsDetialInfo();
		if(assetType == 1){
			StateInvestmentAsset stateInvestmentAsset = stateInvestmentAssetMapper.selectByAssetId(assetId);
			assetsDetialInfo = stateInvestmentAssetTrantoAssetsDetialInfo(stateInvestmentAsset);
		}
		if(assetType == 2){
			IpoAsset ipoAsset = ipoAssetMapper.selectByAssetId(assetId);
			assetsDetialInfo = ipoAssetTrantoAssetsDetialInfo(ipoAsset);
		}
		if(assetType == 3){
			SupplyChainAsset supplyChainAsset = supplyChainAssetMapper.selectByAssetId(assetId);
			assetsDetialInfo = supplyChainAssetTrantoAssetsDetialInfo(supplyChainAsset);
		}
		if(assetType == 4){
			PeAbsAsset peAbsAsset = peAbsAssetMapper.selectByAssetId(assetId);
			assetsDetialInfo = peAbsAssetTrantoAssetsDetialInfo(peAbsAsset);
		}
		if(assetType == 5){
			UsufructTransferAsset usufructTransferAsset = usufructTransferAssetMapper.selectByAssetId(assetId);
			assetsDetialInfo = usufructTransferAssetTrantoAssetsDetialInfo(usufructTransferAsset);
		}
		if(assetType == 6){
			IndustryCommunityAsset industryCommunityAsset = industryCommunityAssetMapper.selectByAssetId(assetId);
			assetsDetialInfo = industryCommunityAssetTrantoAssetsDetialInfo(industryCommunityAsset);
		}
		
		return assetsDetialInfo;
	}
	
	@Override
	public AssetsAttachmentInfo getSubAssetAttachmentsById(int assetId,int assetType) {
		AssetsAttachmentInfo assetsAttachmentInfo = new AssetsAttachmentInfo();
		HashMap fileMap = new LinkedHashMap();
		//城投类资产
		if(assetType == 1){
			StateInvestmentAsset stateInvestmentAsset = stateInvestmentAssetMapper.selectByAssetId(assetId);
			fileMap = queryStateInvestAttachment(fileMap,stateInvestmentAsset);
		}
		//上市公司资产
		if(assetType == 2){
			IpoAsset ipoAsset = ipoAssetMapper.selectByAssetId(assetId);
			fileMap = queryIpoAttachment(fileMap,ipoAsset);
		}
		//供应链资产
		if(assetType == 3){
			SupplyChainAsset supplyChainAsset = supplyChainAssetMapper.selectByAssetId(assetId);
			fileMap = querySupplyChainAttachment(fileMap,supplyChainAsset);
		}
		//私募ABS
		if(assetType == 4){
			PeAbsAsset peAbsAsset = peAbsAssetMapper.selectByAssetId(assetId);
			fileMap = queryPeAbsAttachment(fileMap,peAbsAsset);
		}
		//资产转让
		if(assetType == 5){
			UsufructTransferAsset usufructTransferAsset = usufructTransferAssetMapper.selectByAssetId(assetId);
			fileMap = queryUsufructTransferAttachment(fileMap,usufructTransferAsset);
		}
		//其他工商类资产
		if(assetType == 6){
			IndustryCommunityAsset industryCommunityAsset = industryCommunityAssetMapper.selectByAssetId(assetId);
			fileMap = queryIndustCommuAttachment(fileMap,industryCommunityAsset);
		}
		assetsAttachmentInfo.setAttachment(fileMap);
		return assetsAttachmentInfo;
	}
	
	private HashMap queryPeAbsAttachment(HashMap fileMap, PeAbsAsset peAbsAsset){
		fileMap = fillMap(fileMap,peAbsAsset.getAbsRatingReport(),"absRatingReport",1);
		fileMap = fillMap(fileMap,peAbsAsset.getOtherFile(),"otherFile",2);
		return fileMap;
	}
	
	private HashMap queryUsufructTransferAttachment(HashMap fileMap, UsufructTransferAsset usufructTransferAsset){
		fileMap = fillMap(fileMap,usufructTransferAsset.getRelatedAgreement(),"relatedAgreement",1);
		fileMap = fillMap(fileMap,usufructTransferAsset.getOtherFile(),"otherFile",2);
		return fileMap;
	}
	
	private HashMap querySupplyChainAttachment(HashMap fileMap, SupplyChainAsset supplyChainAsset){
		fileMap = fillMap(fileMap,supplyChainAsset.getLisence(),"lisence",1);
		fileMap = fillMap(fileMap,supplyChainAsset.getOrgInstCode(),"orgInstCode",2);
		fileMap = fillMap(fileMap,supplyChainAsset.getTaxReg(),"taxReg",3);
		fileMap = fillMap(fileMap,supplyChainAsset.getArticle(),"article",4);
		fileMap = fillMap(fileMap,supplyChainAsset.getCaptialVer(),"captialVer",5);
		fileMap = fillMap(fileMap,supplyChainAsset.getFeasibility(),"feasibility",6);
		fileMap = fillMap(fileMap,supplyChainAsset.getAuditReport(),"auditReport",7);
		fileMap = fillMap(fileMap,supplyChainAsset.getYearTransaction(),"yearTransaction",8);
		fileMap = fillMap(fileMap,supplyChainAsset.getOtherFile(),"otherFile",9);
		return fileMap;
	}
	
	private HashMap queryIndustCommuAttachment(HashMap fileMap, IndustryCommunityAsset industryCommunityAsset){
		fileMap = fillMap(fileMap,industryCommunityAsset.getLisence(),"lisence",1);
		fileMap = fillMap(fileMap,industryCommunityAsset.getOrgInstCode(),"orgInstCode",2);
		fileMap = fillMap(fileMap,industryCommunityAsset.getTaxReg(),"taxReg",3);
		fileMap = fillMap(fileMap,industryCommunityAsset.getLeId(),"leId",4);
		fileMap = fillMap(fileMap,industryCommunityAsset.getBankCard(),"bankCard",5);
		fileMap = fillMap(fileMap,industryCommunityAsset.getAcApproval(),"acApproval",6);
		fileMap = fillMap(fileMap,industryCommunityAsset.getArticle(),"article",7);
		fileMap = fillMap(fileMap,industryCommunityAsset.getCaptialVer(),"captialVer",8);
		fileMap = fillMap(fileMap,industryCommunityAsset.getCreditReport(),"creditReport",9);
		fileMap = fillMap(fileMap,industryCommunityAsset.getFeasibility(),"feasibility",10);
		fileMap = fillMap(fileMap,industryCommunityAsset.getAuditReport(),"auditReport",11);
		fileMap = fillMap(fileMap,industryCommunityAsset.getFinanceStatement(),"financeStatement",12);
		fileMap = fillMap(fileMap,industryCommunityAsset.getOtherFile(),"otherFile",13);
		return fileMap;
	}
	
	private HashMap queryIpoAttachment(HashMap fileMap, IpoAsset ipoAsset){
		fileMap = fillMap(fileMap,ipoAsset.getLisence(),"lisence",1);
		fileMap = fillMap(fileMap,ipoAsset.getOrgInstCode(),"orgInstCode",2);
		fileMap = fillMap(fileMap,ipoAsset.getTaxReg(),"taxReg",3);
		fileMap = fillMap(fileMap,ipoAsset.getLeId(),"leId",4);
		fileMap = fillMap(fileMap,ipoAsset.getBankCard(),"bankCard",5);
		fileMap = fillMap(fileMap,ipoAsset.getAcApproval(),"acApproval",6);
		fileMap = fillMap(fileMap,ipoAsset.getArticle(),"article",7);
		fileMap = fillMap(fileMap,ipoAsset.getCaptialVer(),"captialVer",8);
		fileMap = fillMap(fileMap,ipoAsset.getCreditReport(),"creditReport",9);
		fileMap = fillMap(fileMap,ipoAsset.getFeasibility(),"feasibility",10);
		fileMap = fillMap(fileMap,ipoAsset.getAuditReport(),"auditReport",11);
		fileMap = fillMap(fileMap,ipoAsset.getFinanceStatement(),"financeStatement",12);
		fileMap = fillMap(fileMap,ipoAsset.getOtherFile(),"otherFile",13);
		return fileMap;
	}
	
	private HashMap queryStateInvestAttachment(HashMap fileMap, StateInvestmentAsset stateInvestmentAsset){
		fileMap = fillMap(fileMap,stateInvestmentAsset.getLisence(),"lisence",1);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getOrgInstCode(),"orgInstCode",2);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getTaxReg(),"taxReg",3);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getLeId(),"leId",4);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getBankCard(),"bankCard",5);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getAcApproval(),"acApproval",6);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getArticle(),"article",7);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getCaptialVer(),"captialVer",8);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getCreditReport(),"creditReport",9);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getFeasibility(),"feasibility",10);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getAuditReport(),"auditReport",11);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getFinanceStatement(),"financeStatement",12);
		fileMap = fillMap(fileMap,stateInvestmentAsset.getOtherFile(),"otherFile",13);
		return fileMap;
	}
	
	private HashMap fillMap(HashMap fileMap, String fileId, String key, int index){
		if(fileId != null && !"".equals(fileId)){
			if(fileId.contains(",")){
				List<AttachmentInfo>  fileinfosLists = new ArrayList<AttachmentInfo>();
				String[] ids = fileId.split(",");
				for (int i = 0; i < ids.length; i++){
					Fileinfos fileinfo = fileinfosService.queryFileinfos(Long.parseLong(ids[i]));
					AttachmentInfo attachmentInfo = new AttachmentInfo();
					BeanUtils.copyProperties(fileinfo, attachmentInfo);
					attachmentInfo.setIndex(index);
					attachmentInfo.setMeturialKey(key);
					attachmentInfo.setMeturialName(ReqActionContains.muterialMaps().get(key).toString());
					//attachmentInfo.setEditTime(UtilTool.stampToDate(fileinfo.getUploadTime()));
					fileinfosLists.add(attachmentInfo);
				}
				fileMap.put(key, fileinfosLists);
			}else{
				List<AttachmentInfo>  fileinfosLists = new ArrayList<AttachmentInfo>();
				Fileinfos fileinfo = fileinfosService.queryFileinfos(Long.parseLong(fileId));
				AttachmentInfo attachmentInfo = new AttachmentInfo();
				BeanUtils.copyProperties(fileinfo, attachmentInfo);
				attachmentInfo.setIndex(index);
				attachmentInfo.setMeturialKey(key);
				attachmentInfo.setMeturialName(ReqActionContains.muterialMaps().get(key).toString());
				//attachmentInfo.setEditTime(UtilTool.stampToDate(fileinfo.getUploadTime()));
				fileinfosLists.add(attachmentInfo);
				fileMap.put(key, fileinfosLists);
			}
		}else{
			List<AttachmentInfo>  fileinfosLists = new ArrayList<AttachmentInfo>();
			AttachmentInfo attachmentInfo = new AttachmentInfo();
			attachmentInfo.setIndex(index);
			attachmentInfo.setMeturialKey(key);
			attachmentInfo.setMeturialName(ReqActionContains.muterialMaps().get(key).toString());
			fileinfosLists.add(attachmentInfo);
			fileMap.put(key, fileinfosLists);
		}	
		return fileMap;
	}

	public PageInfo<Assets> getAllAssets(Integer pageNo, Integer pageSize) {		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		
		List<Assets> list = assetsMapper.getAllAssets();
		return new PageInfo<Assets>(list);
	}
	
	public PageInfo<Assets> getMgAssets(Integer pageNo, Integer pageSize,Assets assets) {		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		List<Assets> list = assetsMapper.getMgAssets(assets);
		return new PageInfo<Assets>(list);
	}
	
	public PageInfo<Assets> getAssets(Integer pageNo, Integer pageSize,Assets assets) {		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		
		List<Assets> list = assetsMapper.getAssets(assets);
		return new PageInfo<Assets>(list);
	}

	public PageInfo<Assets> getMyAssets(Integer pageNum, Integer pageSize,Integer orgId,String roleType) {		
		    pageNum = pageNum == null ? 1 : pageNum;
			pageSize = pageSize == null ? 10 : pageSize;
			PageHelper.startPage(pageNum, pageSize);
			
			//List<Assets> list = assetsMapper.getMyAssets(assets);
			
			List<Assets> list = null;
			if("1".equals(roleType)){
				list = assetsMapper.getMyRecordAssetsList(orgId);   //查询我的资产-资产列表(备案机构方_我备案的)
			}else if("2".equals(roleType)){
				list = assetsMapper.getFeedBackRecordAssetsList(orgId);   //查询我的资产-资产列表(备案机构方_我反馈的)
			}else if("3".equals(roleType)){
				list = assetsMapper.getFeedBackFundsAssetsList(orgId);   //查询我的资产-资产列表(资金方_我反馈的)
			}else if("4".equals(roleType)){
				list = assetsMapper.getSaleFundsAssetsList(orgId);  //查询我的资产-资产列表(资金方_我销售的)
			}else if("5".equals(roleType)){
				//list = assetsMapper.getPropertyAssetsList(orgId);  //查询我的资产-资产列表(资产方)
				list = assetsMapper.getMyAssets(orgId);
			}else{
				list = assetsMapper.getMyAssets(orgId);
			}
			
			return new PageInfo<Assets>(list);
		}
	
	public AssetsDetialInfo stateInvestmentAssetTrantoAssetsDetialInfo(StateInvestmentAsset stateInvestmentAsset){
		AssetsDetialInfo assetsDetialInfo = new AssetsDetialInfo();
		assetsDetialInfo.setId(stateInvestmentAsset.getAid());
		assetsDetialInfo.setBelongGid(stateInvestmentAsset.getBelongGid());
		assetsDetialInfo.setName(stateInvestmentAsset.getName());
		assetsDetialInfo.setShortName(stateInvestmentAsset.getShortName());
		assetsDetialInfo.setCreatorId(stateInvestmentAsset.getCreatorId());
		assetsDetialInfo.setEditorId(stateInvestmentAsset.getEditorId());
		assetsDetialInfo.setCreateTime(stateInvestmentAsset.getCreateTime());
		assetsDetialInfo.setEditTime(stateInvestmentAsset.getEditTime());
		assetsDetialInfo.setAssetType(stateInvestmentAsset.getAssetType());
		assetsDetialInfo.setBorrower(stateInvestmentAsset.getBorrower());
		assetsDetialInfo.setScale(stateInvestmentAsset.getScale());
		assetsDetialInfo.setTenorType(stateInvestmentAsset.getTenorType());
		assetsDetialInfo.setTenor(stateInvestmentAsset.getTenor());
		assetsDetialInfo.setRepaymentType(stateInvestmentAsset.getRepaymentType());
		assetsDetialInfo.setFinancingCost(stateInvestmentAsset.getFinancingCost());
		assetsDetialInfo.setFinancingType(stateInvestmentAsset.getFinancingType());
		assetsDetialInfo.setFinancingPurpose(stateInvestmentAsset.getFinancingPurpose());
		assetsDetialInfo.setTimingRequirement(stateInvestmentAsset.getTimingRequirement());
		assetsDetialInfo.setCompanyName(stateInvestmentAsset.getCompanyName());
		assetsDetialInfo.setHolderBackground(stateInvestmentAsset.getHolderBackground());
		assetsDetialInfo.setCapital(stateInvestmentAsset.getCapital());
		assetsDetialInfo.setFoundedTime(dateTranToString(stateInvestmentAsset.getFoundedTime()));
		assetsDetialInfo.setMajorBusiness(stateInvestmentAsset.getMajorBusiness());
		assetsDetialInfo.setTotalAsset(stateInvestmentAsset.getTotalAsset());
		assetsDetialInfo.setNetAsset(stateInvestmentAsset.getNetAsset());
		assetsDetialInfo.setMainIncome(stateInvestmentAsset.getMainIncome());
		assetsDetialInfo.setAllowanceIncome(stateInvestmentAsset.getAllowanceIncome());
		assetsDetialInfo.setNetProfit(stateInvestmentAsset.getNetProfit());
		assetsDetialInfo.setAlRatio(stateInvestmentAsset.getAlRatio());
		assetsDetialInfo.setRating(stateInvestmentAsset.getRating());
		assetsDetialInfo.setForecast(stateInvestmentAsset.getForecast());
		assetsDetialInfo.setPublicDebt(stateInvestmentAsset.getPublicDebt());
		assetsDetialInfo.setMatureDate(dateTranToString(stateInvestmentAsset.getMatureDate()));
		assetsDetialInfo.setBankLine(stateInvestmentAsset.getBankLine());
		assetsDetialInfo.setBadSuit(stateInvestmentAsset.getBadSuit());
		assetsDetialInfo.setLawSuit(stateInvestmentAsset.getLawSuit());
		assetsDetialInfo.setFinanceCooporation(stateInvestmentAsset.getFinanceCooporation());
		assetsDetialInfo.setProjectType(stateInvestmentAsset.getProjectType());	
		assetsDetialInfo.setProjectCompliance(stateInvestmentAsset.getProjectCompliance());	
		assetsDetialInfo.setCompanyName(stateInvestmentAsset.getCompanyName());	
		assetsDetialInfo.setSelfFunded(stateInvestmentAsset.getSelfFunded());	
		assetsDetialInfo.setRepayArrangement(stateInvestmentAsset.getRepayArrangement());	
		assetsDetialInfo.setOtherFinancing(stateInvestmentAsset.getOtherFinancing());	
		assetsDetialInfo.setLandValue(stateInvestmentAsset.getLandValue());	
		assetsDetialInfo.setProjectValue(stateInvestmentAsset.getProjectValue());	
		assetsDetialInfo.setOtherCollateral(stateInvestmentAsset.getOtherCollateral());	
		assetsDetialInfo.setReceivable(stateInvestmentAsset.getReceivable());	
		assetsDetialInfo.setCounterParty(stateInvestmentAsset.getCounterParty());	
		assetsDetialInfo.setRepayDate(dateTranToString(stateInvestmentAsset.getRepayDate()));	
		assetsDetialInfo.setPledgeStock(stateInvestmentAsset.getPledgeStock());	
		assetsDetialInfo.setPledgeRatio(stateInvestmentAsset.getPledgeRatio());	
		assetsDetialInfo.setOtherPledge(stateInvestmentAsset.getOtherPledge());	
		assetsDetialInfo.setGuarantor(stateInvestmentAsset.getGuarantor());	
		assetsDetialInfo.setGuarantorBackground(stateInvestmentAsset.getGuarantorBackground());	
		assetsDetialInfo.setGuarantorRating(stateInvestmentAsset.getGuarantorRating());	
		assetsDetialInfo.setGovDecision(stateInvestmentAsset.getGovDecision());	
		assetsDetialInfo.setGovAgreement(stateInvestmentAsset.getGovAgreement());	
		assetsDetialInfo.setFinanceArrangement(stateInvestmentAsset.getFinanceArrangement());	
		assetsDetialInfo.setProvince(stateInvestmentAsset.getProvince());
		assetsDetialInfo.setCity(stateInvestmentAsset.getCity());
		assetsDetialInfo.setAdmGrade(stateInvestmentAsset.getAdmGrade());	
		assetsDetialInfo.setGdp(stateInvestmentAsset.getGdp());	
		assetsDetialInfo.setMainIndustry(stateInvestmentAsset.getMainIndustry());	
		assetsDetialInfo.setBigThreeRatio(stateInvestmentAsset.getBigThreeRatio());	
		assetsDetialInfo.setPopulation(stateInvestmentAsset.getPopulation());	
		assetsDetialInfo.setGovTotalIncome(stateInvestmentAsset.getGovTotalIncome());	
		assetsDetialInfo.setGeneralIncome(stateInvestmentAsset.getGeneralIncome());	
		assetsDetialInfo.setTransferIncome(stateInvestmentAsset.getTransferIncome());	
		assetsDetialInfo.setGovFundIncome(stateInvestmentAsset.getGovFundIncome());	
		assetsDetialInfo.setGovDebt(stateInvestmentAsset.getGovDebt());	
		assetsDetialInfo.setGovGuarantee(stateInvestmentAsset.getGovGuarantee());	
		assetsDetialInfo.setLiabilitiesRatio(stateInvestmentAsset.getLiabilitiesRatio());	
		assetsDetialInfo.setDebtRation(stateInvestmentAsset.getDebtRation());	
		assetsDetialInfo.setLisence(stateInvestmentAsset.getLisence());	
		assetsDetialInfo.setOrgInstCode(stateInvestmentAsset.getOrgInstCode());	
		assetsDetialInfo.setTaxReg(stateInvestmentAsset.getTaxReg());	
		assetsDetialInfo.setLeId(stateInvestmentAsset.getLeId());	
		assetsDetialInfo.setBankCard(stateInvestmentAsset.getBankCard());	
		assetsDetialInfo.setAcApproval(stateInvestmentAsset.getAcApproval());	
		assetsDetialInfo.setArticle(stateInvestmentAsset.getArticle());	
		assetsDetialInfo.setCaptialVer(stateInvestmentAsset.getCaptialVer());	
		assetsDetialInfo.setCreditReport(stateInvestmentAsset.getCreditReport());	
		assetsDetialInfo.setFeasibility(stateInvestmentAsset.getFeasibility());	
		assetsDetialInfo.setAuditReport(stateInvestmentAsset.getAuditReport());	
		assetsDetialInfo.setFinanceStatement(stateInvestmentAsset.getFinanceStatement());	
		assetsDetialInfo.setDataExpierationDate(stateInvestmentAsset.getDataExpierationDate());
		assetsDetialInfo.setDataGoveExpirationDate(stateInvestmentAsset.getDataGoveExpirationDate());
		assetsDetialInfo.setFinanceDvalid(dateTranToString(stateInvestmentAsset.getFinanceDvalid()));
		assetsDetialInfo.setFinancialDvalid(dateTranToString(stateInvestmentAsset.getFinancialDvalid()));
		assetsDetialInfo.setStatus(stateInvestmentAsset.getStatus());
		assetsDetialInfo.setOtherFile(stateInvestmentAsset.getOtherFile());
		
		assetsDetialInfo.setIsGuarante(stateInvestmentAsset.getIsGuarante());
		assetsDetialInfo.setGuarantorType(stateInvestmentAsset.getGuarantorType());
		return assetsDetialInfo;		
	}
	
	
	public AssetsDetialInfo ipoAssetTrantoAssetsDetialInfo(IpoAsset ipoAsset){
		AssetsDetialInfo assetsDetialInfo = new AssetsDetialInfo();
		assetsDetialInfo.setId(ipoAsset.getAid());
		assetsDetialInfo.setAssetType(ipoAsset.getAssetType());
		assetsDetialInfo.setCreatorId(ipoAsset.getCreatorId());
		assetsDetialInfo.setName(ipoAsset.getName());
		assetsDetialInfo.setShortName(ipoAsset.getShortName());
		assetsDetialInfo.setOtherPledge(ipoAsset.getOtherPledge());
		assetsDetialInfo.setBorrower(ipoAsset.getBorrower());
		assetsDetialInfo.setScale(ipoAsset.getScale());
		assetsDetialInfo.setTenor(ipoAsset.getTenor());
		assetsDetialInfo.setTenorType(ipoAsset.getTenorType());
		assetsDetialInfo.setRepaymentType(ipoAsset.getRepaymentType());
		assetsDetialInfo.setFinancingCost(ipoAsset.getFinancingCost());
		assetsDetialInfo.setFinancingType(ipoAsset.getFinancingType());
		assetsDetialInfo.setFinancingPurpose(ipoAsset.getFinancingPurpose());
		assetsDetialInfo.setTimingRequirement(ipoAsset.getTimingRequirement());
		assetsDetialInfo.setIpoRelation(ipoAsset.getIpoRelation());
		assetsDetialInfo.setListedCode(ipoAsset.getListedCode());
		assetsDetialInfo.setHolderBackground(ipoAsset.getHolderBackground());
		assetsDetialInfo.setCapital(ipoAsset.getCapital());
		assetsDetialInfo.setFoundedTime(dateTranToString(ipoAsset.getFoundedTime()));
		assetsDetialInfo.setIndustry(ipoAsset.getIndustry());
		assetsDetialInfo.setLocatedProvince(ipoAsset.getLocatedProvince());
		assetsDetialInfo.setLocatedCity(ipoAsset.getLocatedCity());
		assetsDetialInfo.setMajorBusiness(ipoAsset.getMajorBusiness());
		assetsDetialInfo.setBusinessDesc(ipoAsset.getBusinessDesc());
		assetsDetialInfo.setTotalAsset(ipoAsset.getTotalAsset());
		assetsDetialInfo.setNetAsset(ipoAsset.getNetAsset());
		assetsDetialInfo.setMainIncome(ipoAsset.getMainIncome());
		assetsDetialInfo.setNetProfit(ipoAsset.getNetProfit());
		assetsDetialInfo.setGrossProfitRatio(ipoAsset.getGrossProfitRatio());
		assetsDetialInfo.setRating(ipoAsset.getRating());
		assetsDetialInfo.setForecast(ipoAsset.getForecast());
		assetsDetialInfo.setPublicDebt(ipoAsset.getPublicDebt());
		assetsDetialInfo.setMatureDate(dateTranToString(ipoAsset.getMatureDate()));
		assetsDetialInfo.setBankLine(ipoAsset.getBankLine());
		assetsDetialInfo.setBadSuit(ipoAsset.getBadSuit());
		assetsDetialInfo.setLawSuit(ipoAsset.getLawSuit());
		assetsDetialInfo.setFinanceCooporation(ipoAsset.getFinanceCooporation());
		assetsDetialInfo.setLandValue(ipoAsset.getLandValue());
		assetsDetialInfo.setOtherCollateral(ipoAsset.getOtherCollateral());
		assetsDetialInfo.setReceivable(ipoAsset.getReceivable());
		assetsDetialInfo.setCounterParty(ipoAsset.getCounterParty());
		assetsDetialInfo.setRepayDate(dateTranToString(ipoAsset.getRepayDate()));
		assetsDetialInfo.setPledgeStock(ipoAsset.getPledgeStock());
		assetsDetialInfo.setPledgeRatio(ipoAsset.getPledgeRatio());
		assetsDetialInfo.setGuarantor(ipoAsset.getGuarantor());
		assetsDetialInfo.setGuarantorBackground(ipoAsset.getGuarantorBackground());
		assetsDetialInfo.setGuarantorRating(ipoAsset.getGuarantorRating());
		assetsDetialInfo.setLisence(ipoAsset.getLisence());
		assetsDetialInfo.setOrgInstCode(ipoAsset.getOrgInstCode());
		assetsDetialInfo.setTaxReg(ipoAsset.getTaxReg());
		assetsDetialInfo.setLeId(ipoAsset.getLeId());
		assetsDetialInfo.setBankCard(ipoAsset.getBankCard());
		assetsDetialInfo.setAcApproval(ipoAsset.getAcApproval());
		assetsDetialInfo.setArticle(ipoAsset.getArticle());
		assetsDetialInfo.setCaptialVer(ipoAsset.getCaptialVer());
		assetsDetialInfo.setCreditReport(ipoAsset.getCreditReport());
		assetsDetialInfo.setFeasibility(ipoAsset.getFeasibility());
		assetsDetialInfo.setAuditReport(ipoAsset.getAuditReport());
		assetsDetialInfo.setFinanceStatement(ipoAsset.getFinanceStatement()); 
		assetsDetialInfo.setDataExpierationDate(ipoAsset.getDataExpierationDate());
		assetsDetialInfo.setStatus(ipoAsset.getStatus());
		assetsDetialInfo.setFinanceDvalid(dateTranToString(ipoAsset.getFinanceDvalid()));
		assetsDetialInfo.setOtherFile(ipoAsset.getOtherFile());
		
		assetsDetialInfo.setIsTrust(ipoAsset.getIsTrust());
		assetsDetialInfo.setIsGuarante(ipoAsset.getIsGuarante());
		assetsDetialInfo.setGuarantorType(ipoAsset.getGuarantorType());
		assetsDetialInfo.setGuarantorFinancierRelation(ipoAsset.getGuarantorFinancierRelation());
		assetsDetialInfo.setGuarantorRegistMoney(ipoAsset.getGuarantorRegistMoney());
		assetsDetialInfo.setGuarantorTotalMoney(ipoAsset.getGuarantorTotalMoney());
		assetsDetialInfo.setGuarantorNetMoney(ipoAsset.getGuarantorNetMoney());
		assetsDetialInfo.setGuarantorNetProfit(ipoAsset.getGuarantorNetProfit());
		assetsDetialInfo.setGuarantorRevenue(ipoAsset.getGuarantorRevenue());
		assetsDetialInfo.setGuarantorGrossRate(ipoAsset.getGuarantorGrossRate());
		assetsDetialInfo.setGuarantorSetupDate(dateTranToString(ipoAsset.getGuarantorSetupDate()));
		assetsDetialInfo.setGuarantorDataExpired(dateTranToString(ipoAsset.getGuarantorDataExpired()));
		assetsDetialInfo.setGuarantorRatingOutlook(ipoAsset.getGuarantorRatingOutlook());
		assetsDetialInfo.setGuarantorIsPublish(ipoAsset.getGuarantorIsPublish());
		assetsDetialInfo.setGuarantorLastestDate(dateTranToString(ipoAsset.getGuarantorLastestDate()));
		assetsDetialInfo.setGuarantorBadBreach(ipoAsset.getGuarantorBadBreach());
		assetsDetialInfo.setGuarantorPendingAction(ipoAsset.getGuarantorPendingAction());
		return assetsDetialInfo;
	}
	
	
	public AssetsDetialInfo supplyChainAssetTrantoAssetsDetialInfo(SupplyChainAsset supplyChainAsset){
		AssetsDetialInfo assetsDetialInfo = new AssetsDetialInfo();
		assetsDetialInfo.setId(supplyChainAsset.getAid());
		assetsDetialInfo.setAssetType(supplyChainAsset.getAssetType());
		assetsDetialInfo.setCreatorId(supplyChainAsset.getCreatorId());
		assetsDetialInfo.setName(supplyChainAsset.getName());
		assetsDetialInfo.setShortName(supplyChainAsset.getShortName());
		assetsDetialInfo.setBorrower(supplyChainAsset.getBorrower());
		assetsDetialInfo.setScale(supplyChainAsset.getScale());
		assetsDetialInfo.setTenorType(supplyChainAsset.getTenorType());
		assetsDetialInfo.setTenor(supplyChainAsset.getTenor());
		assetsDetialInfo.setCoreEnterprise(supplyChainAsset.getCoreEnterprise());
		assetsDetialInfo.setIndustryChain(supplyChainAsset.getIndustryChain());
		assetsDetialInfo.setIndustry(supplyChainAsset.getIndustry());
		assetsDetialInfo.setFinancingCost(supplyChainAsset.getFinancingCost());
		assetsDetialInfo.setFinancingType(supplyChainAsset.getFinancingType());
		assetsDetialInfo.setMajorBusiness(supplyChainAsset.getMajorBusiness());
		assetsDetialInfo.setTradeType(supplyChainAsset.getTradeType());
		assetsDetialInfo.setRepaymentPeriod(supplyChainAsset.getRepaymentPeriod());
		assetsDetialInfo.setAvgArAge(supplyChainAsset.getAvgArAge());
		assetsDetialInfo.setTotalAsset(supplyChainAsset.getTotalAsset());
		assetsDetialInfo.setTotalDebt(supplyChainAsset.getTotalDebt());
		assetsDetialInfo.setNetAsset(supplyChainAsset.getNetAsset());
		assetsDetialInfo.setIndustry(supplyChainAsset.getIndustry());
		assetsDetialInfo.setAcReceiveable(supplyChainAsset.getAcReceiveable());
		assetsDetialInfo.setPrepayment(supplyChainAsset.getPrepayment());
		assetsDetialInfo.setMainIncome(supplyChainAsset.getMainIncome());
		assetsDetialInfo.setArRotationRatio(supplyChainAsset.getArRotationRatio());
		assetsDetialInfo.setInventoryRation(supplyChainAsset.getInventoryRation());
		assetsDetialInfo.setNetProfit(supplyChainAsset.getNetProfit());
		assetsDetialInfo.setGrossProfitRatio(supplyChainAsset.getGrossProfitRatio());
		assetsDetialInfo.setTrasactionHistory(supplyChainAsset.getTrasactionHistory());
		assetsDetialInfo.setSettlementType(supplyChainAsset.getSettlementType());
		assetsDetialInfo.setLogisticType(supplyChainAsset.getLogisticType());
		assetsDetialInfo.setLisenceNeeded(supplyChainAsset.getLisenceNeeded());
		assetsDetialInfo.setLineGrandingPolicy(supplyChainAsset.getLineGrandingPolicy());
		assetsDetialInfo.setRating(supplyChainAsset.getRating());
		assetsDetialInfo.setCreditEnforce(supplyChainAsset.getCreditEnforce());
		assetsDetialInfo.setLisence(supplyChainAsset.getLisence());
		assetsDetialInfo.setOrgInstCode(supplyChainAsset.getOrgInstCode());
		assetsDetialInfo.setTaxReg(supplyChainAsset.getTaxReg());
		assetsDetialInfo.setArticle(supplyChainAsset.getArticle());
		assetsDetialInfo.setCaptialVer(supplyChainAsset.getCaptialVer());
		assetsDetialInfo.setFeasibility(supplyChainAsset.getFeasibility());
		assetsDetialInfo.setAuditReport(supplyChainAsset.getAuditReport());
		assetsDetialInfo.setYearTransaction(supplyChainAsset.getYearTransaction());
		assetsDetialInfo.setDataExpierationDate(supplyChainAsset.getDataExpierationDate());
		assetsDetialInfo.setStatus(supplyChainAsset.getStatus());
		assetsDetialInfo.setFinanceDvalid(dateTranToString(supplyChainAsset.getFinanceDvalid()));
		assetsDetialInfo.setOtherFile(supplyChainAsset.getOtherFile());
		
		assetsDetialInfo.setIsGuarante(supplyChainAsset.getIsGuarante());
		assetsDetialInfo.setGuarantorType(supplyChainAsset.getGuarantorType());
		return assetsDetialInfo;
	}
	
	

	public AssetsDetialInfo peAbsAssetTrantoAssetsDetialInfo(PeAbsAsset peAbsAsset){
		AssetsDetialInfo assetsDetialInfo = new AssetsDetialInfo();
		assetsDetialInfo.setId(peAbsAsset.getAid());
		assetsDetialInfo.setAssetType(peAbsAsset.getAssetType());
		assetsDetialInfo.setCreatorId(peAbsAsset.getCreatorId());
		assetsDetialInfo.setName(peAbsAsset.getName());
		assetsDetialInfo.setShortName(peAbsAsset.getShortName());
		assetsDetialInfo.setBorrower(peAbsAsset.getBorrower());
		assetsDetialInfo.setScale(peAbsAsset.getScale());
		assetsDetialInfo.setTenor(peAbsAsset.getTenor());
		assetsDetialInfo.setTenorType(peAbsAsset.getTenorType());
		assetsDetialInfo.setAssetMgr(peAbsAsset.getAssetMgr());
		assetsDetialInfo.setSetTrench(peAbsAsset.getSetTrench());
		assetsDetialInfo.setSeniorPercent(peAbsAsset.getSeniorPercent());
		assetsDetialInfo.setSeniorRating(peAbsAsset.getSeniorRating());
		assetsDetialInfo.setAssetSubType(peAbsAsset.getAssetSubType());
		assetsDetialInfo.setRate(peAbsAsset.getRate());
		assetsDetialInfo.setAbsRatingReport(peAbsAsset.getAbsRatingReport());	
		assetsDetialInfo.setStatus(peAbsAsset.getStatus());
		assetsDetialInfo.setIssueDate(dateTranToString(peAbsAsset.getIssueDate()));
		assetsDetialInfo.setOtherFile(peAbsAsset.getOtherFile());
		
		assetsDetialInfo.setIsGuarante(peAbsAsset.getIsGuarante());
		assetsDetialInfo.setGuarantorType(peAbsAsset.getGuarantorType());
		return assetsDetialInfo;
	}
	
	
	public AssetsDetialInfo usufructTransferAssetTrantoAssetsDetialInfo(UsufructTransferAsset usufructTransferAsset){
		AssetsDetialInfo assetsDetialInfo = new AssetsDetialInfo();
		assetsDetialInfo.setId(usufructTransferAsset.getAid());
		assetsDetialInfo.setCreatorId(usufructTransferAsset.getCreatorId());
		assetsDetialInfo.setAssetType(usufructTransferAsset.getAssetType());
		assetsDetialInfo.setName(usufructTransferAsset.getName());
		assetsDetialInfo.setShortName(usufructTransferAsset.getShortName());
		assetsDetialInfo.setProjectName(usufructTransferAsset.getProjectName());
		assetsDetialInfo.setProjectType(usufructTransferAsset.getProjectType());
		assetsDetialInfo.setBorrower(usufructTransferAsset.getBorrower());
		assetsDetialInfo.setScale(usufructTransferAsset.getScale());
		assetsDetialInfo.setTenorType(usufructTransferAsset.getTenorType());
		assetsDetialInfo.setTenor(usufructTransferAsset.getTenor());
		assetsDetialInfo.setAssetMgr(usufructTransferAsset.getAssetMgr());
		assetsDetialInfo.setRepurchase(usufructTransferAsset.getRepurchase());
		assetsDetialInfo.setTradingStructure(usufructTransferAsset.getTradingStructure());
		assetsDetialInfo.setAssetSubType(usufructTransferAsset.getAssetSubType());
		assetsDetialInfo.setRate(usufructTransferAsset.getRate());
		assetsDetialInfo.setRelatedAgreement(usufructTransferAsset.getRelatedAgreement());
		assetsDetialInfo.setStatus(usufructTransferAsset.getStatus());
		assetsDetialInfo.setOtherFile(usufructTransferAsset.getOtherFile());
		
		assetsDetialInfo.setIsGuarante(usufructTransferAsset.getIsGuarante());
		assetsDetialInfo.setGuarantorType(usufructTransferAsset.getGuarantorType());
		return assetsDetialInfo;
	}
	
	public AssetsDetialInfo industryCommunityAssetTrantoAssetsDetialInfo(IndustryCommunityAsset industryCommunityAsset){
		AssetsDetialInfo assetsDetialInfo = new AssetsDetialInfo();
		BeanUtils.copyProperties(industryCommunityAsset, assetsDetialInfo);
		assetsDetialInfo.setFoundedTime(dateTranToString(industryCommunityAsset.getFoundedTime()));
		assetsDetialInfo.setFinancingDvalid(dateTranToString(industryCommunityAsset.getFinancingDvalid()));
		assetsDetialInfo.setMatureDate(dateTranToString(industryCommunityAsset.getMatureDate()));
		assetsDetialInfo.setRepayDate(dateTranToString(industryCommunityAsset.getRepayDate()));
		assetsDetialInfo.setGuarantorDataExpired(dateTranToString(industryCommunityAsset.getGuarantorDataExpired()));
		assetsDetialInfo.setGuarantorSetupDate(dateTranToString(industryCommunityAsset.getGuarantorSetupDate()));
		assetsDetialInfo.setGuarantorLastestDate(dateTranToString(industryCommunityAsset.getGuarantorLastestDate()));
		
		return assetsDetialInfo;
	}
	
	public IndustryCommunityAsset assetsInfoInsertIndustryCommunityAsset(AssetsDetialInfo assetsInfo){
		IndustryCommunityAsset industryCommunityAsset = new IndustryCommunityAsset();
		BeanUtils.copyProperties(assetsInfo, industryCommunityAsset);
		industryCommunityAsset.setFoundedTime(stringTranToDate(assetsInfo.getFoundedTime()));
		industryCommunityAsset.setFinancingDvalid(stringTranToDate(assetsInfo.getFinancingDvalid()));
		industryCommunityAsset.setMatureDate(stringTranToDate(assetsInfo.getMatureDate()));
		industryCommunityAsset.setRepayDate(stringTranToDate(assetsInfo.getRepayDate()));
		industryCommunityAsset.setGuarantorDataExpired(stringTranToDate(assetsInfo.getGuarantorDataExpired()));
		industryCommunityAsset.setGuarantorSetupDate(stringTranToDate(assetsInfo.getGuarantorSetupDate()));
		industryCommunityAsset.setGuarantorLastestDate(stringTranToDate(assetsInfo.getGuarantorLastestDate()));
		
		return industryCommunityAsset;			
	}

	/**
     * 查询资产在某个步骤的协议文档
     * @param assetId  资产id
     * @param stepId  流程步骤id
     * @return
     */
	public RecordLog queryRecordLog(String assetId, String stepId) {
		return recordLogMapper.queryRecordLog(assetId, stepId);
	}

	/**
     * 将资产状态改为 6 完结
     * @param assetsId
     * @return
     */
	public int updateToOverStatusByAssetsId(Integer assetsId) {
		return assetsMapper.updateToOverStatusByAssetsId(assetsId);
	}

	/**
	 * 查询基础资产列表
	 */
	public PageInfo<PeAccountsReceivable> queryAccountReceivedList(BaseAssetsSearchVo baseAssetsSearchVo) {
		Integer pageNum = null;
		Integer pageSize = null;
		if(baseAssetsSearchVo.getPageNum() != null && baseAssetsSearchVo.getPageNum() != ""){
			pageNum = Integer.parseInt(baseAssetsSearchVo.getPageNum());
		}
		if(baseAssetsSearchVo.getPageSize() != null && baseAssetsSearchVo.getPageSize()  != ""){
			pageSize = Integer.parseInt(baseAssetsSearchVo.getPageSize());
		}
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		
		 List<PeAccountsReceivable> list = peAccountsReceivableMapper.queryListBySelective(baseAssetsSearchVo);
		 return new PageInfo<PeAccountsReceivable>(list);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public int addPeAbsAsset(AssetsDetialInfo assetsDetialInfo) {
		PeAbsAsset peAbsAsset = assetsInfoInsertPeAbsAsset(assetsDetialInfo);
		Assets asset = assetsInfoInsertAsset(assetsDetialInfo);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String createTime = df.format(new Date());// new Date()为获取当前系统时间
		asset.setCreateTime(createTime);
		asset.setEditTime(createTime);
		if(asset.getTenor() != null && asset.getTenorType() != null){
			int tenor = asset.getTenor();
			int tenorType = asset.getTenorType();
			asset.setTenorDay(getTenorDay(tenor,tenorType));
		}	
		int flagm = assetsMapper.insertSelective(asset);
		if(flagm != 1){
			return 0;
		}
		peAbsAsset.setAid(asset.getId());
		assetsDetialInfo.setAid(asset.getId());
		return peAbsAssetMapper.insertSelective(peAbsAsset);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public int editPeAbsAsset(AssetsDetialInfo assetsDetialInfo) {
		PeAbsAsset peAbsAsset = assetsInfoInsertPeAbsAsset(assetsDetialInfo);
		Assets asset = assetsInfoInsertAsset(assetsDetialInfo);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String editTime = df.format(new Date());// new Date()为获取当前系统时间
		asset.setEditTime(editTime);
		if(asset.getTenor() != null && asset.getTenorType() != null){
			int tenor = asset.getTenor();
			int tenorType = asset.getTenorType();
			asset.setTenorDay(getTenorDay(tenor,tenorType));
		}	
		asset.setId(peAbsAsset.getAid());
		int flagm = assetsMapper.updateByPrimaryKeySelective(asset);
		if(flagm != 1){
			return 0;
		}
		assetsDetialInfo.setAid(asset.getId());
		return peAbsAssetMapper.updateByAssetsidSelective(peAbsAsset);
	}
	
	public PeAbsAsset assetsInfoInsertPeAbsAsset(AssetsDetialInfo assetsInfo){		
		PeAbsAsset peAbsAsset = new PeAbsAsset();
		peAbsAsset.setAid(assetsInfo.getAid());
		peAbsAsset.setAssetType(assetsInfo.getAssetType());
		peAbsAsset.setBorrower(assetsInfo.getBorrower());
		peAbsAsset.setScale(assetsInfo.getScale());
		peAbsAsset.setWarnScale(assetsInfo.getWarnScale());
		peAbsAsset.setTenor(assetsInfo.getTenor());
		peAbsAsset.setTenorType(assetsInfo.getTenorType());
		peAbsAsset.setAssetMgr(assetsInfo.getAssetMgr());
		peAbsAsset.setSetTrench(assetsInfo.getSetTrench());
		peAbsAsset.setSeniorPercent(assetsInfo.getSeniorPercent());
		peAbsAsset.setSeniorRating(assetsInfo.getSeniorRating());
		peAbsAsset.setAssetSubType(assetsInfo.getAssetSubType());
		peAbsAsset.setRate(assetsInfo.getRate());
		peAbsAsset.setAbsRatingReport(assetsInfo.getAbsRatingReport());	
		peAbsAsset.setOtherFile(assetsInfo.getOtherFile());
		peAbsAsset.setIssueDate(UtilTool.stringTranToDate(assetsInfo.getIssueDate()));
		peAbsAsset.setExpiredDate(UtilTool.stringTranToDate(assetsInfo.getExpiredDate()));
		
		peAbsAsset.setIsGuarante(assetsInfo.getIsGuarante());
		peAbsAsset.setGuarantorType(assetsInfo.getGuarantorType());
		peAbsAsset.setAssetProvider(assetsInfo.getAssetProvider());
		return peAbsAsset;		
	}
	
	public Assets assetsInfoInsertAsset(AssetsDetialInfo assetsInfo){		
		Assets asset = new Assets();
		asset.setName(assetsInfo.getName());
		asset.setShortName(assetsInfo.getShortName());
		asset.setSalesModel(assetsInfo.getSalesModel());
		asset.setAssetType(assetsInfo.getAssetType());
		asset.setBorrower(assetsInfo.getBorrower());
		asset.setTenorType(assetsInfo.getTenorType());
		asset.setTenor(assetsInfo.getTenor());
		asset.setScale(assetsInfo.getScale());
		asset.setFinancingCost(assetsInfo.getFinancingCost());
		asset.setRecordGid(assetsInfo.getRecordGid());
		asset.setWfType(assetsInfo.getWfType());
		asset.setWfId(assetsInfo.getWfId());
		asset.setWfStatus(assetsInfo.getWfStatus());
		asset.setMaterials(assetsInfo.getMaterials());
		asset.setDueReports(assetsInfo.getDueReports());
		asset.setPassResult(assetsInfo.getPassResult());
		asset.setPassContent(assetsInfo.getPassContent());
		asset.setBelongGid(assetsInfo.getBelongGid());
		asset.setCreatorId(assetsInfo.getCreatorId());
		asset.setCreateTime(assetsInfo.getCreateTime());
		asset.setEditorId(assetsInfo.getEditorId());
		asset.setEditTime(assetsInfo.getEditTime());
		asset.setAuditorId(assetsInfo.getAuditorId());
		asset.setAuditTime(assetsInfo.getAuditTime());
		asset.setPublisherId(assetsInfo.getPublisherId());
		asset.setPublishTime(assetsInfo.getPublishTime());
		asset.setConfirmerId(assetsInfo.getConfirmerId());
		asset.setConfirmTime(assetsInfo.getConfirmTime());
		asset.setBrokerId(assetsInfo.getBrokerId());
		asset.setBrokerTime(assetsInfo.getBrokerTime());
		asset.setRefereeId(assetsInfo.getRefereeId());
		asset.setRefereeTime(assetsInfo.getRefereeTime());
		asset.setStatus(assetsInfo.getStatus());
		return asset;		
	}
	
	/**
     * 资产包列表统计数据
     * @return
     */
    public List<AssetsPackage> assetsPackageStaticData(){
    	return assetsMapper.assetsPackageStaticData();
    }
    
    /**
     * 查询可以用来替换的基础资产（多个应收债务人debtorName,逗号隔开）
     * @return
     */
    public List<PeAccountsReceivable> queryCouldReplaceByMul(BaseAssetsSearchVo basv){
    	 Map<String, Object> params = new HashMap<String, Object>();
		String[] debtorNames = new String[]{};
		if(!"".equals(basv.getDebtorName()) && basv.getDebtorName() != null){
			debtorNames = basv.getDebtorName().split(",");
			params.put("debtorNames", debtorNames);
		}		
		params.put("invoicedDateBegin", basv.getInvoicedDateBegin());
		params.put("invoicedDateEnd", basv.getInvoicedDateEnd());
		params.put("returnDateBegin", basv.getReturnDateBegin());
		params.put("returnDateEnd", basv.getReturnDateEnd());
		
    	return peAccountsReceivableMapper.selectCouldReplaceByMul(params);
    }
    
    /**
     * 查询可以用来替换的基础资产（单个应收债务人debtorName）
     * @return
     */
    public PageInfo<PeAccountsReceivable> queryCouldReplaceBySimple(BaseAssetsSearchVo baseAssetsSearchVo){
    	
    	Integer pageNum = null;
		Integer pageSize = null;
		if(baseAssetsSearchVo.getPageNum() != null && !"".equals(baseAssetsSearchVo.getPageNum())){
			pageNum = Integer.parseInt(baseAssetsSearchVo.getPageNum());
		}
		if(baseAssetsSearchVo.getPageSize() != null && !"".equals(baseAssetsSearchVo.getPageSize())){
			pageSize = Integer.parseInt(baseAssetsSearchVo.getPageSize());
		}
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		
    	 Map<String, Object> params = new HashMap<String, Object>();
		params.put("debtorName", baseAssetsSearchVo.getDebtorName());
		params.put("invoicedDateBegin", baseAssetsSearchVo.getInvoicedDateBegin());
		params.put("invoicedDateEnd", baseAssetsSearchVo.getInvoicedDateEnd());
		params.put("returnDateBegin", baseAssetsSearchVo.getReturnDateBegin());
		params.put("returnDateEnd", baseAssetsSearchVo.getReturnDateEnd());
		
		List<PeAccountsReceivable> list = peAccountsReceivableMapper.selectCouldReplaceBySingle(params);
    	return new PageInfo<PeAccountsReceivable>(list);
    }
    
    /**
     * 查询资产包详情
     * @param id
     * @return
     */
    public AssetPackageVo queryAssetPackageDetail(Integer id){
    	return assetsMapper.queryAssetPackageDetail(id);
    }
    
    /**
     * 查询资产包下的基础资产列表
     * @return
     */
    public PageInfo<PeAccountsReceivable> queryBaseAssetsByAssetId(BaseAssetsSearchVo baseAssetsSearchVo){
    	Integer pageNum = null;
		Integer pageSize = null;
		if(baseAssetsSearchVo.getPageNum() != null && !"".equals(baseAssetsSearchVo.getPageNum())){
			pageNum = Integer.parseInt(baseAssetsSearchVo.getPageNum());
		}
		if(baseAssetsSearchVo.getPageSize() != null && !"".equals(baseAssetsSearchVo.getPageSize())){
			pageSize = Integer.parseInt(baseAssetsSearchVo.getPageSize());
		}
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		
		List<PeAccountsReceivable> list = peAccountsReceivableMapper.queryBaseAssetsByAssetId(baseAssetsSearchVo);
    	return new PageInfo<PeAccountsReceivable>(list);
    }

	@Override
	public int changeBaseAsset(BaseAssetChangeVo baseAssetChangeVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] changeBeforeIds = new String[]{};
		String[] changeAfterIds = new String[]{};
		int count = 0;
		map.put("aid",baseAssetChangeVo.getAid());
		if(!"".equals(baseAssetChangeVo.getChangeBeforeIds()) && baseAssetChangeVo.getChangeBeforeIds() != null){
			//解除被替换的基础资产
			changeBeforeIds = baseAssetChangeVo.getChangeBeforeIds().split(",");
			map.put("changeBeforeIds", changeBeforeIds);
			count = peAccountsReceivableMapper.removeBaseAsset(map);

			//记录“手动被替换”的基础资产历史记录
			saveAssetChangeHisBatch(String.valueOf(baseAssetChangeVo.getAid()),baseAssetChangeVo.getChangeBeforeIds(),baseAssetChangeVo.getCreateName(),"2");
		}
		if(!"".equals(baseAssetChangeVo.getChangeAfterIds()) && baseAssetChangeVo.getChangeAfterIds() != null){
			//绑定用来替换的基础资产
			changeAfterIds = baseAssetChangeVo.getChangeAfterIds().split(",");
			map.put("changeAfterIds", changeAfterIds);
			count = peAccountsReceivableMapper.bindBaseAsset(map);

			//记录“手动替换”的基础资产的历史记录
			saveAssetChangeHisBatch(String.valueOf(baseAssetChangeVo.getAid()),baseAssetChangeVo.getChangeAfterIds(),baseAssetChangeVo.getCreateName(),"3");

		}

		PeAbsAsset assets = peAbsAssetMapper.selectByPrimaryKey(baseAssetChangeVo.getAid());
		AssetPackageVo assetPackageVo = assetsMapper.queryAssetPackageDetail(baseAssetChangeVo.getAid());
		//变更基础资产之后，检测基础资产总金额是否大于预警金额
		if(assets.getWarnScale().subtract(assetPackageVo.getPackageAmount()).doubleValue() <= 0){
			assetsMapper.normalAssetPackage(baseAssetChangeVo.getAid());
		}else{
			assetsMapper.warnAssetPackage(baseAssetChangeVo.getAid());
		}

		return count;
	}
	
	/**
     * 通过多个id批量查询基础资产
     * @return
     */
    public List<PeAccountsReceivable> queryListByIds(String ids){
    	Map<String, Object> map = new HashMap<String, Object>();
		String[] changeBeforeIds = new String[]{};
		if(!"".equals(ids) && ids != null){
			changeBeforeIds = ids.split(",");
			map.put("choiceIds", changeBeforeIds);
		}
		return peAccountsReceivableMapper.queryListByIds(map);
    }
    
   /**
    * 查询资产包列表
    */
    public PageInfo<AssetPackageVo> queryAssetPackageList(AssetPackageSearchVo assetPackageSearchVo){
    	Integer pageNum = null;
		Integer pageSize = null;
		if(assetPackageSearchVo.getPageNum() != null && assetPackageSearchVo.getPageNum() != ""){
			pageNum = Integer.parseInt(assetPackageSearchVo.getPageNum());
		}
		if(assetPackageSearchVo.getPageSize() != null && assetPackageSearchVo.getPageSize()  != ""){
			pageSize = Integer.parseInt(assetPackageSearchVo.getPageSize());
		}
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		
		List<AssetPackageVo> list = assetsMapper.queryAssetPackageList(assetPackageSearchVo);
    	return new PageInfo<AssetPackageVo>(list);
    }

	/**
	 * 删除重选
	 * @param baseAssetsSearchVo
	 * @return
	 */
	public int deleteToChoiceAgain(BaseAssetsSearchVo baseAssetsSearchVo){
		return peAccountsReceivableMapper.deleteToChoiceAgain(baseAssetsSearchVo);
	}

	/**
	 * 兑付资产包
	 * @param aid
	 * @return
	 */
	public int cashAssetPackage(Integer aid){
		return assetsMapper.cashAssetPackage(aid);
	}

	/**
	 * 完结资产包之后解除关联的基础资产（待收和逾期状态）
	 * @param baseAssetsSearchVo
	 * @return
	 */
	public int removeBaseAssetAfterOver(BaseAssetsSearchVo baseAssetsSearchVo){
		StringBuffer baseAssetIds = new StringBuffer();
		BaseAssetsSearchVo basv = new BaseAssetsSearchVo();
		basv.setAid(baseAssetsSearchVo.getAid());
		List<PeAccountsReceivable>  list = peAccountsReceivableMapper.queryBaseAssetsByAssetId(basv);
		for(PeAccountsReceivable par : list){
			baseAssetIds.append(","+par.getId());
		}
		if(!"".equals(baseAssetIds.toString())){
			//记录“完结解除”的基础资产历史记录
			saveAssetChangeHisBatch(String.valueOf(baseAssetsSearchVo.getAid()),baseAssetIds.substring(1),baseAssetsSearchVo.getCreateName(),"7");
		}

		int count = peAccountsReceivableMapper.removeBaseAssetAfterOver(baseAssetsSearchVo);

		return count;
	}

	/**
	 * 兑付资产包
	 * @param aid
	 * @return
	 */
	public int warnAssetPackage(Integer aid){
		return assetsMapper.warnAssetPackage(aid);
	}

	/**
	 * 批量保存资产包内原始基础资产
	 * @param aid
	 * @param baseAssetIds
	 * @return
	 */
	public int saveAssetRelationBatch(String aid,String baseAssetIds){
		List list = new ArrayList();
		String[] baseAssetIdsArray = new String[]{};
		if(!"".equals(baseAssetIds) && baseAssetIds != null){
			baseAssetIdsArray = baseAssetIds.split(",");
			for(String bai : baseAssetIdsArray){
				BaseAssetChangeHisVo bacv = new BaseAssetChangeHisVo();
				bacv.setBaseAssetId(bai);
				bacv.setAid(aid);
				list.add(bacv);
			}
		}

		return peAssetRelationMapper.insertBatchInit(list);

	}

	/**
	 * 批量保存资产包基础资产新增记录
	 * @param aid
	 * @param baseAssetIds
	 * @param changeType  变更动作类型：1-新增  2-手动被替换  3-手动替换  4-自动被替换  5-自动替换 6-自动补充  7-完结解除
	 * @return
	 */
	public int saveAssetChangeHisBatch(String aid,String baseAssetIds,String createName,String changeType){
		String[] baseAssetIdsArray = new String[]{};
		List list = new ArrayList();
		if(!"".equals(baseAssetIds) && baseAssetIds != null){
			baseAssetIdsArray = baseAssetIds.split(",");
			for(String bai : baseAssetIdsArray){
				PeAccountsReceivable par = peAccountsReceivableMapper.queryBaseAsset(Integer.parseInt(bai));
				BaseAssetChangeHisVo bacv = new BaseAssetChangeHisVo();
				bacv.setBaseAssetId(bai);
				bacv.setAid(aid);
				bacv.setChangeType(changeType);
				bacv.setCreateName(createName);
				bacv.setAssetAmount(par.getBillBalance());
				bacv.setReceivableDebtor(par.getDebtorName());
				bacv.setInvoiceCode(par.getInvoiceCode());
				bacv.setInvoiceNo(par.getInvoiceNo());
				list.add(bacv);
			}
		}

		return peAssetChangeHistoryMapper.insertBatchInit(list);
	}


	/**
	 * 新增资产包初始记录
	 * @param aid
	 * @param assetAmount
	 * @param createName
	 * @return
	 */
	public int saveAssetChangeHisSelective(String aid, BigDecimal assetAmount, String createName,String changeType){
		PeAssetChangeHistory pach = new PeAssetChangeHistory();
		pach.setAid(Integer.parseInt(aid));
		pach.setAssetAmount(assetAmount);
		pach.setCreateName(createName);
		pach.setChangeType(changeType);

		return peAssetChangeHistoryMapper.insertSelective(pach);
	}

	/**
	 *批量保存资产包资金更新记录
	 * @param aid
	 * @param assetAmount
	 * @param createName
	 * @return
	 */
	public int saveFundChangeHisSelective(String aid, BigDecimal assetAmount,String createName,String changeType){
		PeFundChangeHistory peFundChangeHistory = new PeFundChangeHistory();
		peFundChangeHistory.setAid(Integer.parseInt(aid));
		peFundChangeHistory.setPaytoAmount(assetAmount);
		peFundChangeHistory.setBalanceAmount(assetAmount);
		peFundChangeHistory.setCreateName(createName);
		peFundChangeHistory.setChangeType(changeType);

		DataDictionary dict = dataDictionaryMapper.selectByKey(DictConstant.YOUHE_PUBLIC_ACCOUNT);
		peFundChangeHistory.setAccountName(dict.getDictName());
		peFundChangeHistory.setAccountNo(dict.getDictValue());


       return peFundChangeHistoryMapper.insertSelective(peFundChangeHistory);
	}

	/**
	 * 根据资产id查询资产包中资产变动记录
	 * @param changeHistoryVo
	 * @return
	 */
	public PageInfo<PeAssetChangeHistory>  queryAssetChangeHisListByAid(ChangeHistoryVo changeHistoryVo){
		Integer pageNum = null;
		Integer pageSize = null;
		if(changeHistoryVo.getPageNum() != null && changeHistoryVo.getPageNum() != ""){
			pageNum = Integer.parseInt(changeHistoryVo.getPageNum());
		}
		if(changeHistoryVo.getPageSize() != null && changeHistoryVo.getPageSize()  != ""){
			pageSize = Integer.parseInt(changeHistoryVo.getPageSize());
		}
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);

		List<PeAssetChangeHistory> list = peAssetChangeHistoryMapper.selectListByAid(changeHistoryVo);

      return new PageInfo<PeAssetChangeHistory>(list);
	}

	@Override
	public Map calculateAssetAmount(ChangeHistoryVo changeHistoryVo) {
		Map map = new HashMap();
		ChangeHistoryVo chv = new ChangeHistoryVo();
		chv.setAid(changeHistoryVo.getAid());
		List<PeAssetChangeHistory> list = peAssetChangeHistoryMapper.selectListByAid(chv);
		List<PeAssetChangeHistory> periodList = peAssetChangeHistoryMapper.selectListByAid(changeHistoryVo);
		map.put("allAssetAmount",handleAsset(list));
		map.put("periodAssetAmount",handleAsset(periodList));

		return map;
	}

	private BigDecimal handleAsset(List<PeAssetChangeHistory> list){
        BigDecimal amount = new BigDecimal(0);
		/**
		 *变更动作类型 0-新增资产包  1-关联基础资产  2-手动被替换  3-手动替换  4-自动被替换  5-自动替换 6-自动补充 7-完结解除  8-逾期 9-已回款移除 10-冲销
		 *           0正；1正；2负；3正；4负；5正；6正；7负；8负；9负；10负
		 */
		for(PeAssetChangeHistory pach : list){
            if("0".equals(pach.getChangeType())){
				amount = amount.add(pach.getAssetAmount());
			}else if("1".equals(pach.getChangeType())){
				amount = amount.add(pach.getAssetAmount());
			}else if("2".equals(pach.getChangeType())){
				amount = amount.subtract(pach.getAssetAmount());
			}else if("3".equals(pach.getChangeType())){
				amount = amount.add(pach.getAssetAmount());
			}else if("4".equals(pach.getChangeType())){
				amount = amount.subtract(pach.getAssetAmount());
			}else if("5".equals(pach.getChangeType())){
				amount = amount.add(pach.getAssetAmount());
			}else if("6".equals(pach.getChangeType())){
				amount = amount.add(pach.getAssetAmount());
			}else if("7".equals(pach.getChangeType())){
				amount = amount.subtract(pach.getAssetAmount());
			}else if("8".equals(pach.getChangeType())){
				amount = amount.subtract(pach.getAssetAmount());
			}else if("9".equals(pach.getChangeType())){
				amount = amount.subtract(pach.getAssetAmount());
			}else if("10".equals(pach.getChangeType())){
				amount = amount.subtract(pach.getAssetAmount());
			}else if("11".equals(pach.getChangeType())){
				amount = amount.subtract(pach.getAssetAmount());
			}
		}
		return amount;
	}

	/**
	 * 根据资产id查询资产包中资金变动记录
	 * @param changeHistoryVo
	 * @return
	 */
	public PageInfo<PeFundChangeHistory>  queryFundChangeHisListByAid(ChangeHistoryVo changeHistoryVo){
		Integer pageNum = null;
		Integer pageSize = null;
		if(changeHistoryVo.getPageNum() != null && changeHistoryVo.getPageNum() != ""){
			pageNum = Integer.parseInt(changeHistoryVo.getPageNum());
		}
		if(changeHistoryVo.getPageSize() != null && changeHistoryVo.getPageSize()  != ""){
			pageSize = Integer.parseInt(changeHistoryVo.getPageSize());
		}
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);

		List<PeFundChangeHistory> list = peFundChangeHistoryMapper.selectListByAid(changeHistoryVo);

       return new PageInfo<PeFundChangeHistory>(list);
	}

	@Override
	public Map calculateFundAmount(ChangeHistoryVo changeHistoryVo) {
		Map map = new HashMap();
		ChangeHistoryVo chv = new ChangeHistoryVo();
		chv.setAid(changeHistoryVo.getAid());
		List<PeFundChangeHistory> list = peFundChangeHistoryMapper.selectListByAid(chv);
		List<PeFundChangeHistory> periodList = peFundChangeHistoryMapper.selectListByAid(changeHistoryVo);

		map.put("allFundAmount",handleFund(list));
		map.put("periodFundAmount",handleFund(periodList));

		return map;
	}

	private BigDecimal handleFund(List<PeFundChangeHistory> list){
		BigDecimal amount = new BigDecimal(0);
		/**
		 *变更动作类型 0-新增资产包 1-回款   2-划款  3-划款失效  4-冲销
		 *           0正 1负  2正  3负  4正
		 */
		for(PeFundChangeHistory pfch : list){
			if("0".equals(pfch.getChangeType())){
				amount = amount.add(pfch.getPaytoAmount());
			}else if("1".equals(pfch.getChangeType())){
				amount = amount.subtract(pfch.getPaybackAmount());
			}else if("2".equals(pfch.getChangeType())){
				amount = amount.add(pfch.getPaytoAmount());
			}else if("3".equals(pfch.getChangeType())){
				amount = amount.subtract(pfch.getPaybackAmount());
			}else if("4".equals(pfch.getChangeType())){
				amount = amount.add(pfch.getPaytoAmount());
			}
		}
		return amount;
	}


	/**
	 * 将资产状态改为 7 失效
	 * @param assetsId
	 * @return
	 */
	public int updateToInvalideStatusByAssetsId(Integer assetsId){
		return assetsMapper.updateToInvalideStatusByAssetsId(assetsId);
	}

		
}
