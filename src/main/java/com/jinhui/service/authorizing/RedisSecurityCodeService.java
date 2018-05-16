package com.jinhui.service.authorizing;

import com.jinhui.constant.ConstantEntity;
import com.jinhui.util.RedisUtils;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by jinhui on 2017/8/25.
 */
public class RedisSecurityCodeService implements SecurityCodeService, Runnable {

    private static ConcurrentMap<String, Code> codeMap = new ConcurrentHashMap<String, Code>();

    private final long expiredTime;
    private final boolean enabledRedisCache;
    private final ScheduledExecutorService validationService;

    public RedisSecurityCodeService() {
        this(true);
    }

    public RedisSecurityCodeService(boolean enabledRedisCache) {
        this(3 * 60 * 1000, enabledRedisCache);
    }

    public RedisSecurityCodeService(long expiredTime, boolean enabledRedisCache) {
        this.enabledRedisCache = enabledRedisCache;
        this.expiredTime = expiredTime;
        if(!enabledRedisCache) {
            /**
             * 在本地完成expired清理动作
             */
            this.validationService = Executors.newSingleThreadScheduledExecutor();
            this.validationService.scheduleAtFixedRate(this, this.expiredTime,
                    this.expiredTime, TimeUnit.MILLISECONDS);
        } else
            this.validationService = null;
    }

    public boolean exist(String code) {
        String codeStr = strConverted(code);
        if(enabledRedisCache) {
            return RedisUtils.getValue(codeStr, ConstantEntity.IMG_CODE) != null;
        }
        Code c = codeMap.get(codeStr);
        if (c == null)
            return false;
        if (!c.isValidation(expiredTime)) {
            codeMap.remove(codeStr);
            return false;
        }
        return true;
    }

    public void put(String code) {
        String codeStr = strConverted(code);
        if(enabledRedisCache) {
            RedisUtils.setValue(codeStr,ConstantEntity.IMG_CODE, codeStr,
                    expiredTime, TimeUnit.MILLISECONDS);
            return;
        }
        codeMap.put(codeStr, new Code(new Date(), codeStr));
    }

    private static String strConverted(String src){
        return src.toLowerCase();
    }

    public void run() {
        /**
         * 清理过期验证码
         */
        Iterator<String> iter = codeMap.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();
            // 由于并发缘故，{@link RedisSecurityCodeService#getCode(String)}
            // 可能已经把该{@param key}对应的Code, 清理掉了
            Code code = codeMap.get(key);
            if(code != null && code.isValidation(expiredTime)){
                iter.remove();
            }
        }
    }

    private class Code {
        final Date createdTime;
        final String value;

        public Code(Date createdTime, String value) {
            this.createdTime = createdTime;
            this.value = value;
        }

        public boolean isValidation(long expiredTime) {
            long duration = System.currentTimeMillis() - createdTime.getTime();
            return duration < expiredTime;
        }
    }

}
