package com.shiro.demo.common.util;

import com.shiro.demo.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisSessionDAO;

import java.util.Collection;
import java.util.Objects;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2019/12/27 17:15
 * @Version 1.0
 **/
public class ShiroUtils {

    private ShiroUtils() {
    }

    private static RedisSessionDAO redisSessionDAO = SpringUtil.getBean(RedisSessionDAO.class);

    /**
     * 获取当前用户Session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 用户登出
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    public static SysUserEntity getUserInfo() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 删除用户缓存信息
     *
     * @param username
     * @param isRemoveSession
     */
    public static void deleteCache(String username, boolean isRemoveSession) {
        //从缓存中获取Session
        Session session = null;
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        SysUserEntity sysUserEntity;
        Object attribute = null;
        for (Session session1nfo : sessions) {
            //遍历Session,找到该用户名称对应的Session
            attribute = session1nfo.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (null == attribute) {
                continue;
            }
            sysUserEntity = (SysUserEntity) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if (null == sysUserEntity) {
                continue;
            }
            if (Objects.equals(sysUserEntity.getUsername(), username)) {
                session = session1nfo;
                break;
            }
        }

        if (null == session || null == attribute) {
            return;
        }
        //删除session
        if (isRemoveSession) {
            redisSessionDAO.delete(session);
        }
        //删除Cache，在访问受限接口时会重新授权
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Authenticator authc = securityManager.getAuthenticator();

        ((LogoutAware) authc).onLogout((SimplePrincipalCollection) attribute);
    }
}
