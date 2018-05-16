package com.jinhui.service.authorizing.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by jinhui on 2017/8/8.
 */
public class DefaultAuthorizingRealm extends AuthorizingRealm {

    /**
     * 获取登录用户角色和功能权限信息，
     * 使用{@link org.apache.shiro.cache.CacheManager}和{@link org.apache.shiro.cache.Cache}获取数据.
     * @param principals 用户登录信息
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object username =principals.getPrimaryPrincipal();
        Cache<Object, AuthorizationInfo> infoCache = getAuthorizationCache();
        AuthorizationInfo info = infoCache.get(username);
        return info;
    }

    /**
     * 验证用户登录，成功并返回通过用户验证信息，否则失败返回异常 {@link AuthenticationException}
     * @param token
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // TODO: 2017/8/8 用户登录验证, 已在login controller中完成
        // Object username = token.getPrincipal();
        return new SimpleAccount(token.getPrincipal(), token.getCredentials(), getName());
    }
}
