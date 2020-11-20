package com.shiro.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shiro.demo.dao.SysMenuDao;
import com.shiro.demo.entity.SysMenuEntity;
import com.shiro.demo.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 权限业务实现
 * @CreateTime 2019/6/14 15:57
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {


    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * 根据角色查询用户权限
     *
     * @CreateTime 2019/6/19 10:14
     * @Param roleId 角色ID
     * @Return List<SysMenuEntity> 权限集合
     */
    @Override
    public List<SysMenuEntity> selectSysMenuByRoleId(Long roleId) {
        return sysMenuDao.selectSysMenuByRoleId(roleId);
    }

    @Override
    public List<SysMenuEntity> list() {
        return sysMenuDao.selectList(new QueryWrapper<>());
    }
}