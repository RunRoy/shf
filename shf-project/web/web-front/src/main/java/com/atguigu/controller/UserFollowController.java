package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
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


    //取消关注
    //userFollow/auth/cancelFollow/'+id).then(response => {
    @RequestMapping("/auth/cancelFollow/{id}")
    public  Result cancelFollow(@PathVariable Long id){
        userFollowService.delete(id);
        return Result.ok();
    }


    //我的关注(购物车)
    //axios.get('/userFollow/auth/list/'+pageNum+'/'+this.page.pageSize).then(function (response) {
    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, HttpServletRequest request) {

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        if (userInfo != null) {
            Long userId = userInfo.getId();
            PageInfo<UserFollowVo> pageInfo = userFollowService.findListPage(pageNum, pageSize, userId);
            return Result.ok(pageInfo);
        }
        return Result.build(null, ResultCodeEnum.LOGIN_AUTH);
    }


    /**
     * 关注房源
     */
    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId, HttpServletRequest request) {
        //获取Session域中用户
        UserInfo user = (UserInfo) request.getSession().getAttribute("USER");
        if (user != null) {
            Long userId = user.getId();
            //调用UserFollowService中关注房源的方法
            userFollowService.follow(userId, houseId);
            return Result.ok();
        }
        // 未登入跳转登入页面
        return Result.build(null, ResultCodeEnum.LOGIN_AUTH);
    }

}
