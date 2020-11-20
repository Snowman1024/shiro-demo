package com.shiro.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiro.demo.entity.SysMenuEntity;

import java.util.List;

/**
 * @Description 权限DAO
 * @Author Snowman1024
 * @CreateTime 2019/12/27 10:17
 */
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

    /**
     * 根据角色查询用户权限
     *
     * @CreateTime 2019/6/19 10:14
     * @Param roleId 角色ID
     * @Return List<SysMenuEntity> 权限集合
     */
    List<SysMenuEntity> selectSysMenuByRoleId(Long roleId);

}
