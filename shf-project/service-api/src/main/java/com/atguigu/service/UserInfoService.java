package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserInfo;

/**
 * @author lystart
 * @create 2023-05-08 16:17
 */
public interface UserInfoService extends BaseService<UserInfo> {

    UserInfo getByPhone(String phone);
}
