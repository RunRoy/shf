package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import com.github.pagehelper.StringUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-05-08 11:46
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    UserInfoService userInfoService;


    /**
     *  退出登入
     */
    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request){
        request.removeAttribute("USER");
        return Result.ok();
    }


    /**
     * 登入
     */
    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo,HttpServletRequest request) {
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        // 校验参数
        if (phone.isEmpty() || password.isEmpty()) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (userInfo == null) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }

        // 校验密码
        if (!MD5.encrypt(password).equals(userInfo.getPassword())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
        request.getSession().setAttribute("USER", userInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("nickName", userInfo.getNickName());
        return Result.ok(map);
    }


    /**
     * 注册：注册的本质实际上往数据库里面插入一条数据
     * 思路：
     * ① 获取数据
     * ② 判断前端传递过来的数据是否有值
     * ③ 校验验证码
     * ④ 保存到数据库
     *
     * @return
     */
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest request) {
        String phone = registerVo.getPhone();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickName();

        // 校验参数
        if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(code)
                || StringUtil.isEmpty(password) || StringUtil.isEmpty(nickName)) {
        }
        // 验证码
        String currentCode = (String) request.getSession().getAttribute("CODE");
        if (!code.equals(currentCode)) {
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }
        // 判断是否已经注册
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (userInfo != null) {
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        // 完成进行注册
        userInfo = new UserInfo();
        userInfo.setPhone(phone);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setStatus(1);
        userInfo.setNickName(nickName);
        userInfoService.insert(userInfo);
        return Result.ok();
    }


    @RequestMapping("/sendCode/{moble}")
    public Result sendCode(@PathVariable String moble, HttpServletRequest request) {
        String code = "1111";
        request.getSession().setAttribute("CODE", code);
        return Result.ok(code);
    }

}
