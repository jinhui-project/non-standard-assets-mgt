
package com.jinhui.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jinhui.model.BaseObject;
import com.jinhui.model.UsRole;
import com.jinhui.model.UsRoleName;
import com.jinhui.model.Users;
import com.jinhui.model.UsersResponse;
import com.jinhui.service.user.UsRoleNameService;
import com.jinhui.service.user.UsRoleService;
import com.jinhui.service.user.UsersService;

/**
 * 用户角色
 * @author:jinhui 2017年4月18日
 */
@Controller
@RequestMapping("/admin")
public class UsRoleController {

    Logger logger = LoggerFactory.getLogger(UsRoleController.class);

    @Autowired
    private UsRoleService usRoleService;

    @Autowired
    private UsRoleNameService usRoleNameService;
    

    @Autowired
    private  UsersService usersService;
    
    
    /**
     * 获取是平台方用户的信息List
     *
     * guyishuai
     */
    @RequestMapping(value="/getUsetIsSuper",produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
    @ResponseBody
    public String getUsetIsSuper(){
        Users us = new Users();
        us.setGid(1);
        List<Users> list = usersService.findList(us);
        return JSON.toJSONString(list); 
    }
    
    
    /**
     * 获取是平台方用户的信息   分页信息
     *
     * guyishuai
     */
    @RequestMapping(value="/getUsetIsSuperPage",produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
    @ResponseBody
    public String getUsetIsSuperPage(int pageIndex, int pageSize){
        UsersResponse ur = new UsersResponse();
        List<Users> listRsp = new ArrayList<Users>();
        Users us = new Users();
        us.setGid(1);
        List<Users> list = usersService.queryUsersPage(us, pageIndex, pageSize);
        if(null != list && list.size() >0 ){
            for(int i =0;i<list.size();i++ ){
                listRsp.add(list.get(i));
                UsRole getRole=new UsRole();
                getRole.setUsId(Long.parseLong(list.get(i).getId().toString()));
                listRsp.get(i).setUsRoleList(usRoleService.findList(getRole)); 
            }
        }
        ur.setList(listRsp);
        ur.setTotalCount(usersService.queryUsersCount(us));
        return JSON.toJSONString(ur); 
    }
    
    
    
    /**
     * 获取是平台方用户的信息   分页信息(角色查询用户)
     *
     * guyishuai
     */
    @RequestMapping(value="/getUsRolePage",produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
    @ResponseBody
    public String getUsRolePage(UsRole usRole){
        UsersResponse ur = new UsersResponse();
        List<Users> listRsp = new ArrayList<Users>();
        Users us = new Users();
        us.setGid(1);
        us.setRoleId(usRole.getRoleId());
        List<Users> list = usersService.queryUsersAndUsRolePage(us, usRole.getPageIndex(), usRole.getPageSize());
        if(null != list && list.size() >0 ){
            for(int i =0;i<list.size();i++ ){
                listRsp.add(list.get(i));
                UsRole getRole=new UsRole();
                getRole.setUsId(Long.parseLong(list.get(i).getId().toString()));
                getRole.setRoleId(usRole.getRoleId());
                listRsp.get(i).setUsRoleList(usRoleService.findList(getRole)); 
            }
        }
        ur.setList(listRsp);
        ur.setTotalCount(usersService.queryUsersAndUsRoleCount(us));
        return JSON.toJSONString(ur); 
    }
    
    
    /**
     * 获取是平台方用户的信息   树节点
     *
     * guyishuai
     */
    @RequestMapping(value="/getUsRoleTree",produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
    @ResponseBody
    public String getUsRoleTree(Users us){
        UsRole usRole = new UsRole();
        usRole.setUsId(Long.parseLong(us.getId().toString()));
        List<UsRole> list = usRoleService.findList(usRole);
        return JSON.toJSONString(list); 
    }


    /**
     * 绑定 UsRole 用户角色（多个角色绑定一个用户）
     *
     * guyishuai
     */
    @RequestMapping("/saveUsRoleList")
    @ResponseBody
    public BaseObject saveUsRoleList(UsRole usRole) {
        BaseObject bo = new BaseObject();
        List<UsRole> list = new ArrayList<UsRole>();
        if (null != usRole.getRoleIds() && !"".equals(usRole.getRoleIds())) {

            String[] arrIds = usRole.getRoleIds().split(",");

            for (String rId : arrIds) {

                UsRole role = new UsRole();
                role.setRoleId(Long.valueOf(rId));
                role.setUsId(usRole.getUsId());
                UsRole ru = usRoleService.findByIdAndRId(role);

                if (null != ru) {
                    logger.info("【保存  UsRole信息  角色roleId 已经存在】" + role.getRoleId());
                    continue;
                }
                logger.info("【  角色roleId 查询 UsRoleName】" + role.getRoleId());
                UsRoleName usRoleName = usRoleNameService.findVo(role.getRoleId());
                if (null != usRoleName) {
                    role.setRoleId(usRoleName.getId());
                    role.setRoleName(usRoleName.getrName());
                    role.setUsId(usRole.getUsId());
                    list.add(role);
                }
            }
        }
        if (list.size() > 0) {
            try {
                usRoleService.addList(list);
            } catch (Exception e) {
                logger.error("【保存  用户角色（多个角色绑定一个用户）信息  saveUsRoleList】" + e);
                bo.setCode(1);
                bo.setMessage("绑定失败");
                return bo;
            }
        }

        bo.setCode(0);
        bo.setMessage("绑定成功");
        return bo;
    }
    
    
    /**
     * 绑定 UsRole 用户角色（多个用户绑定一个角色）
     *
     * guyishuai
     */
    @RequestMapping("/saveUserUsRoleList")
    @ResponseBody
    public BaseObject saveUserUsRoleList(UsRole usRole) {
        BaseObject bo = new BaseObject();
        List<UsRole> list = new ArrayList<UsRole>();
        usRoleService.delRoleId(usRole.getRoleId());
        if (null != usRole.getUsIds() && !"".equals(usRole.getUsIds())) {

            String[] arrIds = usRole.getUsIds().split(",");

            for (String uId : arrIds) {

                UsRole role = new UsRole();
                role.setRoleId(usRole.getRoleId());
                role.setUsId(Long.valueOf(uId));
                UsRole ru = usRoleService.findByIdAndRId(role);

                if (null != ru) {
                    continue;
                }

                logger.info("【  角色roleId 查询 UsRoleName】" + role.getRoleId());
                UsRoleName usRoleName = usRoleNameService.findVo(role.getRoleId());
                if (null != usRoleName) {
                    role.setRoleId(usRoleName.getId());
                    role.setRoleName(usRoleName.getrName());
                    role.setUsId(role.getUsId());
                    list.add(role);
                }
            }
        }
        if (list.size() > 0) {
            try {
                usRoleService.addList(list);
            } catch (Exception e) {
                logger.error("【保存  用户角色（多个用户绑定一个角色）信息  saveUserUsRoleList】" + e);
                bo.setCode(1);
                bo.setMessage("绑定失败");
                return bo;
            }
        }
//        if( null != usRole.getDelUsIds() && !"".equals( usRole.getDelUsIds() )){
//            
//            String[] arrDelIds = usRole.getDelUsIds().split(",");
//            
//            for(String idsDel : arrDelIds){
//              try {
//                  UsRole delRole = new UsRole();
//                  delRole.setRoleId(usRole.getRoleId());
//                  delRole.setUsId(Long.parseLong(idsDel));
//                  UsRole r = usRoleService.findByIdAndRId(delRole);
//                  logger.info("【删除在    delUsId: 】" +idsDel+",角色类型"+usRole.getRoleId());
//                  usRoleService.delId(r.getId());
//                } catch (Exception e) {
//                    logger.error("【删除在添加  UsRole信息  角色UsId 已经存在 】" +idsDel + "异常：" + e);
//                }
//            }
//        }
//        


        bo.setCode(0);
        bo.setMessage("绑定成功");
        return bo;
    }
}
