package com.jinhui.controller.featureName;

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
import com.github.pagehelper.PageInfo;
import com.jinhui.mapper.user.UserMapper;
import com.jinhui.model.UsFeatureName;
import com.jinhui.service.featureName.UsFeatureNameService;
import com.jinhui.util.FormatReturn;
import com.jinhui.util.TokenUtil;

/**
 * 功能控制器
 * @time 2017-4-18 下午3:01:32  
 * @author wsc
 * @desc
 *
 */

@Controller
@RequestMapping("/feature")
public class UsFeatureNameController {
	private static Logger logger = LoggerFactory.getLogger(UsFeatureNameController.class);
	
	@Autowired
	UsFeatureNameService usFeatureNameService;
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 查询单个功能的详情
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/queryfeatureInfo",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryfeatureInfo(@RequestParam("featureId") int featureId){
		logger.info("输入：  "+ "featureId="+featureId);
		UsFeatureName feature = usFeatureNameService.queryFeature(featureId);
		logger.info("输出：   "+JSON.toJSONString(feature));
		return JSON.toJSONString(feature,SerializerFeature.WriteMapNullValue);
	}
	
	 /**
	 * 根据角色id查询功能列表
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/queryFeatureListByRoleId",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryFeatureListByRoleId(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,@RequestParam("roleId") int roleId){
		PageInfo<UsFeatureName> featureList = usFeatureNameService.queryFeatureListByRoleId(pageNum,pageSize,roleId);
		logger.info("输出：   "+JSON.toJSONString(featureList));
		return JSON.toJSONString(featureList,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 查询功能列表
	 * @return
	 */
	@RequestMapping(value="/queryFeatureList",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryFeatureList(){
		List<UsFeatureName> featureList = usFeatureNameService.queryFeatureList();
		logger.info("输出：   "+JSON.toJSONString(featureList));
		return JSON.toJSONString(featureList,SerializerFeature.WriteMapNullValue);
	}
	
	
	/**
	 * 添加功能
	 * @param feature
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addFeature",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String addFeature(HttpServletRequest request,UsFeatureName feature) throws Exception{
		logger.info("输入：  "+ "feature="+feature);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = usFeatureNameService.addFeature(feature);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 修改功能
	 * @param feature
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateFeature",method=RequestMethod.POST)
	@ResponseBody
	public String updateFeature(HttpServletRequest request,UsFeatureName feature) throws Exception{
		logger.info("输入：  "+ "feature="+feature);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = usFeatureNameService.updateFeature(feature);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 删除 功能
	 * @param featureId 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteFeature",method=RequestMethod.POST)
	@ResponseBody
	public String deleteFeature(HttpServletRequest request,@RequestParam("featureId") int featureId) throws Exception{
		logger.info("输入：  "+ "  featureId: "+ featureId);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = usFeatureNameService.deleteFeature(featureId);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
}
