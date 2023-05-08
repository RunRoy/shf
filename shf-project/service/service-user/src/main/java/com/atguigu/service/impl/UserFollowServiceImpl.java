package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.UserFollowDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lystart
 * @create 2023-05-08 19:19
 */
@Service(interfaceClass = UserFollowService.class)
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
   protected UserFollowDao userFollowDao;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long userId, Long houseId) {
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(userId);
        userFollow.setHouseId(houseId);
        userFollowDao.insert(userFollow);
    }
}
