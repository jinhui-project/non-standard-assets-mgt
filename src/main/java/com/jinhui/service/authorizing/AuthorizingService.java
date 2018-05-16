package com.jinhui.service.authorizing;

import com.jinhui.common.Nullable;
import com.jinhui.mapper.authorizing.AuthorizingMapper;
import com.jinhui.mapper.authorizing.entity.AuthorizingEntity;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Map;


/**
 * Created by jinhui on 2017/8/8.
 */
public class AuthorizingService {

    @Autowired
    private AuthorizingMapper authorizingMapper;

    public SimpleAuthorizationInfo authorization(String username){
        List<AuthorizingEntity> auths = authorizingMapper.findAuthorization(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for(AuthorizingEntity entity:auths) {
            authorizationInfo.addRole(entity.getRoleId());
            authorizationInfo.addStringPermission(entity.getFeatureCode());
        }
        return authorizationInfo;
    }

    public List<AuthorizingEntity> findAuthorization(String username){
        List<AuthorizingEntity> auths = authorizingMapper.findAuthorization(username);
        return auths;
    }

    public String uriMappingCode(String url, @Nullable String argv){
        StringBuilder uri = new StringBuilder();
        uri.append(url);
        if(!StringUtils.isEmpty(argv))
            uri.append(":%").append(argv).append("%");
        return authorizingMapper.uriMappingCode(uri.toString());
    }
}
