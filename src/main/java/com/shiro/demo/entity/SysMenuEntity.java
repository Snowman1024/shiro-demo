package com.shiro.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 权限实体
 * @Author Snowman1024
 * @Date 2019/12/27 10:17
 * @Version 1.0
 **/
@Data
@TableName("sys_menu")
public class SysMenuEntity implements Serializable {

    private static final long serialVersionUID = 5475343426631917142L;
    /**
     * 权限ID
     */
    @TableId
    private Long menuId;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限标识
     */
    private String perms;

}
