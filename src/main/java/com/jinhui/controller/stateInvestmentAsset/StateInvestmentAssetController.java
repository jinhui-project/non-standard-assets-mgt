package com.jinhui.controller.stateInvestmentAsset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinhui.model.BaseObject;
import com.jinhui.service.stateInvestmentAsset.StateInvestmentAssetService;

@Controller
@RequestMapping("/admin")
public class StateInvestmentAssetController {
	
	
	@Autowired
	private StateInvestmentAssetService stateInvestmentAssetService;
	
	Logger logger = LoggerFactory.getLogger(StateInvestmentAssetController.class);
	
	/**
	 * 资产评分
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryByIdScore")
	@ResponseBody
	public BaseObject queryByIdScore(Integer id){
		BaseObject bo = new BaseObject();
		String info = stateInvestmentAssetService.queryByIdScore(id);
		bo.setCode(0);
		bo.setMessage(info);
		return bo;
	}

}
