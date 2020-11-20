package com.shiro.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description 角色与权限关系实体
 * @Author Snowman1024
 * @Date 2019/12/27 10:17
 * @Version 1.0
 **/
@Data
@TableName("sys_role_menu")
public class SysRoleMenuEntity implements Serializable {
    private static final long serialVersionUID = 4004923439258608900L;
    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 权限ID
     */
    private Long menuId;
}
