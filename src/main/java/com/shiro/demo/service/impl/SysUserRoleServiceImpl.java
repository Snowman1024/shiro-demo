package com.shiro.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.demo.dao.SysUserRoleDao;
import com.shiro.demo.entity.SysUserRoleEntity;
import com.shiro.demo.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @Description 用户与角色业务实现
 * @CreateTime 2019/6/14 15:57
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

}