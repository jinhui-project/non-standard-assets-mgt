package com.jinhui.service.authorizing;

/**
 * Created by jinhui on 2017/8/25.
 */
public interface SecurityCodeService {
    boolean exist(String codeStr);
    void put(String codeStr);
}
