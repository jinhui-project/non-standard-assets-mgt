package com.jinhui.controller.featureName;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.jinhui.mapper.user.UserMapper;
import com.jinhui.model.BaseObject;
import com.jinhui.model.UsFeature;
import com.jinhui.model.UsFeatureName;
import com.jinhui.service.featureName.UsFeatureNameService;
import com.jinhui.service.featureName.UsFeatureService;
import com.jinhui.util.FormatReturn;
import com.jinhui.util.TokenUtil;

/**
 * 角色功能控制器
 * @time 2017-4-18 下午3:01:32  
 * @author wsc
 * @desc
 *
 */

@Controller
@RequestMapping("/roleFeature")
public class UsFeatureController {
	private static Logger logger = LoggerFactory.getLogger(UsFeatureController.class);
	
	@Autowired
	UsFeatureService usFeatureService;
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 给角色分配功能
	 * @param request
	 * @param feature
	 * @param featureIds  功能ID字符串，多个功能用逗号隔开
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/grantFeaturesForRole",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String grantFeaturesForRole(HttpServletRequest request,UsFeature usFeature,@RequestParam("featureIds") String featureIds) throws Exception{
		logger.info("输入：  "+ "usFeature= "+usFeature + " featureIds= "+featureIds);
		TokenUtil.tokenOperate(request,userMapper);
		String[] idsArray = featureIds.split(",");
		BaseObject bo = new BaseObject();
		List<Integer> idsList = new ArrayList<Integer>();
		for(int i=0;i<idsArray.length;i++){
		    if(StringUtils.isNotEmpty(idsArray[i])){
		    	idsList.add(Integer.parseInt(idsArray[i]));
		    }
		}
		int count = -1;
		count = usFeatureService.grantFeaturesForRole(usFeature, idsList);
		if(count >= 0){
			bo.setCode(0);
			bo.setMessage("分配功能成功！");
		}else if(StringUtils.isEmpty(idsArray[0])){  
			bo.setCode(0);
			bo.setMessage("分配功能成功！");
		}else{
			bo.setCode(1);
			bo.setMessage("分配功能失败！");
		}
		logger.info("输出：   "+JSON.toJSONString(count));
		return JSON.toJSONString(bo,SerializerFeature.WriteMapNullValue);
	}
	
}
