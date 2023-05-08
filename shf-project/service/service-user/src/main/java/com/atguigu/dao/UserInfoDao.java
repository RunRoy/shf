package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.UserInfo;

/**
 * @author lystart
 * @create 2023-05-08 16:31
 */
public interface UserInfoDao extends BaseDao<UserInfo> {
    UserInfo getByPhone(String phone);

}
