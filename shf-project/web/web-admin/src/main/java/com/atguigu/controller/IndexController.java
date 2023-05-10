package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author lystart
 * @create 2023-04-26 18:27
 */
@Controller
public class IndexController {

    private final static String PAGE_INDEX = "frame/index";
    private final static String PAGE_MAIN = "frame/main";
    private final static String PAGE_LOGIN = "frame/login";
    private final static String PAGE_AUTH     = "frame/auth";

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;


    // 跳转到错误页面
    @RequestMapping("/auth")
    public String auth(){
        return PAGE_AUTH;
    }


    /**
     * 跳转登入界面
     */
    @RequestMapping("/login")
    public  String login(){
        return  PAGE_LOGIN;
    }


    /**
     * 框架首页
     */

    @RequestMapping("/")
    public String index(ModelMap modelMap){
        // 模拟一个用户登录 admin
        // 假设登录进来的用户是admin
//        Long adminId = 2L;
        // 从spring security里面获取当前登录的用户
        // SecurityContextHolder.getContext() : 获取spring security容器
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
      Admin admin = adminService.getByUsername(user.getUsername());
        // 通过adminId，查询admin用户
//        Admin admin = adminService.getById(adminId);
        List<Permission> permissionList =  permissionService.findMenuPermissionByAdminId(admin.getId());
        modelMap.addAttribute("permissionList",permissionList);
        modelMap.addAttribute("admin",admin);
        return PAGE_INDEX;
    }

    @RequestMapping("/main")
    public String main() {
        return PAGE_MAIN;
    }
}
