package com.jinhui.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinhui.mapper.user.UsRoleMapper;
import com.jinhui.model.UsRole;
import com.jinhui.service.user.UsRoleService;

@Service
public class UsRoleServiceImpl implements UsRoleService {
    
    @Autowired
    private UsRoleMapper usRoleMapper;


    @Override
    public void addList(List<UsRole> usRole)throws Exception {
         usRoleMapper.addList(usRole);
    }

    @Override
    public void modVoNotNull(UsRole usRole) {
        usRoleMapper.modVoNotNull(usRole);        
    }

    @Override
    public UsRole findVo(Long id) {
        return usRoleMapper.findVo(id);
    }

    @Override
    public List<UsRole> findList(UsRole usRole) {
        return usRoleMapper.findList(usRole);
    }

    @Override
    public UsRole findByIdAndRId(UsRole usRole) {
       List<UsRole> list = usRoleMapper.findList(usRole);
       UsRole u = null;
       if(null !=list && list.size() >0 ){
           u = list.get(0);
       }
        return u;
    }

    @Override
    public List<UsRole> queryUsRolePage(UsRole usRole) {
        int begin = usRole.getPageIndex() *  usRole.getPageSize();
        usRole.setOffset(begin);
        usRole.setLimit(usRole.getPageSize()); 
        return usRoleMapper.queryUsRolePage(usRole);
    }

    @Override
    public Integer queryUsRoleCount(UsRole usRole) {
        return usRoleMapper.queryUsRoleCount(usRole);
    }

    @Override
    public void delId(Long id) {
        usRoleMapper.delId(id);
    }

    @Override
    public void delRoleId(Long roleId) {
        usRoleMapper.delRoleId(roleId);
    }
    
    


}
