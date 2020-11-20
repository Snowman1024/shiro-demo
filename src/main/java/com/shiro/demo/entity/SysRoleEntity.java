package com.shiro.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


/**
 * @Description 角色实体
 * @Author Snowman1024
 * @Date 2019/12/27 10:17
 * @Version 1.0
 **/
@Data
@TableName("sys_role")
public class SysRoleEntity implements Serializable {
    private static final long serialVersionUID = 6689077819685457508L;
    /**
     * 角色ID
     */
    @TableId
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
}
