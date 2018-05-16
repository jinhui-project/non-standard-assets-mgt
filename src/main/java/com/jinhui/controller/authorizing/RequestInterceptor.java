package com.jinhui.controller.authorizing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jinhui.service.authorizing.shiro.PermissionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;




public class RequestInterceptor extends HandlerInterceptorAdapter {

    private String unauthenticatedUrl;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  
            Object handler) throws Exception {
        /**
         * 测试环境不限制
         */
        String env = System.getProperty("deploy.env");
        if("dev".equals(env))
            return true;

        if(PermissionUtils.isLogin(request)) {
            return true;  
        }

        //token已失效，返回提示信息
        request.getRequestDispatcher(unauthenticatedUrl).forward(request, response);
        return false;
    }

    public void setUnauthenticatedUrl(String unauthenticatedUrl) {
        this.unauthenticatedUrl = unauthenticatedUrl;
    }
}
