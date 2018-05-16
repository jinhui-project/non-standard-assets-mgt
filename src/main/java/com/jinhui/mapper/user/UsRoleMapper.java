
package com.jinhui.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.UsRole;


public interface UsRoleMapper {

    void addList(@Param("list")List<UsRole> usRole);
    
    void delList(@Param("list")List<UsRole> usRole);

    void modVoNotNull(UsRole usRole);
    
    void delId(@Param("id")Long id);
    
    void delRoleId(@Param("roleId")Long roleId);
    

    UsRole findVo(@Param("id")Long id);

    List<UsRole> findList(UsRole usRole);
    
    List<UsRole> queryUsRolePage(UsRole usRole);
    
    Integer queryUsRoleCount(UsRole usRole);

}
