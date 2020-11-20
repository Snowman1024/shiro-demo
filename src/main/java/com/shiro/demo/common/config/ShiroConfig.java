package com.shiro.demo.common.config;

import com.shiro.demo.common.shiro.ShiroRealm;
import com.shiro.demo.common.shiro.ShiroSessionIdGenerator;
import com.shiro.demo.common.shiro.ShiroSessionManage;
import com.shiro.demo.common.util.SHA256Util;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description Shiro配置类
 * @Author Snowman1024
 * @Date 2019/12/30 10:28
 * @Version 1.0
 **/
@Configuration
public class ShiroConfig {

    private final String CACHE_KEY = "shiro:cache:";
    private final String SESSION_KEY = "shiro:session:";
    private final int EXPIRE = 1800;

    //Redis配置

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;


    /**
     * Shiro基础配置
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 注意过滤器配置顺序不能颠倒
        // 配置过滤:不会被拦截的链接
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/userLogin/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        shiroFilterFactoryBean.setLoginUrl("/userLogin/unauth");

        return shiroFilterFactoryBean;
    }

    /**
     * 开启Shiro-aop注解支持
     * 使用代理方式所以需要开启代码支持
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * 安全管理器
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //自定义Ssession管理
        securityManager.setSessionManager(sessionManager());
        //自定义Cache实现
        securityManager.setCacheManager(cacheManager());
        //自定义Realm验证
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }


    /**
     * 配置Session管理器
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManage shiroSessionManage = new ShiroSessionManage();
        shiroSessionManage.setSessionDAO(redisSessionDAO());
        return shiroSessionManage;
    }

    /**
     * 配置RedisSessionDAO
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        redisSessionDAO.setExpire(EXPIRE);
        return redisSessionDAO;
    }


    /**
     * 配置Redis管理器
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * SessionID生成器
     *
     * @return
     */
    @Bean
    public ShiroSessionIdGenerator sessionIdGenerator() {
        return new ShiroSessionIdGenerator();
    }

    /**
     * 配置Cache管理器
     * 用于往Redis存储权限和角色标识
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setKeyPrefix(CACHE_KEY);
        //配置缓存的话要求放在session里面的实体类必须有个id标识
        redisCacheManager.setPrincipalIdFieldName("userId");
        return redisCacheManager;
    }

    /**
     * 身份验证器
     *
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /**
     * 凭证匹配器
     * 将密码校验交给Shiro的SimpleAuthenticationInfo进行处理,在这里做匹配配置
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用SHA256算法;
        hashedCredentialsMatcher.setHashAlgorithmName(SHA256Util.HASH_ALGORITHM_NAME);
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(SHA256Util.HASH_ITERATIONS);
        return hashedCredentialsMatcher;
    }
}
