package com.shiro.demo.common.shiro;

import com.shiro.demo.common.constant.RedisConstant;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * @Description 自定义SessionId生成器
 * @Author Snowman1024
 * @Date 2019/12/27 17:33
 * @Version 1.0
 **/
public class ShiroSessionIdGenerator implements SessionIdGenerator {

    /**
     * 实现SessionId生成
     *
     * @param session
     * @return
     */
    @Override
    public Serializable generateId(Session session) {
        Serializable sessionId = new JavaUuidSessionIdGenerator().generateId(session);
        return String.format(RedisConstant.REDIS_PREFIX_LOGIN, sessionId);
    }
}
