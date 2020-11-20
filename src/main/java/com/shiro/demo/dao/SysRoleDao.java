package com.shiro.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiro.demo.entity.SysRoleEntity;

import java.util.List;

/**
 * @Description 角色DAO
 * @Author Snowman1024
 * @CreateTime 2019/12/27 10:17
 */
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

    /**
     * 通过用户ID查询角色集合
     *
     * @CreateTime 2019/6/18 18:01
     * @Param userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<SysRoleEntity> selectSysRoleByUserId(Long userId);

}
