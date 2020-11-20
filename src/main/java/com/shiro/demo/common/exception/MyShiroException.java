package com.shiro.demo.common.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2019/12/27 16:52
 * @Version 1.0
 **/
@ControllerAdvice
public class MyShiroException {


    /**
     * 处理Shiro权限拦截异常
     * 如果返回JSON数据格式请加上 @ResponseBody注解
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public Map<String, Object> defaultExceptionHandler() {
        Map<String, Object> map = new HashMap<>();
        map.put("403", "权限不足");
        return map;
    }
}
