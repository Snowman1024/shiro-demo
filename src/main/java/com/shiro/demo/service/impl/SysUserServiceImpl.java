package com.shiro.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.demo.dao.SysUserDao;
import com.shiro.demo.entity.SysUserEntity;
import com.shiro.demo.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @Description 系统用户业务实现
 * @CreateTime 2019/6/14 15:57
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    /**
     * 根据用户名查询实体
     *
     * @CreateTime 2019/6/14 16:30
     * @Param username 用户名
     * @Return SysUserEntity 用户实体
     */
    @Override
    public SysUserEntity selectUserByName(String username) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserEntity::getUsername, username);
        return this.baseMapper.selectOne(queryWrapper);
    }
}