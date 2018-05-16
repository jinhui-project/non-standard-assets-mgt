package com.jinhui.service.authorizing.shiro;

import com.jinhui.common.Nullable;
import com.jinhui.service.authorizing.AuthorizingService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by jinhui on 2017/8/15.
 */
public class PermissionUtils {

    private static AuthorizingService authorizingService;

    private static ThreadLocal<String> sessionToken = new ThreadLocal<String>();

    public static boolean isLogin(HttpServletRequest request){
        String token = sessionToken(request);
        if(StringUtils.isEmpty(token))
            return false;
        /**
         * 使用token检查是否存在登录session
         */
        //Session session = SecurityUtils.getSecurityManager().getSession(new WebSessionKey(token, request, response));
        Session session = SecurityUtils.getSecurityManager().getSession(new DefaultSessionKey(token));
        if(session != null){
            session.touch();
            sessionToken.set(token);
            return true;
        }
        return false;
    }

    public static Subject getSubject(){
        String token = sessionToken.get();
        if(StringUtils.isEmpty(token))
            throw new AuthenticationException("未经认证");
        return new Subject.Builder()
                .sessionId(sessionToken.get())
                .buildSubject();
    }

    public static void logout(){
        Subject subject = getSubject();
        subject.logout();
    }

    /**
     *
     * @param url eg: /admin/review
     * @param argv eg: WAIT_BIZ_MANAGER
     */
    public static void checkPermission(String url, @Nullable String argv){
        Subject subject = getSubject();
        String permissionCode = authorizingService.uriMappingCode(url, argv);
        if(StringUtils.isEmpty(permissionCode))
            throw new IllegalArgumentException("不明操作");
        subject.checkPermission(permissionCode);
    }

    private static String sessionToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            /**
             * 为了兼容原来使用在request参数里携带token的方式
             */
            token = request.getParameter("token");
        }
        return token;
    }

    public static void setAuthorizingService(AuthorizingService authorizingService) {
        PermissionUtils.authorizingService = authorizingService;
    }
}
