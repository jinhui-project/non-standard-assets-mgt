package com.jinhui.service.authorizing.shiro;


import com.jinhui.util.RedisUtils;
import org.apache.shiro.session.*;
import org.apache.shiro.session.mgt.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 根据 attributeKey，有选择的缓存session信息;
 * 设置 {@parm enabledSharedSessionData}来有选择的启用共享session功能。
 */
public class RedisSessionManager extends DefaultSessionManager {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionManager.class);

    private boolean enabledSharedSessionData;

    private Set<String> sharedSessionDataKeys;

    public RedisSessionManager() {
        enabledSharedSessionData = false;
        sharedSessionDataKeys = new HashSet<String>();
    }

    @Override
    public Collection<Object> getAttributeKeys(SessionKey key) {

        Collection<Object> keys = super.getAttributeKeys(key);
        if(enabledSharedSessionData) {
            /**
             * 从redis获取 {@param key} 对应session的所有attribute key
             */
            Set sharedKeys = RedisUtils.extractAttributeKey((String) key.getSessionId());
            keys.addAll(sharedKeys);
        }
        return keys;
    }

    @Override
    public Object getAttribute(SessionKey sessionKey, Object attributeKey)
            throws InvalidSessionException {
        if(checkSharedStrategy(attributeKey)){
            Object object = RedisUtils.getValue((String) attributeKey, (String) sessionKey.getSessionId());
            return object;
        }
        return super.getAttribute(sessionKey, attributeKey);
    }

    @Override
    public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value)
            throws InvalidSessionException {
        if(checkSharedStrategy(attributeKey)) {
            if(value instanceof Serializable)
                RedisUtils.setValue((String) attributeKey, (String) sessionKey.getSessionId(),
                        (Serializable) value, getGlobalSessionTimeout(), TimeUnit.MILLISECONDS);
            else
                throw new IllegalArgumentException("不可共享非序列化value");
            return;
        }
        super.setAttribute(sessionKey, attributeKey, value);
    }

    private boolean checkSharedStrategy(Object attributeKey){
        return enabledSharedSessionData && sharedSessionDataKeys.contains(attributeKey);
    }

    /**
     * 如果是集群， session只在一台机器上创建，因此必须共享 SessionId。
     * 当request发过来，获取request中携带的 SessionId，使用 SessionId 在本地获取session，
     * 如果为null，则用 SessionId 去redis检查是否存在，如果存在则在本地构建session返回
     * (实际就是{@link SimpleSession}的代理{@link DelegatingSession},{@see RedisSessionManager#restoreSession})，
     * 否则返回空， 请求重新登录。
     * {@link org.apache.shiro.session.mgt.AbstractNativeSessionManager#getSession(SessionKey)}
     * @param key
     * @return
     * @throws SessionException
     */
    @Override
    public Session getSession(SessionKey key) throws SessionException {
        Session session = null;
        try {
            session = getLocalSession(key);
        } catch (UnknownSessionException | ExpiredSessionException se){
            //ignored
            logger.info("获取本地无效session:{}",se);
            session = null;
        }
        if(!enabledSharedSessionData || session != null)
            return session;
        /**
         * 检查redis，判断session是否已创建，
         * 若已创建，则使用SessionFactory在本地构建SimpleSession
         */
        Serializable sid = RedisUtils.getValue((String) key.getSessionId());
        if(sid != null){
            session = restoreSession(key);
        }

        return session;
    }

    /**
     * 每一次通过
     * {@link org.apache.shiro.session.mgt.AbstractValidatingSessionManager#doGetSession(SessionKey)}}
     * 获取session
     * 或是通过{@link org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler}
     * 定时检查，都会去调用
     * {@link org.apache.shiro.session.mgt.AbstractValidatingSessionManager#doValidate(Session)}
     * 验证session是否过期。
     * 共享session过期的标准是该redis中sessionId过期, 由于redis已经帮助完成了session过期检查，
     * 所以这里只需要定期清理本地内存中的过期session。
     * 然而{@link org.apache.shiro.session.mgt.AbstractValidatingSessionManager#doGetSession(SessionKey)}}
     * 是一个final方法,无法被overwrite，所以只能copy Shiro原来的代码实现来定义getLocalSession(SessionKey key)
     * @param key
     * @return
     */
    private Session getLocalSession(SessionKey key){
        Session session = lookupSession(key);
        return session != null ? createExposedSession(session, key) : null;
    }
    private Session lookupSession(SessionKey key) throws SessionException {
        if (key == null) {
            throw new NullPointerException("SessionKey argument cannot be null.");
        }
        //enableSessionValidationIfNecessary
        SessionValidationScheduler scheduler = getSessionValidationScheduler();
        if (enabledSharedSessionData ||
                (isSessionValidationSchedulerEnabled() && (scheduler == null || !scheduler.isEnabled()))
                ) {
            enableSessionValidation();
        }
        Session s = retrieveSession(key);
        if (!enabledSharedSessionData && s != null) {
            validate(s, key);
        }
        return s;
    }

    /**
     * 根据{@link SessionKey}以及继承自{@link DefaultSessionManager}的默认创建方法，
     * 重新在本地构建session。
     * @param key
     * @return
     */
    private Session restoreSession(SessionKey key){
        SimpleSession restoreSession = (SimpleSession) getSessionFactory().createSession(null);
        restoreSession.setId(key.getSessionId());
        restoreSession.setTimeout(getGlobalSessionTimeout());
        create(restoreSession);
        return createExposedSession(restoreSession, key);
    }

    /**
     * 开启一个新的session， 并且在新的session开启之后做一系列的session共享工作。
     * {@link org.apache.shiro.session.mgt.AbstractNativeSessionManager#start(SessionContext)}
     * @param context
     * @return
     */
    @Override
    public Session start(SessionContext context) {
        Session session = super.start(context);
        if(enabledSharedSessionData) {
            shareSessionData(session);
        }
        return session;
    }

    /**
     * 完成session基本数据共享
     */
    private void shareSessionData(Session session){
        refreshTTL(session.getId());
    }

    /**
     * 刷新session存活时间
     */
    private void refreshTTL(Serializable sessionId){
        RedisUtils.setValue((String) sessionId, new Date(),
                getGlobalSessionTimeout(), TimeUnit.MILLISECONDS);
    }

    /**
     * {@link org.apache.shiro.session.mgt.AbstractNativeSessionManager#touch(SessionKey)}
     * @param key
     * @throws InvalidSessionException
     */
    @Override
    public void touch(SessionKey key) throws InvalidSessionException {
        if(enabledSharedSessionData) {
            //刷新session存活时间
            refreshTTL(key.getSessionId());
        }
        super.touch(key);
    }

    /**
     * {@link org.apache.shiro.session.mgt.AbstractNativeSessionManager#getLastAccessTime(SessionKey)}
     * @param key
     * @return
     */
    @Override
    public Date getLastAccessTime(SessionKey key) {
        Serializable lastAccessTime = enabledSharedSessionData ?
                RedisUtils.getValue((String) key.getSessionId()) :
                super.getLastAccessTime(key);
        if(lastAccessTime == null)
            throw new SessionTimeoutException();
        return (Date) lastAccessTime;
    }

    /**
     * 当主动调用{@link Subject#logout()}时，相应会调用该方法来停止session。
     * 因此，如果共享了session，也需要即时清除共享session。
     * {@link org.apache.shiro.session.mgt.AbstractNativeSessionManager#stop(SessionKey)}
     * @param key
     * @throws InvalidSessionException
     */
    @Override
    public void stop(SessionKey key) throws InvalidSessionException {
        super.stop(key);
        if(enabledSharedSessionData)
            RedisUtils.delete((String) key.getSessionId());
    }

    /**
     * 通知session manager那些attribute key对应的数据需要共享。
     * @param key
     */
    public void registerSharedAttributeKey(String key){
        if(!enabledSharedSessionData)
            throw new IllegalArgumentException("不允许共享session数据");
        if(sharedSessionDataKeys == null)
            sharedSessionDataKeys = new HashSet<String>();
        sharedSessionDataKeys.add(key);
    }

    public void setEnabledSharedSessionData(boolean enabledSharedSessionData) {
        this.enabledSharedSessionData = enabledSharedSessionData;
    }

    public Set<String> getSharedSessionDataKeys() {
        if(!enabledSharedSessionData)
            throw new IllegalArgumentException("共享session数据未开启");
        return sharedSessionDataKeys;
    }
}
