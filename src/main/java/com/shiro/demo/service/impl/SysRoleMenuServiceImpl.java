package com.shiro.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.demo.dao.SysRoleMenuDao;
import com.shiro.demo.entity.SysRoleMenuEntity;
import com.shiro.demo.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * @Description 角色与权限业务实现
 * @CreateTime 2019/6/14 15:57
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

}