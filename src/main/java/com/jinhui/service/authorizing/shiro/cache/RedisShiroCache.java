package com.jinhui.service.authorizing.shiro.cache;

import com.jinhui.service.authorizing.AuthorizingService;
import com.jinhui.util.RedisUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by jinhui on 2017/8/8.
 */

/**
 * 对于用户登录授权以及权限的缓存，登录用
 */
public class RedisShiroCache implements Cache<Object, AuthorizationInfo> {

    private static Logger logger = LoggerFactory.getLogger(RedisShiroCache.class);

    private final static String ROLE_SET = "_role";
    private final static String PERMISSION_SET = "_permission";

    private boolean enabledRedisCache = true;

    private AuthorizingService authorizingService;

    private long expiredTime = 30 * 60 * 1000;

    /**
     * 不要求一致性,允许脏读
     * @param username
     * @return
     * @throws CacheException
     */
    public AuthorizationInfo get(Object username) throws CacheException {
        Assert.notNull(username, "username");
        Set<String> roles = null, permissions = null;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(enabledRedisCache) {
            roles = (Set<String>) RedisUtils.getValue(ROLE_SET, username.toString());
            permissions = (Set<String>) RedisUtils.getValue(PERMISSION_SET, username.toString());
        }
        if(roles != null)
            info.addRoles(roles);
        if(permissions != null)
            info.addStringPermissions(permissions);

        if(permissions == null) {
            /**
             * 通常情况下用户权限不会为空，如果在缓存中查询权限集为空，则必须从关系数据库中读取。
             */
            info = authorizingService.authorization(username.toString());
            if(info != null && enabledRedisCache)
                put(username, info);
        }

        return info;
    }

    /**
     * 允许重复set
     * @param username
     * @param value
     * @return 总是返回空
     * @throws CacheException
     */
    public AuthorizationInfo put(Object username, AuthorizationInfo value) throws CacheException {
        if(!enabledRedisCache)
            return null;
        RedisUtils.setValue(ROLE_SET, username.toString(), (HashSet) value.getRoles(),
                expiredTime, TimeUnit.MILLISECONDS);
        RedisUtils.setValue(PERMISSION_SET, username.toString(), (HashSet) value.getStringPermissions(),
                expiredTime, TimeUnit.MILLISECONDS);
        return null;
    }

    public void setEnabledRedisCache(boolean enabledRedisCache) {
        this.enabledRedisCache = enabledRedisCache;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public void setAuthorizingService(AuthorizingService authorizingService) {
        this.authorizingService = authorizingService;
    }

    /**
     * 不需要主动移除权限验证缓存
     * @param uid
     * @return
     * @throws CacheException
     */
    public AuthorizationInfo remove(Object uid) throws CacheException {
        //do nothing;
        return null;
    }

    /**
     * 不需要主动移除权限验证缓存
     * @throws CacheException
     */
    public void clear() throws CacheException {
        //do nothing;
    }

    /**
     * 没有场景需求
     * @return
     */
    public int size() {
        throw new UnsupportedOperationException();
    }

    /**
     * 没有场景需求
     * @return
     */
    public Set<Object> keys() {
        throw new UnsupportedOperationException();
    }

    /**
     * 没有场景需求
     * @return
     */
    public Collection<AuthorizationInfo> values() {
        throw new UnsupportedOperationException();
    }
}
