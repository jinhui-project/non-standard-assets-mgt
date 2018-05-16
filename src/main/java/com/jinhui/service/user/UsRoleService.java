package com.jinhui.service.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.UsRole;


public interface UsRoleService {
    
    void addList(List<UsRole> usRole)throws Exception;

    void modVoNotNull(UsRole usRole);

    UsRole findVo(Long id);

    List<UsRole> findList(UsRole usRole);
    
    UsRole findByIdAndRId(UsRole usRole);
    
    List<UsRole> queryUsRolePage(UsRole usRole);
    
    Integer queryUsRoleCount(UsRole usRole);
    
    void delId(Long id);
    
    void delRoleId(Long roleId);

}
