package com.jinhui.test;

import com.jinhui.service.authorizing.AuthorizingService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.Serializable;

/**
 * Created by jinhui on 2017/8/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:test-context.xml", "classpath*:springmvc-servlet.xml" })
public class AuthorizingTests extends AbstractJUnit4SpringContextTests {

    @Autowired
    private AuthorizingService authorizingService;

    @Test
    public void authorization(){
        authorizingService.authorization("18811783632");
    }

    @Test
    public void login(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //SecurityUtils.getSecurityManager().getSession(new DefaultSessionKey(""));
        UsernamePasswordToken token = new UsernamePasswordToken("18811783632","pwd");
        token.setRememberMe(true);
        subject.login(token);
        Session session = subject.getSession();
        System.out.println(session.getStartTimestamp());
        Serializable sid = session.getId();

        //session = SecurityUtils.getSecurityManager().getSession(new DefaultSessionKey(sid));
        subject = new Subject.Builder().sessionId(sid).buildSubject();
        new Subject.Builder().sessionId(sid).buildSubject();
        subject.checkPermission("PRODUCT_DELETE");
        /*System.out.println(session.getStartTimestamp());
        new Thread(new Runnable() {
            public void run() {
                Subject subject = SecurityUtils.getSubject();
                //使用token检查权限
                subject.checkPermission("PRODUCT_DELETE");
            }
        }).run();*/

    }

}
