package com.jinhui.service.authorizing.shiro;

import org.apache.shiro.session.ExpiredSessionException;

/**
 * Created by jinhui on 2017/8/21.
 */
public class SessionTimeoutException extends ExpiredSessionException {

    public SessionTimeoutException() {
        super("session已结束");
    }
}
