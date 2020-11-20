package com.shiro.demo.common.shiro;

import com.shiro.demo.common.util.ShiroUtils;
import com.shiro.demo.entity.SysMenuEntity;
import com.shiro.demo.entity.SysRoleEntity;
import com.shiro.demo.entity.SysUserEntity;
import com.shiro.demo.service.SysMenuService;
import com.shiro.demo.service.SysRoleService;
import com.shiro.demo.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2019/12/27 17:36
 * @Version 1.0
 **/
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;


    /**
     * 授权权限
     * 用户进行权限验证时候Shiro会去缓存中找,如果查不到数据,会执行这个方法去查权限,并放入缓存中
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户ID
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUserEntity sysUserEntity = (SysUserEntity) principalCollection.getPrimaryPrincipal();
        Long userId = sysUserEntity.getUserId();
        //这里可以进行授权和处理
        Set<String> roleSet = new HashSet<>();
        Set<String> permsSet = new HashSet<>();
        //查询角色和权限(这里根据业务自行查询)
        List<SysRoleEntity> sysRoleEntityList = sysRoleService.selectSysRoleByUserId(userId);
        for (SysRoleEntity sysRoleEntity : sysRoleEntityList) {
            roleSet.add(sysRoleEntity.getRoleName());
            List<SysMenuEntity> sysMenuEntityList = sysMenuService.selectSysMenuByRoleId(sysRoleEntity.getRoleId());
            for (SysMenuEntity sysMenuEntity : sysMenuEntityList) {
                permsSet.add(sysMenuEntity.getPerms());
            }
        }
        //将查到的权限和角色分别传入authorizationInfo中
        authorizationInfo.setStringPermissions(permsSet);
        authorizationInfo.setRoles(roleSet);
        return authorizationInfo;
    }

    /**
     * 身份认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户的输入的账号
        String username = (String) authenticationToken.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到进行验证
        //实际项目中,这里可以根据实际情况做缓存,如果不做,Shiro自己也是有时间间隔机制,2分钟内不会重复执行该方法
        SysUserEntity sysUserEntity = sysUserService.selectUserByName(username);
        //判断账号是否存在
        if (null == sysUserEntity) {
            throw new AuthenticationException();
        }
        //判断账号是否被冻结
        if (null == sysUserEntity.getState() || sysUserEntity.getState().equals("PROHIBIT")) {
            throw new LockedAccountException();
        }
        //进行验证
        SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(
                //用户名
                sysUserEntity,
                //密码
                sysUserEntity.getPassword(),
                //设置盐值
                ByteSource.Util.bytes(sysUserEntity.getSalt()),
                getName());

        //验证成功开始踢人(清除缓存和Session)
        ShiroUtils.deleteCache(username, true);
        return authorizationInfo;
    }
}
