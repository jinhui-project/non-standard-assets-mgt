package com.jinhui.mapper.authorizing;

import com.jinhui.mapper.authorizing.entity.AuthorizingEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by jinhui on 2017/8/8.
 */
public interface AuthorizingMapper {
    List<AuthorizingEntity> findAuthorization(String username);

    String uriMappingCode(String uri);
}
