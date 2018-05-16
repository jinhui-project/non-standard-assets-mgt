package com.jinhui.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jinhui.model.BaseObject;
import com.jinhui.model.UsRoleName;
import com.jinhui.model.UsRoleNameResponse;
import com.jinhui.service.user.UsRoleNameService;

/**
 * 角色信息
 * @author:jinhui 2017年4月18日
 */
@Controller
@RequestMapping("/admin")
public class UsRoleNameController {
    
    Logger logger = LoggerFactory.getLogger(UsRoleNameController.class);
    @Autowired
    private UsRoleNameService usRoleNameService;
    
    /**
     * 获取角色信息List
     *
     * guyishuai
     */
    @RequestMapping(value="/queryUsRoleNameList",produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
    @ResponseBody
    public String queryUsRoleNameList(UsRoleName usRoleName){
        List<UsRoleName> list = usRoleNameService.findList(usRoleName);
     return JSON.toJSONString(list); 
    }
    
    /**
     * 获取角色信息     分页
     *
     * guyishuai
     */
    @RequestMapping(value="/queryUsRoleNamePage",produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
    @ResponseBody
    public String queryUsRoleNamePage(UsRoleName usRoleName){
        UsRoleNameResponse response = new UsRoleNameResponse();
        List<UsRoleName> list = usRoleNameService.queryUsRoleNamePage(usRoleName);
        response.setList(list);
        response.setTotalCount(usRoleNameService.queryUsRoleNameCount(usRoleName));
     return JSON.toJSONString(response); 
    }
    
    /**
     * 保存  角色 
     *
     * guyishuai
     */
    @RequestMapping("/editUsRoleName")
    @ResponseBody
    public BaseObject editUsRoleName(UsRoleName usRoleName){
        BaseObject bo = new BaseObject();
        try {
            if( null == usRoleName.getId()){
                usRoleNameService.addVo(usRoleName);
            }else{
                usRoleNameService.modVoNotNull(usRoleName);
            }
            bo.setCode(0);
            bo.setMessage("保存角色成功!");
        } catch (Exception e) {
            bo.setCode(1);
            bo.setMessage("保存角色 失败!");
            logger.error("【保存 角色 】"+e);
        }
     return bo; 
    }
    
    
    /**
     * 删除角色
     *
     * guyishuai
     */
    @RequestMapping("/delUsRoleName")
    @ResponseBody
    public BaseObject delUsRoleName(Long id){
        BaseObject bo = new BaseObject();
        if( null==id ||id<=0){
            bo.setCode(1);
            bo.setMessage("删除角色id错误!");
        }
        try {
            usRoleNameService.delId(id);
            bo.setCode(0);
            bo.setMessage("删除角色成功!");
        } catch (Exception e) {
            bo.setCode(1);
            bo.setMessage("删除角色 失败!");
            logger.error("【删除角色 】"+e);
        }
     return bo; 
    }
}
