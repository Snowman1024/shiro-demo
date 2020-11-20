package com.shiro.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 系统用户实体
 * @Author Snowman1024
 * @Date 2019/12/27 10:17
 * @Version 1.0
 **/
@Data
@TableName(value = "sys_user")
public class SysUserEntity implements Serializable {
    private static final long serialVersionUID = 3613007507520586725L;

    /**
     * 用户ID
     */
    @TableId
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 状态:NORMAL正常  PROHIBIT禁用
     */
    private String state;
}
