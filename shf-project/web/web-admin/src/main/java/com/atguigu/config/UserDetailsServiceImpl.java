package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author lystart
 * @create 2023-05-10 18:05
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    protected AdminService adminService;

    @Reference
   protected PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getByUsername(username);
        if (admin == null) {
            // 数据库查不到没有该用户
            return null;
        }
        // 获取密码
        String password = admin.getPassword();
        //用户功能权限
        List<String> codeList = permissionService.findCodeListByAdminId(admin.getId());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String code : codeList) {
            if (StringUtils.isEmpty(code)) continue;
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(code);
            authorities.add(authority);
        }
        return new User(username,password,authorities);
    }
}
