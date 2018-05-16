package com.jinhui.controller.agreementpowertemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jinhui.model.AgreementPowerTemplate;
import com.jinhui.model.AgreementTemplate;
import com.jinhui.model.BaseObject;
import com.jinhui.service.agreementpowertemplate.AgreementPowerTemplateService;
import com.jinhui.service.agreementtemplate.AgreementTemplateService;

@Controller
@RequestMapping("/admin")
public class AgreementPowerController {

	Logger logger = LoggerFactory.getLogger(AgreementPowerController.class);
	@Autowired
	private AgreementPowerTemplateService agreementPowerTemplateService;
	
	@Autowired
	private AgreementTemplateService agreementTemplateService;
	
	
	/**
	 * 保存权限信息
	 * @return
	 */
	@RequestMapping("/saveInfoAgreementPower")
	@ResponseBody
	public BaseObject saveInfoAgreementPower(AgreementPowerTemplate ap){
		BaseObject bo = new BaseObject();
		AgreementPowerTemplate a = new AgreementPowerTemplate();
		 a  = agreementPowerTemplateService.findAgreementPower(ap);
		try {
			if( null == a ){
				agreementPowerTemplateService.addAgreementPower(ap);
			}else{
				agreementPowerTemplateService.modNotNullAgreementPower(ap);
			}
			bo.setCode(0);
			bo.setMessage("保存成功");
		} catch (Exception e) {
			logger.error("机构信息【保存权限信息  saveInfoAgreementPower】异常"+e);	
			bo.setCode(1);
			bo.setMessage("保存失败");
		}
		return bo;
	}
	
	/**
	 * 查询
	 * @return
	 */
	@RequestMapping("/queryByIdAgreementPower")
	@ResponseBody
	public AgreementPowerTemplate queryByIdAgreementPower(AgreementPowerTemplate ap){
		AgreementPowerTemplate apt = new AgreementPowerTemplate();
		apt  = agreementPowerTemplateService.findAgreementPower(ap);
		return apt;
	}
	
	/**
	 * 保存协议模板
	 * @return
	 */
	@RequestMapping("/saveAgreementTemplate")
	@ResponseBody
	public BaseObject saveAgreementTemplate(AgreementTemplate agreementTemplate){
		    BaseObject bo = new BaseObject();
		try{
			agreementTemplateService.addTemplateInfo(agreementTemplate);
	    	bo.setCode(0);
			bo.setMessage("保存成功");
		} catch (Exception e) {
			logger.error("机构信息【保存权限信息  saveInfoAgreementPower】异常"+e);	
			bo.setCode(1);
			bo.setMessage("保存失败");
		}
		return bo;
	}
}
