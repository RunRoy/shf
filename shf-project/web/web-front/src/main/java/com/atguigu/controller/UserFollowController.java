package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserFollowService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lystart
 * @create 2023-05-08 19:26
 */

@RestController
@RequestMapping("/userFollow")
public class UserFollowController {
    @Reference
   protected UserFollowService userFollowService;

    /**
     * 关注房源
     */
    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId, HttpServletRequest request){
        //获取Session域中用户
        UserInfo user = (UserInfo)request.getSession().getAttribute("USER");
        if (user != null) {
        Long userId = user.getId();
        //调用UserFollowService中关注房源的方法
        userFollowService.follow(userId, houseId);
        return Result.ok();
        }
        // 未登入跳转登入页面
        return Result.build(null,ResultCodeEnum.LOGIN_AUTH);
    }

}
