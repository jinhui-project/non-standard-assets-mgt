
package com.jinhui.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.UsRoleName;


public interface UsRoleNameMapper {

    void addVo(UsRoleName usRoleName);

    void modVoNotNull(UsRoleName usRoleName);

    UsRoleName findVo(@Param("id")Long id);

    List<UsRoleName> findList(UsRoleName usRoleName);
    
    List<UsRoleName> queryUsRoleNamePage(UsRoleName usRoleName);
    
    Integer queryUsRoleNameCount(UsRoleName usRoleName);
    
    void delId(@Param("id")Long id);

}
