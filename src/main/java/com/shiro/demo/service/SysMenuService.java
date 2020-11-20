package com.shiro.demo.service;

import com.shiro.demo.entity.SysMenuEntity;

import java.util.List;

/**
 * @Description 权限业务接口
 * @CreateTime 2019/6/14 15:57
 */
public interface SysMenuService {


    /**
     * 根据角色查询用户权限
     *
     * @CreateTime 2019/6/19 10:14
     * @Param roleId 角色ID
     * @Return List<SysMenuEntity> 权限集合
     */
    List<SysMenuEntity> selectSysMenuByRoleId(Long roleId);

    public List<SysMenuEntity> list();

}

