package com.jinhui.service.user.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinhui.mapper.featureName.UsFeatureMapper;
import com.jinhui.mapper.user.UsRoleMapper;
import com.jinhui.mapper.user.UsRoleNameMapper;
import com.jinhui.model.UsRole;
import com.jinhui.model.UsRoleName;
import com.jinhui.service.user.UsRoleNameService;

@Service
public class UsRoleNameServiceImpl implements UsRoleNameService {
    Logger logger = LoggerFactory.getLogger(UsRoleNameServiceImpl.class);
    @Autowired
    private UsRoleNameMapper usRoleNameMapper;
    
    @Autowired
    private UsRoleMapper usRoleMapper;
    
    @Autowired
    private UsFeatureMapper usFeatureMapper;

    @Override
    public void addVo(UsRoleName usRoleName) throws Exception {

        usRoleNameMapper.addVo(usRoleName);        
    }

    @Override
    public void modVoNotNull(UsRoleName usRoleName) throws Exception {
        UsRole usRole = new UsRole();
        usRole.setRoleId(usRoleName.getId());
        usRoleNameMapper.modVoNotNull(usRoleName);  
        List<UsRole> urList = usRoleMapper.findList(usRole);
        if( null != urList && urList.size() > 0){
            for(UsRole role : urList){
                role.setRoleName(usRoleName.getrName());
                usRoleMapper.modVoNotNull(role);
            }
        }
    }

    @Override
    public UsRoleName findVo(Long id) {
        return usRoleNameMapper.findVo(id);
    }

    @Override
    public List<UsRoleName> findList(UsRoleName usRoleName) {
        return usRoleNameMapper.findList(usRoleName);
    }

    @Override
    public List<UsRoleName> queryUsRoleNamePage(UsRoleName usRoleName) {
//        List<UsRoleName> list = new  ArrayList<UsRoleName>();
        int begin = usRoleName.getPageIndex() *  usRoleName.getPageSize();
        usRoleName.setOffset(begin);
        usRoleName.setLimit(usRoleName.getPageSize()); 
        List<UsRoleName> listUser  =usRoleNameMapper.queryUsRoleNamePage(usRoleName);
//        if( null != listUser && listUser.size() >0 ){
//         for( UsRoleName u:listUser ){
//          u.setCreateTimeStr(UtilTool.stampToDate(u.getCreateTime()));
//          u.setEditorTimeStr(UtilTool.stampToDate(u.getEditorTime()));
//          list.add(u);
//         }
//        }
        return listUser;
    }

    @Override
    public Integer queryUsRoleNameCount(UsRoleName usRoleName) {
        return usRoleNameMapper.queryUsRoleNameCount(usRoleName);
    }

    @Override
    public void delId(Long id) throws Exception {
        usRoleNameMapper.delId(id);
        UsRole usRole = new UsRole();
        usRole.setRoleId(id);
        List<UsRole> list = usRoleMapper.findList(usRole);
        if( null != list && list.size() > 0){
            usRoleMapper.delList(list); 
        }
        usFeatureMapper.deleteByRoleId(Integer.parseInt(id.toString()));
    }

}
