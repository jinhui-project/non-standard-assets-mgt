package com.jinhui.controller.saleFeedBacks;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jinhui.controller.products.ProductsController;
import com.jinhui.mapper.user.UserMapper;
import com.jinhui.model.PlatFeedbacks;
import com.jinhui.model.SaleFeedbacks;
import com.jinhui.service.saleFeedBacks.SaleFeedBacksService;
import com.jinhui.util.FormatReturn;
import com.jinhui.util.TokenUtil;

@Controller
@RequestMapping("/saleFeedbacks")
public class SaleFeedbacksController {
	private static Logger logger = LoggerFactory.getLogger(ProductsController.class);

	@Autowired
	private SaleFeedBacksService saleFeedBacksService;
	
	@Autowired
	private UserMapper userMapper;
	
	
	/**
	 * 添加资产平台反馈
	 * @param saleInfo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addPlatFeedbacks",method=RequestMethod.POST)
	@ResponseBody
	public String addPlatFeedbacks(HttpServletRequest request,PlatFeedbacks platFeedbacksInfo) throws Exception{
		logger.info("输入：  "+ "platFeedbacksInfo="+platFeedbacksInfo);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = saleFeedBacksService.addPlatFeedbacks(platFeedbacksInfo);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 查询单个资产的销售意向及反馈列表（用户）
	 * @param assetId
	 * @return
	 */
	@RequestMapping(value="/queryFeedbacksListByAssetId",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryFeedbacksListByAssetId(int assetId){
		logger.info("输入：  "+ "assetId="+assetId);
		List<SaleFeedbacks> saleFeedbacksList = saleFeedBacksService.getFeedbacksListByAssetId(assetId);
		logger.info("输出：   "+JSON.toJSONString(saleFeedbacksList));
		return JSON.toJSONString(saleFeedbacksList,SerializerFeature.WriteMapNullValue);
	}
	

	/**
	 * 查询资产平台的反馈
	 * @param assetId
	 * @return
	 */
	@RequestMapping(value="/queryPlatbacksListByAssetId",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryPlatbacksListByAssetId(int assetId){
		logger.info("输入：  "+ "assetId="+assetId);
		List<PlatFeedbacks> platFeedbacksList = saleFeedBacksService.getPlatbacksListByAssetId(assetId);
		logger.info("输出：   "+JSON.toJSONString(platFeedbacksList));
		return JSON.toJSONString(platFeedbacksList,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 查询单个产品的销售意向及反馈列表
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/queryFeedbacksListByProductId",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryFeedbacksListByProductId(@RequestParam("productId") int productId){
		logger.info("输入：  "+ "productId="+productId);
		List<SaleFeedbacks> saleFeedbacksList = saleFeedBacksService.getFeedbacksListByProductId(productId);
		logger.info("输出：   "+JSON.toJSONString(saleFeedbacksList));
		return JSON.toJSONString(saleFeedbacksList,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 添加销售意向及反馈
	 * @param saleInfo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addSaleFeedbacks",method=RequestMethod.POST)
	@ResponseBody
	public String addSaleFeedbacks(HttpServletRequest request,SaleFeedbacks saleFeedbacksInfo) throws Exception{
		logger.info("输入：  "+ "saleFeedbacksInfo="+saleFeedbacksInfo);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = saleFeedBacksService.addSaleFeedbacks(saleFeedbacksInfo);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 修改销售意向及反馈
	 * @param saleFeedbacksInfo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateSaleFeedbacks",method=RequestMethod.POST)
	@ResponseBody
	public String updateSale(HttpServletRequest request,SaleFeedbacks saleFeedbacksInfo) throws Exception{
		logger.info("输入：  "+ "saleFeedbacksInfo="+saleFeedbacksInfo);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = saleFeedBacksService.updateFeedbacksBySelective(saleFeedbacksInfo);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 删除销售意向及反馈
	 * @param feedbacksId 销售意向及反馈Id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteSaleFeedbacks",method=RequestMethod.POST)
	@ResponseBody
	public String deleteSaleFeedbacks(HttpServletRequest request,@RequestParam("feedbacksId") int feedbacksId) throws Exception{
		logger.info("输入：  "+ "feedbacksId="+feedbacksId);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = saleFeedBacksService.deleteSaleFeedbacks(feedbacksId);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
}
