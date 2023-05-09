package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

/**
 * @author lystart
 * @create 2023-05-08 19:18
 */
public interface UserFollowService extends BaseService<UserFollow> {

    void follow(Long userId, Long houseId);

    Boolean isFollow(Long userId, Long houseId);

    PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId);

    //取消关注
    Boolean cancelFollow (Long id);
}
