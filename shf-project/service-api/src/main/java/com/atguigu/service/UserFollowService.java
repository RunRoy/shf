package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;

/**
 * @author lystart
 * @create 2023-05-08 19:18
 */
public interface UserFollowService extends BaseService<UserFollow> {

    void follow(Long userId, Long houseId);
}
