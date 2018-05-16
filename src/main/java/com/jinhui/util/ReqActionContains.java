package com.jinhui.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 需要记录操作日志的请求
 * 
 * @time 2017-4-5 上午10:10:44
 * @author wsc
 * @desc
 * 
 */
public class ReqActionContains {
	private static Map<String, String> ACTION_MAP = new HashMap<String, String>();
	private static Map<String, String> LOGIN_REGIST_MAP = new HashMap<String, String>();
	private static Map<String, String> METURIAL_MAP = new HashMap<String, String>();

	public static Map actionMaps() {
		ACTION_MAP.put("saveOrganization", "创建机构");
		ACTION_MAP.put("updateOrganization", "编辑机构");
		ACTION_MAP.put("updateUserStateAndInfo", "用户信息审核/禁用/启用");
		ACTION_MAP.put("addPlatFeedbacks", "平台反馈");
		ACTION_MAP.put("editStatusByAssetsId", "资产发布");
		ACTION_MAP.put("assetToProject", "资产立项");
		ACTION_MAP.put("changeToOverStatus", "资产完结");
		ACTION_MAP.put("addProduct", "新增产品");
		ACTION_MAP.put("updateProduct", "编辑产品");
		ACTION_MAP.put("deleteProduct", "删除产品");
		ACTION_MAP.put("updateSale", "保存销售计划");
		ACTION_MAP.put("updateOpenSales", "开放销售产品");
		ACTION_MAP.put("overProduct", "结束产品");
		
		return ACTION_MAP;
	}
	
	public static Map loginAndRegistMap(){
		LOGIN_REGIST_MAP.put("login", "登录_内管");
		return LOGIN_REGIST_MAP;
	}
	
	public static Map muterialMaps(){
		METURIAL_MAP.put("lisence", "营业执照副本");
		METURIAL_MAP.put("orgInstCode", "组织机构代码证");
		METURIAL_MAP.put("taxReg", "税务登记证");
		METURIAL_MAP.put("leId", "企业法人代表身份证");
		METURIAL_MAP.put("bankCard", "银行卡复印件");
		METURIAL_MAP.put("acApproval", "开户许可证");
		METURIAL_MAP.put("article", "公司章程");
		METURIAL_MAP.put("captialVer", "最新验资报告");
		METURIAL_MAP.put("creditReport", "央行信用信息报告");
		METURIAL_MAP.put("feasibility", "项目可行性报告");
		METURIAL_MAP.put("auditReport", "近三年审计报告");
		METURIAL_MAP.put("financeStatement", "最近一期财务报表");
		METURIAL_MAP.put("otherFile", "其他");
		METURIAL_MAP.put("yearTransaction", "近三年上下游交易流水");
		METURIAL_MAP.put("absRatingReport", "ABS评级报告");
		METURIAL_MAP.put("relatedAgreement", "相关协议");
		return METURIAL_MAP;
	}

}
