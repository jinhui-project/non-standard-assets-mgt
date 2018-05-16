
package com.jinhui.service.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.UsRoleName;


public interface UsRoleNameService {

    void addVo(UsRoleName usRoleName)throws Exception;

    void modVoNotNull(UsRoleName usRoleName)throws Exception;
    
    void delId(Long id)throws Exception;

    UsRoleName findVo(Long id);

    List<UsRoleName> findList(UsRoleName usRoleName);
    
    List<UsRoleName> queryUsRoleNamePage(UsRoleName usRoleName);
    
    Integer queryUsRoleNameCount(UsRoleName usRoleName);

}
